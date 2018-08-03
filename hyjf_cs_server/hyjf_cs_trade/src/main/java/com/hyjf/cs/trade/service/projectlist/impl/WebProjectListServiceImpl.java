package com.hyjf.cs.trade.service.projectlist.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.HjhAccedeRequest;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.UserCouponConfigCustomizeVo;
import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.RepayPlanService;
import com.hyjf.cs.trade.service.projectlist.WebProjectListService;
import com.hyjf.cs.trade.util.HomePageDefine;
import com.hyjf.cs.trade.util.ProjectConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * web端项目列表Service实现类
 *
 * @author liuyang
 * @version WebProjectListServiceImpl, v0.1 2018/6/13 10:21
 */
@Service
public class WebProjectListServiceImpl extends BaseTradeServiceImpl implements WebProjectListService {

    private static Logger logger = LoggerFactory.getLogger(WebProjectListServiceImpl.class);

    public static final String HJH_DETAIL_BORROW_LIST_COUNT_URL = "http://AM-TRADE/am-trade/hjhPlan/getPlanBorrowListCount";

    public static final String HJH_DETAIL_BORROW_LIST_URL = "http://AM-TRADE/am-trade/hjhPlan/getPlanBorrowList";

    /*hjh加入记录count*/
    public static final String HJH_DETAIL_ACCEDE_COUNT_URL = "http://AM-TRADE/am-trade/hjhPlan/getPlanAccedeCount";

    /*hjh加入记录list*/
    public static final String HJH_DETAIL_ACCEDE_LIST_URL = "http://AM-TRADE/am-trade/hjhPlan/getPlanAccedeList";

    public static final String CREDIT_DETAIL_TENDER_COUNT_URL = "http://AM-TRADE/am-trade/creditTender/getCreditDetailTenderCount";

    public static final String CREDIT_DETAIL_TENDER_LIST_URL = "http://AM-TRADE/am-trade/creditTender/getCreditDetailTenderList";

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private RepayPlanService repayPlanService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private WebProjectListClient webProjectListClient;


    /**
     * 获取Web端项目列表
     *
     * @param request
     * @return
     * @author liuyang
     */
    @Override
    public WebResult searchProjectList(ProjectListRequest request) {
        // 参数验证 略

        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        // ①查询count
        Integer count = amTradeClient.countProjectList(request);
        // 对调用返回的结果进行转换和拼装
        WebResult webResult = new WebResult();
        // 先抛错方式，避免代码看起来头重脚轻。
        if (count == null) {
            logger.error("查询散标投资列表原子层count异常");
            throw new RuntimeException("查询散标投资列表原子层count异常");
        }
        page.setTotal(count);
        //由于result类在转json时会去掉null值，手动初始化为非null，保证json不丢失key
        webResult.setData(new ArrayList<>());
        if (count > 0) {
            List<WebProjectListCsVO> result = new ArrayList<>();
            List<WebProjectListCustomizeVO> list = amTradeClient.searchProjectList(request);
            if (CollectionUtils.isEmpty(list)) {
                logger.error("查询散标投资列表原子层List异常");
                throw new RuntimeException("查询散标投资列表原子层list数据异常");
            }
            result = CommonUtils.convertBeanList(list, WebProjectListCsVO.class);
            webResult.setData(result);
        }
        webResult.setPage(page);
        return webResult;

        //传统的if多重嵌套判断方式
        /*if (Response.isSuccess(response)){
            count = response.getCount();
            page.setTotal(count);
            if (count > 0){
                List<WebProjectListCsVO> result = new ArrayList<>();
                ProjectListResponse dataResponse = amTradeClient.searchProjectList(request);
                if (Response.isSuccess(dataResponse)){
                    result = CommonUtils.convertBeanList(dataResponse.getResultList(),WebProjectListCsVO.class);
                    webResult.setData(result);
                }else{
                    throw  new RuntimeException("查询原子层list数据异常");
                }
            }
            webResult.setPage(page);
            return  webResult;
        }else{ // 如果需要还可以把原子层的错误信息继续向上抛
            throw new RuntimeException("查询原子层count异常");
        }*/

    }

    @Override
    public WebResult getBorrowDetail(Map map, String userId) {
        Object borrowNid = map.get(ProjectConstant.PARAM_BORROW_NID);
        CheckUtil.check(null != borrowNid, MsgEnum.ERR_OBJECT_REQUIRED, "借款编号");
        ProjectListRequest request = new ProjectListRequest();
        // ① 先查出标的基本信息  ② 根据是否是新标的，进行参数组装
        ProjectCustomeDetailVO projectCustomeDetail = amTradeClient.searchProjectDetail(map);
        if (projectCustomeDetail == null) {
            CheckUtil.check(false, MsgEnum.STATUS_CE000013);
        }

        ProjectCustomeDetailCsVO detailCsVO = CommonUtils.convertBean(projectCustomeDetail, ProjectCustomeDetailCsVO.class);

        UserVO userVO = null;
        Map<String, Object> other = new HashMap(); // 原来独立于实体之外的属性，单独放在一个map中
        // 已经登录
        if (userId != null) {
            userVO = amUserClient.findUserById(Integer.valueOf(userId));
            other.put("riskFlag", userVO != null ? userVO.getIsEvaluationFlag() : null);
        }
        //判断新标还是老标，老标走原来逻辑原来页面，新标走新方法 0为老标 1为新标(融通宝走原来页面)  -- 原系统注释
        if (detailCsVO.getIsNew() == 0 || "13".equals(detailCsVO.getType())) {
            // TODO: 2018/6/23  getProjectDetail(modelAndView, detailCsVO,userId);     待确认是否还有老标后再处理
        } else {
            getProjectDetailNew(other, projectCustomeDetail, userVO);
        }
        WebResult webResult = new WebResult();
        detailCsVO.setOther(other);
        webResult.setData(detailCsVO);
        return webResult;
    }

