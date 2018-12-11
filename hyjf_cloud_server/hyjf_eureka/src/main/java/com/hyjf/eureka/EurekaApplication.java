package com.hyjf.eureka;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.registry.ResponseCache;

/**
 * @author xiasq 注册中心
 */
@SpringBootApplication
@EnableEurekaServer
@RestController
@RequestMapping("/eu")
public class EurekaApplication {
	private Logger logger = LoggerFactory.getLogger(EurekaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}

	@RequestMapping("/applications")
	public String getApp() {
		EurekaServerContext serverContext = EurekaServerContextHolder.getInstance().getServerContext();
		PeerAwareInstanceRegistry registry = serverContext.getRegistry();
		ResponseCache responseCache = registry.getResponseCache();

		Applications applications = registry.getApplications();

		List<Application> list = applications.getRegisteredApplications();

		StringBuffer sb = new StringBuffer();
		for (Application application : list) {
			sb.append("appName:").append(application.getName()).append("[");
			List<InstanceInfo> instanceInfos = application.getInstances();
			// logger.info("instanceInfos: {}",
			// JSONObject.toJSONString(instanceInfos));
			for (InstanceInfo instanceInfo : instanceInfos) {
				sb.append(instanceInfo.getInstanceId()).append(",");
				// logger.info("instanceInfo: {}",
				// JSONObject.toJSONString(instanceInfo));
			}

			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	// @RequestMapping("/application/{appName}")
	// public String getApp(@PathVariable String appName) {
	// EurekaServerContext serverContext =
	// EurekaServerContextHolder.getInstance().getServerContext();
	// PeerAwareInstanceRegistry registry = serverContext.getRegistry();
	// ResponseCache responseCache = registry.getResponseCache();
	//
	//
	// Key cacheKey = new Key(Key.EntityType.Application,
	// ResponseCacheImpl.ALL_APPS_DELTA,
	// Key.KeyType.JSON, CurrentRequestVersion.get(),
	// EurekaAccept.full, null
	// );
	//
	// return responseCache.get(cacheKey);
	// }
}
