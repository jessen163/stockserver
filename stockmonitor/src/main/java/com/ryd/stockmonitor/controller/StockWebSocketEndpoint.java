package com.ryd.stockmonitor.controller;

import com.ryd.demo.server.bean.StStock;
import com.ryd.protocol.NettyMessage;
import com.ryd.stockmonitor.net.MessageService;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 股票同步
 * Created by Administrator on 2016/4/14.
 */
@ServerEndpoint("/stock")
public class StockWebSocketEndpoint {

    @OnMessage
    public void onStockListMessage(Session session, String incomingMessage) {
        MessageService.session = session;
//        try {
//            for (int i = 0; i< 100; i++) {
//                    session.getBasicRemote()
//                            .sendText("stocklist: " + i);
//                TimeUnit.SECONDS.sleep(15);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String[] messageArr = null;
        Integer i = null;
        String stockId = null;
        if (incomingMessage.indexOf("@")!=-1) {
            messageArr = incomingMessage.split("@");
            i = Integer.parseInt(messageArr[0]);
            stockId = messageArr[1];
        } else {
            i = Integer.parseInt(incomingMessage);
        }


        while (true) {
            try {
                NettyMessage nettyMessage = new NettyMessage();
                switch (i) {
                    case 1:
                        nettyMessage.setMsgType(2);
                        nettyMessage.setMsgObj(1);
                        MessageService.sendMessage(nettyMessage);
                        TimeUnit.SECONDS.sleep(60);
                        break;
                    case 7:
                        nettyMessage.setMsgType(7);
                        nettyMessage.setMsgObj(1);
                        MessageService.sendMessage(nettyMessage);
                        TimeUnit.SECONDS.sleep(60);
                        break;
                    case 8:
                        nettyMessage.setMsgType(8);
                        nettyMessage.setMsgObj(stockId);
                        MessageService.sendMessage(nettyMessage);
                        TimeUnit.SECONDS.sleep(60);
                        break;
                    case 9:
                        nettyMessage.setMsgType(9);
                        nettyMessage.setMsgObj(stockId);
                        MessageService.sendMessage(nettyMessage);
                        TimeUnit.SECONDS.sleep(60);
                        break;

                    case 10 :
                        nettyMessage.setMsgType(10);
                        nettyMessage.setMsgObj(stockId);
                        MessageService.sendMessage(nettyMessage);
                        TimeUnit.SECONDS.sleep(60);
                        break;
                    default:
                        throw new RuntimeException();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @OnOpen
    public void onOpen() {
//        session.getBasicRemote().sendText("onOpen");
    }

    /*@OnClose
    public void onClose(Session session) {

    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }*/
}
