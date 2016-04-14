package com.ryd.demo.server.common;

/**
 * <p>标题:常量</p>
 * <p>描述:常量</p>
 * 包名：com.ryd.stockanalysis.common
 * 创建人：songby
 * 创建时间：2016/3/28 15:57
 */
public class Constant {

    //1、买，2、卖
    public static Integer STOCK_STQUOTE_TYPE_BUY=1;
    public static Integer STOCK_STQUOTE_TYPE_SELL=2;

    //1、增加，2、减少
    public static Integer STOCK_STQUOTE_ACCOUNTMONEY_TYPE_ADD=1;
    public static Integer STOCK_STQUOTE_ACCOUNTMONEY_TYPE_REDUSE=2;

    //报价状态 1、托管 2、成交 3、过期
    public static Integer STOCK_STQUOTE_STATUS_TRUSTEE=1;
    public static Integer STOCK_STQUOTE_STATUS_DEAL=2;
    public static Integer STOCK_STQUOTE_STATUS_OUTDATE=3;

    //持仓状态 1、托管 2、成交 3、过期
    public static Integer STOCK_STPOSITION_STATUS_TRUSTEE=1;
    public static Integer STOCK_STPOSITION_STATUS_DEAL=2;
    public static Integer STOCK_STPOSITION_STATUS_OUTDATE=3;

    //时间判断结果 1.可以交易和报价 2.只允许报价 3.不允许报价，不允许交易
    public static Integer STQUOTE_TRADE_TIMECOMPARE_1 = 1;
    public static Integer STQUOTE_TRADE_TIMECOMPARE_2 = 2;
    public static Integer STQUOTE_TRADE_TIMECOMPARE_3 = 3;

    //1.节假日 2.特殊工作日
    public static Integer DATE_SCHEDULE_FESTIVALDAY = 1;
    public static Integer DATE_SCHEDULE_SEPCIAL_WORKDAY = 2;

    //佣金比例
    public static double STOCK_COMMINSSION_MONEY_PERCENT = 0.0003;

    //印花税比例
    public static double STOCK_STAMP_TAX_PERCENT = 0.001;

    //股票涨跌幅度
    public static double STOCK_UP_AND_DOWN_PERCENT = 0.1;

    //开盘时间
    public static String STOCK_OPEN_TIME = "9:30:00";

    //收盘时间
    public static String STOCK_CLOSE_TIME = "15:35:00";

    //休盘时间开始，结束
    public static String STOCK_REST_TIME_START = "11:30:00";
    public static String STOCK_REST_TIME_END = "13:00:00";


    //结算时间
    public static String STOCK_SETTLE_TIME = "14:16:00";

}