    // 新标组装标的详情
    private void getProjectDetailNew(Map<String, Object> other, ProjectCustomeDetailVO borrow, UserVO userVO) {
        //标的号
        String borrowNid = borrow.getBorrowNid();

        /* 项目还款方式 */
        String borrowStyle = borrow.getBorrowStyle();
        // 收益率
        BigDecimal borrowApr = new BigDecimal(borrow.getBorrowApr());
        //可投金额
        if (borrow.getInvestAccount() != null) {
            other.put("InvestAccountInt", new BigDecimal(borrow.getInvestAccount()).intValue());
        }
        // 系统当前时间
        String nowTime = String.valueOf(GetDate.getNowTime10());
        other.put("nowTime", nowTime);

        /** 计算最优优惠券开始 pccvip isThereCoupon 1是有最优优惠券，0无最有优惠券 */
        BestCouponListVO couponConfig = new BestCouponListVO();
        BigDecimal couponInterest = BigDecimal.ZERO;
        other.put("interest", BigDecimal.ZERO);
        //用户未登陆,不取优惠券
        if (userVO == null) {
            couponConfig = null;
            other.put("isThereCoupon", 0);//无最优优惠券
            other.put("couponConfig", couponConfig);//优惠券配置信息
            other.put("couponAvailableCount", 0);//可用优惠券张数
            // 登录登录状态和基本信息
            other.put("loginFlag", "0");//登录状态 0未登陆 1已登录
            other.put("openFlag", "0"); //开户状态 0未开户 1已开户
            other.put("investFlag", "0");//是否投资过该项目 0未投资 1已投资
            other.put("riskFlag", "0");//是否进行过风险测评 0未测评 1已测评
            other.put("setPwdFlag", "0");//是否设置过交易密码 0未设置 1已设置
        } else {
            Integer userId = userVO.getUserId();
            // 获取用户最优优惠券
            MyCouponListRequest request = new MyCouponListRequest();
            request.setBorrowNid(borrowNid);
            request.setUserId(String.valueOf(userId));
            request.setPlatform(CustomConstants.CLIENT_PC);
            couponConfig = amUserClient.selectBestCoupon(request);
            if (couponConfig != null) {
                other.put("isThereCoupon", 1);
                if (couponConfig.getCouponType() == 1) {
                    couponInterest = getInterestDj(couponConfig.getCouponQuota(), (int) couponConfig.getCouponProfitTime(), borrowApr);
                } else {
                    couponInterest = getInterest(borrowStyle, couponConfig.getCouponType(), borrowApr, couponConfig.getCouponQuota(), "0",
                            new Integer(borrow.getBorrowPeriod() == null ? "0" : borrow.getBorrowPeriod()));
                }
                couponConfig.setCouponInterest(CustomConstants.DF_FOR_VIEW.format(couponInterest));
                if (couponConfig != null && couponConfig.getCouponType() == 3) {
                    other.put("interest", CustomConstants.DF_FOR_VIEW.format(couponInterest.subtract(couponConfig.getCouponQuota())));
                } else {
                    other.put("interest", CustomConstants.DF_FOR_VIEW.format(couponInterest));
                }
            } else {
                other.put("isThereCoupon", 0);
            }
            other.put("couponConfig", couponConfig);
            /** 可用优惠券张数开始 pccvip */
            request.setMoney("0");
            Integer couponAvailableCount = amUserClient.countAvaliableCoupon(request);
            other.put("couponAvailableCount", String.valueOf(couponAvailableCount));
            other.put("borrowMeasuresMea", borrow.getBorrowMeasuresMea());
            /** 可用优惠券张数结束 pccvip */
            /** 计算最优优惠券结束 */

            /*用户基本信息 开始*/
            other.put("loginFlag", "1");//登录状态 0未登陆 1已登录
            //用户信息
            if (userVO.getOpenAccount() == 1) {
                other.put("openFlag", "1");
            } else {
                other.put("openFlag", "0");
            }
            // 用户是否投资项目
            int count = amTradeClient.countUserInvest(userId, borrowNid);
            if (count > 0) {
                other.put("investFlag", "1");//是否投资过该项目 0未投资 1已投资
            } else {
                other.put("investFlag", "0");//是否投资过该项目 0未投资 1已投资
            }
            //是否设置交易密码
            if (userVO.getIsSetPassword() == 1) {
                other.put("setPwdFlag", "1");
            } else {
                other.put("setPwdFlag", "0");
            }
            //账户信息
            AccountVO account = amTradeClient.getAccountByUserId(userId);
            String userBalance = account.getBankBalance().toString();
            other.put("userBalance", userBalance);

            /* 用户基本信息 结束*/
        }


        //收益
        BigDecimal borrowInterest = BigDecimal.ZERO;

        /**
         * 融通宝收益叠加
         */
        if ("13".equals(borrow.getType())) {
            borrowApr = borrowApr.add(new BigDecimal(borrow.getBorrowExtraYield()));
        }

        switch (borrowStyle) {
            case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“
                // 计算历史回报
                borrowInterest = DuePrincipalAndInterestUtils
                        .getMonthInterest(new BigDecimal(borrow.getBorrowAccount()), borrowApr.divide(new BigDecimal("100")), Integer.parseInt(borrow.getBorrowPeriod()))
                        .divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“
                borrowInterest = DuePrincipalAndInterestUtils
                        .getDayInterest(new BigDecimal(borrow.getBorrowAccount()), borrowApr.divide(new BigDecimal("100")), Integer.parseInt(borrow.getBorrowPeriod()))
                        .divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：；
                borrowInterest = BeforeInterestAfterPrincipalUtils.getInterestCount(new BigDecimal(borrow.getBorrowAccount()), borrowApr.divide(new BigDecimal("100")),
                        Integer.parseInt(borrow.getBorrowPeriod()), Integer.parseInt(borrow.getBorrowPeriod())).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：；
                borrowInterest = AverageCapitalPlusInterestUtils
                        .getInterestCount(new BigDecimal(borrow.getBorrowAccount()), borrowApr.divide(new BigDecimal("100")), Integer.parseInt(borrow.getBorrowPeriod()))
                        .divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_PRINCIPAL:// 还款方式为”等额本金“
                borrowInterest = AverageCapitalUtils.getInterestCount(new BigDecimal(borrow.getBorrowAccount()), borrowApr.divide(new BigDecimal("100")), Integer.parseInt(borrow.getBorrowPeriod()))
                        .divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                break;
            default:
                break;
        }

        if (couponConfig != null && couponConfig.getCouponType() == 3) {
            other.put("capitalInterest", CustomConstants.DF_FOR_VIEW.format(borrowInterest.add(couponConfig.getCouponQuota()).subtract(couponInterest)));
        } else if (couponConfig != null && couponConfig.getCouponType() == 1) {
            other.put("capitalInterest", borrowInterest.add(couponInterest));
        } else {
            other.put("capitalInterest", CustomConstants.DF_FOR_VIEW.format(borrowInterest.subtract(couponInterest)));
        }

        borrow.setBorrowInterest(borrowInterest.toString());

        // TODO: 2018/6/25 汇资产项目后期处理
        if ("9".equals(borrow.getType())) {// 如果项目为汇资产项目
//            // 添加相应的项目详情信息
//            other.put("projectDeatil", borrow);
//            // 4查询相应的汇资产的首页信息
//            WebHzcProjectDetailCustomize borrowInfo = this.borrowService.searchHzcProjectDetail(borrowNid);
//            other.put("borrowInfo", borrowInfo);
//            // 处置预案
//            WebHzcDisposalPlanCustomize disposalPlan = this.borrowService.searchDisposalPlan(borrowNid);
//            other.put("disposalPlan", disposalPlan);
//            // 5查询相应的还款计划
//            List<BorrowRepayPlanCustomBean> repayPlanList = this.borrowService.getRepayPlan(borrowNid);
//            other.put("repayPlanList", repayPlanList);
//            // 相关文件
//            List<BorrowFileCustomBean> files = this.borrowService.searchProjectFiles(borrowNid, CustomConstants.HOST);
//            other.put("fileList", files);
//            /**
//             * 借款类型  1、企业借款 2、借款人  3、汇资产
//             */
//            other.put("borrowType", "3");

        } else {// 项目为非汇资产项目
            // 添加相应的项目详情信息
            other.put(ProjectConstant.RES_PROJECT_DETAIL, borrow);
            /**
             * 借款类型  1、企业借款 2、借款人  3、汇资产
             */
            other.put(ProjectConstant.PARAM_BORROW_TYPE, borrow.getComOrPer());
            //借款人企业信息
            BorrowUserVO borrowUsers = amTradeClient.getBorrowUser(borrowNid);
            //借款人信息
            BorrowManinfoVO borrowManinfo = amTradeClient.getBorrowManinfo(borrowNid);
            //房产抵押信息
            List<BorrowHousesVO> borrowHousesList = amTradeClient.getBorrowHousesByNid(borrowNid);
            //车辆抵押信息
            List<BorrowCarinfoVO> borrowCarinfoList = amTradeClient.getBorrowCarinfoByNid(borrowNid);
            //还款计划
            List<BorrowRepayPlanCsVO> repayPlanList = repayPlanService.getRepayPlan(borrowNid);
            other.put("repayPlanList", repayPlanList);
            //相关文件
           /* List<BorrowFileCustomBean> files = this.borrowService.searchProjectFiles(borrowNid, CustomConstants.HOST);
            other.put("fileList", files);*/  // TODO: 2018/6/25  文件后期处理
            // 还款信息
            BorrowRepayVO borrowRepay = null;
            List<BorrowRepayVO> list = amTradeClient.selectBorrowRepayList(borrowNid, null);
            if (!CollectionUtils.isEmpty(list)) {
                borrowRepay = list.get(0);
            }
            //资产列表
            JSONArray json = new JSONArray();
            //基础信息
            String baseTableData = "";
            //资产信息
            String assetsTableData = "";
            //项目介绍
            String intrTableData = "";
            //信用状况
            String credTableData = "";
            //审核信息
            String reviewTableData = "";
            //其他信息
            String otherTableData = "";
            //借款类型
            int borrowType = Integer.parseInt(borrow.getComOrPer());

            if (borrowType == 1 && borrowUsers != null) {
                //基础信息
                baseTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 1, borrowType, borrow.getBorrowLevel()));
                //信用状况
                credTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 4, borrowType, borrow.getBorrowLevel()));
                //审核信息
                reviewTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 5, borrowType, borrow.getBorrowLevel()));
                //其他信息
                otherTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 6, borrowType, borrow.getBorrowLevel()));
            } else {
                if (borrowManinfo != null) {
                    //基础信息
                    baseTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 1, borrowType, borrow.getBorrowLevel()));
                    //信用状况
                    credTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 4, borrowType, borrow.getBorrowLevel()));
                    //审核信息
                    reviewTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 5, borrowType, borrow.getBorrowLevel()));

                    //其他信息
                    otherTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 6, borrowType, borrow.getBorrowLevel()));
                }
            }
            //资产信息
            if (borrowHousesList != null && borrowHousesList.size() > 0) {
                for (BorrowHousesVO borrowHouses : borrowHousesList) {
                    json.add(ProjectConstant.packDetail(borrowHouses, 2, borrowType, borrow.getBorrowLevel()));
                }
            }
            if (borrowCarinfoList != null && borrowCarinfoList.size() > 0) {
                for (BorrowCarinfoVO borrowCarinfo : borrowCarinfoList) {
                    json.add(ProjectConstant.packDetail(borrowCarinfo, 2, borrowType, borrow.getBorrowLevel()));
                }
            }
            assetsTableData = json.toString();
            //项目介绍
            intrTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrow, 3, borrowType, borrow.getBorrowLevel()));

            //基础信息
            other.put("baseTableData", baseTableData);
            //资产信息
            other.put("assetsTableData", assetsTableData);
            //项目介绍
            other.put("intrTableData", intrTableData);
            //信用状况
            other.put("credTableData", credTableData);
            //审核信息
            other.put("reviewTableData", reviewTableData);
            // 信批需求新增(放款后才显示)
            if (borrow.getStatusOrginal() >= 4 && borrowRepay != null) {
                //其他信息
                other.put("otherTableData", otherTableData);
                other.put("updateTime", ProjectConstant.getUpdateTime(borrowRepay.getAddTime(), borrowRepay.getRepayYestime()));
            } else {
                //其他信息
                other.put("otherTableData", JSONObject.toJSONString(new ArrayList<BorrowDetailBean>()));
            }

        }

    }


    private BigDecimal getInterestDj(BigDecimal couponQuota, Integer couponProfitTime, BigDecimal borrowApr) {
        BigDecimal earnings = new BigDecimal("0");

        earnings = couponQuota.multiply(borrowApr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(couponProfitTime)).setScale(2, BigDecimal.ROUND_DOWN);

        return earnings;

    }

    private BigDecimal getInterest(String borrowStyle, Integer couponType, BigDecimal borrowApr, BigDecimal couponQuota, String money, Integer borrowPeriod) {
        BigDecimal earnings = new BigDecimal("0");

        // 投资金额
        BigDecimal accountDecimal = null;
        if (couponType == 1) {
            // 体验金 投资资金=体验金面值
            accountDecimal = couponQuota;
        } else if (couponType == 2) {
            // 加息券 投资资金=真实投资资金
            accountDecimal = new BigDecimal(money);
            borrowApr = couponQuota;
        } else if (couponType == 3) {
            // 代金券 投资资金=体验金面值
            accountDecimal = couponQuota;
        }
        switch (borrowStyle) {
            case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“：历史回报=投资金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getMonthInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“：历史回报=投资金额*年化收益÷360*天数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getDayInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：历史回报=投资金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：历史回报=投资金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = AverageCapitalPlusInterestUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_PRINCIPAL:// 还款方式为”等额本金“
                earnings = AverageCapitalUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                break;
            default:
                break;
        }
        if (couponType == 3) {
            earnings = earnings.add(couponQuota);
        }
        return earnings;
    }


    /**
     * 散标专区债权转让列表数据
     *
     * @author zhangyk
     * @date 2018/6/20 9:11
     */
    @Override
    public WebResult searchCreditList(CreditListRequest request) {
        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        // 原逻辑默认写死以下参数
        request.setBorrowPeriodMin(0);
        request.setBorrowPeriodMax(100);
        request.setBorrowAprMin(0);
        request.setBorrowAprMax(100);
        request.setDiscountSort("DESC");
        request.setTermSort("DESC");
        request.setCapitalSort("DESC");
        request.setInProgressSort("DESC");
        CreditListResponse res = baseClient.postExe("http://AM-TRADE/am-trade/projectlist/web/countCreditList", request, CreditListResponse.class);
        WebResult webResult = new WebResult();
        int count = res.getCount();
        page.setTotal(count);
        webResult.setData(new ArrayList<>());
        if (count > 0) {
            List<CreditListVO> result = new ArrayList<>();
            CreditListResponse dataResponse = baseClient.postExe("http://AM-TRADE/am-trade/projectlist/web/searchWebCreditList", request, CreditListResponse.class);
            result = dataResponse.getResultList();
            webResult.setData(result);
        }
        webResult.setPage(page);
        return webResult;
    }

    /**
     * web端债转标的详情
     *
     * @author zhangyk
     * @date 2018/6/26 9:57
     */
    @Override
    public WebResult getCreditDetail(Map<String, Object> map, String userId) {
        WebResult webResult = new WebResult();
        String creditNid = (String) map.get(ProjectConstant.PARAM_CREDIT_NID);
        // 数据校验
        CheckUtil.check(StringUtils.isNotBlank(creditNid), MsgEnum.STATUS_CE000001);

        Map<String, Object> result = new HashMap<>();
        // 获取债转的详细参数
        //BorrowCreditDetailVO creditDetail = amBorrowCreditClient.getCreditDetail(creditNid);
        BorrowCreditDetailResponse response = baseClient.getExe("http://AM-TRADE/am-trade/borrowCredit/borrowCreditDetail/" + creditNid, BorrowCreditDetailResponse.class);//
        BorrowCreditDetailVO creditDetail = response.getResult();
        if (Validator.isNull(creditDetail)) {
            throw new RuntimeException("债转详情不存在");
        }
        // 获取相应的标的详情
        String borrowNid = creditDetail.getBorrowNid();

        BorrowResponse res = baseClient.getExe("http://AM-TRADE/am-trade/borrow/getBorrow/" + borrowNid, BorrowResponse.class);//borrowClient.selectBorrowByNid(borrowNid);
        BorrowVO borrow = res.getResult();
        if (Validator.isNull(borrow)) {
            throw new RuntimeException("标的详情不存在");
        }
        // 项目类型
        Integer projectType = borrow.getProjectType();
        BorrowInfoResponse borrowInfoResponse = baseClient.getExe("http://AM-TRADE/am-trade/borrow/getBorrowInfoByNid/" + borrowNid, BorrowInfoResponse.class);
        BorrowInfoVO borrowInfoVO = borrowInfoResponse.getResult();
        if (Validator.isNull(borrowInfoVO)){
            throw new RuntimeException("标的info不存在");
        }
        // 企业标的用户标的区分
        String comOrPer = borrowInfoVO.getCompanyOrPersonal() == null ? "0" : String.valueOf(borrowInfoVO.getCompanyOrPersonal());
        result.put("creditDetail", creditDetail);
        ProjectCustomeDetailVO projectDetail = amTradeClient.selectProjectDetail(borrowNid);
        result.put(ProjectConstant.RES_PROJECT_INFO, projectDetail);

        if (borrowInfoVO.getIsNew() == 0) {
            // 如果项目为汇资产项目
            if (projectType == 9) {  // TODO: 2018/6/26 汇资产暂不处理
                // 4查询相应的汇资产的首页信息
//                WebHzcProjectDetailCustomize borrowInfo = this.projectService.searchHzcProjectDetail(borrowNid);
//                result.put("borrowInfo", borrowInfo);
//                // 处置预案
//                WebHzcDisposalPlanCustomize disposalPlan = this.projectService.searchDisposalPlan(borrowNid);
//                result.put("disposalPlan", disposalPlan);
            }
            // 项目为非汇资产项目
            else {
                // 4查询非汇资产项目的项目信息
                if ("1".equals(comOrPer)) {
                    // 查询相应的企业项目详情
                    ProjectCompanyDetailVO borrowInfo = amTradeClient.searchProjectCompanyDetail(borrowNid);
                    result.put("borrowInfo", borrowInfo);
                } else if ("2".equals(comOrPer)) {
                    // 查询相应的汇直投个人项目详情
                    WebProjectPersonDetailVO borrowInfo = amTradeClient.searchProjectPersonDetail(borrowNid);
                    result.put("borrowInfo", borrowInfo);
                }
                // 风控信息
                // TODO: 2018/6/26  待实现  WebRiskControlVO riskControl = this.projectService.selectRiskControl(borrowNid);
//                riskControl.setControlMeasures(riskControl.getControlMeasures()==null?"":riskControl.getControlMeasures().replace("\r\n", ""));
//                riskControl.setControlMort(riskControl.getControlMort()==null?"":riskControl.getControlMort().replace("\r\n", ""));
//                // 添加风控信息
//                result.put("riskControl", riskControl);
                List<BorrowCarinfoVO> borrowCarinfoVOList = amTradeClient.getBorrowCarinfoByNid(borrowNid);
                if (!CollectionUtils.isEmpty(borrowCarinfoVOList)) {
                    List<WebCarinfoVO> vehiclePledgeList = new ArrayList<>();
                    WebCarinfoVO temp;
                    for (BorrowCarinfoVO vo : borrowCarinfoVOList) {
                        temp = new WebCarinfoVO();
                        temp.setVehicleBrand(vo.getBrand());
                        temp.setEvaluationPrice(String.valueOf(vo.getToprice()));
                        temp.setPlace(vo.getPlace());
                        temp.setVehicleModel(vo.getModel());
                        temp.setVehicleNumber(StringUtils.isBlank(vo.getNumber()) ? "" : vo.getNumber().substring(0, 2) + "****");
                        temp.setPrice(String.valueOf(vo.getPrice()));
                        temp.setRegistration(vo.getRegistration());
                        temp.setVin(StringUtils.isBlank(vo.getVin()) ? "" : vo.getVin().substring(0, 2) + "*****");
                        vehiclePledgeList.add(temp);
                    }
                    result.put("vehiclePledgeList", vehiclePledgeList);
                }
                // 添加相应的汽车抵押信息
            }
            // 获取图片信息
            /*List<BorrowFileCustomBean> projectFileList = projectService.searchProjectFiles(borrowNid, CustomConstants.HOST);
            result.put("borrowFiles", projectFileList);*/  // TODO: 2018/6/26 后期处理   zyk
            // 还款计划
            List<BorrowRepayPlanCsVO> repayPlanList = repayPlanService.getRepayPlan(borrowNid);
            result.put("repayPlanList", repayPlanList);
        } else {
            /**
             * 借款类型 1、企业借款 2、借款人 3、汇资产
             */
            result.put(ProjectConstant.PARAM_BORROW_TYPE, comOrPer);
            // 借款人企业信息
            BorrowUserVO borrowUsers = amTradeClient.getBorrowUser(borrowNid);

            //借款人信息
            BorrowManinfoVO borrowManinfo = amTradeClient.getBorrowManinfo(borrowNid);
            //房产抵押信息
            List<BorrowHousesVO> borrowHousesList = amTradeClient.getBorrowHousesByNid(borrowNid);
            //车辆抵押信息
            List<BorrowCarinfoVO> borrowCarinfoList = amTradeClient.getBorrowCarinfoByNid(borrowNid);
            //还款计划
            List<BorrowRepayPlanCsVO> repayPlanList = repayPlanService.getRepayPlan(borrowNid);
            result.put("repayPlanList", repayPlanList);
            // 相关文件
           /* List<BorrowFileCustomBean> files = this.projectService.searchProjectFiles(borrowNid, CustomConstants.HOST);
            result.put("fileList", files);  // TODO: 2018/6/26  图片后期处理 */
            // 还款信息
            BorrowRepayVO borrowRepay = null;
            List<BorrowRepayVO> list = amTradeClient.selectBorrowRepayList(borrowNid, null);
            if (!CollectionUtils.isEmpty(list)) {
                borrowRepay = list.get(0);
            }

            // 资产列表
            JSONArray json = new JSONArray();
            // 基础信息
            String baseTableData = "";
            // 资产信息
            String assetsTableData = "";
            // 项目介绍
            String intrTableData = "";
            // 信用状况
            String credTableData = "";
            // 审核信息
            String reviewTableData = "";
            //其他信息
            String otherTableData = "";
            // 借款类型
            int borrowType = Integer.parseInt(comOrPer);
            if (borrowType == 1 && borrowUsers != null) {
                // 基础信息
                baseTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 1, borrowType, projectDetail.getBorrowLevel()));
                // 信用状况
                credTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 4, borrowType, projectDetail.getBorrowLevel()));
                // 审核信息
                reviewTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 5, borrowType, projectDetail.getBorrowLevel()));
                //其他信息
                otherTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 6, borrowType, borrow.getBorrowLevel()));
            } else {
                if (borrowManinfo != null) {
                    // 基础信息
                    baseTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 1, borrowType, projectDetail.getBorrowLevel()));
                    // 信用状况
                    credTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 4, borrowType, projectDetail.getBorrowLevel()));
                    // 审核信息
                    reviewTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 5, borrowType, projectDetail.getBorrowLevel()));
                    //其他信息
                    otherTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 6, borrowType, borrow.getBorrowLevel()));
                }
            }
            // 资产信息
            if (borrowHousesList != null && borrowHousesList.size() > 0) {
                for (BorrowHousesVO borrowHouses : borrowHousesList) {
                    json.add(ProjectConstant.packDetail(borrowHouses, 2, borrowType, projectDetail.getBorrowLevel()));
                }
            }
            if (borrowCarinfoList != null && borrowCarinfoList.size() > 0) {
                for (BorrowCarinfoVO borrowCarinfo : borrowCarinfoList) {
                    json.add(ProjectConstant.packDetail(borrowCarinfo, 2, borrowType, projectDetail.getBorrowLevel()));
                }
            }
            assetsTableData = json.toString();
            // 项目介绍
            intrTableData = JSONObject.toJSONString(ProjectConstant.packDetail(projectDetail, 3, borrowType, projectDetail.getBorrowLevel()));
            // 基础信息
            result.put("baseTableData", baseTableData);
            // 资产信息
            result.put("assetsTableData", assetsTableData);
            // 项目介绍
            result.put("intrTableData", intrTableData);
            // 信用状况
            result.put("credTableData", credTableData);
            // 审核信息
            result.put("reviewTableData", reviewTableData);
            // 信批需求新增(放款后才显示)
            if (borrow.getStatus() >= 4 && borrowRepay != null) {
                //其他信息
                result.put("otherTableData", otherTableData);
                result.put("updateTime", ProjectConstant.getUpdateTime(borrowRepay.getAddTime(), borrowRepay.getRepayYestime()));
            } else {
                //其他信息
                result.put("otherTableData", JSONObject.toJSONString(new ArrayList<BorrowDetailBean>()));
            }
        }
        // 剩余可承接金额
        BigDecimal creditAssignCapital = new BigDecimal(creditDetail.getCreditAssignCapital());
        if (creditAssignCapital.compareTo(new BigDecimal("100")) <= 0) {
            result.put("isLast", "1");// 是否是最后一笔
        } else {
            result.put("isLast", "0");
        }
        // 登陆用户
        UserVO userVO = null;
        if (StringUtils.isNotBlank(userId)) {
            userVO = amUserClient.findUserById(Integer.valueOf(userId));
        }
        try {
            if (userVO != null) {
                result.put("loginFlag", "1");// 判断是否登录
                result.put("openFlag", userVO.getBankOpenAccount());// 判断是否开户
                result.put("setPwdFlag", userVO.getIsSetPassword()); // 是否设置交易密码
                result.put("isUserValid", userVO.getStatus());
                //update by jijun 2018/04/09 合规接口改造一期
                result.put("paymentAuthStatus", ""); // 缴费授权

                // 获取用户信息
                AccountVO account = amTradeClient.getAccountByUserId(Integer.valueOf(userId));
                // 可用余额
                result.put("balance", account.getBankBalance().toString());
                // 风险测评改造 mod by liuyang 20180111 start
                // 风险测评标识
                result.put("riskFlag", String.valueOf(userVO.getIsEvaluationFlag()));
                // 风险测评改造 mod by liuyang 20180111 end
            } else {
                result.put("loginFlag", "0");// 判断是否登录
                result.put("openFlag", "0");
                result.put("loginFlag", "0");
                result.put("setPwdFlag", "0");
                result.put("isUserValid", "0");
                result.put("riskFlag", "0");// 是否进行过风险测评 0未测评 1已测评
            }
        } catch (Exception e) {
        }
        webResult.setData(result);
        return webResult;
    }


    /**
     * web端债转详情:承接记录
     * @author zhangyk
     * @date 2018/6/26 9:56
     */
    @Override
    public WebResult getCreditTenderList(WebCreditRequestBean requestBean) {
        WebResult result = new WebResult();
        JSONObject info = new JSONObject();
        String creditNid = requestBean.getCreditNid();
        CheckUtil.check(StringUtils.isNotBlank(creditNid), MsgEnum.ERR_OBJECT_REQUIRED, "债转编号");
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());

        Map<String, Object> req = new HashMap<>();
        req.put("creditNid", creditNid);

        CountResponse response = baseClient.postExe(CREDIT_DETAIL_TENDER_COUNT_URL, req, CountResponse.class);
        int count = response.getCount();
        if (count > 0) {
            req.put(CommonConstant.PAGE_LIMIT_START, page.getOffset());
            req.put(CommonConstant.PAGE_LIMIT_END, page.getLimit());
            CreditTenderListResponse res = baseClient.postExe(CREDIT_DETAIL_TENDER_LIST_URL, req, CreditTenderListResponse.class);
            // 查询列表数据
            List<CreditTenderListCustomizeVO> list = res.getResultList();
            CommonUtils.convertNullToEmptyString(list);
            info.put("recordList", list);
            BorrowCreditVO borrowCreditVO = amTradeClient.getBorrowCreditByCreditNid(creditNid);
            if (borrowCreditVO != null) {
                info.put("assignTotal", borrowCreditVO.getCreditCapitalAssigned());
                info.put("assignTimes", borrowCreditVO.getAssignNum());
            }

        } else {
            info.put("recordList", new ArrayList<>());
            info.put("assignTotal", 0);
            info.put("assignTimes", 0);
        }
        // 加入总数直接从page的count 中取
        page.setTotal(count);
        result.setPage(page);
        result.setData(info);
        return result;
    }


    /**
     * 计划专区上部统计数据
     *
     * @author zhangyk
     * @date 2018/6/19 16:33
     */
    @Override
    public WebResult searchPlanData(ProjectListRequest request) {
        TotalInvestAndInterestResponse response = baseClient.getExe(HomePageDefine.INVEST_INVEREST_AMOUNT_URL, TotalInvestAndInterestResponse.class);
        TotalInvestAndInterestVO totalInvestAndInterestVO = response.getResult();
        Map<String, Object> map = new HashMap<>();
        if (totalInvestAndInterestVO != null) {
            map.put(ProjectConstant.HJH_DATA_ACCEDE_ACCOUNT_TOTAL, CommonUtils.formatAmount(totalInvestAndInterestVO.getHjhTotalInvestAmount()));
            map.put(ProjectConstant.HJH_DATA_INTEREST_TOTAL, CommonUtils.formatAmount(totalInvestAndInterestVO.getHjhTotalInterestAmount()));
            map.put(ProjectConstant.HJH_DATA_ACCEDE_TIMES, totalInvestAndInterestVO.getHjhTotalInvestNum());
        }
        WebResult webResult = new WebResult();
        webResult.setData(map);
        return webResult;
    }

    /**
     * 计划专区计划列表数据
     *
     * @author zhangyk
     * @date 2018/6/21 15:21
     */
    @Override
    public WebResult searchPlanList(ProjectListRequest request) {
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        Integer count = amTradeClient.countPlanList(request);
        WebResult webResult = new WebResult();
        webResult.setData(new ArrayList<>());
        Map<String, Object> result = new HashMap<>();
        if (count == null) {
            logger.error("web查询原子层计划专区计划列表数据count异常");
            throw new RuntimeException("web查询原子层计划专区计划列表数据count异常");
        }
      /*  // 上部统计数据
        Map<String, Object> map = amTradeClient.searchPlanData(request);
        if (map == null) {
            logger.error("web查询原子层计划专区统计数据异常");
            throw new RuntimeException("web查询原子层计划专区统计数据异常");
        }*/
        if (count > 0) {
            List<HjhPlanCustomizeVO> list = amTradeClient.searchPlanList(request);
            if (CollectionUtils.isEmpty(list)) {
                logger.error("web查询原子层计划专区计划列表数据异常");
                throw new RuntimeException("web查询原子层计划专区计划列表数据异常");
            }
            result.put(ProjectConstant.WEB_PLAN_LIST, list);
        }
        webResult.setData(result);
        page.setTotal(count);
        webResult.setPage(page);
        return webResult;
    }

    @Override
    public WebResult getPlanDetail(Map<String, String> map, String userId) {
        Map<String, Object> result = new HashMap<>();
        WebResult webResult = new WebResult();
        // 获取计划编号(列表画面传递-计划编号)
        String planNid = map.get(ProjectConstant.PARAM_PLAN_NID);
        // 阀值
        Integer threshold = 1000;
        result.put("threshold", threshold);
        // 缴费授权
        //update by jijun 2018/04/09 合规接口改造一期
        result.put("paymentAuthStatus", "");

        //加入总人数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ProjectConstant.PARAM_PLAN_NID, planNid);
        HjhAccedeRequest request = new HjhAccedeRequest();
        request.setPlanNid(planNid);
        int joinPeopleNum = amTradeClient.countPlanAccedeRecordTotal(request);
        result.put("joinPeopleNum", String.valueOf(joinPeopleNum));

        // 根据项目标号获取相应的计划信息
        PlanDetailCustomizeVO planDetail = amTradeClient.getPlanDetail(planNid);

        // 线上异常处理 如果为空的话直接返回
        if (planDetail == null) {
            logger.error("未查询到对应的计划，计划编号为:" + planNid);


        }
        // 最小投资金额(起投金额)-->计算最后一笔投资
        if (planDetail.getDebtMinInvestment() != null) {
            result.put("debtMinInvestment", new BigDecimal(planDetail.getDebtMinInvestment()).intValue());
        }
        // 开放额度剩余金额(取小数两位)
        if (planDetail.getAvailableInvestAccount() != null) {

            // 开放额度< 0 显示 0.00
            if (new BigDecimal(planDetail.getAvailableInvestAccount()).compareTo(BigDecimal.ZERO) == -1) {
                // 画面有地方用 planDetail.availableInvestAccount
                planDetail.setAvailableInvestAccount("0.00");
                // 画面也有地方有 availableInvestAccount
                result.put("availableInvestAccount", new BigDecimal(0.00));
            } else {
                result.put("availableInvestAccount", new BigDecimal(planDetail.getAvailableInvestAccount()));
            }
        }
        if (Validator.isNotNull(planDetail)) {
            //系统当前时间戳
            result.put("nowTime", GetDate.getNowTime10());

            /** 汇添金优惠券使用开始 pcc */
            DecimalFormat df = null;
            df = CustomConstants.DF_FOR_VIEW;
            /** 计算最优优惠券开始 pccvip isThereCoupon 1是有最优优惠券，0无最有优惠券 */
            UserCouponConfigCustomizeVo couponConfig = null;
            //获取用户优惠券总张数
            int recordTotal = 0;
            //可用优惠券张数
            int availableCouponListCount = 0;
            if (StringUtils.isNotBlank(userId)) {
                /** 获取用户是否是vip 开始 pccvip 1是vip 0不是vip */
               /* UsersInfo usersInfo = planService.getUsersInfoByUserId(loginUser.getUserId());
                if (usersInfo.getVipId() != null && usersInfo.getVipId() != 0) {
                    result.put("ifVip", 1);
                    String returl = HOST_URL + VIPManageDefine.REQUEST_MAPPING + "/" + VIPManageDefine.INIT_ACTION + ".do";
                    result.put("returl", returl);
                } else {
                    result.put("ifVip", 0);
                    String returl = HOST_URL + ApplyDefine.REQUEST_MAPPING + ApplyDefine.INIT + ".do";
                    result.put("returl", returl);

                }*/
                /** 获取用户是否是vip 结束 pccvip */
            }
            /*优惠券模块开始 */ // TODO: 2018/6/28 优惠券后期处理
            /*couponConfig = planService.getUserOptimalCoupon(couponId, planNid, loginUser.getUserId(), null, "0");
            recordTotal = planService.countCouponUsers(0, loginUser.getUserId());
            availableCouponListCount = planService.getUserCouponAvailableCount(planNid, loginUser.getUserId(), "0", "0");
            *//** 获取用户优惠券总张数开始 pccvip *//*
            result.put("recordTotal", recordTotal);
            *//** 获取用户优惠券总张数结束 pccvip *//*
             *//** 可用优惠券张数开始 pccvip *//*
            result.put("couponAvailableCount", availableCouponListCount);
            *//** 可用优惠券张数结束 pccvip *//*
            BigDecimal couponInterest = BigDecimal.ZERO;
            result.put("interest", BigDecimal.ZERO);
            if (couponConfig != null) {
                result.put("isThereCoupon", 1);

                couponInterest = planService.getCouponInterest(couponConfig.getUserCouponId(), planNid, "0");
                couponConfig.setCouponInterest(df.format(couponInterest));
                if(couponConfig!=null && couponConfig.getCouponType()==3){
                    result.put("interest", df.format(couponInterest.subtract(couponConfig.getCouponQuota())));
                }else{
                    result.put("interest", df.format(couponInterest));
                }

            } else {
                result.put("isThereCoupon", 0);
            }*/

            /*优惠券模块结束 */

            result.put("couponConfig", couponConfig);
            /** 计算最优优惠券结束 */
            /** 汇添金优惠券使用结束 pcc */

            // 计划详情头部(结束)

            PlanDetailBean detailBean = CommonUtils.convertBean(planDetail, PlanDetailBean.class);
            result.put("planDetail", detailBean);
            // 获取计划介绍
            String planIntroduce = planDetail.getPlanConcept();
            if (Validator.isNotNull(planIntroduce)) {
                result.put("planIntroduce", planIntroduce);
            }
            // 获取计划原理
            String planPrinciple = planDetail.getPlanPrinciple();
            if (Validator.isNotNull(planPrinciple)) {
                result.put("planPrinciple", planPrinciple);

            }
            // 获取风控保障措施
            String safeguardMeasures = planDetail.getSafeguardMeasures();
            if (Validator.isNotNull(safeguardMeasures)) {
                result.put("safeguardMeasures", safeguardMeasures);

            }
            // 获取风险保证金措施
            String marginMeasures = planDetail.getMarginMeasures();
            if (Validator.isNotNull(marginMeasures)) {
                result.put("marginMeasures", marginMeasures);

            }
            // 获取常见问题
            String normalQuestions = planDetail.getNormalQuestions();
            if (Validator.isNotNull(normalQuestions)) {
                result.put("normalQuestions", normalQuestions);

            }
            // 获取各种标志位
            String investFlag = "0";
            UserVO userVO = null;
            if (StringUtils.isNotBlank(userId)) {
                userVO = amUserClient.findUserById(Integer.valueOf(userId));
            }
            if (userVO != null) {
                // 用户是否加入过项目
                request.setUserId(userId);

                int count = this.amTradeClient.countPlanAccedeRecordTotal(request);
                if (count > 0) {
                    investFlag = "1";
                } else {
                    investFlag = "0";//是否投资过该项目 0未投资 1已投资
                }
                result.put("investFlag", investFlag);
                // 用户是否开户
                if (userVO.getBankOpenAccount() != null) {
                    result.put("openFlag", 1);
                } else {
                    result.put("openFlag", 0);
                }
                // 用户是否设置交易密码
                if (userVO.getIsSetPassword() == 1) {
                    result.put("setPwdFlag", "1");
                } else {
                    result.put("setPwdFlag", "0");
                }
                // 用户是否被禁用：0 未禁用 1禁用
                if (userVO.getStatus() == 1) {
                    result.put("forbiddenFlag", "1");
                } else {
                    result.put("forbiddenFlag", "0");
                }
                // 用户是否完成风险测评标识
                result.put("riskFlag", userVO.getIsEvaluationFlag());
                result.put("loginFlag", 1);


                // 获取用户账户余额
                AccountVO account = amTradeClient.getAccountByUserId(Integer.valueOf(userId));
                if (Validator.isNotNull(account)) {
                    String userBalance = account.getBankBalance().toString();
                    result.put("userBalance", userBalance);
                }
                // 用户是否完成自动授权标识
                HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(Integer.valueOf(userId));
                if (Validator.isNotNull(hjhUserAuth)) {
                    String autoInvesFlag = hjhUserAuth.getAutoInvesStatus().toString();
                    result.put("autoInvesFlag", autoInvesFlag);
                } else {
                    result.put("autoInvesFlag", "0");//自动投标授权状态 0: 未授权    1:已授权
                }
            } else {
                //状态位用于判断tab的是否可见
                result.put("loginFlag", "0");//登录状态 0未登陆 1已登录
                result.put("openFlag", "0"); //开户状态 0未开户 1已开户
                result.put("investFlag", "0");//是否投资过该项目 0未投资 1已投资
                result.put("riskFlag", "0");//是否进行过风险测评 0未测评 1已测评
                result.put("setPwdFlag", "0");//是否设置过交易密码 0未设置 1已设置
                result.put("forbiddenFlag", "0");//是否禁用 0未禁用 1已禁用
            }
        }
        webResult.setData(result);
        return webResult;
    }


    /**
     * 计划详情:标的组成
     *
     * @author zhangyk
     * @date 2018/7/23 10:09
     */
    @Override
    public WebResult getPlanBorrowList(WebPlanRequestBean request) {
        WebResult result = new WebResult();
        String planNid = request.getPlanNid();
        Date date = GetDate.getDate();
        int dayStart10 = GetDate.getDayStart10(date);
        int dayEnd10 = GetDate.getDayEnd10(date);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planNid", planNid);
        params.put("startTime", dayStart10);
        params.put("endTime", dayEnd10);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        HjhAccedeResponse response = baseClient.postExe(HJH_DETAIL_BORROW_LIST_COUNT_URL, params, HjhAccedeResponse.class);
        int count = response.getAccedeCount();
        result.setData(new ArrayList<>());
        if (count > 0) {
            params.put("limitStart", page.getOffset());
            params.put("limitEnd", page.getLimit());
            BorrowResponse res = baseClient.postExe(HJH_DETAIL_BORROW_LIST_URL, params, BorrowResponse.class);
            List<BorrowVO> list = res.getResultList();
            formatUserName(list);
            result.setData(list);
        }
        page.setTotal(count);
        result.setPage(page);
        return result;
    }


    /**
     * 计划详情:加入记录
     *
     * @author zhangyk
     * @date 2018/7/24 18:51
     */
    @Override
    public WebResult getPlanAccedeList(WebPlanRequestBean requestBean, String userId) {
        WebResult result = new WebResult();
        JSONObject info = new JSONObject();
        CheckUtil.check(StringUtils.isNotBlank(requestBean.getPlanNid()), MsgEnum.ERR_OBJECT_REQUIRED, "计划编号");
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planNid", requestBean.getPlanNid());
        HjhAccedeResponse response = baseClient.postExe(HJH_DETAIL_ACCEDE_COUNT_URL, params, HjhAccedeResponse.class);
        Map<String, Object> totalData = response.getTotalData();
        int count;
        double accedeTotal;
        if (totalData == null || totalData.get("count") == null) {
            count = 0;
            accedeTotal = 0.00;
        } else {
            count = (Integer) totalData.get("count");
            accedeTotal = (double) totalData.get("sum");
        }
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        info.put("planAccedeList", new ArrayList<>());
        if (count > 0) {
            params.put("limitStart", page.getOffset());
            params.put("limitEnd", page.getLimit());
            HjhAccedeListResponse res = baseClient.postExe(HJH_DETAIL_ACCEDE_LIST_URL, params, HjhAccedeListResponse.class);
            List<HjhAccedeCustomizeVO> list = res.getResultList();
            info.put("planAccedeList", list);
        }

        info.put("accedeTimes", count);
        info.put("accedeTotal", accedeTotal);
        result.setData(info);
        page.setTotal(count);
        result.setPage(page);
        return result;
    }

    /**
     * 格式化用户姓名
     *
     * @author zhangyk
     * @date 2018/7/23 14:49
     */
    private void formatUserName(List<BorrowVO> list) {
        for (BorrowVO planAccede : list) {
            String borrowNid = planAccede.getBorrowNid();
            if ("1".equals(planAccede.getCompanyOrPersonal())) {//如果类型是公司 huiyingdai_borrow_users
                BorrowUserVO borrowUser = amTradeClient.getBorrowUser(borrowNid);
                String trueName = borrowUser.getUsername();
                String str = "******";
                if (trueName != null && trueName != "") {
                    if (trueName.length() <= 2) {
                        trueName = str + trueName;
                    } else if (trueName.length() > 2) {
                        String substring = trueName.substring(trueName.length() - 2);
                        trueName = str + substring;
                    }
                }
                planAccede.setBorrowUserName(trueName);
            } else if ("2".equals(planAccede.getCompanyOrPersonal())) {//类型是个人 huiyingdai_borrow_maninfo
                //根据borrowNid查询查询个人的真实姓名
                BorrowManinfoVO borrowManinfoVO = amTradeClient.getBorrowManinfo(borrowNid);
                String trueName = borrowManinfoVO.getName();
                String str = "**";
                if (trueName != null && trueName != "") {
                    if (trueName.length() == 1) {
                        trueName = trueName + str;
                    } else if (trueName.length() >= 1) {
                        String substring = trueName.substring(0, 1);
                        trueName = substring + str;
                    }
                }
                planAccede.setBorrowUserName(trueName);
            }
        }
    }


}
