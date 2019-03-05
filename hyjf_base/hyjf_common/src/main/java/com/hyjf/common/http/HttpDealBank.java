package com.hyjf.common.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hyjf.common.util.StringPool;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 在java中处理http请求.
 *
 */
public class HttpDealBank {
	private static Logger log = LoggerFactory.getLogger(HttpDealBank.class);
	/** THIS_CLASS */
	private static final String THIS_CLASS = HttpDeal.class.getName();

	static HostnameVerifier hv = new HostnameVerifier() {
		@Override
        public boolean verify(String urlHostName, SSLSession session) {
			log.info("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
			return true;
		}
	};
	/**
	 * 处理get请求.
	 *
	 * @return json
	 */
	public static String get(String url) {
		String methodName = "get";
		log.info( "[开始get请求, URL=" + url + "]");
		// 实例化httpclient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 实例化get方法
		HttpGet httpget = new HttpGet(url);
		// 请求结果
		CloseableHttpResponse response = null;
		String content = "";
		try {
			// 执行get方法
			response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				content = EntityUtils.toString(response.getEntity(), StringPool.UTF8);
			}
		} catch (Exception e) {
			log.error(String.valueOf(e));
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(String.valueOf(e));
				}
			}
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error(String.valueOf(e));
				}
			}
			log.info("[结束get请求]");
		}
		return content;
	}

	/**
	 * 处理post请求.
	 *
	 * @param params
	 *            参数
	 * @return json
	 */
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public static String post(String url, Map<String, String> params) {
		String methodName = "post";
		log.info("[开始post请求, URL=" + url + ", 参数=" + params + "]");
		String result = null;
		ResponseEntity response = null;
		try {
			RestTemplate restTemplate = new RestTemplate(new ArrayList<HttpMessageConverter<?>>() {
				{
					add(new FastJsonHttpMessageConverter());
				}
			});
			
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			requestFactory.setConnectTimeout(10000);// 设置建立连接超时时间
			requestFactory.setReadTimeout(20000);// 设置等待返回超时时间
			restTemplate.setRequestFactory(requestFactory);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Accept-Charset", "UTF-8");
			headers.set("contentType", "application/json");
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			HttpEntity entity = new HttpEntity(params, headers);
			// 请求到即信端
			response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
			// 响应报文
			Map<String,String> responseMap = (Map<String,String>) response.getBody();
			result = JSONObject.toJSONString(responseMap);
		} catch (Exception e) {
			log.error("请求发生异常："+e);
			if(response.getStatusCode()!=null) {
				log.info(response.getStatusCode()+"响应报文：{}",response.getBody());
			}
			
		}
		return result;
	}

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
    public static String anrongPost(String url, Map<String, String> params) {
        String methodName = "post";
		log.info("[开始post请求, URL=" + url + ", 参数=" + params + "]");
        String result = null;
        try {
            RestTemplate restTemplate = new RestTemplate(new ArrayList<HttpMessageConverter<?>>() {
                {
                    add(new FastJsonHttpMessageConverter());
                }
            });
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept-Charset", "UTF-8");
            headers.set("contentType", "application/json");
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            HttpEntity entity = new HttpEntity(params, headers);
            String anrognRrl = url + "?member=" + params.get("member") + "&sign=" + params.get("sign");
			log.info("[请求安荣url="+anrognRrl+"参数="+params+"]");
            // 请求到安融
            ResponseEntity response = restTemplate.exchange(anrognRrl, HttpMethod.POST, entity, Map.class);
            // 响应报文
            Map<String,String> responseMap = (Map<String,String>) response.getBody();
            result = JSONObject.toJSONString(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new NewTrustManager();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	static class NewTrustManager implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		@Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		@Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		@Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

	}
}