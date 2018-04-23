package com.hyjf.common.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.hyjf.common.log.LogUtil;
import com.hyjf.common.util.StringPool;

public class HttpPool {
	private static PoolingHttpClientConnectionManager cm = null;
	private static HttpRequestRetryHandler httpRequestRetryHandler = null;
	/** THIS_CLASS */
    private static final String THIS_CLASS = HttpPool.class.getName();
	static{
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        // 实例一个连接池管理器
        cm = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加到200
        cm.setMaxTotal(200);
        // 将每个路由基础的连接增加到20
        cm.setDefaultMaxPerRoute(20);
        // 关闭失效的连接
        cm.closeExpiredConnections();
        // 关闭60秒内不活动的连接
        cm.closeIdleConnections(60, TimeUnit.SECONDS);
        // 将目标主机的最大连接数增加到100
        HttpHost localhost = new HttpHost("http://test.hyjf.com:8080/hyjf-api-web",8080);
        cm.setMaxPerRoute(new HttpRoute(localhost), 100);
        // 连接保持以及重用由RequestDirector进行控制。
        System.out.println("90909090909090909090909");
        //请求重试处理
        httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }                
                if (exception instanceof InterruptedIOException) {// 超时     
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {                    
                    return true;
                }
                return false;
            }
        };
	}
	
	/**
	 * 配置请求
	 * @return
	 */
	private static void config(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpRequestBase.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpRequestBase.setHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");
        
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        httpRequestBase.setConfig(requestConfig);        
    }
	
	/**
	 * 从连接池中取得连接
	 * @return
	 */
	private synchronized static CloseableHttpClient getHttpClient(){
		// 从连接池中取得HttpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler)
                .build();
        return httpClient;
	}
	
	/**
	 * post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> params) {  
		String methodName = "post";
        LogUtil.startLog(THIS_CLASS, methodName, "[开始post请求, URL=" + url + ", 参数=" + params + "]");
		// 从连接池中取得HttpClient
        CloseableHttpClient httpClient = HttpPool.getHttpClient(); 
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        System.out.println(httpClient);
        HttpPost httpPost = new HttpPost(url);  
        config(httpPost);
        // 处理参数
        List<NameValuePair> nvps = HttpClientHandler.buildNameValuePair(params);
        // 结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 提交的参数
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, StringPool.UTF8);
            // 将参数给post方法
            httpPost.setEntity(uefEntity);
            // 执行post方法
            response = httpClient.execute(httpPost);
            if (StringPool.OK.equals(response.getStatusLine().getReasonPhrase()) && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(response.getEntity(), StringPool.UTF8);
                // 释放清空资源
                EntityUtils.consume(entity);
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

            LogUtil.endLog(THIS_CLASS, methodName, "[结束post请求]");
        }
        return content;
    } 
	
	/**
     * 处理get请求.
     *
     * @return json
     */
    public static String get(String url) {
        String methodName = "get";
        LogUtil.startLog(THIS_CLASS, methodName, "[开始get请求, URL=" + url + "]");
        // 实例化httpclient
        CloseableHttpClient httpClient = HttpPool.getHttpClient(); 
        // 实例化get方法
        HttpGet httpget = new HttpGet(url);
        config(httpget);
        // 请求结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 执行get方法
            response = httpClient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == 200) {
            	HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(response.getEntity(), StringPool.UTF8);
                // 释放清空资源
                EntityUtils.consume(entity);
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
            
            LogUtil.startLog(THIS_CLASS, methodName, "[结束get请求]");
        }
        return content;
    }

}
