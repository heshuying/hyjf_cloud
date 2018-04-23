package com.hyjf.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hyjf.common.log.LogUtil;
import com.hyjf.common.util.StringPool;

/**
 * 在java中处理http请求.
 *
 */
public class HttpDealBank {
	/** THIS_CLASS */
	private static final String THIS_CLASS = HttpDeal.class.getName();
	
	static HostnameVerifier hv = new HostnameVerifier() {
		public boolean verify(String urlHostName, SSLSession session) {
			System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
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
		LogUtil.startLog(THIS_CLASS, methodName, "[开始get请求, URL=" + url + "]");
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
			LogUtil.errorLog(THIS_CLASS, methodName, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					LogUtil.errorLog(THIS_CLASS, methodName, e);
				}
			}
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					LogUtil.errorLog(THIS_CLASS, methodName, e);
				}
			}
			LogUtil.startLog(THIS_CLASS, methodName, "[结束get请求]");
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
		LogUtil.startLog(THIS_CLASS, methodName, "[开始post请求, URL=" + url + ", 参数=" + params + "]");
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
			// 请求到即信端
			ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
			// 响应报文
			//System.out.println("(即信端-->P2P)响应报文：\r\n" + response.getBody().toString().replace(",", ",\r\n"));
			Map<String,String> responseMap = (Map<String,String>) response.getBody();
			result = JSONObject.toJSONString(responseMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
    public static String anrongPost(String url, Map<String, String> params) {
        String methodName = "post";
        LogUtil.startLog(THIS_CLASS, methodName, "[开始post请求, URL=" + url + ", 参数=" + params + "]");
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
            LogUtil.infoLog(THIS_CLASS, methodName,"[请求安荣url="+anrognRrl+"参数="+params+"]");
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
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

	}
}