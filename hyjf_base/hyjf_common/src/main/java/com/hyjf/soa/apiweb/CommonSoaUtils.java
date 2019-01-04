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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommonSoaUtils {
	private static Logger logger = LoggerFactory.getLogger(CommonSoaUtils.class);

	private static String SOA_INTERFACE_KEY;
	
	private static String HYJF_API_WEB_URL;
	
	private static final String COUPON_CHECK = "/invest/validateCoupon.json";

	private static final String BANK_SYNBALANCE = "/server/synbalance/synbalance.do";

	// 优惠券出借接口
	private static final String COUPON_INVEST = "/invest/couponTender.json";

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
}


