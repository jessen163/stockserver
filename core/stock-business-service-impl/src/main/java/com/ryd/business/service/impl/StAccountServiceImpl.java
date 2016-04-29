package com.ryd.business.service.impl;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.ArithUtil;
import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.dao.StAccountDao;
import com.ryd.business.dto.SearchAccountDTO;
import com.ryd.business.model.StAccount;
import com.ryd.business.service.StAccountService;
import com.ryd.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
    private ICacheService cacheService;

    @Override
    public boolean addStAccount(StAccount account) {
        account.setId(UUIDUtils.uuidTrimLine());
        account.setAccountType(ApplicationConstants.ACCOUNT_TYPE_REAL);
        account.setCreatetime(System.currentTimeMillis());
        account.setStatus(ApplicationConstants.MODEL_ATRRIBUTE_NORMAL);
        return stAccountDao.add(account) > 0;
    }

    @Override
    public boolean updateStAccount(StAccount account) {

        return stAccountDao.update(account) > 0;
    }

    @Override
    public boolean updateStAccountMoneyAdd(String accountId, BigDecimal money) {
        StAccount stAccount = stAccountDao.getTById(accountId);

        if(stAccount == null){
            return false;
        }
        //原有帐户可用资产
        BigDecimal useMoney = stAccount.getUseMoney();

        stAccount.setUseMoney(ArithUtil.add(useMoney, money));

        return stAccountDao.update(stAccount) > 0;
    }

    @Override
    public boolean updateStAccountMoneyReduce(String accountId, BigDecimal money) {
        StAccount stAccount = stAccountDao.getTById(accountId);

        if(stAccount == null){
            return false;
        }
        //原有帐户可用资产
        BigDecimal useMoney = stAccount.getUseMoney();

        stAccount.setUseMoney(ArithUtil.add(useMoney, money));

        //交易减少费用
        if (ArithUtil.compare(useMoney,money)>=0) {
            stAccount.setUseMoney(ArithUtil.subtract(useMoney, money));
        } else {
            return false;
        }

        return stAccountDao.update(stAccount) > 0;
    }

    @Override
    public StAccount getStAccountById(String accountId){
       return stAccountDao.getTById(accountId);
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
        account.setId(searchAccountDTO.getAccountId());

        Long startTime = searchAccountDTO.getStartDate().getTime();
        Long endTime = searchAccountDTO.getEndDate().getTime();
        return stAccountDao.getTList(account,startTime,endTime,searchAccountDTO.getLimit(),searchAccountDTO.getOffset());
    }

    @Override
    public boolean deleteStAccountById(String accountId) {
        return stAccountDao.deleteTById(accountId) > 0;
    }
}
