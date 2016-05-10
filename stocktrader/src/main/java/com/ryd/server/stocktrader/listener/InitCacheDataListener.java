package com.ryd.server.stocktrader.listener;

import com.ryd.business.service.*;
import com.ryd.business.service.util.BusinessConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * 数据初始化监听
 * TODO 待优化
 * Created by chenji on 2016/5/6.
 */
@Component("initListener")
public class InitCacheDataListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(InitCacheDataListener.class);
    /** ServletContext */
    private ServletContext servletContext;
    @Resource(name = "stQuoteServiceImpl")
    private StQuoteService stQuoteService;
    @Resource(name = "stStockConfigServiceImpl")
    private StStockConfigService stStockConfigService;
    @Resource(name = "stStockServiceImpl")
    private StStockService stStockService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null) {
            init();
        }
    }

    public void init() {
        logger.debug("InitCacheDataListener.....start.....");
        stStockConfigService.findStockConfig(null, 1, Integer.MAX_VALUE);
        stQuoteService.findStQuoteToCache(1000);
        stStockService.findStockListToCache();
        BusinessConstants.isInitQuoteSuccess = true;
        logger.debug("InitCacheDataListener.....end.....");
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
