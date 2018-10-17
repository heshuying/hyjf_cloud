package com.hyjf.am.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version AmBorrowApplication, v0.1 2018/6/1 9:24
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.hyjf")
@EnableAspectJAutoProxy(exposeProxy = true)
public class AmTradeApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AmTradeApplication.class, args);
    }
}
