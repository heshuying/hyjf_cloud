package com.hyjf.soa.apiweb;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

public class NoRetTaskThree implements Runnable {
    private static Logger log = Logger.getLogger(NoRetTaskThree.class);  
    
	String requestUrl = StringUtils.EMPTY;
	private Map<String, String> params;
	
	public NoRetTaskThree(String requestUrl, Map<String, String> params){
		this.requestUrl = requestUrl;
		this.params = params;
	}

	@Override
	public void run() {
		// 循环调用三次接口
	    try {
	        for (int i = 0; i < 3; i++) {
	            String body = HttpClientUtils.postJson(requestUrl, JSON.toJSONString(params));
	            if(Validator.isNotNull(body) && "success".equals(body.toLowerCase())){
	                // 请求成功
	                break;
	            }else if(i-2!=0){
	                log.info("调用接口失败,url:["+requestUrl+"] , 参数:["+params.toString()+"] .循环次数："+(i+2));
	                Thread.sleep(((i+1)*30)*1000);
	            }
            }
        } catch (Exception e) {
            log.info("调用接口出错,url:["+requestUrl+"] , 参数:["+params.toString()+"] .错误："+e.getMessage());
        }
		
	}

}
