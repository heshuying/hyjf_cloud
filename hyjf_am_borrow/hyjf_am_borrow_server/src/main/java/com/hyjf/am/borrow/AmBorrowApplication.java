package com.hyjf.am.borrow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = { "com.hyjf.am.borrow.dao.mapper" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class AmBorrowApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmBorrowApplication.class, args);
	}
}
