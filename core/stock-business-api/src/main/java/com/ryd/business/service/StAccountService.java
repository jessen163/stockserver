package com.ryd.business.service;

import com.ryd.business.dto.SearchAccountDTO;
import com.ryd.business.model.StAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>标题:帐户Service--基础业务</p>
 * <p>描述:帐户Service</p>
 * 包名：com.ryd.business.service
 * 创建人：songby
 * 创建时间：2016/4/20 10:11
 */
public interface StAccountService {

    /**
     * 注册添加
     * @param account
     * @return
     */
    public boolean addStAccount(StAccount account);

    /**
     * 修改用户
     * @param account
     * @return
     */
    public boolean updateStAccount(StAccount account);


    /**
     * 可用资产增加或减少
     * @param accountId
     * @param money
     * @param type 1 增加 2减少
     * @return
     * @throws Exception
     */
    public boolean updateUseMoneyAddReduce(String accountId, BigDecimal money, int type) throws Exception;
    /**
     * 增加资产
     * @param accountId
     * @param money
     * @return
     */
    public boolean updateStAccountMoneyAdd(String accountId, BigDecimal money) throws Exception;

    /**
     * 减少资产
     * @param accountId
     * @param money
     * @return
     */
    public boolean updateStAccountMoneyReduce(String accountId, BigDecimal money) throws Exception;

    /**
     * 根据ID查询
     * @param accountId
     * @return
     */
    public StAccount getStAccountById(String accountId);

    /**
     * 查找数量
     * @param searchAccountDTO
     * @return
     */
    public Integer getCount(SearchAccountDTO searchAccountDTO);

    /**
     * 根据帐号密码查找
     * @param accountNum
     * @param password
     * @return
     */
    public StAccount findStAccount(String accountNum, String password);

    /**
     * 按帐号查找
     * @param accountNum
     * @return
     */
    public StAccount findStAccountByAccountNum(String accountNum);

    /**
     * 查询用户列表
     * @param searchAccountDTO
     * @return
     */
    public List<StAccount> findStAccountList(SearchAccountDTO searchAccountDTO);


    /**
     * 根据类型获取帐户
     * @param accountType
     * @return
     */
    public List<StAccount> findStAccontByAccountType(Short accountType);

    /**
     * 查询所有的帐号
     * @return
     */
    public Map<String, String> findAllAccountNum();

    /**
     * 根据Id查询帐号
     * @param accountId
     * @return
     */
    public String findAccountNumByAccountId(String accountId);
    /**
     * 删除用户
     * @param accountId
     * @return
     */
    public boolean deleteStAccountById(String accountId);
}
