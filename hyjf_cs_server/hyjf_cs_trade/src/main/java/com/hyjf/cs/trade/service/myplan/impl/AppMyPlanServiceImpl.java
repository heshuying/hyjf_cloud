package com.hyjf.cs.trade.service.myplan.impl;

import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.response.trade.HjhUserInvestListResponse;
import com.hyjf.am.response.trade.coupon.AppCouponResponse;
import com.hyjf.am.response.trade.coupon.CouponRepayResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppMyPlanCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.coupon.AppCouponCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistListCustomizeVO;
import com.hyjf.am.vo.trade.repay.CurrentHoldRepayMentPlanListVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.FormatRateUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.bean.app.MyPlanDetailResultBean;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.myplan.AppMyPlanService;
import com.hyjf.cs.trade.util.ProjectConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author pangchengchao
 * @version MyPlanServiceImpl, v0.1 2018/7/26 10:31
 */
@Service
public class AppMyPlanServiceImpl extends BaseTradeServiceImpl implements AppMyPlanService {

    private final String ILLEGAL_PARAMETER_STATUS_DESC = "请求参数非法";
    private final String TOKEN_ISINVALID_STATUS = "Token失效，请重新登录";

    private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");

    private static final String NULL_STR = "待确认";

    private static final String DOUBLE_NULL_STR = "--";

    //初始化放款/承接时间(大于2018年3月28号法大大上线时间)
    private static final int ADD_TIME = 1922195200;

    //放款/承接时间(2018-3-28法大大上线时间）
    private static final int ADD_TIME328 = 1522195200;

    @Autowired
    private BaseClient baseClient;
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 统计我的计划数量 
     */
    @Override
    public Integer countAppMyPlan(AssetManageBeanRequest params) {
        return amTradeClient.countAppMyPlan(params);
    }
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询我的计划列表
     */
    @Override
    public List<AppMyPlanCustomizeVO> selectAppMyPlanList(AssetManageBeanRequest params) {
        return amTradeClient.selectAppMyPlanList(params);
    }

