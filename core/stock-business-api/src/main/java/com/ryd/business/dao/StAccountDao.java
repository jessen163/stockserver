package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.model.StAccount;

import java.util.List;

/**
 * <p>标题:帐户DAO</p>
 * <p>描述:帐户DAO</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/20 9:57
 */
public interface StAccountDao  extends BaseDao<StAccount> {


    /**
     * 根据帐号密码查询帐户
     * @param accountNum
     * @param password
     * @return
     */
    public StAccount getStAccountByLogin(String accountNum,String password);

    /**
     * 按帐号查询
     * @param accountNum
     * @return
     */
    public StAccount getStAccountByAccountNum(String accountNum);

}
