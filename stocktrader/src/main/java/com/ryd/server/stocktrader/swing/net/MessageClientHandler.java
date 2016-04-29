package com.ryd.server.stocktrader.swing.net;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.server.stocktrader.swing.service.impl.MessageServiceImpl;
import com.ryd.server.stocktrader.utils.TestParamBuilderUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端任务处理数据发送和接收
 * TODO 测试
 *
 * Created by Administrator on 2016/4/11.
 */
public class MessageClientHandler extends ChannelInboundHandlerAdapter {

    public MessageClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send the message to Server
        super.channelActive(ctx);
        MessageServiceImpl.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DiyNettyMessage.NettyMessage request = (DiyNettyMessage.NettyMessage) msg;
        MessageServiceImpl.createResponseInfo(request);
    }
}
