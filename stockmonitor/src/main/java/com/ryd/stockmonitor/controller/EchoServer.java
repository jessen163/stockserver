package com.ryd.stockmonitor.controller;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by Administrator on 2016/4/13.
 */
@ServerEndpoint("/echo")
public class EchoServer {
    @OnMessage
    public String echo(String incomingMessage) {
        return "I got this (" + incomingMessage + ")"
                + " so I am sending it back !";
    }
}
