package com.hyjf.signatrues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

/**
 * @author fuqiang
 * @version SignApplication, v0.1 2018/11/13 10:59
 */
@CrossOrigin(origins = "*")
@SpringBootApplication
@EnableDiscoveryClient
public class SignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
