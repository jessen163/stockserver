package com.ryd.demo.server.service;

import com.ryd.demo.server.bean.StStock;

/**
 * <p>标题:股票信息获取ServiceI</p>
 * <p>描述:股票信息获取ServiceI</p>
 * 包名：com.ryd.stockanalysis.service
 * 创建人：songby
 * 创建时间：2016/4/6 17:03
 */
public interface StockGetInfoFromApiI {


    /**
     * 获取股票信息
     * @param st 交易所 sh 上海 sz 深圳
     * @param stockCode 股票编码
     * @return
     */
    public StStock getStStockInfo(String st, String stockCode);
}
