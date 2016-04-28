package com.ryd.server.stocktrader.listener;

import com.ryd.server.stocktrader.net.StockTraderServer;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.stocktrader.listener
 * 创建人：songby
 * 创建时间：2016/4/26 17:34
 */
public class ContextListener implements ServletContextListener {


    private static Logger logger = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        StockTraderServer server = new StockTraderServer();
        try {
            server.bind(8888);
            logger.debug("接口服务端启动成功-------------接口8888-------------");
        } catch (Exception e) {
           logger.debug("接口服务端启动失败--------------------------");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.debug("接口服务端关闭--------------------------");
    }
}
