package com.smart.web;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by wenjg on 2016/6/2.
 */
@EnableWebSocket
public class MyWebSoketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(helloHandler(),"/chapter17/hello");
    }

    @Bean
    public MyWebSocketHandler helloHandler(){
        return new MyWebSocketHandler();
    }
}
