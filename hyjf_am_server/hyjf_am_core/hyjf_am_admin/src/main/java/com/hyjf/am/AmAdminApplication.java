package com.hyjf.am;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version AmAdminApplication, v0.1 2018/6/1 9:24
 */

@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan
@ComponentScan(basePackages = {"com.hyjf.am","com.hyjf.common","com.hyjf.pay.lib"})
public class AmAdminApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean(name = "rocketMQMessageObjectMapper")
    public ObjectMapper jacksonObjectMapper(@Autowired Jackson2ObjectMapperBuilder builder) {
        return builder.createXmlMapper(false).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(AmAdminApplication.class, args);
    }
}
