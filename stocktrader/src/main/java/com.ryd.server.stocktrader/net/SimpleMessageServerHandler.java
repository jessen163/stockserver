package com.ryd.server.stocktrader.net;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * 处理客户端任务
 * TODO 测试
 * Created by Administrator on 2016/4/6.
 */
public class SimpleMessageServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(SimpleMessageServerHandler.class);
    int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 将请求的消息转成实体对象，这里的OrderInfoReq就相当于是我们的实体对象，而其中的OrderInfoReqProto就是我们
         * 编译之前的OrderInfoReqProto.proto文件名，也就是现在的OrderInfoReqProto.java文件，打开这个文件
         * 我们可以看到里面的一个静态内部类，并且这个内部类实现了内部的一个接口，接口中提供了OrderInfoReq属性的get方法，在下面
         * 的具体实现了get方法和set方法，还有其他的一些方法
         */
//        SimpleMessage.Message request = (SimpleMessage.Message) msg;
//        if (request.getContent().indexOf("content")!=-1) {
//            logger.info("来自客户端的第" + ++counter + "条请求消息：" + request.toString());
//            ctx.writeAndFlush(this.createResponseInfo(request.getId()+""));
//        }
        DiyNettyMessage.NettyMessage request = (DiyNettyMessage.NettyMessage) msg;
        ctx.writeAndFlush(this.createResponseInfo(request.getId()));
    }

    private DiyNettyMessage.NettyMessage createResponseInfo(int id){
        //实例化响应消息对象
        /**
         * 这里就是其实就是通过protobuf工具编译OrderInfoRespProto.proto生成的java文件，
         * 打开其中的文件，我们就可以找到相应的方法。
         */
//        SimpleMessage.Message.Builder builder = SimpleMessage.Message.newBuilder();
//        //构建响应消息设置属性值
////        builder.setOrderId(orderId);
////        builder.setRespId("0");
////        builder.setDesc("this is"+ ++counter +"OK!");
//        builder.setId(counter);
//        builder.setKey("key_" + orderId);
//        builder.setContent("content_"+orderId);
//        return builder.build();
        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();
//        SimpleDiyMessage.DiyMessage.StockList.Builder stockbuilder = SimpleDiyMessage.DiyMessage.StockList.newBuilder();
        builder.setId(id);
        builder.setKey("key_" + id);
        builder.setContent("content_" + id);
        // 初始化股票信息
        builder.addStockInfo(DiyNettyMessage.StockInfo.newBuilder().setId("1").setStockCode("1").setStockName("1").setStockPrice(10.0));
        builder.addStockInfo(DiyNettyMessage.StockInfo.newBuilder().setId("2").setStockCode("2").setStockName("2").setStockPrice(20.0));
        builder.addStockInfo(DiyNettyMessage.StockInfo.newBuilder().setId("3").setStockCode("3").setStockName("3").setStockPrice(30.0));
        builder.addStockInfo(DiyNettyMessage.StockInfo.newBuilder().setId("4").setStockCode("4").setStockName("4").setStockPrice(40.0));
        // 初始化报价信息
        builder.addQuoteInfo(DiyNettyMessage.QuoteInfo.newBuilder().setStockId("1").setAccountId("1").setStockPrice(20.1));
        builder.addQuoteInfo(DiyNettyMessage.QuoteInfo.newBuilder().setStockId("2").setAccountId("2").setStockPrice(10.1));
        builder.addQuoteInfo(DiyNettyMessage.QuoteInfo.newBuilder().setStockId("3").setAccountId("3").setStockPrice(30.1));

        return builder.build();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
//        cause.printStackTrace();
        logger.info("客户端断开链接-----------------------------------");
        ctx.close();
    }
}
