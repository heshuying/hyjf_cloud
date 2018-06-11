package com.hyjf.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import zipkin.server.EnableZipkinServer;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

/**
 * @author xiasq
 * @version ZipkinApplication, v0.1 2018/6/8 15:55
 */

@SpringBootApplication
@EnableAsync
public class ZipkinApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZipkinApplication.class, args);
	}

	// Use this for debugging (or if there is no Zipkin server running on port 9411)
	@Bean
	@ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
	public Reporter<Span> spanReporter() {
		return Reporter.CONSOLE;
	}
}
