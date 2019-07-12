//package com.hyjf.data.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import org.apache.tomcat.jdbc.pool.DataSource;
//
///**
// *  * @author zhangqingqing
// * @version HiveJdbcConfig, v0.1 2019/6/19 14:08
// */
//
////@Configuration
//@Slf4j
//public class HiveJdbcConfig {
//
//    @Autowired
//
//    private Environment env;
//
//
//    //@Bean(name = "hiveJdbcDataSource")
//
//    @Qualifier("hiveJdbcDataSource")
//
//    public DataSource dataSource() {
//
//        DataSource dataSource = new DataSource();
//
//        dataSource.setUrl(env.getProperty("hive.url"));
//
//        dataSource.setDriverClassName(env.getProperty("hive.driver-class-name"));
//
//        dataSource.setUsername(env.getProperty("hive.user"));
//
//        dataSource.setPassword(env.getProperty("hive.password"));
//
//        log.debug("Hive DataSource Inject Successfully...");
//
//        return dataSource;
//
//    }
//
//
//    //@Bean(name = "hiveJdbcTemplate")
//
//    public JdbcTemplate hiveJdbcTemplate(@Qualifier("hiveJdbcDataSource") DataSource dataSource) {
//
//        return new JdbcTemplate(dataSource);
//
//    }
//}
