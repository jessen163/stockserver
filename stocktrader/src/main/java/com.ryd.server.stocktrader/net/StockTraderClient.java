package com.ryd.server.stocktrader.net;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 股票交易客户端
 * 客户端由C#完成，当前代码仅用于测试
 * TODO 测试
 * Created by Administrator on 2016/4/21.
 */
public class StockTraderClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //用于处理半包的情况
                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                    //添加解码器，并指定解码的对象是客户端请求消息对象
                    ch.pipeline().addLast(new ProtobufDecoder(DiyNettyMessage.NettyMessage.getDefaultInstance()));
                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                    //便于后续OrderServerHandler对客户端请求消息的使用，不需要进行手工编码
                    ch.pipeline().addLast(new ProtobufEncoder());
                    ch.pipeline().addLast(new SimpleMessageClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        StockTraderClient client = new StockTraderClient();
        client.connect("127.0.0.1", 8080);
    }
}
