package com.hyjf.zuul;

import com.hyjf.zuul.filter.AccessFilter;
import com.hyjf.zuul.filter.ReturnFilter;
import com.hyjf.zuul.filter.XSSFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.hyjf")
//@EnableHystrixDashboard
public class ZuulApplication {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

//    @Bean
//    public IRule ribbonRule() {
//        return new WeightedResponseTimeRule();//这里配置策略，和配置文件对应
//    }
    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    @Bean
    public ReturnFilter returnFilter() {
        return new ReturnFilter();
    }

    @Bean
    public XSSFilter xssFilter() {
        return new XSSFilter();
    }
}
