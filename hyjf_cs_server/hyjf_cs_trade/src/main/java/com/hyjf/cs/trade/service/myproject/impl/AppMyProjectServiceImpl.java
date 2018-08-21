package com.hyjf.cs.trade.service.myproject.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.response.trade.BorrowRecoverPlanResponse;
import com.hyjf.am.response.trade.account.BorrowAccountResponse;
import com.hyjf.am.response.trade.coupon.AppCouponInfoResponse;
import com.hyjf.am.response.trade.coupon.CouponRepayResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.TenderCreditCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppAlreadyRepayListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppTenderCreditRecordListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.borrow.AccountBorrowVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.coupon.AppCouponInfoCustomizeVO;
import com.hyjf.am.vo.trade.repay.CurrentHoldRepayMentPlanListVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.BeforeInterestAfterPrincipalUtils;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.bean.BorrowDetailBean;
import com.hyjf.cs.trade.bean.BorrowProjectDetailBean;
import com.hyjf.cs.trade.bean.CreditDetailsRequestBean;
import com.hyjf.cs.trade.bean.TenderBorrowCreditCustomize;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.SmsProducer;
import com.hyjf.cs.trade.service.myproject.AppMyProjectService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author pangchengchao
 * @version AppMyprojectServiceImpl, v0.1 2018/7/25 14:13
 */
@Service
public class AppMyProjectServiceImpl extends BaseTradeServiceImpl implements AppMyProjectService {

    public static final String  COUPON_CONFIG_URL = "http://AM-TRADE/am-trade/coupon/getborrowtendercpnByUserIdAndOrderId";

    public static final String BORROW_ACCOUNT_URL = "http://AM-TRADE/am-trade/borrow/getBorrowAccountList";

    public static final String BORROW_RECOVER_PLAN_URL = "http://AM-TRADE/am-trade/recoverplan/getBorrowRecoverPlanListByTenderNid";

    public static final String  COUPON_RECOVER_PLAN_URL = "http://AM-TRADE/am-trade/borrow/getCounponRecoverList";

    @Autowired
    private BaseClient baseClient;
    
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private SmsProducer smsProducer;
    
    /**
     * 折让率格式
     */
    private static String regex = "\\d{1}\\.\\d{1}";
	/**
     * 折让率范围开始
     */
    private static float creditDiscountStart = 0.5f;
    /**
     * 折让率范围结束
     */
    private static float creditDiscountEnd = 2.0f;
    
    private static DecimalFormat DF_COM_VIEW = new DecimalFormat("######0.00");

