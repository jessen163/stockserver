package com.ryd.business.dao;

import com.ryd.business.model.StAccount;

import java.util.List;

/**
 * <p>标题:帐户DAO</p>
 * <p>描述:帐户DAO</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/20 9:57
 */
public interface StAccountDao {

    /**
     * 添加帐户
     * @param account
     * @return
     */
    public int add(StAccount account);

    /**
     * 修改帐户信息
     * @param account
     * @return
     */
    public int update(StAccount account);

    /**
     * 根据ID查询帐户
     * @param accountId
     * @return
     */
    public StAccount getStAccountById(String accountId);

    /**
     * 根据条件查询帐户
     * @param stAccount
     * @param limit
     * @param offset
     * @return
     */
    public List<StAccount> getStAccountList(StAccount stAccount, int limit,int offset);

    /**
     * 根据用户名密码查询帐户
     * @param userName
     * @param password
     * @return
     */
    public StAccount getStAccountByLogin(String userName,String password);

    /**
     * 删除帐户
     * @param accountId
     * @return
     */
    public int deleteStAccountById(String accountId);
}
