package com.ryd.stockmonitor;

import com.ryd.stockmonitor.net.StockClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import javax.servlet.Filter;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {

    public static void main( String[] args )
    {
        System.out.println("Hello World!");
        SpringApplication.run(App.class);
        StockClient client = new StockClient();
        try {
            client.connect("127.0.0.1", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
