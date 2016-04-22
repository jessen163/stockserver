package com.ryd.server.stocktrader.net;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端任务处理数据发送和接收
 * TODO 测试
 *
 * Created by Administrator on 2016/4/11.
 */
public class SimpleMessageClientHandler extends ChannelInboundHandlerAdapter {

    public SimpleMessageClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send the message to Server
        super.channelActive(ctx);

        for (int i = 0; i< 10; i++) {
            ctx.writeAndFlush(this.createRequestInfo(1));
        }
    }

    private DiyNettyMessage.NettyMessage createRequestInfo(int id) {
        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();
//        SimpleDiyMessage.DiyMessage.StockList.Builder stockbuilder = SimpleDiyMessage.DiyMessage.StockList.newBuilder();
        builder.setId(id);
        builder.setStatus(0);
        builder.setKey("0");
//        builder.setKey("key_" + id);
//        builder.setContent("content_" + id);
//        // 初始化股票信息
//        builder.addStockInfo(DiyNettyMessage.StockInfo.newBuilder().setId("1").setStockCode("1").setStockName("1").setStockPrice(10.0));
//        builder.addStockInfo(DiyNettyMessage.StockInfo.newBuilder().setId("2").setStockCode("2").setStockName("2").setStockPrice(20.0));
//        builder.addStockInfo(DiyNettyMessage.StockInfo.newBuilder().setId("3").setStockCode("3").setStockName("3").setStockPrice(30.0));
//        builder.addStockInfo(DiyNettyMessage.StockInfo.newBuilder().setId("4").setStockCode("4").setStockName("4").setStockPrice(40.0));
//        // 初始化报价信息
//        builder.addQuoteInfo(DiyNettyMessage.QuoteInfo.newBuilder().setStockId("1").setAccountId("1").setStockPrice(20.1));
//        builder.addQuoteInfo(DiyNettyMessage.QuoteInfo.newBuilder().setStockId("2").setAccountId("2").setStockPrice(10.1));
//        builder.addQuoteInfo(DiyNettyMessage.QuoteInfo.newBuilder().setStockId("3").setAccountId("3").setStockPrice(30.1));

        return builder.build();
    }

//    private SimpleMessage.Message createResponseInfo(Integer id){
//        //实例化响应消息对象
//        /**
//         * 这里就是其实就是通过protobuf工具编译OrderInfoRespProto.proto生成的java文件，
//         * 打开其中的文件，我们就可以找到相应的方法。
//         */
//        SimpleMessage.Message.Builder builder = SimpleMessage.Message.newBuilder();
//        //构建响应消息设置属性值
//        builder.setId(id);
//        builder.setKey("key_" + id);
//        builder.setContent("content_" + id);
//        return builder.build();
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
