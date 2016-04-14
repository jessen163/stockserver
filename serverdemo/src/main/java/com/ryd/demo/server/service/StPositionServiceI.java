package com.ryd.demo.server.service;

/**
 * <p>标题:持仓处理ServiceI</p>
 * <p>描述:持仓处理ServiceI</p>
 * 包名：com.ryd.stockanalysis.service
 * 创建人：songby
 * 创建时间：2016/3/30 13:34
 */
public interface StPositionServiceI {
    /**
     * 增加/减少仓位
     * @param accountId 增加仓位的帐户
     * @param stockId 对应的股票
     * @param amount 增加持仓数量
     * @param type 增加/减少状态
     * @return
     */
    public boolean operateStPosition(String accountId, String stockId, int amount, int type);


    /**
     * 计算持仓平均成本
     * @param accountId
     * @param stockId
     */
    public void calculateAvgPrice(String accountId, String stockId);
}
