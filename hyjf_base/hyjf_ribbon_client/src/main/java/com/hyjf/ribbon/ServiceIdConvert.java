package com.hyjf.ribbon;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author xiasq
 * @version ServiceIdConvert, v0.1 2018/5/24 16:10
 */
public class ServiceIdConvert {
	private Logger logger = LoggerFactory.getLogger(EurekaInvokeClient.class);

	private ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient;

	private synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
		if (applicationInfoManager == null) {
			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
		}
		return applicationInfoManager;
	}

	private synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager,
			EurekaClientConfig clientConfig) {
		if (eurekaClient == null) {
			eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
		}
		return eurekaClient;
	}

	public InetSocketAddress convertServiceIdToRealHost(String vipAddress) {

		// create the client
		ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(
				new MyDataCenterInstanceConfig());
		initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

		// String vipAddress = "AM-CONFIG";
		InstanceInfo nextServerInfo = null;
		try {
			nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
		} catch (Exception e) {
			logger.error("Cannot get an instance of service to talk to from eureka...", e);
			return null;
		} finally {
			eurekaClient.shutdown();
		}
		return new InetSocketAddress(nextServerInfo.getHostName(), nextServerInfo.getPort());
	}

}
