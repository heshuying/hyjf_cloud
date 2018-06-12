package com.hyjf.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * @author xiasq
 * @version ZipkinApplication, v0.1 2018/6/8 15:55
 */

@SpringBootApplication
@EnableZipkinStreamServer
public class ZipkinApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZipkinApplication.class, args);
	}

}
