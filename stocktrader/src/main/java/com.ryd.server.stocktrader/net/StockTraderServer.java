package com.ryd.server.stocktrader.net;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.server.stocktrader.handle.StockTraderHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 服务器端
 * Created by Administrator on 2016/4/21.
 */
public class StockTraderServer {
    public void bind(int port) throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //用于处理半包的情况
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            //添加解码器，并指定解码的对象是客户端请求消息对象
                            ch.pipeline().addLast(new ProtobufDecoder(DiyNettyMessage.NettyMessage.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            //便于后续OrderServerHandler对客户端请求消息的使用，不需要进行手工编码
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new StockTraderHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new StockTraderServer().bind(port);
    }
}