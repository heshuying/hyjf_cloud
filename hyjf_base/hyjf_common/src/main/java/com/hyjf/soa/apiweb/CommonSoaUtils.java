package com.hyjf.soa.apiweb;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommonSoaUtils {

	private static ExecutorService exec = Executors.newFixedThreadPool(50);

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


