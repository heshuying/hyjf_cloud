package com.hyjf.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;



public class HttpClientUtils {
	private static Logger log = Logger.getLogger(HttpClientUtils.class);  
    
    public static String post(String url, Map<String, String> params) {  
        HttpClient httpclient = HttpClients.createDefault();
        
        String body = null;  
        
        log.info("create httppost:" + url);  
        HttpPost post = postForm(url, params);  
          
        body = invoke(httpclient, post);  
        // 释放连接
        post.releaseConnection();
        
        return body;  
    }  
    
    private static String invoke(HttpClient httpclient,  
            HttpUriRequest httpost) {  
          
    	CloseableHttpResponse response = sendRequest(httpclient, httpost);  
        String body = paseResponse(response);  
          
        return body;  
    }  
    
    /**
     * 处理post请求.
     *
     * @param url
     *            参数
     * @return json
     */
    public static String postJson(String url, String jsonStr) {
        
        // 实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 实例化post方法
        HttpPost httpPost = new HttpPost(url);

        // 结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 提交的参数
            StringEntity uefEntity = new StringEntity(jsonStr,"utf-8");
            uefEntity.setContentEncoding("UTF-8");
            uefEntity.setContentType("application/json"); 
            // 将参数给post方法
            httpPost.setEntity(uefEntity);
            // 执行post方法
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                content = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    
    
    
  
    private static String paseResponse(CloseableHttpResponse response) {  
        log.info("get response from http server..");  
        HttpEntity entity = response.getEntity();  
          
        log.info("response status: " + response.getStatusLine());  
          
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
            //log.info(body);
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally {
            try {
            	//关闭连接
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
          
        return body;  
    }  
  
    private static CloseableHttpResponse sendRequest(HttpClient httpclient,  
            HttpUriRequest httpost) {  
        log.info("execute post...");  
        CloseableHttpResponse response = null;  
        try {  
            response = (CloseableHttpResponse) httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        return response;  
    }  
  
    private static HttpPost postForm(String url, Map<String, String> params){  
          
        HttpPost httpost = new HttpPost(url);  
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
        		.setConnectTimeout(60000)
        		.setConnectionRequestTimeout(10000)
        		.setSocketTimeout(60000).build();
        httpost.setConfig(requestConfig);
        //参数传递
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();            
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {  
            nvps.add(new BasicNameValuePair(key, params.get(key)));  
        }  
          
        try {  
            log.info("set utf-8 form entity to httppost");  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return httpost;  
    }  
}
