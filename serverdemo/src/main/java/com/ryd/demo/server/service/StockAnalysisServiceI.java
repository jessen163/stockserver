package com.ryd.demo.server.service;

import com.ryd.demo.server.bean.StAccount;
import com.ryd.demo.server.bean.StQuote;
import com.ryd.demo.server.bean.StStock;
import com.ryd.demo.server.bean.StTradeQueue;

import java.util.List;

/**
 * <p>标题:股票分析处理ServiceI</p>
 * <p>描述:股票分析处理ServiceI</p>
 * 包名：com.ryd.stockanalysis.service
 * 创建人：songby
 * 创建时间：2016/3/29 10:02
 */
public interface StockAnalysisServiceI {

    /**
     * 报价--委托买卖股票
     * @param stQuote
     * @return
     */
    public boolean quotePrice(StQuote stQuote);

    /**
     * 交易
     * @param buyQuote
     * @param sellQuote
     * @param sts
     */
    public void dealTrading(StTradeQueue stTradeQueueMap, StQuote buyQuote, StQuote sellQuote, StStock sts);
    /**
     * 结算结果
     * @return
     */
    public boolean settleResult();

    /**
     * 撤单
     * @param stQuote
     * @return
     */
    public boolean cancelStQuote(StQuote stQuote);

    /**
     * 删除股票队列和我的报价列表中的报价
     * @param stQuote
     * @return
     */
    public boolean removeStQuote(StQuote stQuote);

    /**
     * 模拟报价
     * @param stAccountList
     * @param stStock
     * @return
     */
    public boolean quotePriceBySimulation(List<StAccount> stAccountList, StStock stStock);

    /**
     * 马甲盘模拟报价
     * @return
     */
    public void quotePriceBySimulation();


    /**
     * 同步股票实时价格信息
     * @return
     */
    public void updateSyncStockInfo();
}
