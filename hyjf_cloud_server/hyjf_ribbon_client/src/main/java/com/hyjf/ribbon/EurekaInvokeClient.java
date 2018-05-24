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
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version EurekaInvokeClient, v0.1 2018/5/22 17:06
 */


public class EurekaInvokeClient{
	private Logger logger = LoggerFactory.getLogger(EurekaInvokeClient.class);
	private EurekaInvokeClient(){

	}

	public static EurekaInvokeClient  getInstance(){
		return new EurekaInvokeClient();
	}
	public synchronized RestTemplate build(){

		//if(restTemplates.containsKey(name))return restTemplates.get(name);

		SimpleClientHttpRequestFactory factory = new EurekaClientHttpRequestFactory();
		factory.setReadTimeout(15000);//ms
		factory.setConnectTimeout(5000);//ms

		RestTemplate restTemplate = new RestTemplate(factory);
		//restTemplates.put(name, restTemplate);

		return restTemplate;
	}

	private class EurekaClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

		@Override
		public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
			uri = convertToRealUri(uri);
			return super.createRequest(uri, httpMethod);
		}

		private URI convertToRealUri(URI uri){
			String serviceId = uri.getHost();
			try {
				String host = uri.getHost();
				ExampleEurekaClient sampleClient = new ExampleEurekaClient();

				InetSocketAddress address = sampleClient.sendRequestToServiceUsingEureka_demo(host);

				address.getHostName();
				address.getHostString();
				address.getPort();
				address.getAddress().getHostAddress();
				address.getAddress().getAddress();
				address.getAddress().getHostName();
				address.getAddress().getCanonicalHostName();



				String realHost = address.getAddress().getHostAddress()+":" + address.getPort();
				uri = new URI(uri.toString().replace(serviceId, realHost));
				logger.info("uri: {}", uri.getPath());
				return uri;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}


}
