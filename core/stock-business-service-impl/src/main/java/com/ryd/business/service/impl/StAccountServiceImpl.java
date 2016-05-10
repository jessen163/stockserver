package com.ryd.business.service.impl;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.ArithUtil;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.dao.StAccountDao;
import com.ryd.business.dto.SearchAccountDTO;
import com.ryd.business.exception.AccountBusinessException;
import com.ryd.business.model.StAccount;
import com.ryd.business.service.StAccountService;
import com.ryd.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题:账户业务实现类--基础业务类</p>
 * <p>描述:账户业务实现类--基础业务类</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：songby
 * 创建时间：2016/4/20 10:11
 */
@Service
public class StAccountServiceImpl implements StAccountService {

    @Autowired
    private StAccountDao stAccountDao;

    @Autowired
    private ICacheService iCacheService;

    @Override
    public boolean addStAccount(StAccount account) {
        account.setId(UUIDUtils.uuidTrimLine());
        account.setCreatetime(System.currentTimeMillis());
        account.setStatus(ApplicationConstants.MODEL_ATRRIBUTE_NORMAL);
        return stAccountDao.add(account) > 0;
    }

    @Override
    public boolean updateStAccount(StAccount account) {

        return stAccountDao.update(account) > 0;
    }

    @Override
    public boolean updateStAccountMoneyAdd(String accountId, BigDecimal money) throws Exception{
        boolean rs = false;

        StAccount stAccount = stAccountDao.getTById(accountId);
        if(stAccount == null){
            rs = false;
            if(!rs){
                throw new AccountBusinessException("帐户不存在，增加资产失败");
            }
            return rs;
        }
        //如果是马甲帐户，帐户资金不做处理
        if(stAccount.getAccountType() == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL){
            return true;
        }
        //原有帐户可用资产
        BigDecimal useMoney = stAccount.getUseMoney();
        stAccount.setUseMoney(ArithUtil.add(useMoney, money));
        rs = stAccountDao.update(stAccount) > 0;
        if(!rs){
            throw new AccountBusinessException("帐户增加资产失败");
        }
        return rs;
    }

    @Override
    public boolean updateStAccountMoneyReduce(String accountId, BigDecimal money) throws Exception{
        boolean rs = false;
        StAccount stAccount = stAccountDao.getTById(accountId);

        if(stAccount == null){
            rs = false;
            if(!rs){
                throw new AccountBusinessException("帐户不存在，增加资产失败");
            }
            return rs;
        }

        //如果是马甲帐户，帐户资金不做处理
        if(stAccount.getAccountType() == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL){
            return true;
        }

        //原有帐户可用资产
        BigDecimal useMoney = stAccount.getUseMoney();
        stAccount.setUseMoney(ArithUtil.add(useMoney, money));
        //交易减少费用
        if (ArithUtil.compare(useMoney,money)>=0) {
            stAccount.setUseMoney(ArithUtil.subtract(useMoney, money));
            rs = stAccountDao.update(stAccount) > 0;
        } else {
            rs = false;
            if(!rs){
                throw new AccountBusinessException("帐户资金不足，减少资产失败");
            }
            return rs;
        }
        if(!rs){
            throw new AccountBusinessException("帐户减少资产失败");
        }
        return rs;
    }

    @Override
    public StAccount getStAccountById(String accountId){
       return stAccountDao.getTById(accountId);
    }

    @Override
    public Integer getCount(SearchAccountDTO searchAccountDTO) {
        StAccount account = new StAccount();
        account.setRealName(searchAccountDTO.getRealName());
        account.setAccountName(searchAccountDTO.getAccountName());
        account.setAccountNum(searchAccountDTO.getAccountNum());
        account.setAccountLevel(searchAccountDTO.getAccountLevel());
        account.setAccountType(searchAccountDTO.getAccountType());
        account.setStatus(searchAccountDTO.getStatus());

        Long startTime = searchAccountDTO.getStartDate()==null?null:searchAccountDTO.getStartDate().getTime();
        Long endTime = searchAccountDTO.getEndDate()==null?null:searchAccountDTO.getEndDate().getTime();
        return stAccountDao.getCount(account,startTime,endTime);
    }

    @Override
    public StAccount findStAccount(String accountNum, String password) {
        return stAccountDao.getStAccountByLogin(accountNum, password);
    }

    @Override
    public StAccount findStAccountByAccountNum(String accountNum){
       return stAccountDao.getStAccountByAccountNum(accountNum);
    }

    @Override
    public List<StAccount> findStAccountList(SearchAccountDTO searchAccountDTO) {
        StAccount account = new StAccount();
        account.setRealName(searchAccountDTO.getRealName());
        account.setAccountName(searchAccountDTO.getAccountName());
        account.setAccountNum(searchAccountDTO.getAccountNum());
        account.setAccountLevel(searchAccountDTO.getAccountLevel());
        account.setAccountType(searchAccountDTO.getAccountType());
        account.setStatus(searchAccountDTO.getStatus());

        Long startTime = searchAccountDTO.getStartDate()==null?null:searchAccountDTO.getStartDate().getTime();
        Long endTime = searchAccountDTO.getEndDate()==null?null:searchAccountDTO.getEndDate().getTime();
        return stAccountDao.getTList(account,startTime,endTime,searchAccountDTO.getLimit(),searchAccountDTO.getOffset());
    }

    @Override
    public List<StAccount> findStAccontByAccountType(Short accountType) {
        List<StAccount> accounts = null;

        if(accountType == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL){
            Object virtualAccounts = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_ACCOUNT_VIRTUALLIST, null);
            if(virtualAccounts != null){
                accounts = (List<StAccount>) virtualAccounts;
                return accounts;
            }
        }
        StAccount account = new StAccount();
        account.setAccountType(accountType);
        account.setStatus(ApplicationConstants.MODEL_ATRRIBUTE_NORMAL);

        accounts = stAccountDao.getTList(account, null, null, Integer.MAX_VALUE, 0);

        if(accountType == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL){
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_ACCOUNT_VIRTUALLIST, accounts);
        }

        return accounts;
    }

    @Override
    public Map<String, String> findAllAccountNum() {
        Map<String, String> rsMap = null;
        Object accountNumObj = iCacheService.getStringByKey(CacheConstant.CACHEKEY_ACCOUNT_ACCOUNTNUM_MAP, null);
        if (accountNumObj!=null) {
            rsMap = (Map<String, String>)accountNumObj;
            return rsMap;
        }

        List<StAccount> accList = stAccountDao.getAccountNumList();
        rsMap = new HashMap<String, String>();
        for(StAccount st:accList){
            rsMap.put(st.getId(), st.getAccountNum());
            iCacheService.setStringByKey(CacheConstant.CACHEKEY_ACCOUNT_ACCOUNTNUM_MAP, st.getId(), st.getAccountNum(), Integer.MAX_VALUE);
        }
        return rsMap;
    }

    @Override
    public String findAccountNumByAccountId(String accountId) {
        String accNum = iCacheService.getStringByKey(CacheConstant.CACHEKEY_ACCOUNT_ACCOUNTNUM_MAP, accountId, null);
        if(accNum == null){
            this.findAllAccountNum();
        }
        accNum = iCacheService.getStringByKey(CacheConstant.CACHEKEY_ACCOUNT_ACCOUNTNUM_MAP,accountId,null);
        return accNum;
    }

    @Override
    public boolean deleteStAccountById(String accountId) {
        return stAccountDao.deleteTById(accountId) > 0;
    }
}
