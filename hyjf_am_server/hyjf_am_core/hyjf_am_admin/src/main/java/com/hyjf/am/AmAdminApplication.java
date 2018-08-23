package com.hyjf.am;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version AmAdminApplication, v0.1 2018/6/1 9:24
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.hyjf")
public class AmAdminApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AmAdminApplication.class, args);
    }
}
