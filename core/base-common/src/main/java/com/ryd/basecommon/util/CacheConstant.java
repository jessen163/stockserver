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
    public static final String CACHEKEY_SYSTEM_CONFIG_REST_TIME_END  = "stockMarketRestTimeStart";

    // 缓存key-系统配置参数信息-股市收盘时间
    public static final String CACHEKEY_SYSTEM_CONFIG_CLOSE_TIME  = "stockMarketCloseTime";

    // 缓存key-节假日
    public static final String CACHEKEY_DATE_SCHEDULE_FESTIVALDAY  = "dateScheduleFestivalDayList";

    // 缓存key-股票涨跌幅度
    public static final String  CACHEKEY_STOCK_UP_AND_DOWN_PERCENT = "stockUpAndDownPercent";

    // 缓存key-股票报价队列-买
    public static final String CACHEKEY_STOCK_QUOTE_BUYQUEUE  = "stockQuoteBuyQueue_";
    // 缓存key-股票报价队列-卖
    public static final String CACHEKEY_STOCK_QUOTE_SELLQUEUE  = "stockQuoteSellQueue_";
    // 缓存key-股票价格信息
    public static final String CACHEKEY_STOCK_PRICELIST  = "stockPriceList_";

}
