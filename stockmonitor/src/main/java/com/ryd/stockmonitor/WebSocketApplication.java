package com.ryd.stockmonitor;

import com.ryd.stockmonitor.controller.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Websocket注册入口
 * Created by Administrator on 2016/4/14.
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class WebSocketApplication extends SpringBootServletInitializer
        implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(echoWebSocketHandler(), "/echo").withSockJS();
    }

/*    @Bean
    public ReverseWebSocketEndpoint reverseWebSocketEndpoint() {
        return new ReverseWebSocketEndpoint();
    }

    @Bean
    public EchoServer echoServer() {
        return new EchoServer();
    }*/

    @Bean
    public StockWebSocketEndpoint stockWebSocketEndpoint() {
        return new StockWebSocketEndpoint();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
