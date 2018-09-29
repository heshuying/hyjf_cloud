package com.hyjf.batch.frame.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * @author xiasq
 * @version QuartzConfig, v0.1 2018/1/20 23:15
 * 配置任务调度中心 [QRTZ_JOB_DETAILS], [QRTZ_TRIGGERS] and [QRTZ_CRON_TRIGGERS]
 */
@Configuration
public class QuartzConfig {
	private Logger logger = LoggerFactory.getLogger(QuartzConfig.class);

	@Value("${quartz.threadPool.threadCount}")
	private String threadCount;
	@Value("${quartz.dataSource.quartzDataSource.URL}")
	private String url;
	@Value("${quartz.dataSource.quartzDataSource.user}")
	private String user;
	@Value("${quartz.dataSource.quartzDataSource.password}")
	private String password;
	@Value("${quartz.dataSource.quartzDataSource.maxConnections}")
	private String maxConnections;

	@Bean
	public Scheduler scheduler() throws IOException, SchedulerException {
		logger.info("初始化Scheduler开始...........");
		SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzProperties());
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		return scheduler;
	}

	/**
	 * 设置quartz属性
	 *
	 * @throws IOException
	 */
	public Properties quartzProperties() throws IOException {
		logger.info("db url is ： {}", url);
		Properties prop = new Properties();
		prop.put("quartz.scheduler.instanceName", "ServerScheduler");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
		prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
		prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		prop.put("org.quartz.jobStore.isClustered", "true");
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.put("org.quartz.threadPool.threadCount", threadCount);

		prop.put("org.quartz.dataSource.quartzDataSource.driver", "com.mysql.jdbc.Driver");
		prop.put("org.quartz.dataSource.quartzDataSource.URL", url);
		prop.put("org.quartz.dataSource.quartzDataSource.user", user);
		prop.put("org.quartz.dataSource.quartzDataSource.password", password);
		prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", maxConnections);
		prop.put("org.quartz.dataSource.quartzDataSource.validateOnCheckout", true);
		prop.put("org.quartz.dataSource.quartzDataSource.validationQuery", "select 1");

		return prop;
	}
}
