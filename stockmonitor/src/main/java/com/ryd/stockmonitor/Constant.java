package com.ryd.stockmonitor;

import com.ryd.demo.server.bean.StStock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>标题:常量</p>
 * <p>描述:常量</p>
 * 包名：com.ryd.stockanalysis.common
 * 创建人：songby
 * 创建时间：2016/3/28 15:57
 */
public class Constant {

    //1、买，2、卖
    public static Integer STOCK_STQUOTE_TYPE_BUY = 1;
    public static Integer STOCK_STQUOTE_TYPE_SELL = 2;

    //股票列表
    public static ConcurrentMap<String,String> stockMap = new ConcurrentHashMap<String,String>();
}
