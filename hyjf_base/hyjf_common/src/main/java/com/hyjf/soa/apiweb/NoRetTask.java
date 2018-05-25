package com.hyjf.soa.apiweb;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hyjf.common.http.HttpClientUtils;

public class NoRetTask implements Runnable {
	String requestUrl = StringUtils.EMPTY;
	private Map<String, String> params;
	
	public NoRetTask(String requestUrl, Map<String, String> params){
		this.requestUrl = requestUrl;
		this.params = params;
	}

	@Override
	public void run() {
		// 调用服务接口
		HttpClientUtils.post(requestUrl, params);
	}

}
