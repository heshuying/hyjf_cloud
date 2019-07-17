package com.hyjf.common.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    
    public static String post(String url, Map<String, String> params) {  
        HttpClient httpclient = HttpClients.createDefault();
        
        String body = null;

        logger.info("create httppost:" + url);
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
            logger.error(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return content;
    }

    /**
     * 处理post请求，增加对请求头的处理 add by yangchangwei
     *
     * @param url
     *            参数
     * @return json
     */
    public static String postJson(String url, String jsonStr,Map<String, String> header) {

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
            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
            // 将参数给post方法
            httpPost.setEntity(uefEntity);
            // 执行post方法
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                content = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return content;
    }
    
    
  
    private static String paseResponse(CloseableHttpResponse response) {
        logger.info("get response from http server..");
        HttpEntity entity = response.getEntity();

        logger.info("response status: " + response.getStatusLine());
          
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
        } catch (ParseException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {  
            logger.error(e.getMessage());
        }finally {
            try {
            	//关闭连接
				response.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
          
        return body;  
    }  
  
    private static CloseableHttpResponse sendRequest(HttpClient httpclient,  
            HttpUriRequest httpost) {
        logger.info("execute post...");
        CloseableHttpResponse response = null;  
        try {  
            response = (CloseableHttpResponse) httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            logger.error(e.getMessage());
        } catch (IOException e) {  
            logger.error(e.getMessage());
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
            logger.info("set utf-8 form entity to httppost");
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {  
            logger.error(e.getMessage());
        }  
        return httpost;  
    }


    /**
     * get请求，参数拼接在地址上
     *
     * @param url 请求地址加参数
     * @return 响应
     */
    public static String get(String url) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long lenth = entity.getContentLength();
            if (lenth != -1 && lenth < 2048) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while ((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }
}
