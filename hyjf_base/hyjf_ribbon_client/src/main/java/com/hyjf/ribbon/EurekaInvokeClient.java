package com.hyjf.ribbon;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version EurekaInvokeClient, v0.1 2018/5/22 17:06
 */

public class EurekaInvokeClient {
	private Logger logger = LoggerFactory.getLogger(EurekaInvokeClient.class);

	private EurekaInvokeClient() {
	}

	private static class EurekaInvokeClientHolder {
		private static EurekaInvokeClient INSTANCE = new EurekaInvokeClient();
	}

	public static EurekaInvokeClient getInstance() {
		return EurekaInvokeClientHolder.INSTANCE;
	}

	public synchronized RestTemplate buildRestTemplate() {
		SimpleClientHttpRequestFactory factory = new EurekaClientHttpRequestFactory();
		factory.setReadTimeout(15000);// ms
		factory.setConnectTimeout(5000);// ms
		return new RestTemplate(factory);
	}

	private class EurekaClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

		@Override
		public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
			uri = convertToRealUri(uri);
			return super.createRequest(uri, httpMethod);
		}

		private URI convertToRealUri(URI uri) {
			String serviceId = uri.getHost();
			try {
				String host = uri.getHost();
				ServiceIdConvert sampleClient = new ServiceIdConvert();
				InetSocketAddress address = sampleClient.convertServiceIdToRealHost(host);
				Assert.notNull(address, "Cannot get an instance of service to talk to from eureka...");
				String realHost = address.getAddress().getHostAddress() + ":" + address.getPort();
				logger.info("host is: {}, realHost: {}", host, realHost);
				uri = new URI(uri.toString().replace(serviceId, realHost));
				return uri;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

}
