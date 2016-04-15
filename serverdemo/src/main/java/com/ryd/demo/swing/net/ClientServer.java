package com.ryd.demo.swing.net;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.demo.swing.net
 * 创建人：songby
 * 创建时间：2016/4/15 10:17
 */

public class ClientServer implements Runnable {

    public void run() {
        try {
            StockClient client = new StockClient();//192.168.5.47
            client.connect("127.0.0.1", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
