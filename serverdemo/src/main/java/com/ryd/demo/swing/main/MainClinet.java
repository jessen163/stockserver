package com.ryd.demo.swing.main;


import com.ryd.demo.swing.frame.LoginFrame;
import com.ryd.demo.swing.net.StockClient;

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

class ClientServer implements Runnable {
	@Override
	public void run() {
		try {
			StockClient client = new StockClient();
			client.connect("127.0.0.1", 8888);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
