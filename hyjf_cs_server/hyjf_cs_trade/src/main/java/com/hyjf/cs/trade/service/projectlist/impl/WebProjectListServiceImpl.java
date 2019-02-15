package com.hyjf.cs.trade.service.projectlist.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
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
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.projectlist.CacheService;
import com.hyjf.cs.trade.service.projectlist.WebProjectListService;
import com.hyjf.cs.trade.service.repay.RepayPlanService;
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
import java.util.concurrent.TimeUnit;

/**
 * web端项目列表Service实现类
 *
 * @author liuyang
 * @version WebProjectListServiceImpl, v0.1 2018/6/13 10:21
 */
@Service
@SuppressWarnings("unchecked")
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
    private AuthService authService;
    @Autowired
    private BaseClient baseClient;


    @Autowired
    private CacheService cacheService;


    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    private SystemConfig systemConfig;
    /**
     * 获取Web端项目列表
     *
     * @param request
     * @return
     * @author liuyang
     */
    @Override
    public WebResult searchProjectList(ProjectListRequest request) {
        BorrowProjectListBean resultBean = new BorrowProjectListBean();

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
            logger.error("查询散标出借列表原子层count异常");
            throw new RuntimeException("查询散标出借列表原子层count异常");
        }
        page.setTotal(count);
        //由于result类在转json时会去掉null值，手动初始化为非null，保证json不丢失key
        resultBean.setList(new ArrayList<>());
        resultBean.setNowTime(GetDate.getNowTime10());
        if (count > 0) {
            List<WebProjectListCsVO> result = new ArrayList<>();
            List<WebProjectListCustomizeVO> list = amTradeClient.searchProjectList(request);
            if (CollectionUtils.isEmpty(list)) {
                logger.error("查询散标出借列表原子层List异常");
                throw new RuntimeException("查询散标出借列表原子层list数据异常");
            }
            result = CommonUtils.convertBeanList(list, WebProjectListCsVO.class);
            resultBean.setList(result);
        }
        webResult.setData(resultBean);
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


    /**
     * 获取Web端项目列表(新的)
     * @author zhangyk
     * @date 2018/10/9 15:48
     */
    @Override
    public WebResult searchProjectListNew(ProjectListRequest request) {

        WebResult result = new WebResult();
        BorrowProjectListBean resultBean = new BorrowProjectListBean();
        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());


        List<BorrowProjectTypeVO> borrowTypes = amTradeClient.getProjectTypeList();
        String projectType = request.getProjectType();// 项目类型
        String borrowClass = request.getBorrowClass();// 项目子类型
        // 校验相应的项目类型
        if (borrowTypes != null && borrowTypes.size() > 0) {
            boolean typeFlag = false;
            boolean classFlag = false;
            if (StringUtils.isNotBlank(projectType)) {
                for (BorrowProjectTypeVO borrowType : borrowTypes) {
                    String type = borrowType.getBorrowProjectType();
                    if (type.equals(projectType)) {
                        typeFlag = true;
                    }
                    if (StringUtils.isNotBlank(borrowClass)) {
                        String classType = borrowType.getBorrowClass();
                        if (classType.equals(borrowClass)) {
                            classFlag = true;
                        }
                    } else {
                        classFlag = true;
                    }
                }
            } else {
                resultBean.setList(new ArrayList<WebProjectListCsVO>());
                page.setTotal(0);
            }
            if (typeFlag && classFlag) {

                request.setProjectType(projectType);
                request.setBorrowClass(borrowClass);
                request.setPublishInstCode(CustomConstants.HYJF_INST_CODE);

                // 统计相应的汇直投的数据记录数
                int projectToal = amTradeClient.countProjectList(request);

                Map<String, Object> params = new HashMap<String, Object>();
                if (projectToal > 0) {

                    //add by cwyang 项目列表显示2页
                    int pageNum = 2;
                    if(projectToal > request.getPageSize() * pageNum){
                        projectToal = request.getPageSize() * pageNum;
                    }

                    page.setTotal(projectToal);
                    // 查询相应的汇直投列表数据
                    int limit = page.getLimit();
                    int offSet = page.getOffset();

                    if (offSet == 0 || offSet > 0) {
                        request.setLimitStart(offSet);
                    }
                    if (limit > 0) {
                        request.setLimitEnd(limit);
                    }
                    List<WebProjectListCustomizeVO> projectList = amTradeClient.searchProjectList(request);
                    resultBean.setList( CommonUtils.convertBeanList(projectList, WebProjectListCsVO.class));
                    //int nowTime = GetDate.getNowTime10();
                    // result.setNowTime(nowTime);
                } else {
                    resultBean.setList(new ArrayList<WebProjectListCsVO>());
                    page.setTotal(0);
                }
            } else {
                resultBean.setList(new ArrayList<WebProjectListCsVO>());
                page.setTotal(0);
            }
        } else {
            resultBean.setList(new ArrayList<WebProjectListCsVO>());
            page.setTotal(0);
        }
        resultBean.setNowTime(GetDate.getNowTime10());
        result.setData(resultBean);
        result.setPage(page);
        return result;
    }

    @Override
    public WebResult getBorrowDetail(Map map, String userId) {
        Object borrowNid = map.get(ProjectConstant.PARAM_BORROW_NID);
        CheckUtil.check(null != borrowNid, MsgEnum.ERR_OBJECT_REQUIRED, "借款编号");
        ProjectListRequest request = new ProjectListRequest();
        // ① 先查出标的基本信息  ② 根据是否是新标的，进行参数组装
        ProjectCustomeDetailVO tempProjectCustomeDetail = amTradeClient.searchProjectDetail(map);
        if (tempProjectCustomeDetail == null) {
            CheckUtil.check(false, MsgEnum.STATUS_CE000013);
        }
        // 转换一次是排除业务操作对缓存的干扰
        ProjectCustomeDetailVO projectCustomeDetail = CommonUtils.convertBean(tempProjectCustomeDetail,ProjectCustomeDetailVO.class);
        // 添加缓存后希望能拿到实时的标的剩余金额
        String investAccount = RedisUtils.get(RedisConstants.BORROW_NID + borrowNid);
        if (StringUtils.isNotBlank(investAccount)){
            projectCustomeDetail.setInvestAccount(investAccount);
        }
        ProjectCustomeDetailCsVO detailCsVO = CommonUtils.convertBean(projectCustomeDetail, ProjectCustomeDetailCsVO.class);

        UserVO userVO = null;
        // 原来独立于实体之外的属性，单独放在一个map中
        Map<String, Object> other = new HashMap();
        // 已经登录
        if (userId != null) {
            userVO = amUserClient.findUserById(Integer.valueOf(userId));
            other.put("riskFlag", userVO != null ? userVO.getIsEvaluationFlag() : null);
        }
        //判断新标还是老标，老标走原来逻辑原来页面，新标走新方法 0为老标 1为新标(融通宝走原来页面)  -- 原系统注释
        if (detailCsVO.getIsNew() == 0 || "13".equals(detailCsVO.getType())) {
        } else {
            getProjectDetailNew(other, projectCustomeDetail, userVO);
        }

        other.put("autoTenderAuthStatus", "");
        other.put("autoCreditAuthStatus", "");
        other.put("paymentAuthStatus", "");

        if (userId != null) {
            HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(Integer.valueOf(userId));
            //自动投标授权状态 0未授权  1已授权
            other.put("autoTenderAuthStatus", hjhUserAuth.getAutoInvesStatus());
            //自动债转授权~~
            other.put("autoCreditAuthStatus", hjhUserAuth.getAutoCreditStatus());
            //服务费授权~~
            other.put("paymentAuthStatus", hjhUserAuth.getAutoPaymentStatus());
            //是否开启自动投标授权校验 0未开启 1已开启
            other.put("autoTenderAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus());
            //是否进行自动债转~~
            other.put("autoCreditAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus());
            //是否进行缴费~~
            other.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
            other.put("isCheckUserRole",systemConfig.getRoleIsopen());
        }
        WebResult webResult = new WebResult();
        // detailCsVO.setOther(other);
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
            other.put("investFlag", "0");//是否出借过该项目 0未出借 1已出借
            other.put("riskFlag", "0");//是否进行过风险测评 0未测评 1已测评
            other.put("setPwdFlag", "0");//是否设置过交易密码 0未设置 1已设置
        } else {
            Integer userId = userVO.getUserId();
            // 获取用户最优优惠券
            MyCouponListRequest request = new MyCouponListRequest();
            request.setBorrowNid(borrowNid);
            request.setUserId(String.valueOf(userId));
            request.setPlatform(CustomConstants.CLIENT_PC);
            request.setMoney("0");
            couponConfig = amTradeClient.selectBestCoupon(request);
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
            Integer couponAvailableCount = amTradeClient.countAvaliableCoupon(request);
            other.put("couponAvailableCount", couponAvailableCount == null ? "0" : String.valueOf(couponAvailableCount));
            BorrowInfoVO borrowInfoVO = amTradeClient.getBorrowInfoByNid(borrow.getBorrowNid());
            other.put("borrowMeasuresMea", StringUtils.isNotBlank(borrow.getBorrowMeasuresMea()) ? borrow.getBorrowMeasuresMea() : "");
            /** 可用优惠券张数结束 pccvip */
            /** 计算最优优惠券结束 */

            /*用户基本信息 开始*/
            other.put("loginFlag", "1");//登录状态 0未登陆 1已登录
            //用户信息
            if (null != userVO.getBankOpenAccount() && userVO.getBankOpenAccount() == 1) {
                other.put("openFlag", "1");
            } else {
                other.put("openFlag", "0");
            }
            // 用户是否出借项目
            int count = amTradeClient.countUserInvest(userId, borrowNid);
            if (count > 0) {
                other.put("investFlag", "1");//是否出借过该项目 0未出借 1已出借
            } else {
                other.put("investFlag", "0");//是否出借过该项目 0未出借 1已出借
            }
            //是否设置交易密码
            if (userVO.getIsSetPassword() == 1) {
                other.put("setPwdFlag", "1");
            } else {
                other.put("setPwdFlag", "0");
            }
            //账户信息
            AccountVO account = amTradeClient.getAccountByUserId(userId);
            String userBalance= "";
            if (account != null && account.getBankBalance() != null){
                userBalance = account.getBankBalance().toString();
            }else{
                userBalance = "0.00";
            }
            other.put("userBalance", userBalance);

            /* 用户基本信息 结束*/
        }


        //收益
        BigDecimal borrowInterest = BigDecimal.ZERO;

        /**
         * 融通宝收益叠加
         */
/*        if ("13".equals(borrow.getType())) {
            borrowApr = borrowApr.add(new BigDecimal(borrow.getBorrowExtraYield()));
        }*/
        // add by nxl 设置项目加息收益
        BigDecimal borrowExtraYield = new BigDecimal(StringUtils.isNotBlank(borrow.getBorrowExtraYield())?borrow.getBorrowExtraYield():"0");
        int intFlg = Integer.parseInt(StringUtils.isNotBlank(borrow.getIncreaseInterestFlag())?borrow.getIncreaseInterestFlag():"0");
        boolean isIncrease = Validator.isIncrease(intFlg,borrowExtraYield);
        if(isIncrease){
            borrowApr = borrowApr.add(new BigDecimal(borrow.getBorrowExtraYield()));
            borrow.setIncreaseInterestFlag(String.valueOf(isIncrease));
        }
        // mod by nxl 设置项目加息收益 End

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

        if ("9".equals(borrow.getType())) {// 如果项目为汇资产项目
            // 已经没有汇资产标的, 不在处理

        } else {// 项目为非汇资产项目
            // 添加相应的项目详情信息
            other.put(ProjectConstant.RES_PROJECT_DETAIL, borrow);
            /**
             * 借款类型  1、企业借款 2、借款人  3、汇资产
             */
            other.put(ProjectConstant.PARAM_BORROW_TYPE, borrow.getComOrPer());
            //借款人企业信息
            BorrowUserVO borrowUsers = cacheService.getCacheBorrowUser(borrowNid);
            //借款人信息
            BorrowManinfoVO borrowManinfo = cacheService.getCacheBorrowManInfo(borrowNid);
            //房产抵押信息
            List<BorrowHousesVO> borrowHousesList = amTradeClient.getBorrowHousesByNid(borrowNid);
            //车辆抵押信息
            List<BorrowCarinfoVO> borrowCarinfoList = amTradeClient.getBorrowCarinfoByNid(borrowNid);
            //还款计划
            List<BorrowRepayPlanCsVO> repayPlanList = repayPlanService.getRepayPlan(borrowNid);
            other.put("repayPlanList", repayPlanList);
            // 还款信息
            BorrowRepayVO borrowRepay = null;
            List<BorrowRepayVO> list = amTradeClient.selectBorrowRepayList(borrowNid, null);
            if (!CollectionUtils.isEmpty(list)) {
                borrowRepay = list.get(0);
            }
            //资产列表
            JSONArray json = new JSONArray();
            //基础信息
            List<BorrowDetailBean> baseTableData = new ArrayList<>();
            //资产信息
            JSONArray assetsTableData = new JSONArray();
            //项目介绍
            List<BorrowDetailBean> intrTableData = new ArrayList<>();
            //信用状况
            List<BorrowDetailBean> credTableData = new ArrayList<>();
            //审核信息
            List<BorrowDetailBean> reviewTableData = new ArrayList<>();
            //其他信息
            List<BorrowDetailBean> otherTableData = new ArrayList<>();
            //借款类型
            int borrowType = Integer.parseInt(borrow.getComOrPer());

            if (borrowType == 1 && borrowUsers != null) {
                //基础信息
                baseTableData = ProjectConstant.packDetail(borrowUsers, 1, borrowType, borrow.getBorrowLevel());
                //信用状况
                credTableData = ProjectConstant.packDetail(borrowUsers, 4, borrowType, borrow.getBorrowLevel());
                //审核信息
                reviewTableData = ProjectConstant.packDetail(borrowUsers, 5, borrowType, borrow.getBorrowLevel());
                //其他信息
                otherTableData = ProjectConstant.packDetail(borrowUsers, 6, borrowType, borrow.getBorrowLevel());
            } else {
                if (borrowManinfo != null) {
                    //基础信息
                    baseTableData = ProjectConstant.packDetail(borrowManinfo, 1, borrowType, borrow.getBorrowLevel());
                    //信用状况
                    credTableData = ProjectConstant.packDetail(borrowManinfo, 4, borrowType, borrow.getBorrowLevel());
                    //审核信息
                    reviewTableData = ProjectConstant.packDetail(borrowManinfo, 5, borrowType, borrow.getBorrowLevel());

                    //其他信息
                    otherTableData = ProjectConstant.packDetail(borrowManinfo, 6, borrowType, borrow.getBorrowLevel());
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
            assetsTableData = json;
            //项目介绍
            intrTableData = ProjectConstant.packDetail(borrow, 3, borrowType, borrow.getBorrowLevel());

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
                other.put("updateTime", ProjectConstant.getUpdateTime(GetDate.getTime10(borrowRepay.getCreateTime()), borrowRepay.getRepayYestime()));
            } else {
                //其他信息
                other.put("otherTableData",  new ArrayList<>());
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

        // 出借金额
        BigDecimal accountDecimal = null;
        if (couponType == 1) {
            // 体验金 出借资金=体验金面值
            accountDecimal = couponQuota;
        } else if (couponType == 2) {
            // 加息券 出借资金=真实出借资金
            accountDecimal = new BigDecimal(money);
            borrowApr = couponQuota;
        } else if (couponType == 3) {
            // 代金券 出借资金=体验金面值
            accountDecimal = couponQuota;
        }
        switch (borrowStyle) {
            case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getMonthInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷360*天数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getDayInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：历史回报=出借金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：历史回报=出借金额*年化收益÷12*月数；
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
     * 获取新手标和散标详情：出借记录
     *
     * @author zhangyk
     * @date 2018/8/9 16:58
     */
    @Override
    public WebResult getBorrowInvest(BorrowInvestReqBean form, String userId) {
        WebResult result = new WebResult();
        WebBorrowInvestResult info = new WebBorrowInvestResult();
        this.createProjectInvestPage(result,info, form, userId);
        return result;
    }


    /**
     * 查询定时散标倒计时信息
     * @author zhangyk
     * @date 2018/9/3 14:49
     */
    @Override
    public OntimeCheckBean getBorrowOntime(String borrowNid) {
        CheckUtil.check(StringUtils.isNotBlank(borrowNid),MsgEnum.ERR_OBJECT_REQUIRED,"标的编号");

        String SEPARATE = RedisConstants.COLON;
        // 标的状态key
        String onTimeStatusKey = RedisConstants.REDIS_KEY_ONTIME_STATUS + SEPARATE + borrowNid;
        // 标的定时key
        String onTimeKey = RedisConstants.ONTIME + borrowNid;
        // 标的定时独占锁key
        String onTimeLockKey = RedisConstants.REDIS_KEY_ONTIME_LOCK + SEPARATE + borrowNid;
        String status = RedisUtils.get(onTimeStatusKey);
        OntimeCheckBean ontimeCheckBean  = new OntimeCheckBean();
        if (StringUtils.isNotBlank(status) && "0".equals(status)){
            ontimeCheckBean.setStatus(0);
            return ontimeCheckBean;
        }
        Integer ontime ;
        String ontimeStr = RedisUtils.get(onTimeKey);
        if (StringUtils.isBlank(ontimeStr)){
            ontime = null;
        }else{
            ontime = Integer.valueOf(ontimeStr);
        }

        //Redis中取得定时时间,时间check
        if (ontime != null){
            //取得服务器时间
            Integer nowtime = GetDate.getNowTime10();
            //未到发标时间时，失败返回发标时间
            if (nowtime < ontime) {
                ontimeCheckBean.setStatus(-1);
                ontimeCheckBean.setStatusInfo("未到发标时间！");
                ontimeCheckBean.setOntime(ontime);
                ontimeCheckBean.setNowtime(GetDate.getNowTime10());
                return ontimeCheckBean;
            }else{
                //到时删除Redis中的定时时间
                RedisUtils.del(onTimeKey);
            }
        }

        try {

            //修改标的状态被占用(有效期10秒)
            if (!RedisUtils.tranactionSet(onTimeLockKey, 10)){
                ontimeCheckBean.setStatus(-2);
                ontimeCheckBean.setStatusInfo("锁等待中！");
                ontimeCheckBean.setOntime(ontime);
                ontimeCheckBean.setNowtime(GetDate.getNowTime10());
                return ontimeCheckBean;
            }

            //设定 redis的标的定时状态 为 1 锁定更改中(有效期同batch执行周期，5分钟)
            RedisUtils.set(onTimeStatusKey, "1", 300);

            //该标的非自动发标标的或者未到发标时间(DB验证)
            BorrowAndInfoVO borrowVO = amTradeClient.getBorrowByNidAndNowTime(borrowNid,GetDate.getNowTime10()); // this.borrowService.getOntimeIdByNid(borrowNid, GetDate.getNowTime10());
            if (borrowVO == null) {
                //删除 redis的标的定时独占锁
                RedisUtils.del(onTimeLockKey);
                ontimeCheckBean.setStatus(-3);
                ontimeCheckBean.setStatusInfo("该标的非自动发标标的或者未到发标时间！");
                ontimeCheckBean.setOntime(ontime);
                ontimeCheckBean.setNowtime(GetDate.getNowTime10());
                return ontimeCheckBean;
            }

            // Redis的出借余额校验
            if (RedisUtils.get(RedisConstants.BORROW_NID + borrowNid) != null) {
                logger.error(borrowNid + " 定时发标异常：标的编号在redis已经存在");
                ontimeCheckBean.setStatus(-5);
                ontimeCheckBean.setStatusInfo("定时标的状态异常");
                ontimeCheckBean.setOntime(ontime);
                ontimeCheckBean.setNowtime(GetDate.getNowTime10());
                return ontimeCheckBean;
            }

            //修改标的状态发标
            logger.info("修改数据库中标的发标状态");
            boolean flag = this.updateOntimeSendBorrow(borrowNid);
            if (!flag) {
                //删除 redis的标的定时独占锁
                RedisUtils.del(onTimeLockKey);
                ontimeCheckBean.setStatus(-4);
                ontimeCheckBean.setStatusInfo("定时标的状态修改失败！");
                ontimeCheckBean.setOntime(ontime);
                ontimeCheckBean.setNowtime(GetDate.getNowTime10());
                return ontimeCheckBean;
            }

            // 定时标发标成功推送消息到mq合规上报数据
            // 5.web端散标自动发标页面触发
            JSONObject params = new JSONObject();
            params.put("borrowNid", borrowVO.getBorrowNid());
            params.put("userId", borrowVO.getUserId());
            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTING_TAG, UUID.randomUUID().toString(), params),
                    MQConstant.HG_REPORT_DELAY_LEVEL);

            logger.info("定时标的【" + borrowNid + "】发标完成。（web）");

            //设定  redis的标的定时状态 为 0 标的状态修改成功开标(有效期同batch执行周期，5分钟)
            RedisUtils.set(onTimeStatusKey,"0",300);

            //删除 redis的标的定时独占锁
            RedisUtils.del(onTimeLockKey);

            ontimeCheckBean.setStatus(0);
        } catch (Exception e) {
            //删除 redis的标的定时独占锁
            RedisUtils.del(onTimeLockKey);
            ontimeCheckBean.setStatus(-5);
            ontimeCheckBean.setStatusInfo("未知异常！");
            ontimeCheckBean.setOntime(ontime);
            ontimeCheckBean.setNowtime(GetDate.getNowTime10());
            return ontimeCheckBean;
        }

        return ontimeCheckBean;
    }


    private boolean updateOntimeSendBorrow(String borrowNid) {

        // 当前时间
        int nowTime = GetDate.getNowTime10();
        RightBorrowVO borrow = amTradeClient.getRightBorrowByNid(borrowNid);
        // DB验证
        // 有出借金额发生异常
        BigDecimal zero = new BigDecimal("0");
        BigDecimal borrowAccountYes = borrow.getBorrowAccountYes();
        if (!(borrowAccountYes == null || borrowAccountYes.compareTo(zero) == 0)) {
            logger.error(borrowNid + " 定时发标异常：标的已有出借人出借");
            return false;
        }
        borrow.setBorrowEndTime(String.valueOf(nowTime + borrow.getBorrowValidTime() * 86400));
        // 是否可以进行借款
        borrow.setBorrowStatus(1);
        // 是否可以进行借款
        borrow.setBorrowFullStatus(0);
        // 状态
        borrow.setStatus(2);
        // 初审时间
        borrow.setVerifyTime(nowTime);
        // 剩余可出借金额
        borrow.setBorrowAccountWait(borrow.getAccount());
        boolean flag = amTradeClient.updateRightBorrowByBorrowNid(borrow);
        if (flag) {
            // 写入redis
            RedisUtils.set(RedisConstants.BORROW_NID + borrow.getBorrowNid(), borrow.getBorrowAccountWait().toString());
            // 发送发标短信
            Map<String, String> params = new HashMap<String, String>();
            params.put("val_title", borrow.getBorrowNid());
            SmsMessage smsMessage = new SmsMessage(null, params, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, "【汇盈金服】", CustomConstants.PARAM_TPL_DSFB, CustomConstants.CHANNEL_TYPE_NORMAL);
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,UUID.randomUUID().toString(), smsMessage));
                return true;
            }catch (Exception e){
                logger.error("开标短信发送失败");
                return false;
            }
        } else {
            return false;
        }
    }

    private void createProjectInvestPage(WebResult result, WebBorrowInvestResult info, BorrowInvestReqBean form, String userId) {
        String borrowNid = form.getBorrowNid();
        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        CheckUtil.check(null != borrowNid, MsgEnum.ERR_OBJECT_REQUIRED, "借款编号");
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
        if (borrow != null) {
            info.setInvestTotal(df.format(borrow.getBorrowAccountYes()));
            info.setInvestTimes(String.valueOf(borrow.getTenderTimes()));
        } else {
            info.setInvestTotal(df.format(new BigDecimal("0")));
            info.setInvestTimes("0");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid", borrowNid);
        int recordTotal = amTradeClient.countProjectInvestRecordTotal(params);
        if (recordTotal > 0) {
            params.put("limitStart", page.getOffset());
            params.put("limitEnd", page.getLimit());
            List<AppProjectInvestListCustomizeVO> list = amTradeClient.selectProjectInvestList(params);
            // 由于上市活动没有上线，暂时数据库没有相应的数据表，暂时不处理，待上市活动上线后，再添加
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, String> map = CacheUtil.getParamNameMap(RedisConstants.CLIENT);
                if (!CollectionUtils.isEmpty(map)){
                    for (AppProjectInvestListCustomizeVO vo : list){
                        if (StringUtils.isNotBlank(vo.getClientName())){
                            vo.setClientName(map.get(vo.getClientName()));
                        }
                    }
                }
                List<ProjectInvestListVO> voList = CommonUtils.convertBeanList(list, ProjectInvestListVO.class);
                CommonUtils.convertNullToEmptyString(voList);
                info.setProjectInvestList(voList);
            }
        } else {
            info.setProjectInvestList(new ArrayList<ProjectInvestListVO>());
        }
        page.setTotal(recordTotal);
        result.setPage(page);
        result.setData(info);
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
        //CreditListResponse res = baseClient.postExe("http://AM-TRADE/am-trade/projectlist/web/countCreditList", request, CreditListResponse.class);
        int count  =amTradeClient.getWebCreditListCount(request); //("http://AM-TRADE/am-trade/projectlist/web/countCreditList", request, CreditListResponse.class);
        WebResult webResult = new WebResult();
        //int count = res.getCount();
        page.setTotal(count);
        webResult.setData(new ArrayList<>());
        if (count > 0) {
            List<CreditListVO> result = new ArrayList<>();
            //CreditListResponse dataResponse = baseClient.postExe("http://AM-TRADE/am-trade/projectlist/web/searchWebCreditList", request, CreditListResponse.class);
            result = amTradeClient.getWebCreditList(request);
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
        BorrowCreditDetailResponse response = baseClient.getExe("http://AM-TRADE/am-trade/borrowCredit/borrowCreditDetail/" + creditNid, BorrowCreditDetailResponse.class);
        BorrowCreditDetailVO creditDetail = response.getResult();
        if (Validator.isNull(creditDetail)) {
            throw new RuntimeException("债转详情不存在");
        }
        // 获取相应的标的详情
        String borrowNid = creditDetail.getBorrowNid();

        BorrowResponse res = baseClient.getExe("http://AM-TRADE/am-trade/borrow/getBorrow/" + borrowNid, BorrowResponse.class);//borrowClient.selectBorrowByNid(borrowNid);
        BorrowAndInfoVO borrow = res.getResult();
        if (Validator.isNull(borrow)) {
            throw new RuntimeException("标的详情不存在");
        }
        // 项目类型
        Integer projectType = borrow.getProjectType();
        BorrowInfoResponse borrowInfoResponse = baseClient.getExe("http://AM-TRADE/am-trade/borrow/getBorrowInfoByNid/" + borrowNid, BorrowInfoResponse.class);
        BorrowInfoVO borrowInfoVO = borrowInfoResponse.getResult();
        if (Validator.isNull(borrowInfoVO)) {
            throw new RuntimeException("标的info不存在");
        }
        // 企业标的用户标的区分
        String comOrPer = borrowInfoVO.getCompanyOrPersonal() == null ? "0" : String.valueOf(borrowInfoVO.getCompanyOrPersonal());
        result.put("creditDetail", creditDetail);
        ProjectCustomeDetailVO projectDetail = amTradeClient.selectProjectDetail(borrowNid);
        result.put(ProjectConstant.RES_PROJECT_INFO, projectDetail);

        if (borrowInfoVO.getIsNew() == 0) {
            // 如果项目为汇资产项目
            if (projectType == 9) {
                // 已经没有汇资产项目，不在处理
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
            // 还款计划
            List<BorrowRepayPlanCsVO> repayPlanList = repayPlanService.getRepayPlan(borrowNid);
            result.put("repayPlanList", repayPlanList);
        } else {
            /**
             * 借款类型 1、企业借款 2、借款人 3、汇资产
             */
            result.put(ProjectConstant.PARAM_BORROW_TYPE, comOrPer);
            // 借款人企业信息
            BorrowUserVO borrowUsers = cacheService.getCacheBorrowUser(borrowNid);

            //借款人信息
            BorrowManinfoVO borrowManinfo = cacheService.getCacheBorrowManInfo(borrowNid);
            //房产抵押信息
            List<BorrowHousesVO> borrowHousesList = amTradeClient.getBorrowHousesByNid(borrowNid);
            //车辆抵押信息
            List<BorrowCarinfoVO> borrowCarinfoList = amTradeClient.getBorrowCarinfoByNid(borrowNid);
            //还款计划
            List<BorrowRepayPlanCsVO> repayPlanList = repayPlanService.getRepayPlan(borrowNid);
            result.put("repayPlanList", repayPlanList);
            // 还款信息
            BorrowRepayVO borrowRepay = null;
            List<BorrowRepayVO> list = amTradeClient.selectBorrowRepayList(borrowNid, null);
            if (!CollectionUtils.isEmpty(list)) {
                borrowRepay = list.get(0);
            }

            // 资产列表
            JSONArray json = new JSONArray();
            // 基础信息
            List<BorrowDetailBean>  baseTableData = new ArrayList<>();
            // 资产信息
            List<BorrowDetailBean>  assetsTableData = new ArrayList<>();
            // 项目介绍
            List<BorrowDetailBean>  intrTableData = new ArrayList<>();
            // 信用状况
            List<BorrowDetailBean>  credTableData = new ArrayList<>();
            // 审核信息
            List<BorrowDetailBean>  reviewTableData = new ArrayList<>();
            //其他信息
            List<BorrowDetailBean>  otherTableData = new ArrayList<>();
            // 借款类型
            int borrowType = Integer.parseInt(comOrPer);
            if (borrowType == 1 && borrowUsers != null) {
                // 基础信息
                baseTableData = ProjectConstant.packDetail(borrowUsers, 1, borrowType, projectDetail.getBorrowLevel());
                // 信用状况
                credTableData = ProjectConstant.packDetail(borrowUsers, 4, borrowType, projectDetail.getBorrowLevel());
                // 审核信息
                reviewTableData = ProjectConstant.packDetail(borrowUsers, 5, borrowType, projectDetail.getBorrowLevel());
                //其他信息
                otherTableData = ProjectConstant.packDetail(borrowUsers, 6, borrowType, borrow.getBorrowLevel());
            } else {
                if (borrowManinfo != null) {
                    // 基础信息
                    baseTableData = ProjectConstant.packDetail(borrowManinfo, 1, borrowType, projectDetail.getBorrowLevel());
                    // 信用状况
                    credTableData= ProjectConstant.packDetail(borrowManinfo, 4, borrowType, projectDetail.getBorrowLevel());
                    // 审核信息
                    reviewTableData = ProjectConstant.packDetail(borrowManinfo, 5, borrowType, projectDetail.getBorrowLevel());
                    //其他信息
                    otherTableData = ProjectConstant.packDetail(borrowManinfo, 6, borrowType, borrow.getBorrowLevel());
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
            // 项目介绍
            intrTableData = ProjectConstant.packDetail(projectDetail, 3, borrowType, projectDetail.getBorrowLevel());
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
                Date createDate = borrowRepay.getCreateTime();
                Integer startLong= null ;
                if (null != createDate){
                    startLong = Integer.valueOf(String.valueOf(createDate.getTime()/1000));
                }
                result.put("updateTime", ProjectConstant.getUpdateTime(startLong, borrowRepay.getRepayYestime() == null ? null :borrowRepay.getRepayYestime()));
            } else {
                //其他信息
                result.put("otherTableData", new ArrayList<BorrowDetailBean>());
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
                HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(Integer.valueOf(userId));
                //自动投标授权状态 0未授权  1已授权
                result.put("autoTenderAuthStatus", hjhUserAuth.getAutoInvesStatus());
                //自动债转授权状态
                result.put("autoCreditAuthStatus", hjhUserAuth.getAutoCreditStatus());
                //服务费授权状态
                result.put("paymentAuthStatus", hjhUserAuth.getAutoPaymentStatus());
                //是否开启自动投标授权校验 0未开启 1已开启
                result.put("autoTenderAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus());
                //是否进行自动债转~~
                result.put("autoCreditAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus());
                //是否进行缴费~~
                result.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
                UserInfoVO userInfoVO = amUserClient.findUsersInfoById(Integer.valueOf(userId));
                result.put("roleId",userInfoVO  !=null ? userInfoVO.getRoleId() : "");

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
                logger.warn("散标专区债权转让详情异常:["+ e +"]");
            }
        webResult.setData(result);
        return webResult;
    }


    /**
     * web端债转详情:承接记录
     *
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
            // 查询redis，转化client属性，
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, String> map = CacheUtil.getParamNameMap(RedisConstants.CLIENT);
                if (!CollectionUtils.isEmpty(map)){
                    for (CreditTenderListCustomizeVO vo : list){
                        if (StringUtils.isNotBlank(vo.getClient())){
                            vo.setClient(map.get(vo.getClient()));
                        }
                    }
                }
            }
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
	@Cached(name="webPlanZoneTotalInvestAndInterestCacheOnly-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 10, stopRefreshAfterLastAccess = 10, timeUnit = TimeUnit.MINUTES)
    public WebResult searchPlanData(ProjectListRequest request) {
        TotalInvestAndInterestResponse response = baseClient.getExe(HomePageDefine.INVEST_INVEREST_AMOUNT_URL, TotalInvestAndInterestResponse.class);
        TotalInvestAndInterestVO totalInvestAndInterestVO = response.getResult();
        Map<String, Object> map = new HashMap<>();
        if (totalInvestAndInterestVO != null) {
            map.put(ProjectConstant.HJH_DATA_ACCEDE_ACCOUNT_TOTAL, CommonUtils.formatAmount(totalInvestAndInterestVO.getHjhTotalInvestAmount()));
            map.put(ProjectConstant.HJH_DATA_INTEREST_TOTAL, CommonUtils.formatAmount(totalInvestAndInterestVO.getHjhTotalInterestAmount()));
            map.put(ProjectConstant.HJH_DATA_ACCEDE_TIMES, totalInvestAndInterestVO.getHjhTotalInvestNum());
        } else {
            map.put(ProjectConstant.HJH_DATA_ACCEDE_ACCOUNT_TOTAL, "0");
            map.put(ProjectConstant.HJH_DATA_INTEREST_TOTAL, "0");
            map.put(ProjectConstant.HJH_DATA_ACCEDE_TIMES, "0");
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
        request.setLimitStart(0);
        request.setLimitEnd(4);
        request.setIsHome("1");
        Integer count = amTradeClient.countPlanList(request);
        WebResult webResult = new WebResult();
        webResult.setData(new ArrayList<>());
        Map<String, Object> result = new HashMap<>();
        if (count == null) {
            logger.error("web查询原子层计划专区计划列表数据count异常");
            throw new RuntimeException("web查询原子层计划专区计划列表数据count异常");
        }
        if (count > 0) {
            List<HjhPlanCustomizeVO> list = amTradeClient.searchPlanList(request);
            if (CollectionUtils.isEmpty(list)) {
                logger.error("web查询原子层计划专区计划列表数据异常");
                throw new RuntimeException("web查询原子层计划专区计划列表数据异常");
            }
            result.put(ProjectConstant.WEB_PLAN_LIST, list);
        } else {
            result.put(ProjectConstant.WEB_PLAN_LIST, new ArrayList<HjhPlanCustomizeVO>());
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
        CheckUtil.check(StringUtils.isNotBlank(planNid), MsgEnum.ERR_OBJECT_REQUIRED,"计划编号");
        // 阀值
        Integer threshold = 1000;
        result.put("threshold", threshold);

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
            throw new CheckException("未查询到对应的计划");

        }
        // 最小出借金额(起投金额)-->计算最后一笔出借
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
            //获取用户优惠券总张数
            int recordTotal = 0;
            //可用优惠券张数
            int availableCouponListCount = 0;
            BestCouponListVO  bestCouponListVO = null;
            BigDecimal couponInterest = BigDecimal.ZERO;
            result.put("interest", BigDecimal.ZERO);
            result.put("isThereCoupon", 0);
            if (userId != null){
                /*优惠券模块开始 */
                MyCouponListRequest myCouponListRequest = new MyCouponListRequest();
                myCouponListRequest.setMoney("0");
                myCouponListRequest.setPlatform("0");
                myCouponListRequest.setUserId(userId);
                myCouponListRequest.setBorrowNid(planNid);
                availableCouponListCount = amTradeClient.countHJHAvaliableCoupon(myCouponListRequest);
                bestCouponListVO = amTradeClient.selectHJHBestCoupon(myCouponListRequest);
                if (bestCouponListVO  != null){
                    result.put("isThereCoupon", 1);
                    couponInterest = getCouponInterestForHjh(planNid,bestCouponListVO.getUserCouponId(),"0");//amTradeClient.selectHjhCouponInterest(myCouponListRequest);//.getCouponInterest(bestCouponListVO.getUserCouponId(), planNid, "0");
                    bestCouponListVO.setCouponInterest(df.format(couponInterest));
                    if(bestCouponListVO!=null && bestCouponListVO.getCouponType()==3){
                        result.put("interest", df.format(couponInterest.subtract(bestCouponListVO.getCouponQuota())));
                    }else{
                        result.put("interest", df.format(couponInterest));
                    }
                }else{
                    result.put("isThereCoupon", 0);
                }
            }
            /** 获取用户优惠券总张数开始 pccvip */
            //result.put("recordTotal", recordTotal);
            /** 获取用户优惠券总张数结束 pccvip */
            /** 可用优惠券张数开始 pccvip */
            result.put("couponAvailableCount", availableCouponListCount);
            /** 可用优惠券张数结束 pccvip */


            /*优惠券模块结束 */
            result.put("couponConfig",bestCouponListVO);

            /** 计算最优优惠券结束 */
            /** 汇添金优惠券使用结束 pcc */

            // 计划详情头部(结束)

            PlanDetailBean detailBean = CommonUtils.convertBean(planDetail, PlanDetailBean.class);
            if (StringUtils.isNotBlank(detailBean.getDebtMinInvestment())){
                detailBean.setDebtMinInvestment(String.valueOf(new BigDecimal(detailBean.getDebtMinInvestment()).intValue()));
            }
            if (StringUtils.isNotBlank(detailBean.getDebtInvestmentIncrement())){
                detailBean.setDebtInvestmentIncrement(String.valueOf(new BigDecimal(detailBean.getDebtInvestmentIncrement()).intValue()));
            }
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
                    investFlag = "0";//是否出借过该项目 0未出借 1已出借
                }
                result.put("investFlag", investFlag);
                // 用户是否开户
                if (null != userVO.getBankOpenAccount() && userVO.getBankOpenAccount() == 1 ) {
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
                String userBalance = "";
                if (Validator.isNotNull(account) && account.getBankBalance() != null) {
                    userBalance = account.getBankBalance().toString();
                }else{
                    userBalance = "0.00";
                }
                result.put("userBalance", userBalance);
                // 用户是否完成自动授权标识
                HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(Integer.valueOf(userId));
                if (Validator.isNotNull(hjhUserAuth)) {
                    //自动投标授权状态 0未授权  1已授权
                    result.put("autoTenderAuthStatus", hjhUserAuth.getAutoInvesStatus());
                    //自动债转授权状态
                    result.put("autoCreditAuthStatus", hjhUserAuth.getAutoCreditStatus());
                    //服务费授权状态
                    result.put("paymentAuthStatus", hjhUserAuth.getAutoPaymentStatus());
                } else {
                    result.put("autoTenderAuthStatus", "0");
                    //自动债转授权状态
                    result.put("autoCreditAuthStatus", "0");
                    //服务费授权状态
                    result.put("paymentAuthStatus", "0");
                }
                // 合规三期
                result.put("isCheckUserRole",systemConfig.getRoleIsopen());
            } else {
                //状态位用于判断tab的是否可见
                result.put("autoTenderAuthStatus", "0");
                result.put("autoCreditAuthStatus", "0");
                result.put("paymentAuthStatus", "0");
                //状态位用于判断tab的是否可见
                result.put("loginFlag", "0");//登录状态 0未登陆 1已登录
                result.put("openFlag", "0"); //开户状态 0未开户 1已开户
                result.put("investFlag", "0");//是否出借过该项目 0未出借 1已出借
                result.put("riskFlag", "0");//是否进行过风险测评 0未测评 1已测评
                result.put("setPwdFlag", "0");//是否设置过交易密码 0未设置 1已设置
                result.put("forbiddenFlag", "0");//是否禁用 0未禁用 1已禁用
            }
            //是否开启自动投标授权校验 0未开启 1已开启
            result.put("autoTenderAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus());
            //是否进行自动债转~~
            result.put("autoCreditAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus());
            //是否进行缴费~~
            result.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        }
        webResult.setData(result);
        return webResult;
    }

    private BigDecimal getCouponInterestForHjh(String planNid, String couponId,String money){
        BigDecimal couponInterest=BigDecimal.ZERO;
        CouponConfigVO couponConfig=amTradeClient.getCouponConfigById(couponId);//.getBestCouponById(couponGrantId);
        HjhPlanVO hjhPlan=amTradeClient.getPlanByNid(planNid);//this.getHjhPlanByPlanNid(planNid);
        // 收益率
        BigDecimal planApr =hjhPlan.getExpectApr();
        // 计划期限
        int planPeriod = hjhPlan.getLockPeriod();
        String borrowStyle = hjhPlan.getBorrowStyle();
        if(!borrowStyle.equals("endday")){
            borrowStyle = "end";
        }
        if(couponConfig.getCouponType()==1){
            couponInterest =getInterestDj(couponConfig.getCouponQuota(),couponConfig.getCouponProfitTime().intValue(),planApr);
        }else{
            couponInterest=getInterest(borrowStyle,couponConfig.getCouponType(),planApr,
                    couponConfig.getCouponQuota(),money,planPeriod);
        }
        return couponInterest;
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
            List<BorrowAndInfoVO> list = res.getResultList();
            formatUserName(list);
            List<WebPlanBorrowBean> resultList = CommonUtils.convertBeanList(list,WebPlanBorrowBean.class);
            result.setData(resultList);
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
            accedeTotal =  totalData.get("sum") == null ? 0.00 : (double) totalData.get("sum");
        }
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        info.put("planAccedeList", new ArrayList<>());
        if (count > 0) {
            params.put("limitStart", page.getOffset());
            params.put("limitEnd", page.getLimit());
            HjhAccedeListResponse res = baseClient.postExe(HJH_DETAIL_ACCEDE_LIST_URL, params, HjhAccedeListResponse.class);
            List<HjhAccedeCustomizeVO> list = res.getResultList();
            // 查询redis，转化client属性，
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, String> map = CacheUtil.getParamNameMap(RedisConstants.CLIENT);
                if (!CollectionUtils.isEmpty(map)){
                    for (HjhAccedeCustomizeVO vo : list){
                        if (StringUtils.isNotBlank(vo.getClient())){
                            vo.setClient(map.get(vo.getClient()));
                        }
                    }
                }
            }
            CommonUtils.convertNullToEmptyString(list);
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
    private void formatUserName(List<BorrowAndInfoVO> list) {
        for (BorrowAndInfoVO planAccede : list) {
            String borrowNid = planAccede.getBorrowNid();
            if ("1".equals(planAccede.getCompanyOrPersonal())) {//如果类型是公司 huiyingdai_borrow_users
                BorrowUserVO borrowUser = cacheService.getCacheBorrowUser(borrowNid);
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
                BorrowManinfoVO borrowManinfoVO =  cacheService.getCacheBorrowManInfo(borrowNid);
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
            // 处理借款用途
            if(StringUtils.isNotBlank(planAccede.getFinancePurpose())){
                switch (planAccede.getFinancePurpose()) {
                    case "01":
                        planAccede.setFinancePurpose("个人消费");
                        break;
                    case "02":
                        planAccede.setFinancePurpose("个人经营");
                        break;
                    case "03":
                        planAccede.setFinancePurpose("个人资金周转");
                        break;
                    case "04":
                        planAccede.setFinancePurpose("房贷");
                        break;
                    case "05":
                        planAccede.setFinancePurpose("企业经营");
                        break;
                    case "06":
                        planAccede.setFinancePurpose("企业周转");
                        break;
                    case "99":
                        planAccede.setFinancePurpose("其他");
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * 根据计划编号和用户id查询用户优惠券
     * @author zhangyk
     * @date 2018/8/16 11:08
     */
    @Override
    public WebResult getProjectAvailableUserCoupon(WebPlanRequestBean requestBean, Integer userId) {
        WebResult result = new WebResult();
       /* String planNid = requestBean.getPlanNid();
        String money = requestBean.getMoney();
        CheckUtil.check(StringUtils.isNotBlank(planNid),MsgEnum.ERR_OBJECT_REQUIRED,"计划编号");
        if (StringUtils.isBlank(money)){
            money = "0";
        }
        if (userId == null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("availableCouponList",  new ArrayList<CouponBeanVo>());
            jsonObject.put("notAvailableCouponList", new ArrayList<CouponBeanVo>());
            jsonObject.put("availableCouponListCount", 0);
            jsonObject.put("notAvailableCouponListCount", 0);
            result.setData(jsonObject);
            return result;
        }
        String platform = CustomConstants.CLIENT_PC;
        Object object = getAvailableUserCoupon(planNid,userId,money,platform);
        result.setData(object);*/
        return result;
    }

    private Object getAvailableUserCoupon(String planNid,Integer userId,String money,String platform){
        MyCouponListRequest request = new MyCouponListRequest();
        request.setBorrowNid(planNid);
        request.setUserId(userId.toString());
        request.setPlatform(platform);
        request.setMoney(money);
        CouponResponse response = amTradeClient.getPlanCoupon(request);
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


    /**
     * 查询计划标的组成的标的详情
     * @author zhangyk
     * @date 2018/8/17 9:52
     */
    @Override
    public WebResult getPlanAccedeBorrowDetail(WebBorrowRequestBean requestBean, Integer userId) {
        WebResult result = new WebResult();
        String borrowNid = requestBean.getBorrowNid();
        CheckUtil.check(StringUtils.isNotBlank(borrowNid),MsgEnum.ERR_OBJECT_REQUIRED, "标的编号");
        Map<String,Object> map = new HashMap<>();
        map.put("borrowNid",borrowNid);
        // 根据项目标号获取相应的项目信息
        ProjectCustomeDetailVO tempBorrowDetailVo = amTradeClient.searchProjectDetail(map);
        //没有标的信息
        if (tempBorrowDetailVo == null) {
            throw  new CheckException("标的信息不存在！");
        }
        // add by zyk  标的详情添加缓存 2019年1月22日13:52 begin
        // 转换一次是排除业务操作对缓存的干扰
        ProjectCustomeDetailVO borrowDetailVo = CommonUtils.convertBean(tempBorrowDetailVo,ProjectCustomeDetailVO.class);
        // 添加缓存后希望能拿到实时的标的剩余金额
        String investAccount = RedisUtils.get(RedisConstants.BORROW_NID + borrowNid);
        if (StringUtils.isNotBlank(investAccount)){
            borrowDetailVo.setInvestAccount(investAccount);
        }
        // add by zyk  标的详情添加缓存 2019年1月22日13:52 end
        this.getPlanBorrowDetail(result,borrowDetailVo,userId);
        return result;
    }

    /**
     * 查询标的详情并封装返回
     * @author zhangyk
     * @date 2018/8/17 10:28
     */
    private void getPlanBorrowDetail(WebResult result, ProjectCustomeDetailVO borrowDetailVo, Integer userId){
        JSONObject info = new JSONObject();
        String borrowNid = borrowDetailVo.getBorrowNid();
        info.put("projectDeatil",borrowDetailVo);
        info.put("borrowType",borrowDetailVo.getComOrPer());

        //借款人企业信息
        BorrowUserVO borrowUsers = cacheService.getCacheBorrowUser(borrowNid);
        //借款人信息
        BorrowManinfoVO borrowManinfo = cacheService.getCacheBorrowManInfo(borrowNid);
        //房产抵押信息
        List<BorrowHousesVO> borrowHousesList = amTradeClient.getBorrowHousesByNid(borrowNid);
        //车辆抵押信息
        List<BorrowCarinfoVO> borrowCarinfoList = amTradeClient.getBorrowCarinfoByNid(borrowNid);
        //还款计划
        List<BorrowRepayPlanCsVO> repayPlanList = repayPlanService.getRepayPlan(borrowNid);
        if (CollectionUtils.isEmpty(repayPlanList)){
            repayPlanList = new ArrayList<>();
        }
        info.put("repayPlanList", repayPlanList);
        // 还款信息
        BorrowRepayVO borrowRepay = null;
        List<BorrowRepayVO> list = amTradeClient.selectBorrowRepayList(borrowNid, null);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)){
            borrowRepay = list.get(0);
        }

        //资产列表
        JSONArray json = new JSONArray();
        List<BorrowDetailBean> emptyTable = new ArrayList<>();
        //基础信息
        List<BorrowDetailBean> baseTableData = emptyTable;
        //资产信息
        List<BorrowDetailBean> assetsTableData = emptyTable;
        //项目介绍
        List<BorrowDetailBean> intrTableData = emptyTable;
        //信用状况
        List<BorrowDetailBean> credTableData = emptyTable;
        //审核信息
        List<BorrowDetailBean> reviewTableData = emptyTable;
        //其他信息
        List<BorrowDetailBean> otherTableData = emptyTable;

        //借款类型
        int borrowType = Integer.parseInt(borrowDetailVo.getComOrPer());

        if(borrowType == 1 && borrowUsers != null){
            //基础信息
            baseTableData = ProjectConstant.packDetail(borrowUsers, 1, borrowType, borrowDetailVo.getBorrowLevel());
            //信用状况
            credTableData = ProjectConstant.packDetail(borrowUsers, 4, borrowType, borrowDetailVo.getBorrowLevel());
            //审核信息
            reviewTableData =  ProjectConstant.packDetail(borrowUsers, 5, borrowType, borrowDetailVo.getBorrowLevel());
            //其他信息
            otherTableData =  ProjectConstant.packDetail(borrowUsers, 6, borrowType, borrowDetailVo.getBorrowLevel());
        }else{
            if(borrowManinfo != null){
                //基础信息
                baseTableData = ProjectConstant.packDetail(borrowManinfo, 1, borrowType, borrowDetailVo.getBorrowLevel());
                //信用状况
                credTableData = ProjectConstant.packDetail(borrowManinfo, 4, borrowType, borrowDetailVo.getBorrowLevel());
                //审核信息
                reviewTableData =ProjectConstant.packDetail(borrowManinfo, 5, borrowType, borrowDetailVo.getBorrowLevel());
                //其他信息
                otherTableData = ProjectConstant.packDetail(borrowManinfo, 6, borrowType, borrowDetailVo.getBorrowLevel());
            }
        }

        //资产信息
        if(borrowHousesList != null && borrowHousesList.size() > 0){
            for (BorrowHousesVO  borrowHouses: borrowHousesList) {
                json.add(ProjectConstant.packDetail(borrowHouses, 2, borrowType, borrowDetailVo.getBorrowLevel()));
            }
        }
        if(borrowCarinfoList != null && borrowCarinfoList.size() > 0){
            for (BorrowCarinfoVO borrowCarinfo : borrowCarinfoList) {
                json.add(ProjectConstant.packDetail(borrowCarinfo, 2, borrowType, borrowDetailVo.getBorrowLevel()));
            }
        }

        //项目介绍
        intrTableData = ProjectConstant.packDetail(borrowDetailVo, 3, borrowType, borrowDetailVo.getBorrowLevel());

        //基础信息
        info.put("baseTableData", baseTableData);
        //资产信息
        info.put("assetsTableData", assetsTableData);
        //项目介绍
        info.put("intrTableData", intrTableData);
        //信用状况
        info.put("credTableData", credTableData);
        //审核信息
        info.put("reviewTableData", reviewTableData);
        // 信批需求新增(放款后才显示)
        if(borrowDetailVo.getStatusOrginal()>=4 && borrowRepay != null){
            //其他信息
            info.put("otherTableData", otherTableData);
            Date createDate = borrowRepay.getCreateTime();
            Integer startLong= null ;
            if (null != createDate){
                startLong = Integer.valueOf(String.valueOf(createDate.getTime()/1000));
            }
            info.put("updateTime", ProjectConstant.getUpdateTime(startLong, borrowRepay.getRepayYestime() == null ? null :borrowRepay.getRepayYestime()));
        }else{
            //其他信息
            info.put("otherTableData", new ArrayList<BorrowDetailBean>());
        }

        //用户未登陆（默认都是否状态）
        if(userId == null){
            info.put("loginFlag", "0");//登录状态 0未登陆 1已登录
            info.put("openFlag", "0"); //开户状态 0未开户 1已开户
            info.put("investFlag", "0");//是否出借过该项目 0未出借 1已出借
            info.put("riskFlag", "0");//是否进行过风险测评 0未测评 1已测评
            info.put("setPwdFlag", "0");//是否设置过交易密码 0未设置 1已设置
            info.put("viewableFlag", "0");// add by nxl 未登录不可见
        }else{
            info.put("loginFlag", "1");//登录状态 0未登陆 1已登录
            //用户信息
            UserVO user = amUserClient.findUserById(userId);
            if (user.getBankOpenAccount() != null) {
                info.put("openFlag", "1");
            } else {
                info.put("openFlag", "0");
            }

            int count = 0;
            if(userId!=null) {
                count = amTradeClient.countUserInvest(userId,borrowNid);
            }
            if (count > 0) {
                info.put("investFlag", "1");//是否出借过该项目 0未出借 1已出借
            }else{
                info.put("investFlag", "0");//是否出借过该项目 0未出借 1已出借
            }

            // add 汇计划二期前端优化  针对区分原始标与债转标 nxl 20180424 start
            /**
             * 查看标的详情
             * 原始标：复审中、还款中、已还款状态下 如果当前用户是否投过此标，是：可看，否则不可见
             * 债转标的：未被完全承接时，项目详情都可看；被完全承接时，只有出借者和承接者可查看
             */
            String viewableFlag ="0";
            int intCount = 0;
            DebtCreditRequest request = new DebtCreditRequest();
            request.setBorrowNid(borrowNid);
            List<HjhDebtCreditVO> listHjhDebtCredit = amTradeClient.selectHjhDebtCreditListByBorrowNidAndStatus(request);
            // add by nxl 是否发生过债转
            Boolean isDebt = false;
            if(!CollectionUtils.isEmpty(listHjhDebtCredit)) {
                // 部分承接
                request.setCreditStatus(Arrays.asList(0, 1));
                List<HjhDebtCreditVO> listHjhDebtCreditOnePlace = amTradeClient.selectHjhDebtCreditListByBorrowNidAndStatus(request);
                if(!CollectionUtils.isEmpty(listHjhDebtCreditOnePlace)) {
                    //部分债转
                    viewableFlag ="1";
                }else {
                    // 完全债转
                    for(HjhDebtCreditVO deptcredit:listHjhDebtCredit) {
                        //待承接本金 = 0
                        if(null!=deptcredit.getCreditCapitalWait()&&deptcredit.getCreditCapitalWait().compareTo(BigDecimal.ZERO) == 0) {
                            Map<String, Object> mapParam = new HashMap<String, Object>();
                            mapParam.put(ProjectConstant.PARAM_USER_ID, userId);
                            mapParam.put(ProjectConstant.PARAM_BORROW_NID, borrowNid);
                            int tenderCount = amTradeClient.countCreditTenderByBorrowNidAndUserId(mapParam);
                            if(tenderCount>0 || count>0) {
                                viewableFlag = "1";
                            }
                        }
                    }
                }
                isDebt = true;
            }else {
                //原始标
                //复审中，还款中和已还款状态出借者(可看)(app使用的stattus判断,这里是用statusOriginal判断  不知道是否有影响)
                if(null != borrowDetailVo.getStatusOrginal() && (borrowDetailVo.getStatusOrginal() == 3 || borrowDetailVo.getStatusOrginal()== 4 || borrowDetailVo.getStatusOrginal() == 5)  ) {
                    if(count>0) {
                        //可以查看标的详情
                        viewableFlag ="1";
                    }else {
                        //提示仅出借者可看
                        viewableFlag ="0";
                    }
                }else {
                    viewableFlag="1";
                }
            }
            info.put("viewableFlag", viewableFlag);
            info.put("isDebt", isDebt);
            // add 汇计划二期前端优化  针对区分原始标与债转标  nxl 20180424 end
            //是否设置交易密码
            if(null != user.getIsSetPassword() && user.getIsSetPassword() == 1){
                info.put("setPwdFlag", "1");
            }else{
                info.put("setPwdFlag", "0");
            }
            try {
                if(user.getIsEvaluationFlag()==1 && null != user.getEvaluationExpiredTime()){
                    //测评到期日
                    Long lCreate = user.getEvaluationExpiredTime().getTime();
                    //当前日期
                    Long lNow = System.currentTimeMillis();
                    if (lCreate <= lNow) {
                        //已过期需要重新评测
                        info.put("riskFlag", "2");
                    } else {
                        //未到一年有效期
                        info.put("riskFlag", "1");
                    }
                }else{
                    info.put("riskFlag", "0");
                }
                // modify by liuyang 20180411 用户是否完成风险测评标识 end
            } catch (Exception e) {
                logger.error("查询用户是否完成风险测评标识出错....", e);
                info.put("riskFlag", "0");
            }

            AccountVO account = amTradeClient.getAccount(userId);
            String userBalance = "";
            if (Validator.isNotNull(account) && account.getBankBalance() != null) {
                userBalance = account.getBankBalance().toString();
            }else{
                userBalance = "0.00";
            }
            info.put("userBalance", userBalance);

        }
        result.setData(info);
    }



    /**
     * 查询计划标的组成：承接记录
     * @author zhangyk
     * @date 2018/8/24 10:23
     */
    @Override
    public WebResult getPlanBorrowUndertake(WebBorrowRequestBean requestBean) {
        WebResult result = new WebResult();
        JSONObject info = new JSONObject();
        String borrowNid = requestBean.getBorrowNid();
        CheckUtil.check(StringUtils.isNotBlank(borrowNid),MsgEnum.ERR_OBJECT_REQUIRED,"借款编号");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid",borrowNid);
        // 查询总条数
        int count = amTradeClient.countCreditTenderByBorrowNidAndUserId(params);
        // 初始化总金额
        String totalAccount = "0";
        Page page = Page.initPage(requestBean.getCurrPage(),requestBean.getPageSize());
        if (count > 0){
            DecimalFormat df = CustomConstants.DF_FOR_VIEW;
            params.put("limitStart",page.getOffset());
            params.put("limitEnd", page.getLimit());
            totalAccount = amTradeClient.sumUndertakeAccount(borrowNid);
            List<ProjectUndertakeListVO> list = amTradeClient.selectProjectUndertakeList(params);
            // 查询redis，转化client属性，
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, String> map = CacheUtil.getParamNameMap(RedisConstants.CLIENT);
                if (!CollectionUtils.isEmpty(map)){
                    for (ProjectUndertakeListVO vo : list){
                        if (StringUtils.isNotBlank(vo.getClient())){
                            vo.setClient(map.get(vo.getClient()));
                        }
                    }
                }
            }
            // 格式化属性
            if(StringUtils.isNotBlank(totalAccount)){
                totalAccount = df.format(new BigDecimal(totalAccount));
            }
            CommonUtils.convertNullToEmptyString(list);
            info.put("recordList", list);
            // 总承接金额
            info.put("sumAccount", String.valueOf(totalAccount));
            // 承接总人次
            info.put("sumTimes", count);
        }else{
            info.put("recordList", new ArrayList<>());
            // 总承接金额
            info.put("sumAccount", totalAccount);
            // 承接总人次
            info.put("sumTimes", 0);
        }
        page.setTotal(count);
        result.setData(info);
        result.setPage(page);
        return result;
    }
}
