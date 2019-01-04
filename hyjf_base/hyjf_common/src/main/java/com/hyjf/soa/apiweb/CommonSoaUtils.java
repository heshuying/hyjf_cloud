package com.hyjf.soa.apiweb;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommonSoaUtils {
	private static String SOA_INTERFACE_KEY;
	
	private static String HYJF_API_WEB_URL;

	/** 权限 CONTROLLOR @RequestMapping值 */
	public static final String REQUEST_MAPPING = "/bank/merchant/account";

	private static ExecutorService exec = Executors.newFixedThreadPool(50);
    
	
	public static String getSOA_INTERFACE_KEY() {
		return SOA_INTERFACE_KEY;
	}

	@Value("${release.coupon.accesskey}")
	public static void setSOA_INTERFACE_KEY(String sOA_INTERFACE_KEY) {
		CommonSoaUtils.SOA_INTERFACE_KEY = sOA_INTERFACE_KEY;
	}

	public static String getHYJF_API_WEB_URL() {
		return HYJF_API_WEB_URL;
	}

	@Value("${hyjf.api.web.url}")
	public static void setHYJF_API_WEB_URL(String hYJF_API_WEB_URL) {
		CommonSoaUtils.HYJF_API_WEB_URL = hYJF_API_WEB_URL;
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


