package com.ryd.server.stocktrader.listener;

import com.bugull.mongo.BuguConnection;
import com.bugull.mongo.BuguFramework;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * mongodb数据库连接池初始化
 * Created by chenji on 2016/4/26.
 */
public class InitMongoDBListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        org.apache.ibatis.logging.LogFactory.useLog4JLogging();
        ServletContext sc = servletContextEvent.getServletContext();//由事件得到servletcontext
        String mongoIp = sc.getInitParameter("mongo.ip");//通过参数获取ServletContext的value
        Integer mongoPort = Integer.parseInt(sc.getInitParameter("mongo.port"));//通过参数获取ServletContext的value
        String mongoDBName = sc.getInitParameter("mongo.dbname");//通过参数获取ServletContext的value
//        String mongoUsername = sc.getInitParameter("mongo.username");//通过参数获取ServletContext的value
//        String mongoPassword = sc.getInitParameter("mongo.password");//通过参数获取ServletContext的value

        //连接数据库
        BuguConnection conn = BuguFramework.getInstance().createConnection();
        conn.connect(mongoIp, mongoPort, mongoDBName); // , mongoUsername, mongoPassword
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //释放资源
        BuguFramework.getInstance().destroy();
    }
}
