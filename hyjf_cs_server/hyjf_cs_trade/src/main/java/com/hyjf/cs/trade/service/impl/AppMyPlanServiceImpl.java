package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.BorrowRecoverPlanResponse;
import com.hyjf.am.response.trade.account.BorrowAccountResponse;
import com.hyjf.am.response.trade.coupon.AppCouponInfoResponse;
import com.hyjf.am.response.trade.coupon.CouponRepayResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.assetmanage.AppMyPlanCustomizeVO;
import com.hyjf.am.vo.trade.borrow.AccountBorrowVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.AppCouponInfoCustomizeVO;
import com.hyjf.am.vo.trade.repay.CurrentHoldRepayMentPlanListVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.bean.BaseResultBeanFrontEnd;
import com.hyjf.cs.trade.bean.BorrowDetailBean;
import com.hyjf.cs.trade.bean.BorrowProjectDetailBean;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.AppMyPlanService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version MyPlanServiceImpl, v0.1 2018/7/26 10:31
 */
@Service
public class AppMyPlanServiceImpl extends BaseTradeServiceImpl implements AppMyPlanService {
    public static final String  COUPON_CONFIG_URL = "http://AM-TRADE/am-trade/coupon/getborrowtendercpnByUserIdAndOrderId";

    public static final String BORROW_ACCOUNT_URL = "http://AM-TRADE/am-trade/borrow/getBorrowAccountList";

    public static final String BORROW_RECOVER_PLAN_URL = "http://AM-TRADE/am-trade/recoverplan/getBorrowRecoverPlanListByTenderNid";

    public static final String  COUPON_RECOVER_PLAN_URL = "http://AM-TRADE/am-trade/borrow/getCounponRecoverList";

    @Autowired
    private BaseClient baseClient;

    @Override
    public Integer countAppMyPlan(AssetManageBeanRequest params) {
        return amTradeClient.countAppMyPlan(params);
    }

    @Override
    public List<AppMyPlanCustomizeVO> selectAppMyPlanList(AssetManageBeanRequest params) {
        return amTradeClient.selectAppMyPlanList(params);
    }


    /**
     * 查询我的计划详情
     * @author zhangyk
     * @date 2018/7/31 9:32
     */
    @Override
    public JSONObject getMyPlanDetail(String borrowNid, HttpServletRequest request, String userId) {
        CheckUtil.check(StringUtils.isNotBlank(borrowNid),MsgEnum.ERR_OBJECT_BLANK, "计划编号");
        String token = request.getParameter("token");
        String sign = request.getParameter("sign");
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

}
