package com.hyjf.soa.apiweb;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommonSoaUtils {
	private static Logger logger = LoggerFactory.getLogger(CommonSoaUtils.class);

	private static String SOA_INTERFACE_KEY;
	
	private static String HYJF_API_WEB_URL="http://localhost:8081";
	
	private static final String COUPON_CHECK = "/invest/validateCoupon.json";

	private static final String COUPON_SEND_USER = "userCouponServer/userCouponSend.json";

	private static final String BANK_SYNBALANCE = "/server/synbalance/synbalance.do";

	// 优惠券出借接口
	private static final String COUPON_INVEST = "/invest/couponTender.json";

	// 优惠券出借接口
	private static final String  SYNBALANCE= "/hyjf-api/server/synbalance/synbalance.do";

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

	/**
	 * 出借校验优惠券
	 */
	public static JSONObject CheckCoupon(String userId, String borrowNid, String money, String platform,
			String couponGrantId) {
		Map<String, String> params = new HashMap<String, String>();
		// 用户编号
		params.put("userId", userId);
		// 项目编号
		params.put("borrowNid", borrowNid);
		// 出借金额
		params.put("money", money);
		// 平台标识
		params.put("platform", platform);
		// 优惠券id
		params.put("couponGrantId", couponGrantId);
		String timestamp = GetDate.getNowTime10() + "";
		// 时间戳
		params.put("timestamp", timestamp);
		// 发放优惠券url
		String requestUrl = HYJF_API_WEB_URL + CommonSoaUtils.COUPON_CHECK;
		String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + borrowNid + money + platform
				+ couponGrantId + timestamp + SOA_INTERFACE_KEY));
		params.put("chkValue", sign);
		logger.info("优惠券出借校验调用:" + requestUrl);
		// 0:成功，1：失败
		String result = HttpClientUtils.post(requestUrl, params);
		JSONObject status = JSONObject.parseObject(result);
		return status;

	}

	

	/**
     * 优惠券出借(PC+APP)
     */
    public static JSONObject CouponInvestForPC(String userId, String borrowNid, String money, String platform,
            String couponGrantId, String ordId, String ip, String couponOldTime) {
        Map<String, String> params = new HashMap<String, String>();
        // 用户编号
        params.put("userId", userId);
        // 项目编号
        params.put("borrowNid", borrowNid);
        // 出借金额
        params.put("money", money);
        // 平台标识
        params.put("platform", platform);
        // 优惠券id
        params.put("couponGrantId", couponGrantId);
        // 订单号
        params.put("ordId", ordId);
        // ip
        params.put("ip", ip);
        // 排他check
        params.put("couponOldTime", couponOldTime);
        String timestamp = GetDate.getNowTime10() + "";
        // 时间戳
        params.put("timestamp", timestamp);
        // 优惠券出借url
        String requestUrl = HYJF_API_WEB_URL + CommonSoaUtils.COUPON_INVEST;
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + borrowNid + money + platform
                + couponGrantId + ip + ordId + couponOldTime + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        logger.info("优惠券出借调用:" + requestUrl);

        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
        
    }


	public static void sendUserCouponNoRet(CommonParamBean paramBean) {
		Map<String, String> params = new HashMap<String, String>();
		// 用户编号
		params.put("userId", paramBean.getUserId());
		// 发放优惠券的类别标识
		params.put("sendFlg", String.valueOf(paramBean.getSendFlg()));
		if (paramBean.getVipId() != null) {
			// vip编号
			params.put("vipId", String.valueOf(paramBean.getVipId()));
		}
		// 发放优惠券的类别 子区分
		if(paramBean.getSubFlg() != null){
			params.put("subFlg", String.valueOf(paramBean.getSubFlg()));
		}
		if (paramBean.getPrizeGroupCode() != null) {
			// 奖品组编号
			params.put("prizeGroupCode", String.valueOf(paramBean.getPrizeGroupCode()));
		}
		if (paramBean.getCouponCode() != null) {
			// 优惠券编号
			params.put("couponCode", String.valueOf(paramBean.getCouponCode()));
		}
		if(paramBean.getSendCount() != null){
			params.put("sendCount", String.valueOf(paramBean.getSendCount()));
		}
		if(paramBean.getActivityId() != null){
			params.put("activityId", String.valueOf(paramBean.getActivityId()));
		}
		if(paramBean.getCouponSource() != null){
			params.put("couponSource", String.valueOf(paramBean.getCouponSource()));
		}
		if(StringUtils.isNotEmpty(paramBean.getRemark())){
			params.put("remark", paramBean.getRemark());
		}
		// 发放优惠券url

		String webUrl = HYJF_API_WEB_URL;
		webUrl = StringUtils.endsWith(webUrl, "/") ? webUrl : webUrl + "/";
		String requestUrl = webUrl + COUPON_SEND_USER;
		logger.info("--------调用apiweb接口发放优惠券,用户编号："+paramBean.getUserId()+"------------");
		logger.info("apiweb接口url：" + requestUrl);
		String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + paramBean.getUserId() + paramBean.getSendFlg() + SOA_INTERFACE_KEY));
		params.put("sign", sign);
		logger.info("-----------------调用apiweb接口发放优惠券----------------");
		CommonSoaUtils.noRetPost(requestUrl, params);
	}
	/**
	 * 银行存管同步余额（异步）
	 */
	public static void synBalance(Integer userId) {
		logger.info("-----------------调用apiweb接口同步余额异步userId:"+userId+"----------------");
		Map<String, String> params = new HashMap<String, String>();
		// 用户编号
		params.put("userId", userId+"");
		String timestamp = GetDate.getNowTime10() + "";
		// 时间戳
		params.put("timestamp", timestamp);
		// 发放优惠券url
		String webUrl = HYJF_API_WEB_URL;
		webUrl = StringUtils.endsWith(webUrl, "/") ? webUrl : webUrl + "/";
		String requestUrl = webUrl + CommonSoaUtils.BANK_SYNBALANCE;

		logger.info("apiweb接口url：" + requestUrl);
		String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
		params.put("chkValue", sign);
		logger.info("-----------------调用apiweb接口同步余额异步----------------");
		CommonSoaUtils.noRetPost(requestUrl, params);
	}

	public static JSONObject synBalanceRetPost(String account, String instCode, String webHost, String aopAccesskey) {
		Map<String, String> params = new HashMap<String, String>();
		String timestamp = GetDate.getNowTime10() + "";
		// 时间戳
		params.put("timestamp", timestamp);
		params.put("accountId", account);
		params.put("instCode", instCode);
		// 优惠券出借url
		String requestUrl = webHost + CommonSoaUtils.SYNBALANCE;
		String sign = StringUtils.lowerCase(MD5.toMD5Code(aopAccesskey + account + instCode + timestamp + aopAccesskey));
		params.put("chkValue", sign);
		logger.info("同步余额调用:" + requestUrl);

		String result = HttpClientUtils.post(requestUrl, params);
		JSONObject status = JSONObject.parseObject(result);
		return status;
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


