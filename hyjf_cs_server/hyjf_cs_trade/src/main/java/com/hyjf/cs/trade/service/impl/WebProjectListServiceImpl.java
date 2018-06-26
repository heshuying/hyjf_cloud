package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.*;
import com.hyjf.common.util.calculate.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.BorrowDetailBean;
import com.hyjf.cs.trade.bean.BorrowRepayPlanCsVO;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.WebProjectListService;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
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

    @Autowired
    private WebProjectListClient webProjectListClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private CouponConfigClient couponConfigClient;

    @Autowired
    private AmBorrowTenderClient amBorrowTenderClient;

    @Autowired
    private AmAccountClient amAccountClient;

    @Autowired
    private AmBorrowUserClient amBorrowUserClient;

    @Autowired
    private AmBorrowManinfoClient amBorrowManinfoClient;

    @Autowired
    private AmBorrowHousesClient amBorrowHousesClient;

    @Autowired
    private AmBorrowCarinfoClient amBorrowCarinfoClient;

    @Autowired
    private RepayPlanServiceImpl repayPlanService;

    @Autowired
    private AmBorrowRepayClient amBorrowRepayClient;

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
        ProjectListResponse response = webProjectListClient.countProjectList(request);
        // 对调用返回的结果进行转换和拼装
        WebResult webResult = new WebResult();
        // 先抛错方式，避免代码看起来头重脚轻。
        if (!Response.isSuccess(response)) {
            logger.error("查询散标投资列表原子层count异常");
            throw new RuntimeException("查询散标投资列表原子层count异常");
        }
        int count = response.getCount();
        page.setTotal(count);
        //由于result类在转json时会去掉null值，手动初始化为非null，保证json不丢失key
        webResult.setData(new ArrayList<>());
        if (count > 0) {
            List<WebProjectListCsVO> result = new ArrayList<>();
            ProjectListResponse dataResponse = webProjectListClient.searchProjectList(request);
            if (!Response.isSuccess(dataResponse)) {
                logger.error("查询散标投资列表原子层List异常");
                throw new RuntimeException("查询散标投资列表原子层list数据异常");
            }
            result = CommonUtils.convertBeanList(dataResponse.getResultList(), WebProjectListCsVO.class);
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
                ProjectListResponse dataResponse = webProjectListClient.searchProjectList(request);
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
        Object borrowNid = map.get("borrowNid");
        CheckUtil.check(null == borrowNid, MsgEnum.ERR_OBJECT_REQUIRED, "借款编号");
        ProjectListRequest request = new ProjectListRequest();
        // ① 先查出标的基本信息  ② 根据是否是新标的，进行参数组装
        ProjectCustomeDetailVO projectCustomeDetail = webProjectListClient.searchProjectDetail(map);
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
        if (detailCsVO.getIsNew() == 0 || detailCsVO.getType().equals("13")) {
            // TODO: 2018/6/23  getProjectDetail(modelAndView, detailCsVO,userId);     待确认是否还有老标后再处理
        } else {
            getProjectDetailNew(other, projectCustomeDetail, userVO);
        }
        WebResult webResult = new WebResult();
        webResult.setData(other);
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
            couponConfig = couponConfigClient.selectBestCoupon(request);
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
            Integer couponAvailableCount = couponConfigClient.countAvaliableCoupon(request);
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
            int count = amBorrowTenderClient.countUserInvest(userId, borrowNid); // TODO: 2018/6/25 //待实现
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
            AccountVO account = amAccountClient.getAccountByUserId(userId);  // TODO: 2018/6/25  待实现
            String userBalance = account.getBankBalance().toString();
            other.put("userBalance", userBalance);

            /* 用户基本信息 结束*/
        }


        //收益
        BigDecimal borrowInterest = BigDecimal.ZERO;

        /**
         * 融通宝收益叠加
         */
        if (borrow.getType().equals("13")) {
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

        if (borrow.getType().equals("9")) {// 如果项目为汇资产项目
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
            other.put("projectDeatil", borrow);
            /**
             * 借款类型  1、企业借款 2、借款人  3、汇资产
             */
            other.put("borrowType", borrow.getComOrPer());
            //借款人企业信息
            BorrowUserVO borrowUsers = amBorrowUserClient.getBorrowUser(borrowNid);
            //借款人信息
            BorrowManinfoVO borrowManinfo = amBorrowManinfoClient.getBorrowManinfo(borrowNid);
            //房产抵押信息
            List<BorrowHousesVO> borrowHousesList = amBorrowHousesClient.getBorrowHousesByNid(borrowNid);
            //车辆抵押信息
            List<BorrowCarinfoVO> borrowCarinfoList = amBorrowCarinfoClient.getBorrowCarinfoByNid(borrowNid);
            //还款计划
            List<BorrowRepayPlanCsVO> repayPlanList = repayPlanService.getRepayPlan(borrowNid);
            other.put("repayPlanList", repayPlanList);
            //相关文件
           /* List<BorrowFileCustomBean> files = this.borrowService.searchProjectFiles(borrowNid, CustomConstants.HOST);
            other.put("fileList", files);*/  // TODO: 2018/6/25  文件后期处理
            // 还款信息
            BorrowRepayVO borrowRepay = null;
            List<BorrowRepayVO> list = amBorrowRepayClient.selectBorrowRepayList(borrowNid, null);
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
                baseTableData = JSONObject.toJSONString(packDetail(borrowUsers, 1, borrowType, borrow.getBorrowLevel()));
                //信用状况
                credTableData = JSONObject.toJSONString(packDetail(borrowUsers, 4, borrowType, borrow.getBorrowLevel()));
                //审核信息
                reviewTableData = JSONObject.toJSONString(packDetail(borrowUsers, 5, borrowType, borrow.getBorrowLevel()));
                //其他信息
                otherTableData = JSONObject.toJSONString(packDetail(borrowUsers, 6, borrowType, borrow.getBorrowLevel()));
            } else {
                if (borrowManinfo != null) {
                    //基础信息
                    baseTableData = JSONObject.toJSONString(packDetail(borrowManinfo, 1, borrowType, borrow.getBorrowLevel()));
                    //信用状况
                    credTableData = JSONObject.toJSONString(packDetail(borrowManinfo, 4, borrowType, borrow.getBorrowLevel()));
                    //审核信息
                    reviewTableData = JSONObject.toJSONString(packDetail(borrowManinfo, 5, borrowType, borrow.getBorrowLevel()));

                    //其他信息
                    otherTableData = JSONObject.toJSONString(packDetail(borrowManinfo, 6, borrowType, borrow.getBorrowLevel()));
                }
            }
            //资产信息
            if (borrowHousesList != null && borrowHousesList.size() > 0) {
                for (BorrowHousesVO borrowHouses : borrowHousesList) {
                    json.add(packDetail(borrowHouses, 2, borrowType, borrow.getBorrowLevel()));
                }
            }
            if (borrowCarinfoList != null && borrowCarinfoList.size() > 0) {
                for (BorrowCarinfoVO borrowCarinfo : borrowCarinfoList) {
                    json.add(packDetail(borrowCarinfo, 2, borrowType, borrow.getBorrowLevel()));
                }
            }
            assetsTableData = json.toString();
            //项目介绍
            intrTableData = JSONObject.toJSONString(packDetail(borrow, 3, borrowType, borrow.getBorrowLevel()));

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
                other.put("updateTime", getUpdateTime(borrowRepay.getAddTime(), borrowRepay.getRepayYestime()) );
            } else {
                //其他信息
                other.put("otherTableData", JSONObject.toJSONString(new ArrayList<BorrowDetailBean>()));
            }

        }

    }


    /**
     * 计算更新时间
     *
     * @param timeLoan
     * @param timeRepay
     * @return
     */
    public static String getUpdateTime(Integer timeLoan, Integer timeRepay) {
        if (timeLoan == null) {
            return "";
        }

        Integer timeCurr = GetDate.getNowTime10();
        if (timeRepay != null && timeCurr > timeRepay) {
            timeCurr = timeRepay;
        }

        Integer timeDiff = timeCurr - timeLoan;
        Integer timeDiffMonth = timeDiff / (60 * 60 * 24 * 31);

        Calendar timeLoanCal = Calendar.getInstance();
        timeLoanCal.setTimeInMillis(timeLoan * 1000L);

        if (timeDiffMonth >= 1) {
            timeLoanCal.add(Calendar.MONTH, timeDiffMonth);
        }

        return GetDate.formatDate(timeLoanCal);
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
     * 封装项目详情页
     *
     * @param objBean
     * @param type        1 基础信息 2资产信息 3项目介绍 4信用状况 5审核状况
     * @param borrowType  1借款人 2企业借款
     * @param borrowLevel 信用评级
     * @return
     */
    private List<BorrowDetailBean> packDetail(Object objBean, int type, int borrowType, String borrowLevel) {
        List<BorrowDetailBean> detailBeanList = new ArrayList<BorrowDetailBean>();
        String currencyName = "元";
        // 得到对象
        Class c = objBean.getClass();
        // 得到方法
        Field fieldlist[] = c.getDeclaredFields();
        for (int i = 0; i < fieldlist.length; i++) {
            // 获取类属性
            Field f = fieldlist[i];
            // 得到方法名
            String fName = f.getName();
            try {
                // 参数方法获取
                String paramName = fName.substring(0, 1).toUpperCase() + fName.substring(1, fName.length());
                // 取得结果
                Method getMethod = c.getMethod(BankCallConstant.GET + paramName);
                if (getMethod != null) {
                    Object result = getMethod.invoke(objBean);
                    // 结果不为空时
                    if (Validator.isNotNull(result)) {
                        //封装bean
                        BorrowDetailBean detailBean = new BorrowDetailBean();
                        detailBean.setId(fName);
                        detailBean.setVal(result.toString());
                        if (type == 1) {
                            if (borrowType == 2) {//个人借款
                                switch (fName) {
                                    case "name":
                                        detailBean.setKey("姓名");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 1, 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "cardNo":
                                        detailBean.setKey("身份证号");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "sex":
                                        detailBean.setKey("性别");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("男");
                                        } else {
                                            detailBean.setVal("女");
                                        }
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "old":
                                        if (!"0".equals(detailBean.getVal())) {
                                            detailBean.setKey("年龄");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "merry":
                                        if (!("0".equals(result.toString()) || result.toString() == null)) {
                                            detailBean.setKey("婚姻状况");
                                            if ("1".equals(result.toString())) {
                                                detailBean.setVal("已婚");
                                            } else if ("2".equals(result.toString())) {
                                                detailBean.setVal("未婚");
                                            } else if ("3".equals(result.toString())) {
                                                detailBean.setVal("离异");
                                            } else if ("4".equals(result.toString())) {
                                                detailBean.setVal("丧偶");
                                            }
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "city":
                                        detailBean.setKey("工作城市");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "domicile":
                                        detailBean.setKey("户籍地");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "position":
                                        detailBean.setKey("岗位职业");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "annualIncome":
                                        detailBean.setKey("年收入");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "overdueReport":
                                        detailBean.setKey("征信报告逾期情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "debtSituation":
                                        detailBean.setKey("重大负债状况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "otherBorrowed":
                                        detailBean.setKey("其他平台借款情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    default:
                                        break;
                                }
                            } else {//企业借款

                                switch (fName) {
                                    case "currencyName":
                                        currencyName = detailBean.getVal();
                                        break;
                                    case "username":
                                        detailBean.setKey("借款主体");
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), detailBean.getVal().length() - 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "city":
                                        detailBean.setKey("注册地区");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "regCaptial":
                                        detailBean.setKey("注册资本");
                                        if (StringUtils.isNotBlank(detailBean.getVal())) {
                                            detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + currencyName);
                                        }
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "comRegTime":
                                        detailBean.setKey("注册时间");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "socialCreditCode":
                                        detailBean.setKey("统一社会信用代码");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "registCode":
                                        detailBean.setKey("注册号");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "legalPerson":
                                        detailBean.setKey("法定代表人");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 1, 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "industry":
                                        detailBean.setKey("所属行业");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "mainBusiness":
                                        detailBean.setKey("主营业务");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "overdueReport":
                                        detailBean.setKey("征信报告逾期情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "debtSituation":
                                        detailBean.setKey("重大负债状况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "otherBorrowed":
                                        detailBean.setKey("其他平台借款情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else if (type == 2) {
                            switch (fName) {
                                case "housesType":
                                    detailBean.setKey("资产类型");
                                    //String houseType = this.borrowService.getParamName("HOUSES_TYPE", detailBean.getVal());
                                    String key = "hyjf_param_name:HOUSES_TYPE";
                                    Map<String, String> houseTypeMap = RedisUtils.hgetall(key);
                                    if (!CollectionUtils.isEmpty(houseTypeMap)) {
                                        detailBean.setVal(houseTypeMap.get(detailBean.getVal()));
                                    } else {
                                        detailBean.setVal("住宅");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesArea":
                                    detailBean.setKey("资产面积");
                                    detailBean.setVal(detailBean.getVal() + "m<sup>2</sup>");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesCnt":
                                    detailBean.setKey("资产数量");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesToprice":
                                    detailBean.setKey("评估价值");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesBelong":
                                    detailBean.setKey("资产所属");
                                    detailBeanList.add(detailBean);
                                    break;
                                //车辆
                                case "brand":
                                    BorrowDetailBean carBean = new BorrowDetailBean();
                                    carBean.setId("carType");
                                    carBean.setKey("资产类型");
                                    carBean.setVal("车辆");
                                    detailBeanList.add(carBean);
                                    detailBean.setKey("品牌");
                                    detailBeanList.add(detailBean);
                                    break;

                                case "model":
                                    detailBean.setKey("型号");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "place":
                                    detailBean.setKey("产地");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "price":
                                    detailBean.setKey("购买价格");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "toprice":
                                    detailBean.setKey("评估价值");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "number":
                                    detailBean.setKey("车牌号");
                                    //数据脱敏
                                    detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 2, 4));
                                    detailBeanList.add(detailBean);
                                    break;
                                case "registration":
                                    detailBean.setKey("车辆登记地");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "vin":
                                    detailBean.setKey("车架号");
                                    //数据脱敏
                                    detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 5));
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }

                        } else if (type == 3) {
                            switch (fName) {
                                case "borrowContents":
                                    detailBean.setKey("项目信息");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "fianceCondition":
                                    detailBean.setKey("财务状况 ");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "financePurpose":
                                    detailBean.setKey("借款用途");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "monthlyIncome":
                                    detailBean.setKey("月薪收入");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(detailBean.getVal());
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "payment":
                                    detailBean.setKey("还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "firstPayment":
                                    detailBean.setKey("第一还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "secondPayment"://还没有
                                    detailBean.setKey("第二还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "costIntrodution":
                                    detailBean.setKey("费用说明");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        } else if (type == 4) {
                            switch (fName) {
                                case "overdueTimes":
                                    detailBean.setKey("在平台逾期次数");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "overdueAmount":
                                    detailBean.setKey("在平台逾期金额");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "litigation":
                                    detailBean.setKey("涉诉情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        } else if (type == 5) {
                            if (borrowType == 2) {
                                switch (fName) {
                                    case "isCard":
                                        detailBean.setKey("身份证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isIncome":
                                        detailBean.setKey("收入状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isCredit":
                                        detailBean.setKey("信用状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAsset":
                                        detailBean.setKey("资产状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isVehicle":
                                        detailBean.setKey("车辆状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isDrivingLicense":
                                        detailBean.setKey("行驶证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isVehicleRegistration":
                                        detailBean.setKey("车辆登记证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isMerry":
                                        detailBean.setKey("婚姻状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isWork":
                                        detailBean.setKey("工作状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAccountBook":
                                        detailBean.setKey("户口本");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                switch (fName) {
                                    case "isCertificate":
                                        detailBean.setKey("企业证件");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isOperation":
                                        detailBean.setKey("经营状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isFinance":
                                        detailBean.setKey("财务状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isEnterpriseCreidt":
                                        detailBean.setKey("企业信用");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isLegalPerson":
                                        detailBean.setKey("法人信息");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAsset":
                                        detailBean.setKey("资产状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isPurchaseContract":
                                        detailBean.setKey("购销合同");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isSupplyContract":
                                        detailBean.setKey("供销合同");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else if (type == 6) {
                            switch (fName) {
                                case "isFunds":
                                    detailBean.setKey("借款资金运用情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isManaged":
                                    detailBean.setKey("借款人经营状况及财务状况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isAbility":
                                    detailBean.setKey("借款人还款能力变化情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isOverdue":
                                    detailBean.setKey("借款人逾期情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isComplaint":
                                    detailBean.setKey("借款人涉诉情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isPunished":
                                    detailBean.setKey("借款人受行政处罚情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                continue;
            }
        }
        if (type == 1 || type == 4) {
            //信用评级单独封装
            BorrowDetailBean detailBean = new BorrowDetailBean();
            detailBean.setId("borrowLevel");
            detailBean.setKey("信用评级");
            detailBean.setVal(borrowLevel);
            detailBeanList.add(detailBean);
        }
        return detailBeanList;
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
        CreditListResponse res = webProjectListClient.countCreditList(request);
        WebResult webResult = new WebResult();
        if (!Response.isSuccess(res)) {
            logger.error("查询债权转让原子层count异常");
            throw new RuntimeException("查询债权转让原子层count异常");
        }
        int count = res.getCount();
        page.setTotal(count);
        webResult.setData(new ArrayList<>());
        if (count > 0) {
            List<TenderCreditDetailCustomizeCsVO> result = new ArrayList<>();
            CreditListResponse dataResponse = webProjectListClient.searchCreditList(request);
            if (!Response.isSuccess(dataResponse)) {
                logger.error("查询债权转让原子层list数据异常");
                throw new RuntimeException("查询债权转让原子层list数据异常");
            }
            result = CommonUtils.convertBeanList(dataResponse.getResultList(), TenderCreditDetailCustomizeCsVO.class);
            webResult.setData(result);
        }
        webResult.setPage(page);
        return webResult;
    }

    /**
     * 计划专区上部统计数据
     *
     * @author zhangyk
     * @date 2018/6/19 16:33
     */
    @Override
    public WebResult searchPlanData(ProjectListRequest request) {
        Map<String, Object> map = webProjectListClient.searchPlanData(request);
        if (map == null) {
            logger.error("web查询原子层计划专区上部统计数据异常");
            throw new RuntimeException("web查询原子层计划专区上部统计数据异常");
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
        Integer count = webProjectListClient.countPlanList(request);
        WebResult webResult = new WebResult();
        webResult.setData(new ArrayList<>());
        if (count == null) {
            logger.error("web查询原子层计划专区计划列表数据count异常");
            throw new RuntimeException("web查询原子层计划专区计划列表数据count异常");
        }
        if (count > 0) {
            Map<String, Object> map = webProjectListClient.searchPlanData(request);
            if (map == null) {
                logger.error("web查询原子层计划专区计划列表数据异常");
                throw new RuntimeException("web查询原子层计划专区计划列表数据异常");
            }
            webResult.setData(map);
        }
        page.setTotal(count);
        webResult.setPage(page);
        return webResult;
    }


}