    /**
     *  获取我的计划详情
     * @author zhangyk
     * @date 2018/8/1 10:55
     */
    @Override
    public MyPlanDetailResultBean getMyPlanDetail(Integer couponType, String type, String orderId, HttpServletRequest request,String userId) {
        MyPlanDetailResultBean result = new MyPlanDetailResultBean();

        logger.info("request params: orderId is: {}, couponType is: {}", orderId, couponType);
        result.setAccedeOrderId(orderId);
        // 检查参数正确性
        if ( Validator.isNull(couponType) || Validator.isNull(type)
                || Validator.isNull(orderId)) {
            result.setStatus(BaseResultBeanFrontEnd.FAIL);
            result.setStatusDesc(ILLEGAL_PARAMETER_STATUS_DESC);
            return result;
        }

        if (StringUtils.isBlank(userId)) {
            result.setStatus(BaseResultBeanFrontEnd.FAIL);
            result.setStatusDesc(TOKEN_ISINVALID_STATUS);
            return result;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("accedeOrderId", orderId);
        params.put("userId", userId);
        List<TenderAgreementVO> tenderAgreementVOList = amTradeClient.selectTenderAgreementByNid(orderId);
        if(!CollectionUtils.isEmpty(tenderAgreementVOList)){
            TenderAgreementVO tenderAgreement = tenderAgreementVOList.get(0);
            Integer fddStatus = tenderAgreement.getStatus();
            //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
            //System.out.println("构建查询条件******************1法大大协议状态："+tenderAgreement.getStatus());
            if(fddStatus.equals(3)){
                result.setFddStatus("1");
            }else {
                //隐藏下载按钮
                //System.out.println("构建查询条件******************2法大大协议状态：0");
                result.setFddStatus("2");
            }
        }else {
            int addTime = ADD_TIME;
            List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(orderId);//居间协议
            BorrowRecoverVO borrowRecoverVO = amTradeClient.selectBorrowRecoverByNid(orderId);
            if(borrowRecoverVO != null){
                addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
            }
            /**
             * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
             * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
             * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
             */
            if (addTime<ADD_TIME328) {
                //下载老版本协议
                result.setFddStatus("1");
            } else {
                //隐藏下载按钮
                result.setFddStatus("0");
            }
        }
        // 真实资金出借
        if (couponType == 0) {
            UserHjhInvistDetailCustomizeVO customize = amTradeClient.selectUserHjhInvistDetail(params);//myPlanService.selectUserHjhInvistDetail(params);
            if (customize == null) {
                result.setStatus(BaseResultBeanFrontEnd.FAIL);
                result.setStatusDesc("加入订单号不正确......");
                return result;
            }
            // 1. 计划信息
            this.copyPlanBaseInfoToResult(result, customize, type);

            // 2.加入信息
            this.copyPlanCapitalInfoToResult(result, customize,type);
            // 3. 真实资金出借优惠券信息是空
            result.setCouponIntr(null);

            // 4.真实出借还款计划
            /*HjhRepay hjhRepay = myPlanService.getPlanRepayment(orderId);*/
            String url ="http://AM-TRADE/am-trade/hjhRepay/hjhRepaymentDetails/"+ orderId;
            HjhRepayResponse repayResponse = baseClient.getExe(url,HjhRepayResponse.class);
            HjhRepayVO hjhRepayVO = CollectionUtils.isEmpty(repayResponse.getResultList())? null : repayResponse.getResultList().get(0);
            this.copyPlanRepaymentToResult(result, hjhRepayVO, customize);

            // 计划处于出借中状态
            List<String> statusList = Arrays.asList("0", "2", "99");
            // 出借中状态不显示持有列表
            if (customize != null && !statusList.contains(customize.getOrderStatus())) {
                // 5. 持有项目列表
                String url1 = "http://AM-TRADE/am-trade/hjhDebtCredit/getUserHjhInvestList";
                HjhUserInvestListResponse response = baseClient.postExe(url1,params,HjhUserInvestListResponse.class);
                List<UserHjhInvistListCustomizeVO> userHjhInvistListCustomizeVOList = response.getResultList();
                this.copyPlanHoldInvestToResult(result,userHjhInvistListCustomizeVOList);
            }

        } else { // 优惠券出借
            String url = "http://AM-TRADE/am-trade/coupon/getAppMyPlanCouponInfo";
            AppCouponResponse response = baseClient.postExe(url,params,AppCouponResponse.class);
            AppCouponCustomizeVO appCouponCustomize = CollectionUtils.isEmpty(response.getResultList()) ? null :response.getResultList().get(0);
            if (appCouponCustomize == null) {
                result.setStatus(BaseResultBeanFrontEnd.FAIL);
                result.setStatusDesc("优惠券加入订单号不正确......");
                return result;
            }
            // 1. 计划信息
            this.copyCouponPlanBaseToResult(result, appCouponCustomize, type);
            // 有本金出借才显示加入信息
            if (!org.springframework.util.StringUtils.isEmpty(appCouponCustomize.getRealTenderId())) {
                // 2.加入信息
                this.copyCouponPlanCapitalToResult(result, appCouponCustomize, type);
            } else {
                result.setInvestIntr(null);
            }
            // 3.优惠券信息
            this.copyPlanCouponInfoToResult(result, appCouponCustomize);

            // 4.优惠券出借还款计划
            String  couponRecoverPlanUrl = "http://AM-TRADE/am-trade/coupon/getCounponRecoverList/"+ orderId;
            CouponRepayResponse res = baseClient.getExe(couponRecoverPlanUrl,CouponRepayResponse.class);
            List<CurrentHoldRepayMentPlanListVO> repaymentPlanList = res.getResultList();
            this.copyPlanCouponRepaymentToResult(result, repaymentPlanList);

            // 5.优惠券出借不显示持有项目
        }
        return  result;
    }


    /**
     * 本金出借还款计划 目前只有一条 repay表存的是已回款金额，回款总额应从accede中取
     *
     * @param result
     * @param hjhRepay
     */
    private void copyPlanRepaymentToResult(MyPlanDetailResultBean result, HjhRepayVO hjhRepay, UserHjhInvistDetailCustomizeVO customize) {
        if (hjhRepay != null) {
            List<MyPlanDetailResultBean.RepayPlan> repayPlans = result.getRepayPlan();
            MyPlanDetailResultBean.RepayPlan repayPlan = new MyPlanDetailResultBean.RepayPlan();
            repayPlan.setTime(customize.getLastPaymentTime());

            repayPlan.setAccount(DF_FOR_VIEW.format(new BigDecimal(customize.getWaitTotal().replace(",","")).add(new BigDecimal(customize.getReceivedTotal().replace(",","")))));
            // 目前只有一期
            repayPlan.setNumber("1");
            // 0 -未还款 1- 部分还款 2- 已还款
            if (hjhRepay.getRepayStatus() == 2) {
                repayPlan.setStatus("已回款");
            } else {
                repayPlan.setStatus("未回款");
            }

            repayPlans.add(repayPlan);
        }
    }


    /**
     * 优惠券还款计划
     *
     * @param result
     * @param repaymentPlanList
     */
    private void copyPlanCouponRepaymentToResult(MyPlanDetailResultBean result,
                                                 List<CurrentHoldRepayMentPlanListVO> repaymentPlanList) {
        if (!CollectionUtils.isEmpty(repaymentPlanList)) {
            List<MyPlanDetailResultBean.RepayPlan> repayPlans = result.getRepayPlan();
            MyPlanDetailResultBean.RepayPlan repayPlan;
            for (CurrentHoldRepayMentPlanListVO entity : repaymentPlanList) {
                repayPlan = new MyPlanDetailResultBean.RepayPlan();
                repayPlan.setTime(entity.getRecoverTime());
                repayPlan.setAccount(entity.getRecoverAccountWait());
                repayPlan.setNumber(entity.getRecoverPeriod());
                repayPlan.setStatus(entity.getRecoveStatus());
                repayPlans.add(repayPlan);
            }
        }
    }

    /**
     * 优惠券信息
     *
     * @param result
     * @param appCouponCustomize
     */
    private void copyPlanCouponInfoToResult(MyPlanDetailResultBean result, AppCouponCustomizeVO appCouponCustomize) {
        MyPlanDetailResultBean.CouponIntr couponIntr = result.getCouponIntr();

        if (Arrays.asList("1","2","3").contains(appCouponCustomize.getCouponType())){

            switch (appCouponCustomize.getCouponType()) {
                case "1":
                    couponIntr.setCouponTypeCode(1);
                    couponIntr.setCouponType("体验金");
                    break;
                case "2":
                    couponIntr.setCouponTypeCode(2);
                    couponIntr.setCouponType("加息券");
                    break;
                case "3":
                    couponIntr.setCouponTypeCode(3);
                    couponIntr.setCouponType("代金券");
                    break;
                default:
                    logger.error("coupon type is error");
                    break;
            }

        }else if (Validator.isNumber(appCouponCustomize.getCouponType())
                && !Arrays.asList("0","1","2","3").contains(appCouponCustomize.getCouponType())){
            couponIntr.setCouponTypeCode(Integer.parseInt(appCouponCustomize.getCouponType()));
            couponIntr.setCouponType("体验金");
        }

        couponIntr.setCouponAmount(appCouponCustomize.getCouponAmount());
        couponIntr.setInterestOnCall(appCouponCustomize.getRecoverAccountInterestWait());
        couponIntr.setCapitalOnCall(appCouponCustomize.getRecoverAccountCapitalWait());
        couponIntr.setCapitalInterestOnCall(appCouponCustomize.getRecoverAccountWait());
    }


    /**
     * 优惠券加入信息
     *
     * @param result
     * @param appCouponCustomize
     * @param type
     */
    private void copyCouponPlanCapitalToResult(MyPlanDetailResultBean result, AppCouponCustomizeVO appCouponCustomize, String type) {
        MyPlanDetailResultBean.InvestIntr investIntr = result.getInvestIntr();
        investIntr.setAddDate(appCouponCustomize.getAddTime());
        investIntr.setCapital(appCouponCustomize.getAccedeAccount());
        investIntr.setCapitalInterest(appCouponCustomize.getReceivedTotal());
        investIntr.setCapitalOnCall(appCouponCustomize.getWaitCaptical());
        investIntr.setInterestOnCall(appCouponCustomize.getWaitInterest());
    }


    /**
     * 优惠券计划信息
     *
     * @param result
     * @param appCouponCustomize
     * @param type
     */
    private void copyCouponPlanBaseToResult(MyPlanDetailResultBean result, AppCouponCustomizeVO appCouponCustomize,
                                            String type) {
        MyPlanDetailResultBean.ProjectIntr projectIntr = result.getProjectIntr();
        //projectIntr.setStatus(type);
        // 计划处于出借中状态
        List<String> statusList = Arrays.asList("0", "2", "99", "9");
        // 出借中状态不显示持有列表
        if(appCouponCustomize == null){
            return;
        }
        if(StringUtils.isBlank(appCouponCustomize.getOrderStatus())){
            // 优惠券单独投资的情况
            if(appCouponCustomize.getRecoverStatus().equals("0")){
                projectIntr.setStatus("出借中");
            }else if(appCouponCustomize.getRecoverStatus().equals("1")){
                projectIntr.setStatus("已退出");
            }else{
                projectIntr.setStatus("未回款");
            }
        }else{
            // 含有本金投资的情况
            if (statusList.contains(appCouponCustomize.getOrderStatus())) {
                projectIntr.setStatus("出借中");
            } else if("7".equals(appCouponCustomize.getOrderStatus())){
                projectIntr.setStatus("已退出");
            } else{
                projectIntr.setStatus("未回款");
            }
        }

        projectIntr.setPlanName(appCouponCustomize.getPlanName());
        projectIntr.setBorrowApr(FormatRateUtil.formatBorrowApr(appCouponCustomize.getPlanApr()));
        // mod by nxl 智投服务 修改优惠券锁定期显示 start
        // add 汇计划二期前端优化 持有中计划详情修改锁定期 nxl 20180420 start
      /*  SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
        Date datePeriod = null;
        if (NULL_STR.equals(appCouponCustomize.getCountInterestTime()) || DOUBLE_NULL_STR.equals(appCouponCustomize.getCountInterestTime())) {
            appCouponCustomize.setPlanPeriod("— —");
        }else {
            Date dateAddTime;
            try {
                dateAddTime = smp.parse(appCouponCustomize.getCountInterestTime());
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dateAddTime);
                if (appCouponCustomize.getPlanPeriod().contains("天")) {
                    String days = appCouponCustomize.getPlanPeriod().split("天")[0];
                    int intD = Integer.parseInt(days);
                    calendar.add(Calendar.DAY_OF_MONTH, +intD);
                    datePeriod = calendar.getTime();
                }
                if (appCouponCustomize.getPlanPeriod().contains("个月")) {
                    String days = appCouponCustomize.getPlanPeriod().split("个月")[0];
                    int intD = Integer.parseInt(days);
                    calendar.add(Calendar.MONTH, +intD);
                    datePeriod = calendar.getTime();
                }
                if (datePeriod != null) {
                    String endStrDate = smp.format(datePeriod);
                    String startStrDate = appCouponCustomize.getAddTime().substring(0, 10);
                    appCouponCustomize.setPlanPeriod(startStrDate + "~" + endStrDate);
                }
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }*/
        // add 汇计划二期前端优化 持有中计划详情修改锁定期 nxl 20180420 end
        // mod by nxl 智投服务 修改优惠券锁定期显示 end
        projectIntr.setBorrowPeriod(appCouponCustomize.getPlanPeriod());
        projectIntr.setBorrowPeriodUnit(CommonUtils.getPeriodUnitByRepayStyle(appCouponCustomize.getRepayStyle()));
        projectIntr.setRepayStyle(appCouponCustomize.getRepayMethod());
        projectIntr.setOnAccrual(ProjectConstant.PLAN_ON_ACCRUAL);
    }

    /**
     * 计划的持有项目列表
     *
     * @param result
     * @param userHjhInvistBorrowList
     */
    private void copyPlanHoldInvestToResult(MyPlanDetailResultBean result,
                                            List<UserHjhInvistListCustomizeVO> userHjhInvistBorrowList) {
        if (!CollectionUtils.isEmpty(userHjhInvistBorrowList)) {
            List<MyPlanDetailResultBean.BorrowComposition> projectIntrs = result.getBorrowComposition();
            MyPlanDetailResultBean.BorrowComposition borrow;
            for (UserHjhInvistListCustomizeVO entity : userHjhInvistBorrowList) {
                borrow = new MyPlanDetailResultBean.BorrowComposition();
                borrow.setAccount(entity.getAccount());
                borrow.setBorrowNid(entity.getBorrowNid());
                borrow.setTenderTime(entity.getAddTime());
                borrow.setType(entity.getType());
                borrow.setNid(entity.getNid());
                //1为债转
                String  nid = entity.getNid();
                if("1".equals(entity.getType())){
                    nid = entity.getInvestOrderId();
                }
                int addTime = ADD_TIME;
                List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(nid);//居间协议
                BorrowRecoverVO borrowRecoverVO = amTradeClient.selectBorrowRecoverByNid(nid);
                if(borrowRecoverVO != null){
                    addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                }
                if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                    TenderAgreementVO tenderAgreement = tenderAgreementsNid.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    //System.out.println("构建查询条件******************1法大大协议状态："+tenderAgreement.getStatus());
                    if(fddStatus.equals(3)){
                        borrow.setFddStatus(1);
                    }else {
                        //协议生成中
                        borrow.setFddStatus(2);
                    }
                }else {
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    if (addTime<ADD_TIME328) {
                        //下载老版本协议
                        borrow.setFddStatus(1);
                    } else {
                        //隐藏下载按钮
                        borrow.setFddStatus(0);
                    }
                }
                projectIntrs.add(borrow);

            }
        }
    }

