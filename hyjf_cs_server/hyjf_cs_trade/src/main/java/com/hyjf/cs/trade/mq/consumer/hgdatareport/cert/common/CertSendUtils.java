package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common;

import com.hyjf.common.util.CustomConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CertSendUtils {

	static Logger logger = LoggerFactory.getLogger(CertSendUtils.class);
	private static String thisMessName = "应急中心数据上报";
	private static String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";
	public static PoolingHttpClientConnectionManager  poolConnManager=null;


	public static String getReq(String certCrtpath,String url) {
		CertSSLProtocolSocketFactory certSSLProtocolSocketFactory = new CertSSLProtocolSocketFactory();
		CertSSLProtocolSocketFactory.crtPath = certCrtpath;
		Protocol myhttps = new Protocol("https",certSSLProtocolSocketFactory , 443);
		Protocol.registerProtocol("https", myhttps);
		String strResult = "";
		HttpClient httpClient = new HttpClient();

		HttpClientParams httpClientParams = httpClient.getParams();
		httpClientParams.setConnectionManagerTimeout(500000);

		HttpConnectionManagerParams httpConnectionManagerParams = httpClient.getHttpConnectionManager().getParams();
		httpConnectionManagerParams.setConnectionTimeout(500000);
		httpConnectionManagerParams.setSoTimeout(500000);
		BufferedReader reader = null;
		try {
			// get
			GetMethod get = new GetMethod(url);
			//设置请求报文头的编码
			get.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			httpClient.executeMethod(get);
			reader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream(), "utf-8"));
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				strResult += tmp + "\r\n";
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("io流关闭错误");
				}
			}
		}
		return removeSlash(strResult);
	}

	public static String postRequest(String certCrtpath,String url, Map<String,String> param ) {
		CertSSLProtocolSocketFactory certSSLProtocolSocketFactory = new CertSSLProtocolSocketFactory();
		CertSSLProtocolSocketFactory.crtPath = certCrtpath ;
		Protocol myhttps = new Protocol("https",certSSLProtocolSocketFactory , 443);
		Protocol.registerProtocol("https", myhttps);
		String strResult = "";
		HttpClient httpClient = new HttpClient();

		HttpClientParams httpClientParams = httpClient.getParams();
		httpClientParams.setConnectionManagerTimeout(20000);

		//		HttpConnectionParams
		HttpConnectionManagerParams httpConnectionManagerParams = httpClient.getHttpConnectionManager().getParams();
		httpConnectionManagerParams.setConnectionTimeout(20000);
		httpConnectionManagerParams.setSoTimeout(20000);

		//		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));

		BufferedReader reader = null;
		try {
			// post请求
			PostMethod post = new PostMethod(url);
			//			post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
			List<NameValuePair> params=new ArrayList<>();
			for(Map.Entry<String, String> entry:param.entrySet()){
				params.add(new NameValuePair(entry.getKey(),entry.getValue()));
			}
			NameValuePair[] names= new NameValuePair[params.size()];
			for(int i=0;i<params.size();i++){
				names[i] = new NameValuePair(params.get(i).getName(),params.get(i).getValue());	
			}
			//设置请求报文头的编码
			post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			//			post.setRequestHeader("Content-Type", "multipart/form-data ; charset=utf-8");
			//			post.setRequestHeader("Connection", "keep-alive");

			post.setRequestBody(names);
			httpClient.executeMethod(post);
			reader = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), "utf-8"));
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				strResult += tmp + "\r\n";
			}
		} catch (Exception e) {
			logger.info(logHeader,e);
		} finally {
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("io流关闭错误");
				}
			}
		}

		return removeSlash(strResult);
	}




	public static String removeSlash(String result){
		result=result.replace("\\", "");
		if(result.length()>0)
			result=result.substring(0, result.length()-1);
		return result;
	}


	public static String deleteRequest(String url,Map<String,String> dataForm) {
		Protocol myhttps = new Protocol("https", new CertSSLProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		String strResult = "";
		HttpClient http = new HttpClient();
		BufferedReader reader = null;
		try {
			DeleteMethod delete = new DeleteMethod(url);

			delete.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");


			List<NameValuePair> data = new ArrayList<NameValuePair>();  
			if(dataForm!=null){  
				Set<String> keys = dataForm.keySet();  
				for(String key:keys){  
					NameValuePair nameValuePair = new NameValuePair(key, (String) dataForm.get(key));  
					data.add(nameValuePair);  
				}  
			}

			delete.setQueryString(data.toArray(new NameValuePair[0])); 
			http.executeMethod(delete);
			reader = new BufferedReader(new InputStreamReader(delete.getResponseBodyAsStream(), "utf-8"));
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				strResult += tmp + "\r\n";
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("io流关闭错误");
				}
			}
		}
		return removeSlash(strResult);
	}

}
