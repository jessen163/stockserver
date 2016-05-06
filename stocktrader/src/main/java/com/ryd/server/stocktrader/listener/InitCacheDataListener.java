package com.ryd.server.stocktrader.listener;

import com.ryd.business.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据初始化监听
 * TODO 待优化
 * Created by chenji on 2016/5/6.
 */
public class InitCacheDataListener {
    private static Logger logger = LoggerFactory.getLogger(InitCacheDataListener.class);
    @Autowired
    private static StQuoteService stQuoteService;
    @Autowired
    private static StStockConfigService stStockConfigService;
    @Autowired
    private static StStockService stStockService;

    public InitCacheDataListener() {
        init();
    }

   public void init() {
        logger.debug("InitCacheDataListener.....start.....");
        stStockConfigService.findStockConfig(null, 1, Integer.MAX_VALUE);
        stQuoteService.findStQuoteToCache(1000);
        stStockService.findStockListToCache();
        logger.debug("InitCacheDataListener.....end.....");
    }
}
