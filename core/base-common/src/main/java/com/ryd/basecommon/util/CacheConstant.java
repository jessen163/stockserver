package com.ryd.basecommon.util;

/**
 * <p>标题:Redis key</p>
 * <p>描述:Redis key</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/23 11:11
 */
public class CacheConstant {



    // 缓存key-系统配置参数信息
    public static final String CACHEKEY_SYSTEM_CONFIG_MAP  = "systemConfig";

    // 缓存key-系统配置参数信息-股市开盘时间
    public static final String CACHEKEY_SYSTEM_CONFIG_OPEN_TIME  = "stockMarketOpenTime";

    // 缓存key-系统配置参数信息-股市休盘时间开始
    public static final String CACHEKEY_SYSTEM_CONFIG_REST_TIME_START  = "stockMarketRestTimeStart";

    // 缓存key-系统配置参数信息-股市休盘时间结束
    public static final String CACHEKEY_SYSTEM_CONFIG_REST_TIME_END  = "stockMarketRestTimeEnd";

    // 缓存key-系统配置参数信息-股市收盘时间
    public static final String CACHEKEY_SYSTEM_CONFIG_CLOSE_TIME  = "stockMarketCloseTime";

    // 缓存key-系统配置参数信息-佣金比例
    public static final String CACHEKEY_SYSTEM_COMMINSSION_PERCENT  = "stockCommissionPercent";

    // 缓存key-系统配置参数信息-印花税比例
    public static final String CACHEKEY_SYSTEM_CONFIG_TAX_PERCENT  = "stockTaxPercent";

    // 缓存key-系统配置参数信息-自动化交易时间间隔
    public static final String CACHEKEY_SYSTEM_CONFIG_AUTOTRADE_INTERVAL  = "autoTradeInterval";

    // 缓存key-系统配置参数信息-股票涨跌幅度
    public static final String CACHEKEY_SYSTEM_CONFIG_UPANDDOWN_PERCENT  = "stockUpAndDownPercent";

    // 缓存key-系统配置参数信息-股市结算时间
    public static final String CACHEKEY_SYSTEM_CONFIG_SETTLE_TIME  = "stockMarketSettleTime";

    // 缓存key-系统配置参数信息-佣金最小值
    public static final String CACHEKEY_SYSTEM_CONFIG_COMMISIONFEE_MIN  = "minCommisionFee";

    // 缓存key-系统配置参数信息-印花税最小值
    public static final String CACHEKEY_SYSTEM_CONFIG_TAXFEE_MIN  = "minTaxFee";

    // 缓存key-节假日
    public static final String CACHEKEY_DATE_SCHEDULE_FESTIVALDAY  = "dateScheduleFestivalDayList";

    // 缓存key-股票涨跌幅度
    public static final String  CACHEKEY_STOCK_UP_AND_DOWN_PERCENT = "stockUpAndDownPercent";

    // 缓存key-股票报价队列-买
    public static final String CACHEKEY_STOCK_QUOTE_BUYQUEUE  = "stockQuoteBuyQueue_";
    // 缓存key-股票报价队列-卖
    public static final String CACHEKEY_STOCK_QUOTE_SELLQUEUE  = "stockQuoteSellQueue_";
    // 缓存key-股票价格信息
    public static final String CACHEKEY_STOCK_PRICEMAP  = "stockPriceMap_";
    // 缓存key-股票价格信息
    public static final String CACHEKEY_STOCK_PRICELIST  = "stockPriceList_";
    // 缓存key-队列中的股票信息
    public static final String CACHEKEY_QUEUE_STOCKID_LIST  = "queueStockIdList_";


    // 缓存key-股票列表信息
    public static final String CACHEKEY_STOCKCONFIGLIST  = "stockConfigList";
    // 缓存key-股票列表信息-MAP集合
    public static final String CACHEKEY_STOCKCONFIGLIST_MAP  = "stockConfigListMap";
    // 缓存key-股票ID信息-MAP集合
    public static final String CACHEKEY_STOCKCONFIGID_LIST  = "stockConfigIdList";
    // 缓存key-股票ID名称信息-MAP集合
    public static final String CACHEKEY_STOCKCONFIGIDNAME_MAP  = "stockConfigIdNameMap";

    // 缓存key-股票ID信息-马甲订单集合
    public static final String CACHEKEY_SIMULATIONQUOTELIST = "simulationQuoteList";
    // 缓存key-交易记录信息-所有股票
    public static final String CACHEKEY_TRADERECORDLIST = "traderecordList";
    // 缓存key-交易记录信息-单只股票
    public static final String CACHEKEY_TRADERECORDLIST_STOCKID = "traderecordListStockId_";

    // 缓存key-帐户信息-模拟帐户列表
    public static final String CACHEKEY_ACCOUNT_VIRTUALLIST = "virtualAccountList";

    // 缓存key-帐户帐号信息-MAP集合
    public static final String CACHEKEY_ACCOUNT_ACCOUNTNUM_MAP = "accountAccountNumMap_";
}
