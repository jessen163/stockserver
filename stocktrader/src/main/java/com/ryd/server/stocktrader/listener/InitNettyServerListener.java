package com.ryd.server.stocktrader.listener;

import com.ryd.server.stocktrader.net.StockTraderServer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化netty服务器
 * Created by Administrator on 2016/4/28.
 */
public class InitNettyServerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext sc = servletContextEvent.getServletContext();//由事件得到servletcontext
        Integer stockserverPort = Integer.parseInt(sc.getInitParameter("stockserver.port"));
        try {
            new StockTraderServer().bind(stockserverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
