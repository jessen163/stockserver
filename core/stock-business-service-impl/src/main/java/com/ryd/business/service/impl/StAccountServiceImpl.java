package com.ryd.business.service.impl;

import com.ryd.business.dao.StAccountDao;
import com.ryd.business.model.StAccount;
import com.ryd.business.service.StAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：songby
 * 创建时间：2016/4/20 10:11
 */
@Service
public class StAccountServiceImpl implements StAccountService {

    @Autowired
    public StAccountDao stAccountDao;

    @Override
    public boolean addStAccount(StAccount account) {
        return stAccountDao.add(account) > 0;
    }

    @Override
    public boolean updateStAccount(StAccount account) {
        return stAccountDao.update(account) > 0;
    }

    @Override
    public StAccount getStAccountById(String accountId){
        StAccount account = new StAccount();
        account.setId(accountId);
       return stAccountDao.getTById(account);
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
    public List<StAccount> findStAccountList(StAccount account, int pageIndex, int limit) {
        Integer offset = (pageIndex-1)*limit;
        return stAccountDao.getTList(account,limit,offset);
    }

    @Override
    public boolean deleteStAccountById(StAccount account) {
        return stAccountDao.deleteTById(account) > 0;
    }
}
