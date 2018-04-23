package com.hyjf.am.borrow.datasouce;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author xiasq
 * @version DruidDataSourceConfig, v0.1 2018/1/21 22:32
 */

@Configuration
@MapperScan(basePackages = { "com.hyjf.am.borrow.dao.mapper" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class DruidDataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		DataSource druidDataSource = new DruidDataSource();
		return druidDataSource;
	}
}
