package com.ryd.stockmonitor.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端任务处理数据发送和接收
 *
 * Created by Administrator on 2016/4/11.
 */
public class StockClientHandler extends ChannelInboundHandlerAdapter {

    public StockClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send the message to Server
        super.channelActive(ctx);
        MessageService.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageService.doMsgForShunt(msg);
    }
}
