package com.ryd.business.dao.impl;

import com.ryd.business.dao.StAccountDao;
import com.ryd.business.model.StAccount;
import com.ryd.business.mybatis.StAccountMapper;
import org.apache.commons.lang.StringUtils;
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
    public int add(StAccount obj) {

       return stAccountMapper.insert(obj);
    }

    @Override
    public int update(StAccount obj){
        if(StringUtils.isBlank(obj.getId())){
            return -1;
        }
        return stAccountMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public StAccount getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StAccount> getTList(StAccount obj, Long startTime,Long endTime, int limit,int offset) {

        if(obj==null){
            obj = new StAccount();
        }
        return stAccountMapper.selectListByKeySelective(obj, startTime, endTime, limit, offset);
    }

    @Override
    public StAccount getStAccountByLogin(String accountNum,String password){

        StAccount st = stAccountMapper.selectByNamePassword(accountNum, password);
        return st;
    }

    @Override
    public StAccount getStAccountByAccountNum(String accountNum){
        return stAccountMapper.selectByNameKey(accountNum);
    }

    @Override
    public int deleteTById(String id){
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stAccountMapper.deleteByPrimaryKey(id);
    }
}