    /**
     * 计划信息
     *
     * @param result
     * @param customize
     * @param type
     */
    private void copyPlanBaseInfoToResult(MyPlanDetailResultBean result, UserHjhInvistDetailCustomizeVO customize,
                                          String type) {
        MyPlanDetailResultBean.ProjectIntr projectIntr = result.getProjectIntr();
        // 计划处于出借中状态(orderStatus1锁定中 2退出中 3已退出)
        String orderStatus = customize.getOrderStatus();
        if(StringUtils.isNotBlank(orderStatus)){
            //非锁定中,app端隐藏出借服务协议按钮
            projectIntr.setType(orderStatus);
        }else{
            projectIntr.setType("0");
        }
        // 计划处于出借中状态
        List<String> statusList = Arrays.asList("0", "2", "99", "9");
        // 出借中状态不显示持有列表
        if (customize != null && statusList.contains(customize.getOrderStatus())) {
            projectIntr.setStatus("出借中");
        } else if("7".equals(customize.getOrderStatus())){
            projectIntr.setStatus("已退出");
        } else{
            projectIntr.setStatus("未回款");
        }
        // mod by nxl 智投服务 修改锁定期显示 start
        // add 汇计划二期前端优化 修改锁定期的显示方式  nxl 20180426 start
       /* SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
        Date datePeriod = null;
        if (NULL_STR.equals(customize.getCountInterestTime()) || DOUBLE_NULL_STR.equals(customize.getCountInterestTime())) {
            customize.setPlanPeriod("— —");
        }else {
            try {
                Date dateAddTime = smp.parse(customize.getCountInterestTime());
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dateAddTime);
                if (customize.getPlanPeriod().contains("天")) {
                    String days = customize.getPlanPeriod().split("天")[0];
                    int intD = Integer.parseInt(days);
                    calendar.add(Calendar.DAY_OF_MONTH, +intD);
                    datePeriod = calendar.getTime();
                }
                if (customize.getPlanPeriod().contains("个月")) {
                    String days = customize.getPlanPeriod().split("个月")[0];
                    int intD = Integer.parseInt(days);
                    calendar.add(Calendar.MONTH, +intD);
                    datePeriod = calendar.getTime();
                }
                if (datePeriod != null) {
                    String endStrDate = smp.format(datePeriod);
                    String startStrDate = customize.getAddTime().substring(0, 10);
                    customize.setPlanPeriod(startStrDate + "~" + endStrDate);
                }
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        customize.getPlanPeriod();
        projectIntr.setBorrowPeriod(customize.getPlanPeriod());*/
        // mod by nxl 智投服务 修改锁定期显示 end
        // add 汇计划二期前端优化 修改锁定期的显示方式  nxl 20180426 end
        projectIntr.setBorrowApr(StringUtils.isBlank(customize.getPlanApr()) ? "" :FormatRateUtil.formatBorrowApr(customize.getPlanApr()).replace("%",""));
        projectIntr.setBorrowPeriod(customize.getPlanPeriod());
        projectIntr.setBorrowPeriodUnit(CommonUtils.getPeriodUnitByRepayStyle(customize.getRepayStyle()));
        projectIntr.setRepayStyle(customize.getRepayMethod());
        projectIntr.setOnAccrual(ProjectConstant.PLAN_ON_ACCRUAL);
        projectIntr.setPlanName(customize.getPlanName());
    }

    /**
     * 加入信息
     *
     * @param result
     * @param customize
     * @param type
     */
    private void copyPlanCapitalInfoToResult(MyPlanDetailResultBean result, UserHjhInvistDetailCustomizeVO customize, String type) {
        MyPlanDetailResultBean.InvestIntr investIntr = result.getInvestIntr();
        investIntr.setAddDate(customize.getAddTime());
        investIntr.setCapital(DF_FOR_VIEW.format(new BigDecimal(customize.getAccedeAccount().replaceAll(",",""))));
        investIntr.setCapitalInterest(customize.getReceivedTotal());

        // 计划处于出借中状态
        List<String> statusList = Arrays.asList("0", "2", "99");
        // 出借中状态不显示持有列表
        if (customize != null && statusList.contains(customize.getOrderStatus())) {
            investIntr.setCapitalOnCall("--");
            investIntr.setInterestOnCall("--");
            investIntr.setIsHyphen("1");
        }else{
            investIntr.setCapitalOnCall(DF_FOR_VIEW.format(new BigDecimal(customize.getWaitCaptical())));
            investIntr.setInterestOnCall(customize.getWaitInterest());
            investIntr.setIsHyphen("0");
        }
    }
}
