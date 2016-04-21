package com.ryd.business.dao.impl;

import com.ryd.business.dao.StAccountDao;
import com.ryd.business.model.StAccount;
import com.ryd.business.mybatis.StAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题:帐户DaoImpl</p>
 * <p>描述:帐户DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/20 9:58
 */
@Repository
public class StAccountDaoImpl implements StAccountDao {

    @Autowired
    public StAccountMapper stAccountMapper;

    @Override
    public int add(StAccount account) {

       return stAccountMapper.insert(account);
    }

    @Override
    public int update(StAccount account){
       return stAccountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public StAccount getStAccountById(String accountId) {
        return stAccountMapper.selectByPrimaryKey(accountId);
    }

    @Override
    public List<StAccount> getStAccountList(StAccount stAccount, int limit,int offset) {

        if(stAccount==null){
            stAccount = new StAccount();
        }
        return stAccountMapper.selectListByKeySelective(stAccount,limit,offset);
    }

    @Override
    public StAccount getStAccountByLogin(String userName,String password){

        return stAccountMapper.selectByNamePassword(userName,password);
    }

    @Override
    public int deleteStAccountById(String accountId){
        return stAccountMapper.deleteByPrimaryKey(accountId);
    }
}
