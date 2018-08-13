package com.hyjf.client.autoinvestsys;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hyjf.common.http.HttpClientUtils;

@Service
public class InvestSysUtils {

	//  自动投资插入同步crm接口
	private static final String AUTO_INSERT_INVEST_SYS = "/debtplanaccede/insertDebtAction.json";
	
	//  自动投资更新同步crm接口
	private static final String AUTO_UPDATE_INVEST_SYS = "/debtplanaccede/updateDebtAction.json";
	//银行存管插入投资插入同步crm接口
	private static final String AUTO_UPDATE_BORROWTENDER_SYS = "/borrowtender/insertInfoAction";
	
	
	/**
	 * 银行存管插入投资插入同步crm接口
	 * add by cwyang 2017-5-18
	 */
	public static void insertBorrowTenderSys(String id) {
		Map<String, String> params = new HashMap<String, String>();
		// 需要保存的计划投资对象转json字符串
		params.put("id", id);
		// 请求路径 todo
		// String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_CRM_URL) + AUTO_UPDATE_BORROWTENDER_SYS;
		String requestUrl = "";
		InvestSysUtils.noRetPost(requestUrl,params);
	}
	/**
	 * 自动投资插入同步crm接口
	 */
	public static void insertInvestSys(String debtinfo) {
		Map<String, String> params = new HashMap<String, String>();
		// 需要保存的计划投资对象转json字符串
		params.put("debtinfo", debtinfo);
		// 请求路径 todo
		//String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_CRM_URL) + AUTO_INSERT_INVEST_SYS;
		String requestUrl = "";
		InvestSysUtils.noRetPost(requestUrl,params);
	}
	/**
	 * 自动投资更新同步crm接口
	 */
	public static void updateInvestSys(String debtinfo) {
		Map<String, String> params = new HashMap<String, String>();
		// 需要保存的计划投资对象转json字符串
		params.put("debtinfo", debtinfo);
		// 请求路径 todo
		//String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_CRM_URL) + AUTO_UPDATE_INVEST_SYS;
		String requestUrl = "";
		InvestSysUtils.noRetPost(requestUrl,params);
	}

	/**
	 * 无需等待返回的http请求
	 * @param requestUrl
	 * @param paramsMap
	 */
	public static void noRetPost(String requestUrl, Map<String,String> paramsMap) {
		ExecutorService exec = Executors.newFixedThreadPool(15);
		NoRetTask task = new NoRetTask(requestUrl,paramsMap);
		exec.execute(task);
	}

}

/**
 * 无需等待返回的http请求类
 * @author zhangjinpeng
 *
 */
class NoRetTask implements Runnable {
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


