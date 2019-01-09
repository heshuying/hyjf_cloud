package com.hyjf.cs.message.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.hyjf.cs.message.config.properties.HyjfEnvProperties;
import com.hyjf.cs.message.config.properties.JPushProperties;
import com.hyjf.cs.message.config.properties.MongoDbProperties;
import com.hyjf.cs.message.config.properties.SmsProperties;

/**
 * @author xiasq
 * @version PropertiesConfig, v0.1 2019/1/2 9:56
 */
@Configuration
@EnableConfigurationProperties({ SmsProperties.class, HyjfEnvProperties.class, MongoDbProperties.class,
		JPushProperties.class })
public class PropertiesConfig {

	public static HyjfEnvProperties hyjfEnvProperties;

	public static JPushProperties jPushProperties;

	public static boolean isPassSend(String mobile) {
		if (hyjfEnvProperties == null) {
			return false;
		}
		if (StringUtils.isBlank(mobile)) {
			return false;
		}

		final Boolean envTest = hyjfEnvProperties.isTest();
		final String whiteList = hyjfEnvProperties.getWhiteList();

		if (envTest == null || StringUtils.isBlank(whiteList)) {
			return false;
		}

		if (envTest.booleanValue()) {
			if (!whiteList.contains(mobile)) {
				return false;
			}
		}
		return true;
	}

	@Autowired
	public void setHyjfEnvProperties(HyjfEnvProperties hyjfEnvProperties) {
		PropertiesConfig.hyjfEnvProperties = hyjfEnvProperties;
	}

	@Autowired
	public void setjPushProperties(JPushProperties jPushProperties) {
		PropertiesConfig.jPushProperties = jPushProperties;
	}
}