    @Override
    public boolean isAllowChannelAttorn(Integer userId) {
        // 根据userId获取用户注册推广渠道
        UtmPlatVO utmPlat = amUserClient.selectUtmPlatByUserId(userId);
        if (utmPlat != null && utmPlat.getAttornFlag() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest params) {
        return amTradeClient.selectCurrentHoldObligatoryRightListTotal(params);
    }

    @Override
    public List<CurrentHoldObligatoryRightListCustomizeVO> selectAppCurrentHoldObligatoryRightList(AssetManageBeanRequest params) {
        return amTradeClient.selectCurrentHoldObligatoryRightList(params);
    }

    @Override
    public int countAlreadyRepayListRecordTotal(AssetManageBeanRequest params) {
        return amTradeClient.selectRepaymentListTotal(params);
    }

    @Override
    public List<AppAlreadyRepayListCustomizeVO> selectAppAlreadyRepayList(AssetManageBeanRequest params) {
        return amTradeClient.selectAppAlreadyRepayList(params);
    }

    @Override
    public int countCreditRecord(AssetManageBeanRequest params) {
        return amTradeClient.countCreditRecordTotal(params);
    }

    @Override
    public List<AppTenderCreditRecordListCustomizeVO> searchAppCreditRecordList(AssetManageBeanRequest params) {
        return amTradeClient.searchAppCreditRecordList(params);
    }

    @Override
    public Integer selectTenderToCreditListCount(AssetManageBeanRequest params) {
        return amTradeClient.selectTenderToCreditListCount(params);
    }


    /**
     * 查询我的散标详情
     * @author zhangyk
     * @date 2018/7/31 9:32
     */
    @Override
    public JSONObject getMyProjectDetail(String borrowNid, HttpServletRequest request, String userId) {
        CheckUtil.check(StringUtils.isNotBlank(borrowNid),MsgEnum.ERR_OBJECT_BLANK, "标的编号");
        String orderId = request.getParameter("orderId");
        // 优惠券类型，0代表本金投资
        String couponType = request.getParameter("couponType");
        // 如果不为空，为承接的标的
        String assignNid = request.getParameter("assignNid");

        JSONObject jsonObject = new JSONObject();

        ProjectCustomeDetailVO borrow = amTradeClient.selectProjectDetail(borrowNid);
        jsonObject.put("projectName", borrow == null ? "" : borrow.getBorrowNid());
        jsonObject.put("couponType", "");

        /*验证用户是否登录*/
        if (StringUtils.isBlank(userId)){
            jsonObject.put("status", BaseResultBeanFrontEnd.FAIL);
            jsonObject.put("statusDesc", "请登录用户");
            jsonObject.put("projectDetail", new ArrayList<>());
            jsonObject.put("repayPlan", new ArrayList<>());
            jsonObject.put("transferInfo", null);
            return jsonObject;
        }

        /*如果标不存在，则返回*/
        if (borrow == null) {
            jsonObject.put("status", BaseResultBeanFrontEnd.FAIL);
            jsonObject.put("statusDesc", "未查到标的信息");
            jsonObject.put("projectName", "");
            jsonObject.put("projectDetail", new ArrayList<>());
            jsonObject.put("repayPlan", new ArrayList<>());
            jsonObject.put("transferInfo", null);
            return jsonObject;
        }

        // 跳转到投资服务协议
        jsonObject.put("isCredit", false);
        // 1. 资产信息
        List<BorrowProjectDetailBean> detailBeansList = new ArrayList<>();
        List<BorrowDetailBean> borrowBeansList = new ArrayList<>();


        preckCredit(borrowBeansList, "历史年回报率", borrow.getBorrowApr() + "%");
        if ("endday".equals(borrow.getBorrowStyle())) {
            preckCredit(borrowBeansList, "项目期限", borrow.getBorrowPeriod() + "天");
        } else {
            preckCredit(borrowBeansList, "项目期限", borrow.getBorrowPeriod() + "个月");
        }
        preckCredit(borrowBeansList, "回款方式", borrow.getRepayStyle());

        preck(detailBeansList, "资产信息", borrowBeansList);

        // TODO: 2018/7/31  后期处理
       /* AppRiskControlCustomize riskControl = projectService.selectRiskControl(borrow.getBorrowNid());
        if(riskControl!=null){
            riskControl.setControlMeasures(riskControl.getControlMeasures()==null?"":riskControl.getControlMeasures().replace("\r\n", ""));
            riskControl.setControlMort(riskControl.getControlMort()==null?"":riskControl.getControlMort().replace("\r\n", ""));
        }*/
        /*jsonObject.put("riskControl", riskControl);*/
        jsonObject.put("riskControl", "");

        // 区别本金投资和优惠券投资，返回值不同
        if (!"0".equals(couponType)) {
            Map<String,Object> params = new HashMap<>();
            params.put("userId",userId);
            params.put("orderId",orderId);
            AppCouponInfoResponse response = baseClient.postExe(COUPON_CONFIG_URL,params,AppCouponInfoResponse.class);//amTradeClient.getCouponfigByUserIdAndBorrowNid(userId, orderId);
            AppCouponInfoCustomizeVO appCouponInfoCustomize = response.getResult();
            if (appCouponInfoCustomize != null) {
                if(StringUtils.isNotBlank(appCouponInfoCustomize.getRealOrderId())){
                    // 2. 投资信息 ( 有真实资金，显示投资信息 )
                    this.setTenderInfoToResult(detailBeansList, appCouponInfoCustomize.getRealOrderId());
                }
                List<BorrowDetailBean> borrowBeansList2 = new ArrayList<>();
                switch (appCouponInfoCustomize.getCouponType()) {
                    case "1":
                        jsonObject.put("couponType", "体验金");
                        preckCredit(borrowBeansList2, "优惠券面额", CommonUtils.formatAmount(new BigDecimal(appCouponInfoCustomize.getCouponQuota())) + "元");
                        preckCredit(borrowBeansList2, "优惠券类型", "体验金");
                        preckCredit(borrowBeansList2, "待收利息", CommonUtils.formatAmount(new BigDecimal(appCouponInfoCustomize.getRecoverAccountInterestWait())) + "元");
                        break;
                    case "2":
                        jsonObject.put("couponType", "加息券");
                        preckCredit(borrowBeansList2, "优惠券面额", appCouponInfoCustomize.getCouponQuota() + "%");
                        preckCredit(borrowBeansList2, "优惠券类型", "加息券");
                        preckCredit(borrowBeansList2, "待收利息", CommonUtils.formatAmount(new BigDecimal(appCouponInfoCustomize.getRecoverAccountInterestWait())) + "元");
                        break;
                    case "3":
                        jsonObject.put("couponType", "代金券");
                        preckCredit(borrowBeansList2, "优惠券面额", CommonUtils.formatAmount(new BigDecimal(appCouponInfoCustomize.getCouponQuota())) + "元");
                        preckCredit(borrowBeansList2, "优惠券类型", "代金券");
                        preckCredit(borrowBeansList2, "待收利息",
                                CommonUtils.formatAmount(new BigDecimal(appCouponInfoCustomize.getRecoverAccountInterestWait()).subtract(
                                        new BigDecimal(appCouponInfoCustomize.getRecoverAccountCapitalWait()))) + "元");
                        break;
                    default:
                        logger.error("coupon type is error");
                }

                preckCredit(borrowBeansList2, "待收本金", CommonUtils.formatAmount(new BigDecimal(appCouponInfoCustomize.getRecoverAccountCapitalWait())) + "元");
                preckCredit(borrowBeansList2, "待收总额", CommonUtils.formatAmount(new BigDecimal(appCouponInfoCustomize.getRecoverAaccountWait())) + "元");
                preck(detailBeansList, "优惠券信息", borrowBeansList2);
            } else {
                logger.error("未查询到优惠券信息...");
                preck(detailBeansList, "优惠券信息", new ArrayList<BorrowDetailBean>());
                jsonObject.put("couponType", "体验金");
            }

            // 3. 优惠券回款计划
            this.setCouponRepayPlanToResult(jsonObject, orderId);
        } else {
            // 这里要区别是普通投资 还是 承接债转
            logger.info("债转编号： {}, 空代表普通投资，否则为承接债转...,投资订单号: {}", assignNid, orderId);
            if(StringUtils.isBlank(assignNid)){
                // 2. 投资信息(本金投资)
                this.setTenderInfoToResult(detailBeansList, orderId);

                if(CommonUtils.isStageRepay(borrow.getBorrowStyle())){
                    // 3.回款计划(本金投资 - 分期)
                    this.setRepayPlanByStagesToResult(jsonObject, orderId);
                } else {
                    // 3.回款计划(本金投资 - 不分期)
                    this.setRepayPlanToResult(jsonObject, orderId);
                }
            } else {
                List<CreditTenderVO> list = amTradeClient.selectCreditTender(assignNid);
                CreditTenderVO creditTender;
                if (CollectionUtils.isEmpty(list)){
                    creditTender = null;
                }else {
                    creditTender = list.get(0);
                }
                if (creditTender != null) {
                    // 2. 投资信息(承接标的投资信息)
                    this.setCreditTenderInfoToResult(detailBeansList, creditTender);
                    // 3.回款计划(承接标的)
                    this.setCreditRepayPlanByStagesToResult(jsonObject, orderId);
                    // 跳转到债转协议
                    this.setCreditUrlValue(jsonObject, creditTender);
                }

            }

        }
        return null;
    }



    private void setCreditUrlValue(JSONObject result,CreditTenderVO creditTender){
        result.put("isCredit", true);
        // 跳债转协议需要的字段
        // 原标nid
        result.put("bidNid", creditTender.getBidNid());
        // 债转标号
        result.put("creditNid", creditTender.getCreditNid());
        // 债转投标单号
        result.put("creditTenderNid", creditTender.getCreditTenderNid());
        // 认购单号
        result.put("assignNid", creditTender.getAssignNid());
    }


    /**
     * 债转投资还款计划
     * @param result
     * @param
     * @param
     */
    private void setCreditRepayPlanByStagesToResult(JSONObject result, String assignNid) {
        List<CreditRepayVO> creditRepays = amTradeClient.selectCreditRepayList(Integer.valueOf(assignNid));
        JSONArray jsonArray = new JSONArray();
        if (!CollectionUtils.isEmpty(creditRepays)) {
            for (CreditRepayVO creditRepay : creditRepays) {
                JSONObject js = new JSONObject();
                js.put("number", creditRepay.getAssignRepayPeriod());
                js.put("account", CommonUtils.formatAmount(creditRepay.getAssignAccount()));
                if (creditRepay.getStatus() == 0) {
                    js.put("status", "未回款");
                } else {
                    js.put("status", "已回款");
                }
                js.put("time", GetDate.times10toStrYYYYMMDD(creditRepay.getAssignRepayNextTime()));
                jsonArray.add(js);
            }
        }
        result.put("repayPlan", jsonArray);
    }

    /**
     * 投资信息 - 承接标
     * @param detailBeansList
     * @param
     */
    private void setCreditTenderInfoToResult(List<BorrowProjectDetailBean> detailBeansList, CreditTenderVO creditTender) {
        // 2. 投资信息(本金投资)
        CreditRepayVO creditRepay = amTradeClient.selectCreditRepayList(Integer.valueOf(creditTender.getAssignNid())).get(0);
        if (creditTender != null) {
            List<BorrowDetailBean> borrowBeansList1 = new ArrayList<>();
            preckCredit(borrowBeansList1, "投资本金", CommonUtils.formatAmount(creditTender.getAssignCapital()) + "元");
            preckCredit(borrowBeansList1, "已收本息", CommonUtils.formatAmount(creditRepay.getAssignRepayAccount()) + "元");
            preckCredit(borrowBeansList1, "待收本金", CommonUtils.formatAmount(creditRepay.getAssignCapital().subtract(creditRepay.getAssignRepayCapital())) + "元");
            if (creditRepay.getStatus() != 0){//已回款
                preckCredit(borrowBeansList1, "待收利息", "0.00元");
            } else {
                preckCredit(borrowBeansList1, "待收利息", CommonUtils.formatAmount(creditTender.getAssignInterest()) + "元");
            }
            preckCredit(borrowBeansList1, "投资时间", GetDate.date2Str(creditTender.getAddTime(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
            preck(detailBeansList, "投资信息", borrowBeansList1);
        } else {
            preck(detailBeansList, "投资信息", new ArrayList<BorrowDetailBean>());
        }
    }

    /**
     * 本金投资还款计划 - 不分期
     * @param result
     * @param
     */
    private void setRepayPlanToResult(JSONObject result, String orderId) {
        BorrowRecoverVO borrowRecover = amTradeClient.selectBorrowRecoverByNid(orderId);
        JSONArray jsonArray = new JSONArray();
        if (borrowRecover != null) {
            JSONObject js = new JSONObject();
            js.put("time", GetDate.times10toStrYYYYMMDD(borrowRecover.getRecoverTime()));
            js.put("number", "1");
            js.put("account", CommonUtils.formatAmount(borrowRecover.getRecoverAccount()));
            if (borrowRecover.getRecoverStatus() == 1) {
                js.put("status", "已回款");
            } else {
                js.put("status", "未回款");
            }
            jsonArray.add(js);
        }
        result.put("repayPlan", jsonArray);
    }


    /**
     * 本金投资还款计划 - 分期
     * @param result
     * @param
     */
    private void setRepayPlanByStagesToResult(JSONObject result, String orderId) {
        BorrowRecoverPlanResponse response = baseClient.getExe(BORROW_RECOVER_PLAN_URL + "/" + orderId,BorrowRecoverPlanResponse.class);//projectService.selectBorrowRecoverPlanByNid(orderId);
        List<BorrowRecoverPlanVO> recoverPlanList = response.getResultList();
        JSONArray jsonArray = new JSONArray();
        if (!CollectionUtils.isEmpty(recoverPlanList)) {
            for (BorrowRecoverPlanVO plan : recoverPlanList) {
                JSONObject js = new JSONObject();
                js.put("time", GetDate.times10toStrYYYYMMDD(plan.getRecoverTime()));
                js.put("number", plan.getRecoverPeriod());
                js.put("account", CommonUtils.formatAmount(plan.getRecoverAccount()));
                if (plan.getRecoverStatus() == 1) {
                    js.put("status", "已回款");
                } else {
                    js.put("status", "未回款");
                }
                jsonArray.add(js);
            }
        }
        result.put("repayPlan", jsonArray);
    }


    /**
     * 优惠券投资还款计划
     *
     * @param result
     * @param orderId
     */
    private void setCouponRepayPlanToResult(JSONObject result, String orderId) {
        CouponRepayResponse response = baseClient.getExe(COUPON_RECOVER_PLAN_URL + "/" + orderId,CouponRepayResponse.class);
        List<CurrentHoldRepayMentPlanListVO> repaymentPlanList = response.getResultList();
        JSONArray jsonArray = new JSONArray();
        if (!CollectionUtils.isEmpty(repaymentPlanList)) {

            // 体验金只有一期还款，但是期数是第三期，强制改成1
            if (repaymentPlanList.size() == 1) {
                CurrentHoldRepayMentPlanListVO entity = repaymentPlanList.get(0);
                JSONObject js = new JSONObject();
                js.put("time", entity.getRecoverTime());
                js.put("number", "1");
                js.put("account", entity.getRecoverAccountWait());
                js.put("status", entity.getRecoveStatus());
                jsonArray.add(js);
            } else {
                for (CurrentHoldRepayMentPlanListVO entity : repaymentPlanList) {
                    JSONObject js = new JSONObject();
                    js.put("time", entity.getRecoverTime());
                    js.put("number", entity.getRecoverPeriod());
                    js.put("account", entity.getRecoverAccountWait());
                    js.put("status", entity.getRecoveStatus());
                    jsonArray.add(js);
                }
            }
        }
        result.put("repayPlan", jsonArray);
    }


    /**
     * 投资信息
     * @param detailBeansList
     * @param orderId
     */
    private void setTenderInfoToResult(List<BorrowProjectDetailBean> detailBeansList, String orderId) {
        // 2. 投资信息(本金投资)
        BorrowTenderRequest btRequest = new BorrowTenderRequest();
        btRequest.setTenderNid(orderId);
        BorrowTenderVO borrowTender = amTradeClient.selectBorrowTender(btRequest);
        if (borrowTender != null) {
            List<BorrowDetailBean> borrowBeansList1 = new ArrayList<>();
            preckCredit(borrowBeansList1, "投资本金", CommonUtils.formatAmount(borrowTender.getAccount()) + "元");
            preckCredit(borrowBeansList1, "已收本息", CommonUtils.formatAmount(borrowTender.getRecoverAccountYes()) + "元");
            String borrowNid = borrowTender.getBorrowNid();
            BorrowAccountResponse response = baseClient.getExe(BORROW_ACCOUNT_URL + "/" +borrowNid ,BorrowAccountResponse.class);
            List<AccountBorrowVO> list = response.getResultList();

            if (CollectionUtils.isEmpty(list)) {
                preckCredit(borrowBeansList1, "待收本金", "--");
                preckCredit(borrowBeansList1, "待收利息", "--");
            } else {
                preckCredit(borrowBeansList1, "待收本金", CommonUtils.formatAmount(borrowTender.getRecoverAccountCapitalWait()) + "元");
                preckCredit(borrowBeansList1, "待收利息", CommonUtils.formatAmount(borrowTender.getRecoverAccountInterestWait()) + "元");
            }

            if (borrowTender.getAddTime() != null) {
                preckCredit(borrowBeansList1, "投资时间", GetDate.timestamptoStrYYYYMMDDHHMM(String.valueOf(borrowTender.getAddTime())));
            } else {
                preckCredit(borrowBeansList1, "投资时间", "");
            }

            preck(detailBeansList, "投资信息", borrowBeansList1);
        } else {
            preck(detailBeansList, "投资信息", new ArrayList<BorrowDetailBean>());
        }
    }


    /**
     * 封装TenderCreditBorrowBean对象，放入list中
     * @param borrowBeansList
     * @param key 字段名
     * @param val 字段值
     */
    private void preckCredit(List<BorrowDetailBean> borrowBeansList,String key, String val){
        if(!StringUtils.isEmpty(key)){
            BorrowDetailBean borrowBean = new BorrowDetailBean();
            borrowBean.setId("");
            borrowBean.setKey(key);
            borrowBean.setVal(val);
            borrowBeansList.add(borrowBean);
        }
    }


    private void preck(List<BorrowProjectDetailBean> jsonObject,String keyName,List<BorrowDetailBean> msg){
        BorrowProjectDetailBean detailBean = new BorrowProjectDetailBean();
        detailBean.setId("");
        detailBean.setTitle(keyName);
        detailBean.setMsg(msg);
        jsonObject.add(detailBean);
    }

    /**
     * @author libin
     * App端:发送短信验证码(ajax请求)短信验证码数据保存(取自web)
     */
	@Override
	public AppResult sendCreditCode(TenderBorrowCreditCustomize request, Integer userId) {
		UserVO user = amUserClient.findUserById(userId);
        if(user.getMobile()==null){
            throw new CheckException(MsgEnum.STATUS_ZC000001);
        }
        AppResult result = new AppResult();
        String checkCode = GetCode.getRandomSMSCode(6);
        if(systemConfig.isHyjfEnvTest()){
            // 测试环境验证码111111
            checkCode = "111111";
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("val_code", checkCode);
        SmsMessage smsMessage = new SmsMessage(userId, param, user.getMobile(), null, MessageConstant.SMS_SEND_FOR_MOBILE, null,
                CustomConstants.PARAM_TPL_ZHUCE, CustomConstants.CHANNEL_TYPE_NORMAL);
        try{
            smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
        }catch (Exception e){
            throw new CheckException(MsgEnum.ERROR_SMS_SEND);
        }
		return result;
	}

	/**
     * 债转提交保存
     * @author libin
     * @param request
     * @param userId
     * @return
     */
	@Override
	public AppResult saveTenderToCredit(TenderBorrowCreditCustomize request, Integer userId) {
		AppResult result = new AppResult();
        // 检查是否能债转
        checkCanCredit(request,userId);
        checkTenderToCreditParam(request,userId);
        // 债转保存
        try{
            insertTenderToCredit(userId, request);
        }catch (Exception e){
            result.setStatusInfo(MsgEnum.ERR_SYSTEM_UNUSUAL);
        }
        return result;
	}
	
    /**
	 * 检查是否可债转
	 *
	 * @param request
	 * @param userId
	 * @return
	 */
	/*@Override*/
	public AppResult checkCanCredit(CreditDetailsRequestBean request, Integer userId) {
		AppResult webResult = new AppResult();
	    if (StringUtils.isEmpty(request.getBorrowNid()) || StringUtils.isEmpty(request.getTenderNid())) {
	        // 转让失败,无法获取借款和投资编号
	        throw  new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
	    }
	    Integer creditedNum = amTradeClient.tenderAbleToCredit(userId);
	    if (creditedNum != null && creditedNum >= 3) {
	        // 今天的转让次数已满3次,请明天再试
	        throw  new CheckException(MsgEnum.ERROR_CREDIT_THREE);
	    }
	    return webResult;
	}
	
    /**
	 * 检查债转提交保存的参数
	 * @param request
	 * @param userId
	 */
	private void checkTenderToCreditParam(TenderBorrowCreditCustomize request, Integer userId) {
	    // 验证折让率
	    if (StringUtils.isEmpty(request.getCreditDiscount())) {
	        // 折让率不能为空
	        throw  new CheckException(MsgEnum.ERROR_CREDIT_CREDIT_DISCOUNT_NULL);
	    } else {
	        if (request.getCreditDiscount().matches(regex)) {
	            float creditDiscount = Float.parseFloat(request.getCreditDiscount());
	            if (creditDiscount > creditDiscountEnd || creditDiscount < creditDiscountStart) {
	                // 折让率范围错误
	                throw  new CheckException(MsgEnum.ERROR_CREDIT_DISCOUNT_ERROR);
	            }
	        } else {
	            // 折让率格式错误
	            throw  new CheckException(MsgEnum.ERROR_CREDIT_DISCOUNT_FORMAT_ERROR);
	        }
	    }
	    // 验证手机验证码
	    if (StringUtils.isEmpty(request.getTelcode())) {
	        // 手机验证码不能为空
	        throw  new CheckException(MsgEnum.STATUS_ZC000010);
	    } else {
	        UserVO user = amUserClient.findUserById(userId);
	        int result = amUserClient.checkMobileCode(user.getMobile(), request.getTelcode(), CommonConstant.PARAM_TPL_ZHUCE
	                , request.getPlatform(), CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_YIYAN);
	        if (result == 0) {
	            throw new CheckException(MsgEnum.STATUS_ZC000015);
	        }
	    }
	}
	
    /**
	 * 插入转让信息
	 * @param userId
	 * @param request
	 * @return
	 */
	private Integer insertTenderToCredit(int userId, TenderBorrowCreditCustomize request) {
	    // 当前日期
	    Integer nowTime = GetDate.getNowTime10();
	    // 查询borrow 和 BorrowRecover
	    BorrowVO borrow = amTradeClient.selectBorrowByNid(request.getBorrowNid());
	    if (borrow == null) {
	        throw new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
	    }
	    BorrowRecoverVO recover = this.amTradeClient.getBorrowRecoverByTenderNid(request.getTenderNid());
	    if (recover == null) {
	        throw new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
	    }
	    // 债转计算
	    Map<String, BigDecimal> creditCreateMap = selectExpectCreditFeeForBigDecimal(borrow, recover,
	            request.getCreditDiscount(), nowTime);
	    // 声明要保存的债转对象
	    BorrowCreditVO borrowCredit = new BorrowCreditVO();
	    // 生成creditNid
	    // 获取当前时间的日期
	    String nowDate = (GetDate.yyyyMMdd.format(new Date()) != null && !"".equals(GetDate.yyyyMMdd.format(new Date()))) ? GetDate.yyyyMMdd.format(new Date()) : "0";
	    Integer creditedNum = amTradeClient.tenderAbleToCredit(userId);
	    Integer creditNumToday = (creditedNum == null ? 0 : creditedNum);
	    String creditNid = nowDate.substring(2) + String.format("%04d", (creditNumToday + 1));
	    // 获取待债转数据
	    TenderCreditCustomizeVO tenderToCreditDetail = amTradeClient.selectTenderToCreditDetail(userId, request.getBorrowNid(),
	            request.getTenderNid());
	    // 债转nid
	    borrowCredit.setCreditNid(Integer.parseInt(creditNid));
	    // 转让用户id
	    borrowCredit.setCreditUserId(userId);
	    int lastdays = 0;
	    int holddays = 0;
	    String borrowStyle = borrow.getBorrowStyle();
	    // 到期还本还息和按天计息，到期还本还息
	    if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
	        try {
	            String nowDateStr = GetDate.getDateTimeMyTimeInMillis(nowTime);
	            String recoverDate = GetDate.getDateTimeMyTimeInMillis(recover.getRecoverTime());
	            String hodeDate = GetDate.getDateTimeMyTimeInMillis(recover.getAddTime());
	            lastdays = GetDate.daysBetween(nowDateStr, recoverDate);
	            holddays = GetDate.daysBetween(hodeDate, nowDateStr);
	        } catch (Exception e) {
	            // 债转数据错误
	            throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
	        }
	    }
	    // 等额本息和等额本金和先息后本
	    if (borrowStyle.equals(CalculatesUtil.STYLE_MONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
	        String bidNid = borrow.getBorrowNid();
	        List<BorrowRepayPlanVO> borrowRepayPlans = amTradeClient.getBorrowRepayPlansByPeriod(bidNid, borrow.getBorrowPeriod());
	        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
	            try {
	                String hodeDate = GetDate.getDateTimeMyTimeInMillis(recover.getAddTime());
	                lastdays = GetDate.daysBetween(GetDate.getDateTimeMyTimeInMillis(nowTime), GetDate.getDateTimeMyTimeInMillis(borrowRepayPlans.get(0).getRepayTime()));
	                holddays = GetDate.daysBetween(hodeDate, GetDate.getDateTimeMyTimeInMillis(nowTime));
	            } catch (Exception e) {
	                // 债转数据错误
	                throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
	            }
	        }
	    }
	    // 原标nid
	    borrowCredit.setBidNid(tenderToCreditDetail.getBorrowNid());
	    // 原标年化利率
	    borrowCredit.setBidApr(new BigDecimal(tenderToCreditDetail.getBorrowApr()));
	    // 原标标题
	    borrowCredit.setBidName(tenderToCreditDetail.getBorrowName());
	    // 投标nid
	    borrowCredit.setTenderNid(tenderToCreditDetail.getTenderNid());
	    // 转让状态 0.进行中,1.停止
	    borrowCredit.setCreditStatus(0);
	    // 排序
	    borrowCredit.setCreditOrder(0);
	    // 债转期限-天
	    borrowCredit.setCreditTerm(lastdays);
	    borrowCredit.setCreditTermHold(holddays);
	    // 剩余的债转期数-期
	    borrowCredit.setCreditPeriod(recover.getRecoverPeriod());
	    // 客户端
	    borrowCredit.setClient(request.getPlatform());
	    // 债转本金
	    borrowCredit.setCreditCapital(creditCreateMap.get("creditCapital"));
	    // 债转总额
	    borrowCredit.setCreditAccount(creditCreateMap.get("creditAccount"));
	    // 债转总利息
	    borrowCredit.setCreditInterest(creditCreateMap.get("creditInterest"));
	    // 需垫付利息
	    borrowCredit.setCreditInterestAdvance(creditCreateMap.get("assignInterestAdvance"));
	    // 折价率
	    borrowCredit.setCreditDiscount(new BigDecimal(request.getCreditDiscount()));
	    // 总收入,
	    borrowCredit.setCreditIncome(creditCreateMap.get("assignPay"));
	    // 服务费
	    borrowCredit.setCreditFee(creditCreateMap.get("assignPay").multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_DOWN));
	    // 出让价格
	    borrowCredit.setCreditPrice(creditCreateMap.get("creditPrice"));
	    // 已认购本金
	    borrowCredit.setCreditCapitalAssigned(BigDecimal.ZERO);
	    // 已承接部分的利息
	    borrowCredit.setCreditInterestAssigned(BigDecimal.ZERO);
	    // 已垫付利息
	    borrowCredit.setCreditInterestAdvanceAssigned(BigDecimal.ZERO);
	    // 已还款总额
	    borrowCredit.setCreditRepayAccount(BigDecimal.ZERO);
	    // 已还本金
	    borrowCredit.setCreditRepayCapital(BigDecimal.ZERO);
	    // 已还利息
	    borrowCredit.setCreditRepayInterest(BigDecimal.ZERO);
	    // 债转最后还款日
	    borrowCredit.setCreditRepayEndTime(Integer.parseInt(GetDate.get10Time(tenderToCreditDetail.getRepayLastTime())));
	    // 上次还款日
	    borrowCredit.setCreditRepayLastTime(recover.getRecoverYestime() != null ? recover.getRecoverYestime() : nowTime);
	    // 下次还款日
	    borrowCredit.setCreditRepayNextTime(recover.getRecoverTime() != null ? recover.getRecoverTime() : nowTime);
	    // 最终实际还款日
	    borrowCredit.setCreditRepayYesTime(0);
	    // 创建日期
	    borrowCredit.setCreateDate(Integer.parseInt(GetDate.yyyyMMdd.format(new Date())));
	    // 创建时间
	    borrowCredit.setAddTime(nowTime);
	    // 结束时间
	    borrowCredit.setEndTime(nowTime + 24 * 3600 * 3);
	    // 认购时间
	    borrowCredit.setAssignTime(0);
	    // 投资次数
	    borrowCredit.setAssignNum(0);
	    // 还款状态 0还款中、1已还款、2还款失败
	    borrowCredit.setRepayStatus(0);
	    // 给前端展示用
	    request.setCreditEndTime(borrowCredit.getEndTime());
	    request.setCreditPrice(DF_COM_VIEW.format(borrowCredit.getCreditPrice().setScale(2, BigDecimal.ROUND_DOWN)));
	    if (borrow != null) {
	        if ("endmonth".equals(borrow.getBorrowStyle())) {
	            // 从第几期开始
	            borrowCredit.setRecoverPeriod(borrow.getBorrowPeriod() - recover.getRecoverPeriod());
	        } else {
	            // 从第几期开始
	            borrowCredit.setRecoverPeriod(0);
	        }
	    } else {
	        // 从第几期开始
	        borrowCredit.setRecoverPeriod(0);
	    }
	
	    // 操作数据库表
	    return amTradeClient.insertCredit(borrowCredit);
	}
	
    /**
     * 债转各项金额计算
     * @param borrow
     * @param borrowRecover
     * @param creditDiscount
     * @param nowTime
     * @return
     */
    public Map<String, BigDecimal> selectExpectCreditFeeForBigDecimal(BorrowVO borrow, BorrowRecoverVO borrowRecover, String creditDiscount, int nowTime) {
        Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();

        // 债转本息
        BigDecimal creditAccount = BigDecimal.ZERO;
        // 债转期全部利息
        BigDecimal creditInterest = BigDecimal.ZERO;
        // 垫付利息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
        BigDecimal assignInterestAdvance = BigDecimal.ZERO;
        // 债转利息
        BigDecimal assignPayInterest = BigDecimal.ZERO;
        // 实付金额 承接本金*（1-折价率）+应垫付利息
        BigDecimal assignPay = BigDecimal.ZERO;
        // 预计收益 承接人债转本息—实付金额
        BigDecimal assignInterest = BigDecimal.ZERO;
        // 可转本金
        BigDecimal creditCapital = BigDecimal.ZERO;
        // 折后价格
        BigDecimal creditPrice = BigDecimal.ZERO;
        // 已发生债转的未还利息
        BigDecimal creditRepayInterestWait = BigDecimal.ZERO;

        String borrowStyle = borrow.getBorrowStyle();
        // 可转本金
        creditCapital = borrowRecover.getRecoverCapital().subtract(borrowRecover.getRecoverCapitalYes()).subtract(borrowRecover.getCreditAmount());
        // 折后价格
        creditPrice = creditCapital.multiply(new BigDecimal(1).subtract(new BigDecimal(creditDiscount).divide(new BigDecimal(100)))).setScale(2, BigDecimal.ROUND_DOWN);
        // 年利率
        BigDecimal yearRate = borrow.getBorrowApr().divide(new BigDecimal("100"));
        // 到期还本还息和按天计息，到期还本还息
        if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
            int lastDays = 0;
            try {
                String nowDate = GetDate.getDateTimeMyTimeInMillis(nowTime);
                String recoverDate = GetDate.getDateTimeMyTimeInMillis(Integer.valueOf(borrowRecover.getRecoverTime()));
                lastDays = GetDate.daysBetween(nowDate, recoverDate);
            } catch (Exception e) {
                throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
            }
            // 剩余天数
            if (borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {// 按天
                // 债转本息
                creditAccount = DuePrincipalAndInterestUtils.getDayPrincipalInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
                // 债转期全部利息
                creditInterest = DuePrincipalAndInterestUtils.getDayInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
                // 垫付利息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
                assignInterestAdvance = DuePrincipalAndInterestUtils.getDayAssignInterestAdvance(creditCapital, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays + ""));
                // 债转利息
                assignPayInterest = creditInterest;
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = creditPrice.add(assignInterestAdvance);
                // 预计收益 承接人债转本息—实付金额
                assignInterest = creditAccount.subtract(assignPay);// 计算投资收益
            } else {// 按月
                // 债转本息
                creditAccount = DuePrincipalAndInterestUtils.getMonthPrincipalInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
                // 债转期全部利息
                creditInterest = DuePrincipalAndInterestUtils.getMonthInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
                // 垫付利息 垫息总额=债权本金*年化收益÷12*融资期限-债权本金*年化收益÷360*剩余天数
                assignInterestAdvance = DuePrincipalAndInterestUtils.getMonthAssignInterestAdvance(creditCapital, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays + ""));
                // 债转利息
                assignPayInterest = creditInterest;
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = creditPrice.add(assignInterestAdvance);
                // 预计收益 承接人债转本息—实付金额
                assignInterest = creditAccount.subtract(assignPay);// 计算投资收益
            }
        }
        // 等额本息和等额本金和先息后本
        if (borrowStyle.equals(CalculatesUtil.STYLE_MONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
            // 根据投资订单号检索已债转还款信息
            List<CreditRepayVO> creditRepayList = amTradeClient.selectCreditRepayList(borrowRecover.getTenderId());
            int lastDays = 0;
            List<BorrowRepayPlanVO> borrowRepayPlans = this.amTradeClient.selectBorrowRepayPlan(borrow.getBorrowNid(), 0);
            if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
                try {
                    String nowDate = GetDate.getDateTimeMyTimeInMillis(nowTime);
                    String recoverDate = GetDate.getDateTimeMyTimeInMillis(borrowRepayPlans.get(0).getRepayTime());
                    lastDays = GetDate.daysBetween(nowDate, recoverDate);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
                }
            }
            // 债转本息
            creditAccount = BeforeInterestAfterPrincipalUtils.getPrincipalInterestCount(creditCapital, yearRate, borrowRecover.getRecoverPeriod(), borrowRecover.getRecoverPeriod());
            // 每月应还利息
            BigDecimal interest = BeforeInterestAfterPrincipalUtils.getPerTermInterest(creditCapital, borrow.getBorrowApr().divide(new BigDecimal(100)), borrowRecover.getRecoverPeriod(),
                    borrowRecover.getRecoverPeriod());
            // 债转期全部利息
            // creditInterest =
            // BeforeInterestAfterPrincipalUtils.getInterestCount(creditCapital,
            // yearRate, borrowRecover.getRecoverPeriod(),
            // borrowRecover.getRecoverPeriod());
            if (creditRepayList != null && creditRepayList.size() > 0) {
                for (CreditRepayVO creditRepay : creditRepayList) {
                    creditRepayInterestWait = creditRepayInterestWait.add(creditRepay.getAssignInterest());
                }
            }
            creditInterest = borrowRecover.getRecoverInterestWait().subtract(creditRepayInterestWait);
            // 垫付利息 垫息总额=投资人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
            assignInterestAdvance = BeforeInterestAfterPrincipalUtils.getAssignInterestAdvance(creditCapital, creditCapital, yearRate, interest, new BigDecimal(lastDays + ""));
            // 债转利息
            assignPayInterest = creditInterest;
            // 实付金额 承接本金*（1-折价率）+应垫付利息
            assignPay = creditPrice.add(assignInterestAdvance);
            // 预计收益 承接人债转本息—实付金额
            assignInterest = creditAccount.subtract(assignPay);// 计算投资收益
        }
        resultMap.put("creditAccount", creditAccount.setScale(2, BigDecimal.ROUND_DOWN));// 债转本息
        resultMap.put("creditInterest", creditInterest.setScale(2, BigDecimal.ROUND_DOWN));// 预计收益
        resultMap.put("assignInterestAdvance", assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN));// 垫付利息
        resultMap.put("assignPayInterest", assignPayInterest.setScale(2, BigDecimal.ROUND_DOWN));// 债转利息
        resultMap.put("assignPay", assignPay.setScale(2, BigDecimal.ROUND_DOWN));// 实付金额
        resultMap.put("assignInterest", assignInterest.setScale(2, BigDecimal.ROUND_DOWN));// 债转期全部利息
        resultMap.put("creditCapital", creditCapital.setScale(2, BigDecimal.ROUND_DOWN));// 可转本金
        resultMap.put("creditPrice", creditPrice.setScale(2, BigDecimal.ROUND_DOWN));// 折后价格
        return resultMap;
    }
}
