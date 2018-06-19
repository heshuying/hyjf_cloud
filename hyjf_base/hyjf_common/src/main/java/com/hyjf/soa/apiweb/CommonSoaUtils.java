package com.hyjf.soa.apiweb;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.common.http.HttpClientUtils;

public class CommonSoaUtils {
	private static Logger logger = LoggerFactory.getLogger(CommonSoaUtils.class);

	private static ExecutorService exec = Executors.newFixedThreadPool(50);
    
	/**
	 * 无需等待返回的http请求
	 * @param requestUrl
	 * @param paramsMap
	 */
	public static void noRetPost(String requestUrl, Map<String,String> paramsMap) {
		NoRetTask task = new NoRetTask(requestUrl,paramsMap);
		exec.execute(task);
	}
    
    /**
     * 
     * 异步发送三次请求
     * @author sss
     * @param requestUrl
     * @param paramsMap
     */
    public static void noRetPostThree(String requestUrl, Map<String,String> paramsMap) {
       NoRetTaskThree task = new NoRetTaskThree(requestUrl,paramsMap);
       exec.execute(task);
    }
	
	
}

class Task implements Callable<String> {
	private String loginUrl;
	private Map<String, String> params;

	public void init(String loginUrl, Map<String, String> params) {
		this.loginUrl = loginUrl;
		this.params = params;
	}

	@Override
	public String call() throws Exception {
		return HttpClientUtils.post(loginUrl, params);
	}

}


