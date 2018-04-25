package com.hyjf.soa.apiweb;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.security.utils.MD5;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.PropUtils;
import com.hyjf.common.util.PropertiesConstants;
import com.hyjf.common.validator.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CommonSoaUtils {
	private static Logger _log = LoggerFactory.getLogger(CommonSoaUtils.class);  
	public static final String SOA_COUPON_KEY;
	public static final String SOA_INTERFACE_KEY;
	private static ExecutorService exec;
	static{
		SOA_COUPON_KEY = PropUtils.getSystem("release.coupon.accesskey");
		SOA_INTERFACE_KEY = PropUtils.getSystem("aop.interface.accesskey");
		exec = Executors.newFixedThreadPool(50);
	}

	// 优惠券发放接口
	private static final String COUPON_SEND_USER = "userCouponServer/userCouponSend.json";
	// 优惠券发放接口
	private static final String COUPON_BATCH_SEND_USER = "/userCouponServer/batchUserCouponSend.json";
	// 优惠券校验接口
	private static final String COUPON_CHECK = "/invest/validateCoupon.json";
	// 优惠券投资接口
	private static final String COUPON_INVEST = "/invest/couponTender.json";
	// 优惠券投资接口
	private static final String INVITE_USER = "/registRecommend/inviteUser.json";
	/*// 优惠券放款接口
	private static final String COUPON_LOANS = "/coupon/loans/borrowLoansForCoupon.json";
	// 优惠券还款接口
	private static final String COUPON_REPAY = "/coupon/repay/borrowRepayForCoupon.json";
	// 汇添金优惠券还款接口
	private static final String COUPON_REPAY_HTJ = "/coupon/repay/couponRepayForHtj.json";*/
	// 体验金按收益期限还款接
	private static final String COUPON_ONLY_REPAY = "/coupon/repay/borrowRepayForCouponOnly.json";
	// 投资更新V值
	private static final String VIP_VALUE = "/coupon/loans/tenderVipValue.json";
	
	// 获取当天谜题信息
    private static final String GET_PRESENT_RIDDLES = "/lanternfestival/getPresentRiddles.json";
    // 返回今天用户是否答题表示
    private static final String GET_TODAY_USER_ANSWER_FLAG = "/lanternfestival/getTodayUserAnswerFlag.json";
    // 获取用户累计获得优惠券信息
    private static final String GET_USER_PRESENT_CUMULATIVE_COUPON = "/lanternfestival/getUserPresentCumulativeCoupon.json";
    // 获取用户灯笼点亮列表
    private static final String GET_USER_LANTERN_ILLUMINE_LIST = "/lanternfestival/getUserLanternIllumineList.json";
    // 修改用户答题信息
    private static final String UPDATE_USER_ANSWER_RECORD = "/lanternfestival/updateUserAnswerRecord.json";
    // 记录用户答题信息
    private static final String INSERT_USER_ANSWER_RECORD_INIT = "/lanternfestival/insertUserAnswerRecordInit.json";
    // 校验用户答题
    private static final String CHECK = "/lanternfestival/check.json";
    
    // 新年活动注册且开户发放财神卡
    private static final String NEWYEAR_SEND_CARD = "/get/card/newyearSendCard.json";
    // 新年活动注册或邀请好友注册
    private static final String NEWYEAR_REGIST = "/get/card/newyearRegist.json";
    // 新年活动投资发放财神卡
    private static final String NEWYEAR_TENDER = "/get/card/newyearTender.json";
    
    
    
    /*******************************汇添金优惠券使用start pcc********************************/
    // 获取用户优惠券总张数
    private static final String PLAN_COUNT_COUPON_USERS = "/plan/coupon/countCouponUsers.json";
    // 获取当前标的可用优惠券数量
    private static final String PLAN_GET_USER_COUPON_AVAILABLE_COUNT = "/plan/coupon/getUserCouponAvailableCount.json";
    // 获取当最优优惠券
    private static final String PLAN_GET_BEST_COUPON = "/plan/coupon/getBestCoupon.json";
    // 计算优惠券预期收益
    private static final String PLAN_GET_COUPON_INTEREST = "/plan/coupon/getCouponInterest.json";
    // 获取用户优惠券可用列表和不可以列表
    private static final String PLAN_GET_PROJECT_AVAILABLE_USER_COUPON = "/plan/coupon/getProjectAvailableUserCoupon.json";
    
    // 汇添金优惠券校验接口
    private static final String PLAN_COUPON_CHECK = "/plan/coupon/validateCoupon.json";
    // 汇添金优惠券投资接口
    private static final String PLAN_COUPON_INVEST = "/plan/coupon/couponTender.json";
    // 汇添金优惠券还款收益及时间更新
    private static final String PLAN_COUPON_RECOVER = "/plan/coupon/updateCouponRecoverHtj.json";
    
    /*******************************汇添金优惠券使用end pcc********************************/
    
    /*******************************风险测评start  pcc********************************/
    // 返回用户是否测评标识
    private static final String GET_USEREVALATIONRESULT_BY_USERID = "financialAdvisor/getUserEvalationResultByUserId.json";
    
    /*******************************风险测评end  pcc********************************/
    /*******************************银行存管start  pcc********************************/
    // 同步余额异步调用
    private static final String BANK_SYNBALANCE = "synbalance/synbalance.json";
    private static final String BANKACCUNOUT_SYNBALANCE = "synbalance/synbankbalance.json";
    
    /** 同步用户线下充值交易明细及异常处理对账（后台admin用*/
    public static final String SYNBALANCE_EXCEPTION = "synbalance/synexception.do";
    // 快捷银行列表
    private static final String BANK_LIST = "/quickbanklist/getbanklist.json";
    /*******************************风险测评end  pcc********************************/
	/**
	 * 发放优惠券（同步）
	 */
    //上市活动2充值
    private static final String CHONGZHI = "listed2/recharge";
    //上市活动2提现
    private static final String TIXIAN = "listed2/withdraw";
    //上市活动2投资
    private static final String TOUZI = "listed2/investment";
    
	public static String sendUserCoupon(CommonParamBean CommonParamBean) {
		Map<String, String> params = new HashMap<String, String>();
		// 用户编号
		params.put("userId", CommonParamBean.getUserId());
		// 发放优惠券的类别标识
		if(Validator.isNotNull(CommonParamBean.getSendFlg())){
		    params.put("sendFlg", String.valueOf(CommonParamBean.getSendFlg()));
		}
		if (CommonParamBean.getVipId() != null) {
			// vip编号
			params.put("vipId", String.valueOf(CommonParamBean.getVipId()));
		}
		// 发放优惠券的类别 子区分
		if(CommonParamBean.getSubFlg() != null){
			params.put("subFlg", String.valueOf(CommonParamBean.getSubFlg()));
		}
		if (CommonParamBean.getPrizeGroupCode() != null) {
			// 奖品组编号
			params.put("prizeGroupCode", String.valueOf(CommonParamBean.getPrizeGroupCode()));
		}
		if (CommonParamBean.getCouponCode() != null) {
			// 优惠券编号
			params.put("couponCode", String.valueOf(CommonParamBean.getCouponCode()));
		}
		if(CommonParamBean.getSendCount() != null){
			params.put("sendCount", String.valueOf(CommonParamBean.getSendCount()));
		}
		if(CommonParamBean.getActivityId() != null){
		    params.put("activityId", String.valueOf(CommonParamBean.getActivityId()));
		}
		if(CommonParamBean.getCouponSource() != null){
		    params.put("couponSource", String.valueOf(CommonParamBean.getCouponSource()));
		}
		if(StringUtils.isNotEmpty(CommonParamBean.getRemark())){
		    params.put("remark", CommonParamBean.getRemark());
		}
		// 发放优惠券url
		String webUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
		webUrl = StringUtils.endsWith(webUrl, "/") ? webUrl : webUrl + "/";
		String requestUrl = webUrl + CommonSoaUtils.COUPON_SEND_USER;
		_log.info("--------调用apiweb接口发放优惠券,用户编号："+CommonParamBean.getUserId()+"------------");
		_log.info("apiweb接口url：" + requestUrl);
		String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_COUPON_KEY + CommonParamBean.getUserId()
				+ CommonParamBean.getSendFlg() + SOA_COUPON_KEY));
		params.put("sign", sign);
		_log.info("-----------------调用apiweb接口发放优惠券----------------");
		// 0:成功，1：失败
		return HttpClientUtils.post(requestUrl, params);
	}
	
	/**
	 * 发放优惠券（异步）
	 */
	public static void sendUserCouponNoRet(CommonParamBean CommonParamBean) {
		Map<String, String> params = new HashMap<String, String>();
		// 用户编号
		params.put("userId", CommonParamBean.getUserId());
		// 发放优惠券的类别标识
		params.put("sendFlg", String.valueOf(CommonParamBean.getSendFlg()));
		if (CommonParamBean.getVipId() != null) {
			// vip编号
			params.put("vipId", String.valueOf(CommonParamBean.getVipId()));
		}
		// 发放优惠券的类别 子区分
        if(CommonParamBean.getSubFlg() != null){
            params.put("subFlg", String.valueOf(CommonParamBean.getSubFlg()));
        }
        if (CommonParamBean.getPrizeGroupCode() != null) {
            // 奖品组编号
            params.put("prizeGroupCode", String.valueOf(CommonParamBean.getPrizeGroupCode()));
        }
        if (CommonParamBean.getCouponCode() != null) {
            // 优惠券编号
            params.put("couponCode", String.valueOf(CommonParamBean.getCouponCode()));
        }
        if(CommonParamBean.getSendCount() != null){
            params.put("sendCount", String.valueOf(CommonParamBean.getSendCount()));
        }
        if(CommonParamBean.getActivityId() != null){
            params.put("activityId", String.valueOf(CommonParamBean.getActivityId()));
        }
        if(CommonParamBean.getCouponSource() != null){
            params.put("couponSource", String.valueOf(CommonParamBean.getCouponSource()));
        }
        if(StringUtils.isNotEmpty(CommonParamBean.getRemark())){
            params.put("remark", CommonParamBean.getRemark());
        }
		// 发放优惠券url
		String webUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
		webUrl = StringUtils.endsWith(webUrl, "/") ? webUrl : webUrl + "/";
		String requestUrl = webUrl + CommonSoaUtils.COUPON_SEND_USER;
		_log.info("--------调用apiweb接口发放优惠券,用户编号："+CommonParamBean.getUserId()+"------------");
		_log.info("apiweb接口url：" + requestUrl);
		String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_COUPON_KEY + CommonParamBean.getUserId()
				+ CommonParamBean.getSendFlg() + SOA_COUPON_KEY));
		params.put("sign", sign);
		_log.info("-----------------调用apiweb接口发放优惠券----------------");
		CommonSoaUtils.noRetPost(requestUrl, params);
	}

	/**
	 * 投资校验优惠券
	 */
	public static JSONObject CheckCoupon(String userId, String borrowNid, String money, String platform,
			String couponGrantId) {
		Map<String, String> params = new HashMap<String, String>();
		// 用户编号
		params.put("userId", userId);
		// 项目编号
		params.put("borrowNid", borrowNid);
		// 投资金额
		params.put("money", money);
		// 平台标识
		params.put("platform", platform);
		// 优惠券id
		params.put("couponGrantId", couponGrantId);
		String timestamp = GetDate.getNowTime10() + "";
		// 时间戳
		params.put("timestamp", timestamp);
		// 发放优惠券url
		String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_CHECK;
		String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + borrowNid + money + platform
				+ couponGrantId + timestamp + SOA_INTERFACE_KEY));
		params.put("chkValue", sign);
		_log.info("优惠券投资校验调用:" + requestUrl);
		// 0:成功，1：失败
		String result = HttpClientUtils.post(requestUrl, params);
		JSONObject status = JSONObject.parseObject(result);
		return status;

	}
	
	
	

	/**
	 * 优惠券投资
	 */
	public static JSONObject CouponInvest(String userId, String borrowNid, String money, String platform,
			String couponGrantId, String ordId, String ip, String couponOldTime) {
		Map<String, String> params = new HashMap<String, String>();
		// 用户编号
		params.put("userId", userId);
		// 项目编号
		params.put("borrowNid", borrowNid);
		// 投资金额
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
		// 发放优惠券url
		String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_INVEST;
		String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + borrowNid + money + platform
				+ couponGrantId + ip + ordId + couponOldTime + timestamp + SOA_INTERFACE_KEY));
		params.put("chkValue", sign);
		_log.info("优惠券投资调用:" + requestUrl);
		// 0:成功，1：失败
		String intmin = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_TIME);
		// add by zhangjp 优惠券投资 end
		BigDecimal decimalAccount = StringUtils.isNotEmpty(money) ? new BigDecimal(money) : BigDecimal.ZERO;
		if (decimalAccount.compareTo(BigDecimal.ZERO) != 1) {
			String result = HttpClientUtils.post(requestUrl, params);
			JSONObject status = JSONObject.parseObject(result);
			return status;
		} else {
			ExecutorService exec = Executors.newCachedThreadPool();
			Task task = new Task();
			task.init(requestUrl, params);
			Future<String> future = exec.submit(task);
			String taskResult = null;
			String failReason = null;
			try {
				// 等待计算结果，最长等待timeout秒，timeout秒后中止任务
				taskResult = future.get(Integer.parseInt(intmin), TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				failReason = "主线程在等待计算结果时被中断！";
			} catch (ExecutionException e) {
				failReason = "主线程等待计算结果，但计算抛出异常！";
			} catch (TimeoutException e) {
				failReason = "主线程等待计算结果超时，因此中断任务线程！";
				exec.shutdownNow();
			}

			_log.info("\ntaskResult : " + taskResult);
			_log.info("failReason : " + failReason);
			return null;
		}

	}
	
	/**
     * 优惠券投资(PC+APP)
     */
    public static JSONObject CouponInvestForPC(String userId, String borrowNid, String money, String platform,
            String couponGrantId, String ordId, String ip, String couponOldTime) {
        Map<String, String> params = new HashMap<String, String>();
        // 用户编号
        params.put("userId", userId);
        // 项目编号
        params.put("borrowNid", borrowNid);
        // 投资金额
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
        // 优惠券投资url
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_INVEST;
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + borrowNid + money + platform
                + couponGrantId + ip + ordId + couponOldTime + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        _log.info("优惠券投资调用:" + requestUrl);

        
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
        

    }
	
	/**
	 * 注册开户发放推荐星接口
	 */
	public static void sendRecommend(String inviteByUser,String inviteUser) {
		String timestamp = String.valueOf(GetDate.getNowTime10());
		String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + inviteUser + inviteByUser + timestamp + SOA_INTERFACE_KEY));
		Map<String, String> params = new HashMap<String, String>();
		// 推荐人编号
		params.put("inviteUser", inviteUser);
		// 注册用户编号
		params.put("inviteByUser", inviteByUser);
		// 时间戳
		params.put("timestamp", timestamp);
		// 签名
		params.put("chkValue", chkValue);
		// 请求路径
		String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.INVITE_USER;
		CommonSoaUtils.noRetPost(requestUrl,params);
	}
	
	/**
	 * 优惠券放款(已改为调用mq服务)
	 */
	public static void couponLoans(String borrowNid) {
		/*String timestamp = String.valueOf(GetDate.getNowTime10());
		String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + borrowNid + timestamp + SOA_INTERFACE_KEY));
		Map<String, String> params = new HashMap<String, String>();
		// 借款项目编号
		params.put("borrowNid", borrowNid);
		// 时间戳
		params.put("timestamp", timestamp);
		// 签名
		params.put("chkValue", chkValue);
		// 请求路径
		String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_LOANS;
		_log.info("开始请求优惠券放款接口，url：" + requestUrl + " params: " + params);
		CommonSoaUtils.noRetPost(requestUrl,params);*/
	}
	
	/**
	 * 直投类优惠券还款（已改为调用mq服务）
	 */
	public static void couponRepay(String borrowNid,Integer periodNow) {
		/*String timestamp = String.valueOf(GetDate.getNowTime10());
		String periodNowStr = periodNow == null ? StringUtils.EMPTY : periodNow.toString();
		String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + borrowNid + periodNow + timestamp + SOA_INTERFACE_KEY));
		Map<String, String> params = new HashMap<String, String>();
		// 借款项目编号
		params.put("borrowNid", borrowNid);
		// 当前期
		params.put("periodNow", periodNowStr);
		// 时间戳
		params.put("timestamp", timestamp);
		// 签名
		params.put("chkValue", chkValue);
		// 请求路径
		String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_REPAY;
		_log.info("开始请求直投类优惠券还款接口，url：" + requestUrl + " params: " + params);
		CommonSoaUtils.noRetPost(requestUrl,params);*/
	}
	
	/**
	 * 汇添金优惠券还款(已改成调用mq)
	 */
	public static void couponRepayHTJ(String planNid) {
		/*String timestamp = String.valueOf(GetDate.getNowTime10());
		String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + planNid + timestamp + SOA_INTERFACE_KEY));
		Map<String, String> params = new HashMap<String, String>();
		// 借款项目编号
		params.put("planNid", planNid);
		// 时间戳
		params.put("timestamp", timestamp);
		// 签名
		params.put("chkValue", chkValue);
		// 请求路径
		String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_REPAY_HTJ;
		_log.info("开始请求汇添金优惠券还款接口，url：" + requestUrl + " params: " + params);
		CommonSoaUtils.noRetPost(requestUrl,params);*/
	}
	
	/**
	 * 体验金按收益期限还款
	 */
	public static void couponOnlyRepay(List<String> nidList) {
		String timestamp = String.valueOf(GetDate.getNowTime10());
		String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + timestamp + SOA_INTERFACE_KEY));
		Map<String, String> params = new HashMap<String, String>();
		// 借款项目编号
		params.put("nidList", JSONObject.toJSONString(nidList));
		// 时间戳
		params.put("timestamp", timestamp);
		// 签名
		params.put("chkValue", chkValue);
		// 请求路径
		String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_ONLY_REPAY;
		_log.info("开始请求体验金按收益期限还款接口，url：" + requestUrl + " params: " + params);
		HttpClientUtils.post(requestUrl, params);
	}
	
	/**
	 * 投资更新V值
	 */
	public static void updateVipValue(String nid,Integer userId) {
		String timestamp = String.valueOf(GetDate.getNowTime10());
		String userIdStr = userId == null ? StringUtils.EMPTY : userId.toString();
		String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + nid + userId + timestamp + SOA_INTERFACE_KEY));
		Map<String, String> params = new HashMap<String, String>();
		// 借款项目编号
		params.put("nid", nid);
		// 当前期
		params.put("userId", userIdStr);
		// 时间戳
		params.put("timestamp", timestamp);
		// 签名
		params.put("chkValue", chkValue);
		// 请求路径
		String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.VIP_VALUE;
		CommonSoaUtils.noRetPost(requestUrl,params);
	}
	
	/**
     * 获取当天谜题信息
     */
    public static JSONObject getPresentRiddles() {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.GET_PRESENT_RIDDLES;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
	
    /**
     * 获取当前用户今天是否答过题
     */
    public static JSONObject getTodayUserAnswerFlag(Integer userId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId+"");
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.GET_TODAY_USER_ANSWER_FLAG;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    /**
     * 获取用户累计获得优惠券信息
     */
    public static JSONObject getUserPresentCumulativeCoupon(Integer userId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();

        // 用户id
        params.put("userId", userId+"");
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.GET_USER_PRESENT_CUMULATIVE_COUPON;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    /**
     * 批量上传发券接口
     */
    public static JSONObject getBatchCoupons(String userId,Map<String,String> paramsMap) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_COUPON_KEY + userId + timestamp + SOA_COUPON_KEY));
        Map<String, String> params = new HashMap<String, String>();

        // 用户id
        params.put("userId", userId+"");
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("sign", chkValue);
        // 数据
        params.put("usercoupons", paramsMap.get("usercoupons"));
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_BATCH_SEND_USER;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
	
    /**
     * 获取用户灯笼点亮列表
     */
    public static JSONObject getUserLanternIllumineList(Integer userId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();

        // 用户id
        params.put("userId", userId+"");
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.GET_USER_LANTERN_ILLUMINE_LIST;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }

    /**
     * 初始化记录用户答题信息
     */
    public static JSONObject insertUserAnswerRecordInit(Integer userId,Integer questionId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + questionId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 谜题id
        params.put("questionId", questionId+"");
        // 用户id
        params.put("userId", userId+"");
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.INSERT_USER_ANSWER_RECORD_INIT;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    /**
     * 修改用户答题记录
     */
    public static JSONObject updateUserAnswerRecord(Integer userId,String questionId,String userAnswer) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + questionId + userAnswer + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 当前期
        params.put("questionId", questionId+"");
        // 用户id
        params.put("userId", userId+"");
        // 用户答案
        params.put("userAnswer", userAnswer);
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.UPDATE_USER_ANSWER_RECORD;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    /**
     * 校验用户答题
     */
    public static JSONObject check(Integer userId,String questionId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + questionId  + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 当前期
        params.put("questionId", questionId+"");
        // 用户id
        params.put("userId", userId+"");
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.CHECK;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    /**
     * 新年活动注册或邀请好友注册
     * @param userId 推荐人
     * @param inviteUserId 被邀请人
     */
    public static void newyearRegist(Integer userId,String inviteUserId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());
        if(StringUtils.isNotEmpty(inviteUserId)){
        	params.put("inviteUserId", inviteUserId);
        }
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.NEWYEAR_REGIST;
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    
    /**
     * 新年活动注册且开户发放财神卡
     */
    public static void newyearSendCard() {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.NEWYEAR_SEND_CARD;
        HttpClientUtils.post(requestUrl, params);
    }
    
    /**
     * 新年活动投资发放财神卡
     */
    public static void newyearTender(String tenderNid) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + tenderNid + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 时间戳
        params.put("tenderNid", tenderNid);
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.NEWYEAR_TENDER;
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    
    
    
    public static JSONObject countCouponUsers(int usedFlag, Integer userId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + usedFlag  + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 当前期
        params.put("usedFlag", usedFlag+"");
        // 用户id
        params.put("userId", userId+"");
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.PLAN_COUNT_COUPON_USERS;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    
    public static JSONObject getUserCouponAvailableCount(String planNid, Integer userId, String money, String platform) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + planNid + userId + money + platform + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 当前期
        params.put("planNid", planNid);
        // 用户id
        params.put("userId", userId+"");
        // 用户id
        params.put("money", money);
        // 用户id
        params.put("platform", platform);
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.PLAN_GET_USER_COUPON_AVAILABLE_COUNT;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    public static JSONObject getBestCoupon(String planNid, Integer userId, String money, String platform) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        userId =userId==null?0:userId;
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + planNid + userId + money + platform + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 当前期
        params.put("planNid", planNid);
        // 用户id
        params.put("userId", userId+"");
        // 用户id
        params.put("money", money);
        // 用户id
        params.put("platform", platform);
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.PLAN_GET_BEST_COUPON;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    public static JSONObject getCouponInterest(String couponId, String planNid, String money) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + planNid + couponId + money  + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 当前期
        params.put("planNid", planNid);
        // 用户id
        params.put("money", money);
        // 用户id
        params.put("couponGrantId", couponId);
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.PLAN_GET_COUPON_INTEREST;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }

    public static JSONObject getProjectAvailableUserCoupon(String platform, String planNid, String money, Integer userId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + planNid + userId + money + platform + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 当前期
        params.put("planNid", planNid);
        // 用户id
        params.put("money", money);
        // 用户id
        params.put("userId", userId+"");
        // 用户id
        params.put("platform", platform);
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.PLAN_GET_PROJECT_AVAILABLE_USER_COUPON;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    
    /**
     * 汇添金优惠券投资(PC)
     */
    public static JSONObject planCouponInvest(String userId, String planNid, String money, String platform,
            String couponGrantId, String ordId, String ip, String couponOldTime) {
        Map<String, String> params = new HashMap<String, String>();
        // 用户编号
        params.put("userId", userId);
        // 项目编号
        params.put("planNid", planNid);
        // 投资金额
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
        // 发放优惠券url
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.PLAN_COUPON_INVEST;
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + planNid + money + platform
                + couponGrantId + ip + ordId + couponOldTime + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        _log.info("优惠券投资调用:" + requestUrl);

        
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
        

    }
    
    /**
     * 汇添金优惠券收益及还款时间更新(PC)
     */
    public static void planCouponRecover(String planNid) {
        Map<String, String> params = new HashMap<String, String>();
        
        // 项目编号
        params.put("planNid", planNid);
        String timestamp = GetDate.getNowTime10() + "";
        // 时间戳
        params.put("timestamp", timestamp);
        // 发放优惠券url
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.PLAN_COUPON_RECOVER;
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + planNid + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        _log.info("汇添金优惠券收益及还款时间更新:" + requestUrl);
        CommonSoaUtils.noRetPost(requestUrl, params);
    }
    
    
    /**
     * 汇添金投资校验优惠券
     */
    public static JSONObject planCheckCoupon(String userId, String planNid, String money, String platform,
            String couponGrantId) {
        Map<String, String> params = new HashMap<String, String>();
        // 用户编号
        params.put("userId", userId);
        // 项目编号
        params.put("planNid", planNid);
        // 投资金额
        params.put("money", money);
        // 平台标识
        params.put("platform", platform);
        // 优惠券id
        params.put("couponGrantId", couponGrantId);
        String timestamp = GetDate.getNowTime10() + "";
        // 时间戳
        params.put("timestamp", timestamp);
        // 发放优惠券url
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.PLAN_COUPON_CHECK;
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + planNid + money + platform
                + couponGrantId + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        _log.info("优惠券投资校验调用:" + requestUrl);
        // 0:成功，1：失败
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;

    }
    
    /**
     * 返回用户是否测评标识
     */
    public static JSONObject getUserEvalationResultByUserId(String userId) {
        Map<String, String> params = new HashMap<String, String>();
        // 用户编号
        params.put("userId", userId);
        
        String timestamp = GetDate.getNowTime10() + "";
        // 时间戳
        params.put("timestamp", timestamp);
        // 发放优惠券url
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.GET_USEREVALATIONRESULT_BY_USERID;
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId  + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        _log.info("返回用户是否测评标识调用:" + requestUrl);
        // 0:成功，1：失败
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;

    }
    
    
    /**
     * 银行存管同步余额（异步）
     */
    public static JSONObject synBalanceRetPost(Integer userId) {
        _log.info("-----------------调用apiweb接口同步余额异步userId:"+userId+"----------------");
        Map<String, String> params = new HashMap<String, String>();
        // 用户编号
        params.put("userId", userId+"");
        String timestamp = GetDate.getNowTime10() + "";
        // 时间戳
        params.put("timestamp", timestamp);
        // 发放优惠券url
        String webUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        webUrl = StringUtils.endsWith(webUrl, "/") ? webUrl : webUrl + "/";
        String requestUrl = webUrl + CommonSoaUtils.BANK_SYNBALANCE;

        _log.info("apiweb接口url：" + requestUrl);
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        _log.info("-----------------调用apiweb接口同步余额异步----------------");
        // 0:成功，1：失败
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;
    }
    
    
    /**
     * 银行存管同步余额（异步）
     */
    public static void synBalance(Integer userId) {
        _log.info("-----------------调用apiweb接口同步余额异步userId:"+userId+"----------------");
        Map<String, String> params = new HashMap<String, String>();
        // 用户编号
        params.put("userId", userId+"");
        String timestamp = GetDate.getNowTime10() + "";
        // 时间戳
        params.put("timestamp", timestamp);
        // 发放优惠券url
        String webUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        webUrl = StringUtils.endsWith(webUrl, "/") ? webUrl : webUrl + "/";
        String requestUrl = webUrl + CommonSoaUtils.BANK_SYNBALANCE;

        _log.info("apiweb接口url：" + requestUrl);
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        _log.info("-----------------调用apiweb接口同步余额异步----------------");
        CommonSoaUtils.noRetPost(requestUrl, params);
    }
    
    /**
     * 银行存管同步余额（异步）
     */
    public static void synBalance(String bankAccount) {
        _log.info("-----------------调用apiweb接口同步余额异步bankAccout:"+bankAccount+"----------------");
        Map<String, String> params = new HashMap<String, String>();
        // 用户编号
        params.put("bankAccount", bankAccount+"");
        String timestamp = GetDate.getNowTime10() + "";
        // 时间戳
        params.put("timestamp", timestamp);
        // 发放优惠券url
        String webUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        webUrl = StringUtils.endsWith(webUrl, "/") ? webUrl : webUrl + "/";
        String requestUrl = webUrl + CommonSoaUtils.BANKACCUNOUT_SYNBALANCE;

        _log.info("apiweb接口url：" + requestUrl);
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + bankAccount + timestamp + SOA_INTERFACE_KEY));
        params.put("chkValue", sign);
        _log.info("-----------------调用apiweb接口同步余额异步----------------");
        CommonSoaUtils.noRetPost(requestUrl, params);
    }
    
    /**
     * 根据查询条件取得用户线下充值信息（同步）
     */
    public static JSONObject offlineRechargeList(Map<String,String> searchCon) {
        _log.info("-----------------调用apiweb接口取得用户线下充值信息----------------");
        String timestamp = GetDate.getNowTime10() + "";
        // 时间戳
        searchCon.put("timestamp", timestamp);
        // api-web url
        String webUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        webUrl = StringUtils.endsWith(webUrl, "/") ? webUrl : webUrl + "/";
        String requestUrl = webUrl + CommonSoaUtils.SYNBALANCE_EXCEPTION;
        String sign = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + searchCon.get("userId") + timestamp + SOA_INTERFACE_KEY));
        searchCon.put("chkValue", sign);
        String result = HttpClientUtils.post(requestUrl, searchCon);
        JSONObject obj = JSONObject.parseObject(result);
        return obj;
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
     * 返回快捷银行充值限额
     */
    public static JSONObject getBanksList() {
        Map<String, String> params = new HashMap<String, String>();
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.BANK_LIST;
        _log.info("返回快捷银行充值限额:" + requestUrl);
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;

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
     * 新年活动充值
     * @param userId 推荐人
     * @param money 金钱
     */
    public static void newyeaRecharge(Integer userId,int money) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
 //       String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());
        params.put("money", String.valueOf(money));
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
//        params.put("chkValue", chkValue);
        // 请求路径
        String url=PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        if(!url.substring(url.length()-1, url.length()).equals("/")) {
        	url=url+"/";
        }
        String requestUrl = url + CommonSoaUtils.CHONGZHI;
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    /**
     * 新年活动提现
     * @param userId 推荐人
     * @param money 金钱
     */
    public static void newyearWithdraw(Integer userId,int money) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
 //       String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());
        params.put("money", String.valueOf(money));
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
  //      params.put("chkValue", chkValue);
        // 请求路径
        String url=PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        if(!url.substring(url.length()-1, url.length()).equals("/")) {
        	url=url+"/";
        }
        String requestUrl = url + CommonSoaUtils.TIXIAN;
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    /**
     * 新年活动投资
     * @param userId 推荐人
     * @param money 金钱
     */
    public static void newyearInvestment(Integer userId,int money) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
       // String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());
        params.put("money", String.valueOf(money));
        // 时间戳
        params.put("timestamp", timestamp);

        // 请求路径
        String url=PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        if(!url.substring(url.length()-1, url.length()).equals("/")) {
        	url=url+"/";
        }
        String requestUrl = url + CommonSoaUtils.TOUZI;
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    /**
     * 上市投资活动
     * @param userId 用户id
     * @param money 金钱
     * @param type 1天标2月标
     * @param num 标的期限
     */

    public static void listInvestment(Integer userId,BigDecimal money,String type ,int num) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
       // String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());
        params.put("money", String.valueOf(money));
        params.put("type", String.valueOf(type));
        params.put("num", String.valueOf(num));
        // 时间戳
        params.put("timestamp", timestamp);

        // 请求路径
        String url=PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        if(!url.substring(url.length()-1, url.length()).equals("/")) {
        	url=url+"/";
        }
        String requestUrl = url + "act2018list/investment";
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    /**
     * 上市开户活动
     * @param userId 用户id
     * @param money 金钱
     * @param type 1天标2月标
     * @param num 标的期限
     */

    public static void listOpenAcc(Integer userId) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
       // String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());

        // 时间戳
        params.put("timestamp", timestamp);

        // 请求路径
        String url=PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        if(!url.substring(url.length()-1, url.length()).equals("/")) {
        	url=url+"/";
        }
        String requestUrl = url + "act2018list/open";
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    /**
     * 上市开户活动
     * @param userId 用户id
     * @param money 金钱
     * @param type 1天标2月标
     * @param num 标的期限
     */

    public static void listBorrow(String borrowNid) {
    	_log.info("上市活动开始放款请求"+borrowNid);
        String timestamp = String.valueOf(GetDate.getNowTime10());
       // String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("borrowNid", borrowNid);

        // 时间戳
        params.put("timestamp", timestamp);

        // 请求路径
        String url=PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL);
        if(!url.substring(url.length()-1, url.length()).equals("/")) {
        	url=url+"/";
        }
        String requestUrl = url + "act2018list/actonefang";
        CommonSoaUtils.noRetPost(requestUrl,params);
    }



    /**
     * 上市活动充值
     * @param userId 推荐人
     * @param money 金钱
     */
    public static void listedTwoRecharge(Integer userId,BigDecimal money) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
 //       String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());
        params.put("money", String.valueOf(money));
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
//        params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.CHONGZHI;
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    /**
     * 上市活动提现
     * @param userId 推荐人
     * @param money 金钱
     */
    public static void listedTwoWithdraw(Integer userId,BigDecimal money) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
 //       String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());
        params.put("money", String.valueOf(money));
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
  //      params.put("chkValue", chkValue);
        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.TIXIAN;
        CommonSoaUtils.noRetPost(requestUrl,params);
    }
    /**
     * 上市活动投资
     * @param userId 推荐人
     * @param money 金钱
     */
    public static void listedTwoInvestment(Integer userId,BigDecimal money) {
        String timestamp = String.valueOf(GetDate.getNowTime10());
       // String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + timestamp + SOA_INTERFACE_KEY));
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", userId.toString());
        params.put("money", String.valueOf(money));
        // 时间戳
        params.put("timestamp", timestamp);

        // 请求路径
        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.TOUZI;
        CommonSoaUtils.noRetPost(requestUrl,params);
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


