package com.ryd.server.stocktrader.swing.main;

import com.ryd.server.stocktrader.swing.frame.LoginFrame;
import com.ryd.server.stocktrader.swing.net.ClientServer;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>标题:客户端主程序</p>
 * <p>描述:客户端主程序</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class MainClinet {

	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newCachedThreadPool();// 线程池
		es.execute(new ClientServer());

		LoginFrame.instance().open();

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
	}
}

