package com.ryd.server.stocktrader.handle;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理客户端任务
 *
 * Created by Administrator on 2016/4/22.
 */
public class StockTraderHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DiyNettyMessage.NettyMessage request = (DiyNettyMessage.NettyMessage) msg;
        ctx.writeAndFlush(MessageHandle.handleClientRequestInfo(request));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 初始化消息
        MessageHandle.ctx = ctx;
    }
}
