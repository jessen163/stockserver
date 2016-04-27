package com.ryd.basecommon.util;

/**
 * 系统全局常量
 * Created by Administrator on 2016/4/19.
 */
public class ApplicationConstants {
    // 股票服务器-股票基本信息url 参数为字符串，碰到多只股票使用“,”分隔 如：sh600094,sh600095
    public static final String STOCK_SERVER_STOCKBASE_URL = "http://hq.sinajs.cn/list=";
    // 股票服务器-股票成交量信息url 参数同上
    public static final String STOCK_SERVER_STOCKTURNOVER_URL = "http://hq.sinajs.cn/rn=sdrah&list=";

//    http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=40&sort=turnoverratio&asc=0&node=sz_a&symbol=&_s_r_a=sort

    /** 消息接口ID 心跳 id=1 **/
    public final static int NETTYMESSAGE_ID_HEARTBEAT = 1;
    /** 消息接口ID 股票信息 id=5 **/
    public final static int NETTYMESSAGE_ID_STOCKINFO = 5;
    /** 消息接口ID 股票价格详情 id=6 **/
    public final static int NETTYMESSAGE_ID_STOCKPRICEDETAIL = 6;
    /** 消息接口ID 登陆 id=9 **/
    public final static int NETTYMESSAGE_ID_LOGIN = 9;
    /** 消息接口ID 报价（限价买入、限价卖出、市价买入、市价卖出） id=7 **/
    public final static int NETTYMESSAGE_ID_QUOTE = 7;
    /** 消息接口ID 撤单 id=8 **/
    public final static int NETTYMESSAGE_ID_WITHDRAWORDER = 8;
    /** 消息接口ID 我的资金 id=10 **/
    public final static int NETTYMESSAGE_ID_MYCAPITAL = 10;
    /** 消息接口ID 我的股份 id=11 **/
    public final static int NETTYMESSAGE_ID_MYPOSITION = 11;
    /** 消息接口ID 我的报价（当日委托/历史委托） id=12 **/
    public final static int NETTYMESSAGE_ID_MYQUOTELIST = 12;
    /** 消息接口ID 我的成交记录（当日成交/历史成交） id=13 **/
    public final static int NETTYMESSAGE_ID_MYTRADERECORD = 13;
    /** 消息接口ID 我的资金流水 id=14 **/
    public final static int NETTYMESSAGE_ID_MYMONEYJOURNAL = 14;


    /** 消息接口状态-status 请求 status=0 **/
    public final static int NETTYMESSAGE_STATUS_REQUEST = 0;

    /** 消息接口状态-status 请求 status=1 **/
    public final static int NETTYMESSAGE_STATUS_RESPONSE_SUCCESS = 1;
    /** 消息接口状态-status 内容为空 status=2 **/
    public final static int NETTYMESSAGE_STATUS_RESPONSE_NO_CONTENT = 2;
    /** 消息接口状态-status 服务器异常 status=3 **/
    public final static int NETTYMESSAGE_STATUS_RESPONSE_SERVER_ERROR = 3;

    // 1、买，2、卖
    public static final Short STOCK_QUOTETYPE_BUY = 1;
    public static final Short STOCK_QUOTETYPE_SELL = 2;

    //1、增加，2、减少
    public static final Integer STOCK_STQUOTE_ACCOUNTMONEY_TYPE_ADD=1;
    public static final Integer STOCK_STQUOTE_ACCOUNTMONEY_TYPE_REDUSE=2;

    //报价状态 1.托管  2.交易中 3.已成交 4.撤单  5. 结算未成交 6.结算部分股票成交
    public static final Short STOCK_STQUOTE_STATUS_TRUSTEE=1;
    public static final Short STOCK_STQUOTE_STATUS_DEALING=2;
    public static final Short STOCK_STQUOTE_STATUS_ALREADYDEAL=3;
    public static final Short STOCK_STQUOTE_STATUS_REVOKE=4;
    public static final Short STOCK_STQUOTE_STATUS_NOTDEAL=5;
    public static final Short STOCK_STQUOTE_STATUS_PARTDEAL=6;

    //持仓状态 1、托管 2、成交 3、过期
    public static final Integer STOCK_STPOSITION_STATUS_TRUSTEE=1;
    public static final Integer STOCK_STPOSITION_STATUS_DEAL=2;
    public static final Integer STOCK_STPOSITION_STATUS_OUTDATE=3;

    //时间判断结果 1.可以交易和报价 2.只允许报价 3.不允许报价，不允许交易
    public static final Integer STQUOTE_TRADE_TIMECOMPARE_1 = 1;
    public static final Integer STQUOTE_TRADE_TIMECOMPARE_2 = 2;
    public static final Integer STQUOTE_TRADE_TIMECOMPARE_3 = 3;

    //1.节假日 2.特殊工作日
    public static final Short DATE_SCHEDULE_FESTIVALDAY = 1;
    public static final Short DATE_SCHEDULE_SEPCIAL_WORKDAY = 2;

    // 交易主线程是否停止
    public static volatile boolean isMainThreadStop = false;

    // 交易子线程是否停止
    public static volatile boolean isSubThreadStop = false;

    // 交易子线程是否等待
    public static volatile boolean isSubThreadWait = false;
}
