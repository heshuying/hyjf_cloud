package com.hyjf.pay.lib.duiba.sdk;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditTool {

	private String appKey;
	private String appSecret;
	public CreditTool(String appKey,String appSecret){
		this.appKey=appKey;
		this.appSecret=appSecret;
	}
	
	/**
	 * 通用的url生成方法
	 * @param url
	 * @param params
	 * @return
	 */
	public String buildUrlWithSign(String url,Map<String, String> params){
		Map<String, String> newparams=new HashMap<String, String>(params);
		newparams.put("appKey", appKey);
		newparams.put("appSecret", appSecret);
		if(newparams.get("timestamp")==null){
			newparams.put("timestamp", System.currentTimeMillis()+"");
		}
		String sign=SignTool.sign(newparams);
		newparams.put("sign", sign);

		newparams.remove("appSecret");

		return AssembleTool.assembleUrl(url, newparams);
	}
	
	
	/**
	 *  构建在兑吧商城自动登录的url地址
	 * @param uid 用户id
	 * @param dbredirect 免登陆接口回传回来 dbredirect参数
	 * @param credits 用户积分余额
	 * @return 自动登录的url地址
	 */
	public String buildAutoLoginRequest(String uid, Long credits, String dbredirect){
		String url="https://activity.m.duiba.com.cn/autoLogin/autologin?";
		Map<String, String> params=new HashMap<String, String>();
		params.put("uid", uid);
		params.put("credits", credits+"");
		if(dbredirect!=null){
			params.put("redirect", dbredirect);
		}
		String autologinUrl= buildUrlWithSign(url,params);
		return autologinUrl;
	}
	
	
	/**
	 * 构建向兑吧查询兑换订单状态的url地址
	 * @param orderNum 兑吧的订单号
	 * @return
	 */
	public  String buildCreditOrderStatusRequest(String orderNum,String bizId){
		if(orderNum==null){
			orderNum="";
		}
		if(bizId==null){
			bizId="";
		}
		String url="https://activity.m.duiba.com.cn/status/orderStatus?";
		Map<String, String> params=new HashMap<String, String>();
		params.put("orderNum", orderNum);
		params.put("bizId", bizId);
		String buildUrl = buildUrlWithSign(url,params);
		return buildUrl;
	}
	/**
	 * 构建开发者审核结果的的方法
	 * @param params
	 * @return 发起请求的url
	 */
	public String buildCreditAuditRequest(CreditAuditParams params){
		String url="https://activity.m.duiba.com.cn/audit/apiAudit?";
		Map<String, String> signParams=new HashMap<String, String>();
		if(params.getPassOrderNums()!=null && params.getPassOrderNums().size()>0){
			String s=null;
			for(String o:params.getPassOrderNums()){
				if(s==null){
					s=o;
				}else{
					s+=","+o;
				}
			}
			signParams.put("passOrderNums", s);
		}else{
			signParams.put("passOrderNums", "");
		}
		if(params.getRejectOrderNums()!=null && params.getRejectOrderNums().size()>0){
			String s=null;
			for(String o:params.getRejectOrderNums()){
				if(s==null){
					s=o;
				}else{
					s+=","+o;
				}
			}
			signParams.put("rejectOrderNums", s);
		}else{
			signParams.put("rejectOrderNums", "");
		}
		String	buildUrl = buildUrlWithSign(url,signParams);
		return buildUrl;
	}
	/**
	 * 构建开发者向兑吧发起虚拟商品充值成功/失败的确认通知请求
	 * @param p
	 * @return
	 */
	public String buildCreditConfirmRequest(CreditConfirmParams p){
		String url="https://activity.m.duiba.com.cn/confirm/confirm?";
		Map<String, String> params=new HashMap<String, String>();
		params.put("success", p.isSuccess()+"");
		params.put("errorMessage", p.getErrorMessage());
		params.put("orderNum", p.getOrderNum());
		String bulidurl = buildUrlWithSign(url,params);
		return bulidurl;
	}

	
	
	
	/**
	 * 前置商品查询URL
	 * @param count 查询返回的商品数量，最大支持60个
	 * @return
	 */
	public String queryForFrontItem(String count) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("count", count);
		String url = buildUrlWithSign(
				"https://activity.m.duiba.com.cn/queryForFrontItem/query?", params);
		return url;
	}
	
	
	
	/**
	 * 构建向兑吧请求增加活动次数的url地址
	 * @param uid ：用户id   activityId:活动id, times:增加活动次数, bizId：本次请求开发者订单号，保证唯一性
	 * @return
	 */
	
	public String getActivityTimes(String uid ,String activityId, String validType, String times, String bizId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", uid);
		params.put("bizId", bizId);
		params.put("activityId", activityId);
		params.put("validType", validType);
		params.put("times", times);
		String url = buildUrlWithSign(
				"https://activity.m.duiba.com.cn/activityVist/addTimes?", params);
		return url;
		
	}
		
	/**
	 * 自有商品批量取消发货
	 * @param
	 * orderNums 最大支持100个
	 * 方法中，对于超过100的会自动截取前100个
	 * @return
	 */
	public  String batchCancel(List<String> orderNums) {
		Map<String, String> params = new HashMap<String, String>();
		if(orderNums.size()>100)
		orderNums= orderNums.subList(0, 100);
		
		StringBuffer orders = new StringBuffer();
		
		for (String orderNum:orderNums) 
			orders.append(orderNum).append(",");
		
		params.put("orderNums", orders.deleteCharAt(orders.length()-1).toString());
		String url = buildUrlWithSign(
				"https://activity.m.duiba.com.cn/sendObject/batchCancel?", params);
		return url;
	}

	/**
	 * 自有商品批量发货
	 * @params info 格式如下
	 * 发货的数量，每次请求不超过100个
	 * 方法中，对于超过100的会自动截取前100个
	 * @return
	 */
	public  String batchSend(List<ExpressInfo> infos) {
		Map<String, String> params = new HashMap<String, String>();
		StringBuffer expressInfo = new StringBuffer();
		if(infos.size()>100){
			infos = infos.subList(0, 100);
		}
		for (ExpressInfo info:infos) {
			expressInfo.append(info);
		}
		expressInfo.deleteCharAt(expressInfo.length()-1);
		params.put("expressInfo", expressInfo.toString());
		String url = buildUrlWithSign(
				"https://activity.m.duiba.com.cn/sendObject/batchSend?", params);
		return url;
	}			
	
	
	
	//==============================================请求解析方法===============================================================//
	
	/**
	 * 积分消耗请求的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CreditConsumeParams parseCreditConsume(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		CreditConsumeParams params=new CreditConsumeParams();
		params.setAppKey(appKey);
		params.setUid(request.getParameter("uid"));
		params.setCredits(Long.valueOf(request.getParameter("credits")));
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		params.setDescription(request.getParameter("description"));
		params.setOrderNum(request.getParameter("orderNum"));
		params.setType(request.getParameter("type"));
		if(request.getParameter("facePrice")!=null){
			params.setFacePrice(Integer.valueOf(request.getParameter("facePrice")));
		}
		if(request.getParameter("actualPrice")!=null){
			params.setActualPrice(Integer.valueOf(request.getParameter("actualPrice")));
		}
		if(request.getParameter("itemCode")!=null){
			params.setItemCode(request.getParameter("itemCode"));
		}
		params.setAlipay(request.getParameter("alipay"));
		params.setPhone(request.getParameter("phone"));
		params.setQq(request.getParameter("qq"));
		if(request.getParameter("waitAudit")!=null){
			params.setWaitAudit(Boolean.valueOf(request.getParameter("waitAudit")));
		}
		params.setIp(request.getParameter("ip"));
		params.setParams(request.getParameter("params"));
		if(request.getParameter("transfer")!=null){
			params.setTransfer(request.getParameter("transfer"));
		}
		return params;
	}
	/**
	 * 积分消耗结果通知请求  的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CreditNotifyParams parseCreditNotify(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		
		CreditNotifyParams params=new CreditNotifyParams();
		params.setSuccess(Boolean.valueOf(request.getParameter("success")));
		params.setErrorMessage(request.getParameter("errorMessage"));
		params.setBizId(request.getParameter("bizId"));
		params.setUid(request.getParameter("uid"));
		params.setAppKey(request.getParameter("appKey"));
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		params.setOrderNum(request.getParameter("orderNum"));
		if(request.getParameter("transfer")!=null){
			params.setTransfer(request.getParameter("transfer"));
		}
		return params;
	}
	/**
	 * 需要审核的兑换 的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CreditNeedAuditParams parseCreditAudit(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		
		CreditNeedAuditParams params=new CreditNeedAuditParams();
		params.setAppKey(appKey);
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		params.setBizId(request.getParameter("bizId"));
		
		return params;
	}
	
	
	
	/**
	 * 虚拟商品充值解析
	 * @param request
	 * @return
	 * @throws Exception 
	 */
		public VirtualParams virtualConsume(HttpServletRequest request) throws Exception{
			if(!appKey.equals(request.getParameter("appKey"))){
				throw new Exception("appKey不匹配");
			}
			if(request.getParameter("timestamp")==null){
				throw new Exception("请求中没有带时间戳");
			}
			boolean verify=SignTool.signVerify(appSecret, request);
			if(!verify){
				throw new Exception("签名验证失败");
			}
			VirtualParams params=new VirtualParams();
			params.setAppKey(appKey);
			params.setUid(request.getParameter("uid"));
			params.setDevelopBizId(request.getParameter("developBizId"));
			params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
			params.setDescription(request.getParameter("description"));
			params.setOrderNum(request.getParameter("orderNum"));
			if(request.getParameter("account")!=null){
				params.setAccount(request.getParameter("account"));	
			}
			params.setParams(request.getParameter("params"));
			if(request.getParameter("transfer")!=null){
				params.setTransfer(request.getParameter("transfer"));
			}
			return params;
		}

		
		/**
		 * 加积分接口解析
		 * @param request
		 * @return
		 * @throws Exception 
		 */	
	
	public AddCreditsParams parseaddCredits(HttpServletRequest request) throws Exception {
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		AddCreditsParams params=new AddCreditsParams();
		params.setAppKey(appKey);
		params.setUid(request.getParameter("uid"));
		params.setCredits(Long.valueOf(request.getParameter("credits")));
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		params.setDescription(request.getParameter("description"));
		params.setOrderNum(request.getParameter("orderNum"));
		params.setType(request.getParameter("type"));
		if(request.getParameter("transfer")!=null){
			params.setTransfer(request.getParameter("transfer"));
		}
		return params;
	}
	
	
	

	/**
	 * 虚拟卡消耗请求的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public VirtualCardConsumeParams parseVirtualCardConsume(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		VirtualCardConsumeParams params=new VirtualCardConsumeParams();
		params.setAppKey(appKey);
		params.setUid(request.getParameter("uid"));
		params.setCount(Long.valueOf(request.getParameter("count")));
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		params.setDescription(request.getParameter("description"));
		params.setOrderNum(request.getParameter("orderNum"));
		params.setType(request.getParameter("type"));
		if(request.getParameter("waitAudit")!=null){
			params.setWaitAudit(Boolean.valueOf(request.getParameter("waitAudit")));
		}
		params.setIp(request.getParameter("ip"));
		params.setParams(request.getParameter("params"));
		if(request.getParameter("transfer")!=null){
			params.setTransfer(request.getParameter("transfer"));
		}
		return params;
	}
	/**
	 * 虚拟卡消耗结果通知请求的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public VirtualCardNotifyParams parseVirtualCardNotify(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}

		VirtualCardNotifyParams params=new VirtualCardNotifyParams();
		params.setSuccess(Boolean.valueOf(request.getParameter("success")));
		params.setErrorMessage(request.getParameter("errorMessage"));
		params.setBizId(request.getParameter("bizId"));
		params.setUid(request.getParameter("uid"));
		params.setAppKey(request.getParameter("appKey"));
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		params.setOrderNum(request.getParameter("orderNum"));
		return params;
	}
	
}
