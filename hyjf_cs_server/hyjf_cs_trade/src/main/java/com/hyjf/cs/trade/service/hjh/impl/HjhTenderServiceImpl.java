/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.hjh.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.crmtender.CrmInvestMsgBean;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;
import com.hyjf.am.vo.coupon.CouponBeanVo;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.EvaluationConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MsgCode;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.consumer.CouponService;
import com.hyjf.cs.trade.service.coupon.AppCouponService;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Description 加入计划
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:50
 */
@Service
public class HjhTenderServiceImpl extends BaseTradeServiceImpl implements HjhTenderService {
    private static final Logger logger = LoggerFactory.getLogger(HjhTenderServiceImpl.class);
    /** 历史回报文字描述常量 */
    public static final String PROSPECTIVE_EARNINGS = "历史回报 ";

    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private CouponService couponService;
    @Autowired
    private AppCouponService appCouponService;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AuthService authService ;
    @Autowired
    private HjhTenderService hjhTenderService;
    /**
     * @param request
     * @Description 检查加入计划的参数
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 9:47
     */
    @Override
    @HystrixCommand(commandKey = "加入计划(三端)-joinPlan",fallbackMethod = "fallBackJoinPlan",ignoreExceptions = CheckException.class,commandProperties = {
            //设置断路器生效
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //熔断5秒后去尝试请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
    public WebResult<Map<String, Object>> joinPlan(TenderRequest request) {
        UserVO loginUser = amUserClient.findUserById(request.getUserId());
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        String key = RedisConstants.HJH_TENDER_REPEAT + userId;
        boolean checkTender = RedisUtils.tranactionSet(key, RedisConstants.TENDER_OUT_TIME);
        if(!checkTender){
            // 用户正在出借
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_IN_PROGRESS);
        }
        if (StringUtils.isEmpty(request.getBorrowNid())) {
            // 项目编号不能为空
            throw new CheckException(MsgEnum.STATUS_CE000013);
        }

        if(request.getPlatform()==null){
            // 出借平台不能为空
            throw new CheckException(MsgEnum.STATUS_ZC000018);
        }
        // 查询选择的优惠券
        CouponUserVO cuc = null;
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = amTradeClient.getCouponUser(request.getCouponGrantId(), userId);
        }
        HjhPlanVO plan = amTradeClient.getPlanByNid(request.getBorrowNid());
        if (plan == null) {
            throw new CheckException(MsgEnum.FIND_PLAN_ERROR);
        }
        if (plan.getPlanInvestStatus() == 2) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_PLAN_CLOSE);
        }
        logger.info("加入计划出借校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        // 查询用户信息
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        UserVO user = amUserClient.findUserById(userId);
        // 检查用户状态  角色  授权状态等  是否允许出借
        checkUser(user, userInfo);
        //保存用户操作日志
        String lockPeriod = plan.getLockPeriod().toString();
        String dayOrMonth ="";
        if (plan.getIsMonth().intValue()!=0) {//月标
            dayOrMonth = lockPeriod + "个月智投";
        } else {
            dayOrMonth = lockPeriod + "天智投";
        }
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE4);
        userOperationLogEntity.setIp(request.getIp());
        userOperationLogEntity.setPlatform(Integer.valueOf(request.getPlatform()));
        userOperationLogEntity.setRemark(dayOrMonth);
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(userInfo.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        // 检查江西银行账户
        BankOpenAccountVO account = amUserClient.selectBankAccountById(userId);
        if (account == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(account.getAccount())) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 查询用户账户表-出借账户
        AccountVO tenderAccount = amTradeClient.getAccount(userId);
        // 检查出借金额
        checkTenderMoney(request, plan, account, cuc, tenderAccount);
        logger.info("加入计划出借校验通过userId:{},ip:{},平台{},优惠券为:{}", userId, request.getIp(), request.getPlatform(), request.getCouponGrantId());

        // 开始出借------------------------------------------------------------------------------------------------------------------------------------------
        return tender(request, plan, account, cuc, tenderAccount);
    }

    /**
     * 加入计划失败回调方法
     * @param request
     * @return
     */
    public WebResult<Map<String, Object>> fallBackJoinPlan(TenderRequest request){
        logger.info("==================已进入 加入计划(三端) fallBackJoinPlan 方法================");
        WebResult<Map<String,Object>> result = new WebResult<>();
        result.setStatus(AppResult.FAIL);
        result.setStatusDesc("加入失败，请重试！");
        return result;
    }

    /**
     * 根据出借项目id历史回报
     *
     * @param tender
     * @return
     */
    @Override
    public WebResult<TenderInfoResult> getInvestInfo(TenderRequest tender) {
        TenderInfoResult investInfo = new TenderInfoResult();
        String planNid = tender.getBorrowNid();
        String money = tender.getAccount();
        // 获取优惠券编号
        Integer couponId = tender.getCouponGrantId();
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        HjhPlanVO plan = amTradeClient.getPlanByNid(planNid);
        if (null == plan) {
            // 计划不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_PLAN_NOT_EXIST);
        }
        UserVO loginUser = null;
        if(tender.getUserId()!=null){
            loginUser = amUserClient.findUserById(tender.getUserId());
            tender.setUser(loginUser);
        }

        BigDecimal couponInterest = BigDecimal.ZERO;
        CouponUserVO couponUser = null;
        if (couponId != null && couponId > 0) {
            //couponConfig = amTradeClient.selectHJHBestCoupon(request);
            couponUser = amTradeClient.getCouponUser(tender.getCouponGrantId(),tender.getUserId());
        }
        /** 计算最优优惠券开始 isThereCoupon 1是有最优优惠券，0无最有优惠券 */
        MyCouponListRequest request = new MyCouponListRequest();
        request.setBorrowNid(tender.getBorrowNid());
        if(loginUser!=null){
            request.setUserId(String.valueOf(loginUser.getUserId()));
        }
        request.setPlatform(tender.getPlatform());

        request.setMoney(money);
        if(loginUser!=null){
            /** 可用优惠券张数开始 */
            int availableCouponListCount = amTradeClient.countHJHAvaliableCoupon(request);
            investInfo.setCouponAvailableCount(availableCouponListCount);
            if(couponId==null || couponId.intValue()==0){
                BestCouponListVO bestCouponListVO = amTradeClient.selectHJHBestCoupon(request);
                logger.info("最优优惠券   " + JSONObject.toJSONString(bestCouponListVO));
                if(bestCouponListVO!=null){
                    couponUser = amTradeClient.getCouponUser(Integer.parseInt(bestCouponListVO.getUserCouponId()),tender.getUserId());
                }
                investInfo.setCouponConfig(bestCouponListVO);
                investInfo.setIsThereCoupon(1);
            }else{
                if(couponUser!=null){
                    BestCouponListVO couponConfig = new BestCouponListVO();
                    couponConfig.setCouponType(couponUser.getCouponType());
                    couponConfig.setUserCouponId(couponUser.getId()+"");
                    couponConfig.setCouponQuota(couponUser.getCouponQuota());
                    couponConfig.setCouponQuotaStr(couponConfig.getCouponQuotaStr());
                    couponConfig.setTenderQuotaType(couponUser.getCouponType());
                    investInfo.setIsThereCoupon(1);
                    investInfo.setCouponConfig(couponConfig);
                }
            }
            /** 可用优惠券张数结束 */

            /** 获取用户优惠券总张数开始 */
            int recordTotal = amTradeClient.getUserCouponCount(tender.getUser().getUserId(), "0");
            investInfo.setRecordTotal(recordTotal);
            /** 获取用户优惠券总张数结束 */
            //BestCouponListVO couponConfig = new BestCouponListVO();
            if (!"0".equals(plan.getCouponConfig()) && loginUser != null) {
                BigDecimal borrowApr = plan.getExpectApr();
                if (couponUser != null) {
                    investInfo.setIsThereCoupon(1);
                    if (couponUser != null) {
                        if (couponUser.getCouponType() == 1) {
                            couponInterest = couponService.getInterestDj(couponUser.getCouponQuota(), couponUser.getCouponProfitTime().intValue(), plan.getExpectApr());
                        } else {
                            couponInterest = couponService.getInterest(plan.getBorrowStyle(), couponUser.getCouponType(), plan.getExpectApr(), couponUser.getCouponQuota(), money, plan.getLockPeriod());
                        }

                        couponUser.setCouponInterest(df.format(couponInterest));
                        if (couponUser.getCouponType() == 2) {
                            borrowApr = borrowApr.add(couponUser.getCouponQuota());
                        }
                        if (couponUser.getCouponType() == 3) {
                            money = new BigDecimal(money).add(couponUser.getCouponQuota()).toString();
                        }
                    }
                    couponUser.setCouponInterest(df.format(couponInterest));
                } else {
                    investInfo.setIsThereCoupon(0);
                }
                investInfo.setCouponUser(couponUser);
                /** 计算最优优惠券结束 */
                investInfo.setCouponCapitalInterest(df.format(couponInterest));
            } else {
                investInfo.setCouponAvailableCount(0);
            }
        }else{
            investInfo.setRecordTotal(0);
            investInfo.setCouponAvailableCount(0);
        }

        BigDecimal earnings = new BigDecimal("0");
        // 如果出借金额不为空
        if (!StringUtils.isBlank(money) && new BigDecimal(money).compareTo(BigDecimal.ZERO) == 1 ||!(StringUtils.isEmpty(money) && couponUser != null && couponUser.getCouponType() == 1 && couponUser.getAddFlg()!=null&& couponUser.getAddFlg() == 1)) {
            // 收益率
            BigDecimal borrowApr = plan.getExpectApr();
            // 周期
            Integer borrowPeriod = plan.getLockPeriod();
            // 还款方式
            String borrowStyle = plan.getBorrowStyle();

            if (StringUtils.isNotEmpty(borrowStyle)) {

                if (StringUtils.equals("endday", borrowStyle)){
                    // 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷365*锁定期；
                    earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                    investInfo.setEarnings(df.format(earnings));
                } else {
                    // 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
                    earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                    investInfo.setEarnings(df.format(earnings));
                }
            }
        } else {
            // 出借金额错误
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_FORMAT);
        }
        if(couponUser!=null && couponUser.getCouponType()==3){
            investInfo.setCapitalInterest(df.format(earnings));
        }else{
            investInfo.setCapitalInterest(df.format(earnings.add(couponInterest)));
        }
        WebResult<TenderInfoResult> result = new WebResult<TenderInfoResult>();
        result.setData(investInfo);
        return result;
    }

    /**
     * App端获取计划出借信息
     *
     * @param tender
     * @return
     */
    @Override
    public AppInvestInfoResultVO getInvestInfoApp(TenderRequest tender) {
        String money = tender.getMoney();
        String platform = tender.getPlatform();
        String couponId = tender.getCouponId();
        String planNid = tender.getBorrowNid();
        AppInvestInfoResultVO resultVo = new AppInvestInfoResultVO();
        if (StringUtils.isNotBlank(money) && new BigDecimal(money).compareTo(BigDecimal.ZERO) > 0) {
            resultVo.setRealAmount("¥" + CommonUtils.formatAmount(null, money));
            // mod by nxl 智投服务 修改 确认加入->确认授权
            //resultVo.setButtonWord("确认加入" + CommonUtils.formatAmount(null, money) + "元");
            resultVo.setButtonWord("确认授权" + CommonUtils.formatAmount(null, money) + "元");
        }else if(StringUtils.isBlank(money) || new BigDecimal(money).compareTo(BigDecimal.ZERO) == 0){
            resultVo.setRealAmount("¥0.00");
            resultVo.setButtonWord("确认");
        }

        // 查询计划信息  传入borrowNid
        resultVo.setStandardValues(CustomConstants.TENDER_THRESHOLD);
        // 根据项目标号获取相应的计划信息
        HjhPlanVO plan = amTradeClient.getPlanByNid(planNid);
        if (null == plan) {
            // 计划不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_PLAN_NOT_EXIST);
        }
        resultVo.setBorrowApr(plan.getExpectApr()+"%");
        PlanDetailCustomizeVO planDetail = amTradeClient.getPlanDetail(planNid);
        resultVo.setPaymentOfInterest("0" + "元");
        // 获取用户最优优惠券
        CouponUserVO couponConfig = null;
        if (null != planDetail) {

            // add by liuyang 神策数据统计 20180820 start
            BorrowStyleVO borrowStyle = this.amTradeClient.getBorrowStyle(plan.getBorrowStyle());
            if (borrowStyle!=null){
                resultVo.setBorrowStyleName(StringUtils.isBlank(borrowStyle.getName()) ? "" : borrowStyle.getName());
            }else{
                resultVo.setBorrowStyleName("");
            }

            // 项目名称
            resultVo.setProjectName(plan.getPlanName());
            // 借款期限
            resultVo.setBorrowPeriod(plan.getLockPeriod());
            if (plan.getIsMonth() == 0) {
                resultVo.setDurationUnit("天");
            } else {
                resultVo.setDurationUnit("月");
            }
            // add by liuyang 神策数据统计 20180820 end

            resultVo.setBorrowNid(planNid);
            // -设置  开放额度剩余金额
            String borrowAccountWait = "0";
            if (planDetail.getAvailableInvestAccount() != null) {
                borrowAccountWait =  CommonUtils.formatAmount(null, planDetail.getAvailableInvestAccount());
            }
            borrowAccountWait = borrowAccountWait.replaceAll(",", "");
            // 开放额度剩余金额
            resultVo.setBorrowAccountWait(borrowAccountWait);
            String initMoney = "0";
            // -设置 最小出借金额(起投金额)-->计算最后一笔出借
            if (planDetail.getDebtMinInvestment() != null) {
                initMoney = new BigDecimal(planDetail.getDebtMinInvestment()).intValue()+"";
            }
            resultVo.setInitMoney(initMoney);
            // -设置优惠券
            logger.info("HJH couponId is:{}, borrowNid is :{}", couponId, planNid);
            JSONObject userCoupon = appCouponService.getPlanCoupon(tender.getUserId(),planNid, money,
                    platform);
            logger.info("userCoupon： ",JSONObject.toJSONString(userCoupon));
            if (couponId == null || "".equals(couponId) || couponId.length() == 0 || "-1".equals(couponId) ) {
                // 不用获取最优优惠券了
                //couponConfig = planService.getUserOptimalCoupon(couponId, borrowNid, userId, money, platform);
            } else {
                // 如果已经有了优惠券  判断优惠券是否可用appCouponService.getPlanCoupon(userId,borrowNid,money,platform);
                if (isHjhCouponAvailable(couponId, userCoupon)) {
                    couponConfig = amTradeClient.getCouponUser(Integer.parseInt(couponId),tender.getUserId() );
                }
            }
            logger.info("优惠券信息couponConfig: {}", JSONObject.toJSONString(couponConfig));
            if("-1".equals(couponId)){
                couponConfig = null;
            }
            // 刚加载页面并且可投小于起投
            if ((StringUtils.isBlank(money) || money.equals("0")) && new BigDecimal(borrowAccountWait).compareTo(new BigDecimal(planDetail.getDebtMinInvestment())) < 1) {
                money = new BigDecimal(borrowAccountWait).intValue() + "";
            }
            if (money.contains(",")) {
                money = money.replace(",", "");
            }

            BigDecimal earnings = new BigDecimal("0");
            // 计算收益
            if ((!StringUtils.isBlank(money) && Double.parseDouble(money) >= 0)) {
                // 这里有个坑，如果计划剩余可投小于用户出借金额，那么计算收益需要用计划剩余可投计算，不能使用用户出借金额计算收益
                logger.info("计划剩余可投: {}", borrowAccountWait);
                logger.info("用户出借金额: {}", money);
                //if (new BigDecimal(borrowAccountWait).compareTo(new BigDecimal(money)) < 0) {
                //	logger.info("计划剩余可投小于用户出借金额,收益按照计划剩余可投计算...");
                //	earnings = planService.setProspectiveEarnings(resultVo,couponConfig, borrowNid,userId,platform,borrowAccountWait);
                //} else {
                logger.info("计划剩余可投大于用户出借金额,收益按照用户出借金额计算...");
                earnings = getProspectiveEarnings(plan,resultVo,couponConfig, planNid,tender.getUserId(),platform,money);
                //}
            }
            logger.info("本金出借计算出的收益是: {}", earnings);

            // 设置优惠券
            resultVo.setCapitalInterest("");
            resultVo.setConfirmCouponDescribe("未使用优惠券");
            resultVo.setUsedCouponDes("未使用");
            resultVo.setCouponType("");
            JSONObject counts =  userCoupon;
            String couponAvailableCount = "0";
            if(counts!=null&&counts.containsKey("availableCouponListCount")){
                couponAvailableCount = counts.getString("availableCouponListCount");
            }else{
                couponAvailableCount = "0";
            }

            if (couponConfig != null) {
                if (couponConfig != null && couponConfig.getId() > 0 && couponConfig.getCouponType() == 1) {
                    resultVo.setCouponDescribe("体验金: " + couponConfig.getCouponQuota() + "元");
                    resultVo.setConfirmCouponDescribe("体验金: " + couponConfig.getCouponQuota() + "元");
                    resultVo.setCouponType("体验金");
                    resultVo.setUsedCouponDes("体验金: " + couponConfig.getCouponQuota() + "元");
                }
                if (couponConfig != null && couponConfig.getId() > 0 && couponConfig.getCouponType() == 2) {
                    resultVo.setCouponDescribe("加息券: " + couponConfig.getCouponQuota() + "%");
                    resultVo.setConfirmCouponDescribe("加息券: " + couponConfig.getCouponQuota() + "%");
                    resultVo.setCouponType("加息券");
                    resultVo.setUsedCouponDes("加息券: " + couponConfig.getCouponQuota() + "%");

                }
                if (couponConfig != null && couponConfig.getId() > 0 && couponConfig.getCouponType() == 3) {
                    resultVo.setCouponDescribe("代金券: " + couponConfig.getCouponQuota() + "元");
                    resultVo.setConfirmCouponDescribe("代金券: " + couponConfig.getCouponQuota() + "元");
                    resultVo.setCouponType("代金券");
                    resultVo.setUsedCouponDes("代金券: " + couponConfig.getCouponQuota() + "元");
                    //resultVo.setRealAmount("¥" + CommonUtils.formatAmount(null, new BigDecimal(money).add(couponConfig.getCouponQuota())));

                }
                resultVo.setCouponName(couponConfig.getCouponName());
                resultVo.setCouponQuota(couponConfig.getCouponQuota().toString());
                resultVo.setEndTime(couponConfig.getAddTime() + "-" + couponConfig.getEndTime());
                resultVo.setIsThereCoupon("1");
                resultVo.setCouponId(couponConfig.getId()+"");
                resultVo.setIsUsedCoupon("1");

                logger.info("开始计算优惠券收益....");
                String calculateIncomeCapital = "";
                if (new BigDecimal(borrowAccountWait).compareTo(new BigDecimal(money)) < 0) {
                    logger.info("同样，计划剩余可投小于用户出借金额,收益按照计划剩余可投计算...");
                    calculateIncomeCapital = borrowAccountWait;
                } else {
                    logger.info("同样，计划剩余可投大于用户出借金额,收益按照用户出借金额计算...");
                    calculateIncomeCapital = money;
                }
                logger.info("优惠券金额按照{}计算....", calculateIncomeCapital);
                BigDecimal couponInterest = BigDecimal.ZERO;
                if (couponConfig != null) {
                    if (couponConfig.getCouponType() == 1) {
                        couponInterest = couponService.getInterestDj(couponConfig.getCouponQuota(), couponConfig.getCouponProfitTime().intValue(), plan.getExpectApr());
                    } else {
                        couponInterest = couponService.getInterest(plan.getBorrowStyle(), couponConfig.getCouponType(), plan.getExpectApr(), couponConfig.getCouponQuota(), money, plan.getLockPeriod());
                    }
                }
                logger.info("优惠券历史回报计算结果couResult: {}", couponInterest);

                resultVo.setCapitalInterest(couponConfig + "元");
                // 优惠券历史回报
                // 历史回报
                BigDecimal borrowInterest = earnings.add(couponInterest);

                //备注
                resultVo.setDesc("历史年回报率:  "+plan.getExpectApr()+"%      历史回报:  " + borrowInterest+"元");
                resultVo.setDesc0("历史年回报率: "+plan.getExpectApr()+"%");
                resultVo.setDesc1("历史回报: " + CommonUtils.formatAmount(null,borrowInterest) + "元");

                resultVo.setProspectiveEarnings(CommonUtils.formatAmount(null, borrowInterest) + "元");
                resultVo.setInterest(CommonUtils.formatAmount(null, borrowInterest));
            }else{
                // 没有可用优惠券
                resultVo.setIsThereCoupon("0");
                resultVo.setCouponDescribe("暂无可用");
                resultVo.setCouponName("");
                resultVo.setCouponQuota("");
                resultVo.setEndTime("");
                resultVo.setCouponId("-1");

                if(userCoupon!=null&& !"0".equals(userCoupon.getString("availableCouponListCount"))){
                    resultVo.setIsThereCoupon("1");
                    resultVo.setCouponDescribe("请选择");
                }else if (userCoupon!=null&& "0".equals(userCoupon.getString("availableCouponListCount")) && !"0".equals(userCoupon.getString("notAvailableCouponListCount"))) {
                    resultVo.setIsThereCoupon("1");
                    resultVo.setCouponDescribe("暂无可用");
                }else {
                    resultVo.setIsThereCoupon("0");
                    resultVo.setCouponDescribe("无可用");
                }

                resultVo.setDesc("历史年回报率: "+plan.getExpectApr()+"%      历史回报: " + earnings +"元");
                resultVo.setDesc0("历史年回报率: "+plan.getExpectApr()+"%");
                resultVo.setDesc1("历史回报: " + CommonUtils.formatAmount(null,earnings) + "元");
                resultVo.setProspectiveEarnings(CommonUtils.formatAmount(null,earnings) + "元");
            }
            // 可用优惠券数量
            resultVo.setCouponAvailableCount(couponAvailableCount);
            resultVo.setConfirmRealAmount("出借金额: " + CommonUtils.formatAmount(null, money) + "元");
            // -设置 用户余额
            AccountVO account = amTradeClient.getAccount(tender.getUserId());
            BigDecimal balance = account.getBankBalance();
            resultVo.setBalance(CommonUtils.formatAmount(null, balance));
            resultVo.setStatus(CustomConstants.APP_STATUS_SUCCESS);
            resultVo.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
            // 起投金额
            resultVo.setInitMoney(plan.getMinInvestment().intValue()+"");
            // 递增金额
            resultVo.setIncreaseMoney(plan.getInvestmentIncrement().intValue()+"");
            resultVo.setInvestmentDescription(resultVo.getInitMoney() + "元起投," + resultVo.getIncreaseMoney() + "元递增");
            // 开放额度剩余金额
            resultVo.setBorrowAccountWait(CommonUtils.formatAmount(null, borrowAccountWait));
            resultVo.setBorrowAccountWait1(borrowAccountWait);
            BigDecimal tmpmoney = balance.subtract(plan.getMinInvestment()).divide(plan.getInvestmentIncrement(), 0, BigDecimal.ROUND_DOWN)
                    .multiply(plan.getInvestmentIncrement()).add(plan.getMinInvestment());
            if (balance.subtract(plan.getMinInvestment()).compareTo(new BigDecimal("0")) < 0) {
                // 可用余额<起投金额 时 investAllMoney 传 -1
                // 全投金额
                resultVo.setInvestAllMoney("-1");
            } else {
                String borrowAccountWaitStr = resultVo.getBorrowAccountWait().replace(",", "");
                if (plan.getMaxInvestment().compareTo(new BigDecimal(borrowAccountWaitStr)) < 0) {
                    if(balance.compareTo(plan.getMaxInvestment())<0){
                        resultVo.setInvestAllMoney(balance + "");
                    }else{
                        resultVo.setInvestAllMoney(plan.getMaxInvestment() + "");
                    }
                } else if (tmpmoney.compareTo(new BigDecimal(borrowAccountWaitStr)) < 0) {
                    // 全投金额
                    if(balance.compareTo(tmpmoney)<0){
                        resultVo.setInvestAllMoney(balance + "");
                    }else{
                        resultVo.setInvestAllMoney(tmpmoney + "");
                    }
                    resultVo.setInvestAllMoney(tmpmoney + "");
                } else {
                    // 全投金额
                    resultVo.setInvestAllMoney(resultVo.getBorrowAccountWait() + "");
                }
            }
            resultVo.setAnnotation("");
        } else {
            logger.info("=================HJH borrow is null! =============");
            resultVo.setStatusDesc(CustomConstants.APP_STATUS_DESC_FAIL);
        }

        return resultVo;
    }

    /**
     * 检查计划出借的参数
     *
     * @param request
     */
    @Override
    public Map<String, Object>  checkPlan(TenderRequest request) {
        UserVO loginUser = amUserClient.findUserById(request.getUserId());
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);

        if (StringUtils.isEmpty(request.getBorrowNid())) {
            // 项目编号不能为空
            throw new CheckException(MsgEnum.STATUS_CE000013);
        }

        if (request.getPlatform() == null) {
            // 出借平台不能为空
            throw new CheckException(MsgEnum.STATUS_ZC000018);
        }
        // 查询选择的优惠券
        CouponUserVO cuc = null;
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = amTradeClient.getCouponUser(request.getCouponGrantId(), userId);
        }
        // 查询计划
        if (StringUtils.isEmpty(request.getBorrowNid())) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_PLAN_NOT_EXIST);
        }
        HjhPlanVO plan = amTradeClient.getPlanByNid(request.getBorrowNid());
        if (plan == null) {
            throw new CheckException(MsgEnum.FIND_PLAN_ERROR);
        }
        if (plan.getPlanInvestStatus() == 2) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_PLAN_CLOSE);
        }
        logger.info("加入计划出借校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        // 查询用户信息
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        UserVO user = amUserClient.findUserById(userId);
        // 检查用户状态  角色  授权状态等  是否允许出借
        checkUser(user, userInfo);
        //从user中获取客户类型，ht_user_evalation_result（用户测评总结表）
        //校验用户测评
        Map<String, Object> resultEval = hjhTenderService.checkEvaluationTypeMoney(request,plan.getInvestLevel(),CustomConstants.TENDER_CHECK_LEVE_HJH);
        // 检查江西银行账户
        BankOpenAccountVO account = amUserClient.selectBankAccountById(userId);
        if (account == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(account.getAccount())) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 查询用户账户表-出借账户
        AccountVO tenderAccount = amTradeClient.getAccount(userId);
        // 检查出借金额
        checkTenderMoney(request, plan, account, cuc, tenderAccount);
        logger.info("加入计划出借校验通过userId:{},ip:{},平台{},优惠券为:{}", userId, request.getIp(), request.getPlatform(), request.getCouponGrantId());
        return resultEval;
    }

    /**
     * 检查计划出借的合规自查
     *
     * @param request
     */
    @Override
    public Map<String, Object> checkEvaluationTypeMoney(TenderRequest request,String checkLeve,String borrowFlag) {
        //返回参数初始化
        Map<String, Object> result = new HashMap<String, Object>();
        //初始化默认值
        result.put("riskTested","");
        result.put("message","");
        result.put("evalType","");
        result.put("evalFlagType","");
        result.put("revaluationMoney","");
        result.put("investLevel",checkLeve);
        //测评判断逻辑开始
        UserVO loginUser = amUserClient.findUserById(request.getUserId());
        Integer userId = loginUser.getUserId();
        UserEvalationResultVO userEvalationResultCustomize = amUserClient.selectUserEvalationResultByUserId(userId);
        if(userEvalationResultCustomize != null){
            EvaluationConfigVO evalConfig = new EvaluationConfigVO();
            //1.散标／债转出借者测评类型校验
            String debtEvaluationTypeCheck = "0";
            //2.散标／债转单笔投资金额校验
            String deptEvaluationMoneyCheck = "0";
            //3.散标／债转待收本金校验
            String deptCollectionEvaluationCheck = "0";
            //4.智投出借者测评类型校验
            String intellectualEveluationTypeCheck = "0";
            //5.智投单笔投资金额校验
            String intellectualEvaluationMoneyCheck = "0";
            //6.智投待收本金校验
            String intellectualCollectionEvaluationCheck = "0";
            //获取开关信息
            List<EvaluationConfigVO> evalConfigList = amTradeClient.selectEvaluationConfig(evalConfig);
            if (evalConfigList != null && evalConfigList.size() > 0) {
                evalConfig = evalConfigList.get(0);
                //1.散标／债转出借者测评类型校验
                debtEvaluationTypeCheck = evalConfig.getDebtEvaluationTypeCheck() == null ? "0" : String.valueOf(evalConfig.getDebtEvaluationTypeCheck());
                //2.散标／债转单笔投资金额校验
                deptEvaluationMoneyCheck = evalConfig.getDeptEvaluationMoneyCheck() == null ? "0" : String.valueOf(evalConfig.getDeptEvaluationMoneyCheck());
                //3.散标／债转待收本金校验
                deptCollectionEvaluationCheck = evalConfig.getDeptCollectionEvaluationCheck() == null ? "0" : String.valueOf(evalConfig.getDeptCollectionEvaluationCheck());
                //4.智投出借者测评类型校验
                intellectualEveluationTypeCheck = evalConfig.getIntellectualEveluationTypeCheck() == null ? "0" : String.valueOf(evalConfig.getIntellectualEveluationTypeCheck());
                //5.智投单笔投资金额校验
                intellectualEvaluationMoneyCheck = evalConfig.getIntellectualEvaluationMoneyCheck() == null ? "0" : String.valueOf(evalConfig.getIntellectualEvaluationMoneyCheck());
                //6.智投待收本金校验
                intellectualCollectionEvaluationCheck = evalConfig.getIntellectualCollectionEvaluationCheck() == null ? "0" : String.valueOf(evalConfig.getIntellectualCollectionEvaluationCheck());
                //7.投标时校验（二期）(预留二期开发)
            }
            //初始化金额返回值
            String revaluation_money, revaluation_money_principal;
            //根据类型从redis或数据库中获取测评类型和上限金额
            String eval_type = userEvalationResultCustomize.getEvalType();
            result.put("evalType",eval_type);
            switch (eval_type) {
                case "保守型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getConservativeEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getConservativeEvaluationSingleMoney());
                    }
                    //从redis获取金额（代收本金）
                    revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL);
                    //如果reids不存在或者为零尝试获取数据库（代收本金）
                    if ("0".equals(revaluation_money_principal)) {
                        revaluation_money_principal = evalConfig.getConservativeEvaluationPrincipalMoney() == null ? "0" : String.valueOf(evalConfig.getConservativeEvaluationPrincipalMoney());
                    }
                    break;
                case "稳健型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getSteadyEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getSteadyEvaluationSingleMoney());
                    }
                    //从redis获取金额（代收本金）
                    revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS_PRINCIPAL) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS_PRINCIPAL);
                    //如果reids不存在或者为零尝试获取数据库（代收本金）
                    if ("0".equals(revaluation_money_principal)) {
                        revaluation_money_principal = evalConfig.getSteadyEvaluationPrincipalMoney() == null ? "0" : String.valueOf(evalConfig.getSteadyEvaluationPrincipalMoney());
                    }
                    break;
                case "成长型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_GROWTH) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_GROWTH);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getGrowupEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getGrowupEvaluationSingleMoney());
                    }
                    //从redis获取金额（代收本金）
                    revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_GROWTH_PRINCIPAL) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_GROWTH_PRINCIPAL);
                    //如果reids不存在或者为零尝试获取数据库（代收本金）
                    if ("0".equals(revaluation_money_principal)) {
                        revaluation_money_principal = evalConfig.getGrowupEvaluationPrincipalMoney() == null ? "0" : String.valueOf(evalConfig.getGrowupEvaluationPrincipalMoney());
                    }
                    break;
                case "进取型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getEnterprisingEvaluationSinglMoney() == null ? "0" : String.valueOf(evalConfig.getEnterprisingEvaluationSinglMoney());
                    }
                    //从redis获取金额（代收本金）
                    revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE_PRINCIPAL) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE_PRINCIPAL);
                    //如果reids不存在或者为零尝试获取数据库（代收本金）
                    if ("0".equals(revaluation_money_principal)) {
                        revaluation_money_principal = evalConfig.getEnterprisingEvaluationPrincipalMoney() == null ? "0" : String.valueOf(evalConfig.getEnterprisingEvaluationPrincipalMoney());
                    }
                    break;
                default:
                    revaluation_money = null;
                    revaluation_money_principal = null;
            }
            if (revaluation_money_principal == null) {
                logger.info("=============从redis中获取测评类型和上限金额异常!(没有获取到对应类型的限额数据) eval_type=" + eval_type);
            } else {
                //代收本金限额校验
                if ((CustomConstants.EVALUATION_CHECK.equals(deptCollectionEvaluationCheck) && (CustomConstants.TENDER_CHECK_LEVE_HZR.equals(borrowFlag) || CustomConstants.TENDER_CHECK_LEVE_HSB.equals(borrowFlag)))
                        || (CustomConstants.EVALUATION_CHECK.equals(intellectualCollectionEvaluationCheck) && CustomConstants.TENDER_CHECK_LEVE_HJH.equals(borrowFlag))) {
                    //获取冻结金额和代收本金
                    CallCenterAccountDetailVO accountDetail = amTradeClient.queryAccountEvalDetail(userId);
                    if (accountDetail != null) {
                        BigDecimal planFrost = accountDetail.getPlanFrost();// plan_frost 汇添金计划真实冻结金额
                        BigDecimal bankFrost = accountDetail.getBankFrost();// bank_frost 银行冻结金额
                        BigDecimal bankAwaitCapital = accountDetail.getBankAwaitCapital();// bank_await_capital 银行待收本金
                        BigDecimal account = BigDecimal.ZERO;
                        //加法运算
                        account = account.add(planFrost).add(bankFrost).add(bankAwaitCapital).add(new BigDecimal(request.getAccount()));
                        //金额对比判断（校验金额 大于 设置测评金额）（代收本金）
                        if (account.compareTo(new BigDecimal(revaluation_money_principal)) > 0) {
                            //返回类型和限额
                            result.put("evalType",eval_type);
                            result.put("revaluationMoney",StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money_principal).intValue()));
                            //返回错误码
                            result.put("riskTested",CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS_PRINCIPAL);
                            result.put("message","如果您继续出借， ## \n当前累计出借本金将超过 \n您的风险等级 #"+eval_type+"# 对应的限额。");
                        }
                    }
                }
            }
            if (revaluation_money == null) {
                logger.info("=============从redis中获取测评类型和上限金额异常!(没有获取到对应类型的限额数据) eval_type=" + eval_type);
            } else {
                if ((CustomConstants.EVALUATION_CHECK.equals(deptEvaluationMoneyCheck) && (CustomConstants.TENDER_CHECK_LEVE_HZR.equals(borrowFlag) || CustomConstants.TENDER_CHECK_LEVE_HSB.equals(borrowFlag)))
                        || (CustomConstants.EVALUATION_CHECK.equals(intellectualEvaluationMoneyCheck) && CustomConstants.TENDER_CHECK_LEVE_HJH.equals(borrowFlag))) {
                    //金额对比判断（校验金额 大于 设置测评金额）
                    if (new BigDecimal(request.getAccount()).compareTo(new BigDecimal(revaluation_money)) > 0) {
                        //返回类型和限额
                        result.put("evalType",eval_type);
                        result.put("revaluationMoney",StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()));
                        //返回错误码
                        result.put("riskTested",CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS);
                        result.put("message","您当前的风险测评类型为 #"+eval_type+"# \n根据监管要求,\n"+eval_type+"用户单笔最高出借限额 #"
                                +StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue())+"# 。");
                    }
                }
            }
            //风险类型校验
            if ((CustomConstants.EVALUATION_CHECK.equals(debtEvaluationTypeCheck) && (CustomConstants.TENDER_CHECK_LEVE_HZR.equals(borrowFlag) || CustomConstants.TENDER_CHECK_LEVE_HSB.equals(borrowFlag)))
                    || (CustomConstants.EVALUATION_CHECK.equals(intellectualEveluationTypeCheck) && CustomConstants.TENDER_CHECK_LEVE_HJH.equals(borrowFlag))) {
                //计划类判断用户类型为稳健型以上才可以投资
                if (!CommonUtils.checkStandardInvestment(eval_type,borrowFlag,checkLeve)) {
                    //返回错误码
                    result.put("evalType",eval_type);
                    result.put("evalFlagType",checkLeve);
                    result.put("revaluationMoney",StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()));
                    //返回错误码
                    result.put("riskTested",CustomConstants.BANK_TENDER_RETURN_CUSTOMER_STANDARD_FAIL);
                    result.put("message",CommonUtils.DESC_PROJECT_RISK_LEVEL_DESC.replace("{0}", userEvalationResultCustomize.getEvalType()).replace("{1}",checkLeve));
                }
            }
        }else{
            logger.info("=============该用户测评总结数据为空! userId="+userId);
        }
        //测评到期日
        Long lCreate = loginUser.getEvaluationExpiredTime().getTime();
        //当前日期
        Long lNow = System.currentTimeMillis();
        // 判断用户测评有效期
        if (loginUser.getIsEvaluationFlag() == 0) {
            result.put("riskTested",CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL);
            result.put("message","根据监管要求，出借前必须进行风险测评。");
        } else {
            if(loginUser.getIsEvaluationFlag()==1 && null != loginUser.getEvaluationExpiredTime()){
                if (lCreate <= lNow) {
                    //已过期需要重新评测
                    //返回错误码
                    result.put("riskTested",CustomConstants.BANK_TENDER_RETURN_ANSWER_EXPIRED);
                    result.put("message","根据监管要求，测评已过期，出借前必须进行风险测评。");
                }
            } else {
                result.put("riskTested",CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL);
                result.put("message","根据监管要求，出借前必须进行风险测评。");
            }
        }
        return result;
    }

    /**
     * 加入计划失败  恢复redis
     *
     * @param tender
     */
    @Override
    public void recoverRedis(TenderRequest tender) {
        String redisKey = RedisConstants.HJH_PLAN + tender.getBorrowNid();
        JedisPool poolNew = RedisUtils.getPool();
        Jedis jedis = poolNew.getResource();
        BigDecimal accountBigDecimal = new BigDecimal(tender.getAccount());
        String balanceLast = RedisUtils.get(redisKey);
        try{
            if (StringUtils.isNotBlank(balanceLast)) {
                while ("OK".equals(jedis.watch(redisKey))) {
                    balanceLast = RedisUtils.get(redisKey);
                    BigDecimal recoverAccount = accountBigDecimal.add(new BigDecimal(balanceLast));
                    Transaction tx = jedis.multi();
                    tx.set(redisKey, recoverAccount + "");
                    List<Object> result = tx.exec();
                    if (result == null || result.isEmpty()) {
                        jedis.unwatch();
                    } else {
                        logger.info("加入计划用户:" + tender.getUserId() + "***********from redis恢复redis："
                                + tender.getAccount());
                        break;
                    }
                }
            }
        }catch(Exception e){
            logger.info("抛出异常:[{}]",e);
        }finally {
            //返还
            RedisUtils.returnResource(poolNew,jedis);
        }

    }

    /**
     * 加入计划成功后,发送神策数据统计MQ
     *
     * @param sensorsDataBean
     */
    @Override
    public void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_HJH_INVEST_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
    }

    /**
     * 计算预期收益
     * @param resultVo
     * @param couponConfig
     * @param planNid
     * @param userId
     * @param platform
     * @param money
     * @return
     */
    private BigDecimal getProspectiveEarnings(HjhPlanVO plan,AppInvestInfoResultVO resultVo, CouponUserVO couponConfig, String planNid, Integer userId, String platform, String money) {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal earnings = new BigDecimal("0");
        if (null != plan) {
            // 如果出借金额不为空
            if (!StringUtils.isBlank(money) && (new BigDecimal(money).compareTo(BigDecimal.ZERO) > 0)
                    || !(StringUtils.isEmpty(money) && couponConfig != null && couponConfig.getCouponType() == 1
                    && couponConfig.getAddFlg() == 1)) {
                // 收益率
                BigDecimal borrowApr = plan.getExpectApr();
                // 周期
                Integer borrowPeriod = plan.getLockPeriod();
                // 还款方式
                String borrowStyle = plan.getBorrowStyle();//endday

                if (StringUtils.isNotEmpty(borrowStyle)) {
                    if (StringUtils.equals("endday", borrowStyle)){
                        // 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷365*锁定期；
                        earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                    } else {
                        // 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
                        earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                    }
                    resultVo.setBorrowInterest(PROSPECTIVE_EARNINGS + df.format(earnings) + "元");
                    resultVo.setProspectiveEarnings(df.format(earnings) + "元");
                    resultVo.setInterest(df.format(earnings));
                }
            } else {
                resultVo.setBorrowInterest(PROSPECTIVE_EARNINGS + "0元");
                resultVo.setProspectiveEarnings(df.format(earnings) + "元");
                resultVo.setInterest(df.format(earnings));
            }
        }
        return earnings;
    }

    /**
     * 检查优惠券可用吗
     * @param couponId
     * @param userCoupon
     * @return
     */
    private boolean isHjhCouponAvailable(String couponId, JSONObject userCoupon) {
        String obj = JSONObject.toJSONString(userCoupon.get("availableCouponList"));
        List<CouponBeanVo> coupons = JSONArray.parseArray(obj, CouponBeanVo.class);
        if (!CollectionUtils.isEmpty(coupons)) {
            for (CouponBeanVo couponBean : coupons) {
                if (couponBean.getUserCouponId().equals(couponId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Description 开始出借
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 14:56
     */
    private  WebResult<Map<String, Object>> tender(TenderRequest request, HjhPlanVO plan, BankOpenAccountVO account, CouponUserVO cuc, AccountVO tenderAccount) {
        WebResult<Map<String, Object>> result= new WebResult<Map<String, Object>>();
        Map<String, Object> tenderEarnings;
        result.setStatus(WebResult.ERROR);
        Integer userId = request.getUser().getUserId();
        BigDecimal decimalAccount = new BigDecimal(request.getAccount());
        request.setBankOpenAccount(account);
        request.setTenderAccount(tenderAccount);
        //校验用户测评
        Map<String, Object> resultEval = hjhTenderService.checkEvaluationTypeMoney(request,plan.getInvestLevel(),CustomConstants.TENDER_CHECK_LEVE_HJH);
        // 体验金出借
        if (decimalAccount.compareTo(BigDecimal.ZERO) != 1 && cuc != null && (cuc.getCouponType() == 3 || cuc.getCouponType() == 1)) {
            logger.info("体验{},优惠金出借开始:userId:{},平台{},券为:{}", userId, request.getPlatform(), request.getCouponGrantId());
            // 体验金出借
            couponService.couponTender(request, plan,  cuc, userId);
            // 计算收益
            tenderEarnings = getTenderEarnings(request,plan,cuc);
            //放入用户测评返回值
            tenderEarnings.putAll(resultEval);
            result.setData(tenderEarnings);
            result.setStatus(WebResult.SUCCESS);
            //用户测评校验状态转换
            if(resultEval!=null){
                if(resultEval.get("riskTested") != null && resultEval.get("riskTested") != ""){
                    result.setStatus((String) resultEval.get("riskTested"));
                    result.setStatusDesc((String) resultEval.get("message"));
                }
            }
            logger.info("体验金出借结束:userId{}" + userId);
            return result;
        }
        String redisKey = RedisConstants.HJH_PLAN + plan.getPlanNid();
        // 计划剩余金额
        String balance = RedisUtils.get(redisKey);
        JedisPool poolNew = RedisUtils.getPool();
        Jedis jedis = poolNew.getResource();
        // 操作redis----------------------------------------------
        if (StringUtils.isNotBlank(balance)) {
            MsgCode redisMsgCode = null;
            try {
                while ("OK".equals(jedis.watch(redisKey))) {
                    balance = RedisUtils.get(redisKey);
                    if (StringUtils.isNotBlank(balance)) {
                        logger.info("加入计划冻结前可用金额为:{},userId:{},planNid:{},平台:{}", decimalAccount, userId, plan.getPlanNid(), request.getPlatform());
                        logger.info("加计划未减前可用开放额度redis:{},userId:{},planNid:{},平台:{}", balance, userId, plan.getPlanNid(), request.getPlatform());
                        if (new BigDecimal(balance).compareTo(BigDecimal.ZERO) == 0) {
                            logger.info("planNid:{},可加入剩余金额为{}元", plan.getPlanNid(), balance);
                            redisMsgCode = MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE;
                            throw new CheckException(redisMsgCode);
                        } else {
                            Transaction tx = jedis.multi();
                            // 事务：计划当前可用额度 = 计划未投前可用余额 - 用户出借额度
                            if (new BigDecimal(balance).compareTo(decimalAccount) < 0) {
                                logger.info("计划可用开放额度redis扣除失败redis值不够了：userId:{},planNid{},balance:{}元  decimalAccount:{}", userId, plan.getPlanNid(), balance,decimalAccount);
                                redisMsgCode = MsgEnum.ERR_AMT_TENDER_INVESTMENT;
                                throw new CheckException(redisMsgCode);
                            }
                            BigDecimal lastAccount = new BigDecimal(balance).subtract(decimalAccount);
                            tx.set(redisKey, lastAccount + "");
                            List<Object> result1 = tx.exec();
                            if (result1 == null || result1.isEmpty()) {
                                jedis.unwatch();
                                logger.info("计划可用开放额度redis扣除失败：userId:{},planNid{},金额{}元", userId, plan.getPlanNid(), balance);
                                //redisMsgCode = MsgEnum.ERR_AMT_TENDER_INVESTMENT;
                                //throw new CheckException(redisMsgCode);
                            } else {
                                logger.info("加计划redis操作成功userId:{},平台:{},planNid{},计划扣除后可用开放额度redis:{}", userId, request.getPlatform(), plan.getPlanNid(), lastAccount);
                                // 写队列
                                break;
                            }
                        }
                    } else {
                        logger.info("您来晚了：userId:{},planNid{},金额{}元", userId, plan.getPlanNid(), balance);
                        redisMsgCode = MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE;
                        throw new CheckException(redisMsgCode);
                    }
                }
            } catch (Exception e) {
                if (redisMsgCode != null) {
                    throw new CheckException(redisMsgCode);
                } else {
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                }
            } finally {
                RedisUtils.returnResource(poolNew, jedis);
            }
        } else {
            logger.info("您来晚了：userId:{},planNid{},金额{}元", userId, plan.getPlanNid(), balance);
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE);
        }
        // 生成冻结订单-----------------------
        boolean afterDealFlag = false;
        // 插入数据库  真正开始操作加入计划表
        try{
            afterDealFlag = updateAfterPlanRedis(request, plan);
            logger.info("加入计划updateAfterPlanRedis 操作结果 ", afterDealFlag);
        }catch (Exception e){
            logger.error("加入计划报错了 userId:"+userId+"  planNid:"+ plan.getPlanNid(),e);
            // 恢复redis
            recoverRedis(request);
            throw e;
        }
        if(afterDealFlag){
            // 计算收益
            tenderEarnings = getTenderEarnings(request,plan,cuc);
            result.setStatus(WebResult.SUCCESS);
            //用户测评校验状态转换
            if(resultEval!=null){
                if(resultEval.get("riskTested") != null && resultEval.get("riskTested") != ""){
                    result.setStatus((String) resultEval.get("riskTested"));
                    result.setStatusDesc((String) resultEval.get("message"));
                }
            }
            //放入用户测评返回值
            tenderEarnings.putAll(resultEval);
            result.setData(tenderEarnings);
        }else{
            result.setStatus(AppResult.FAIL);
            result.setStatusDesc("加入失败，请重试！");
        }
        return result;
    }

    /**
     * 获取预期收益
     * @param request
     * @param plan
     * @param couponUser
     * @return
     */
    private Map<String, Object> getTenderEarnings(TenderRequest request, HjhPlanVO plan, CouponUserVO couponUser) {
        Map<String, Object> result = new HashedMap();
        if(request.getEarnings()==null){
            request.setEarnings(BigDecimal.ZERO);
        }
        // 历史回报
        result.put("earnings", CommonUtils.formatAmount(null, request.getEarnings()));
        // 优惠券收益
        result.put("couponInterest", request.getCouponInterest()==null?0:request.getCouponInterest());
        // 出借金额
        result.put("account", CommonUtils.formatAmount(null, request.getAccount()));
        // 出借的计划
        result.put("borrowNid", plan.getPlanNid());

        // 神策数据统计追加
        // 计划加入订单号
        result.put("accedeOrderId",request.getPlanOrderId());

        result.put("plan","1");
        // 如果有优惠券  放上优惠券面值和类型
        if (couponUser != null) {
            BigDecimal couponInterest = BigDecimal.ZERO;
            if (couponUser.getCouponType() == 1) {
                couponInterest = couponService.getInterestDj(couponUser.getCouponQuota(), couponUser.getCouponProfitTime().intValue(), plan.getExpectApr());
            } else {
                couponInterest = couponService.getInterest(plan.getBorrowStyle(), couponUser.getCouponType(), plan.getExpectApr(), couponUser.getCouponQuota(), request.getAccount(), plan.getLockPeriod());
            }
            logger.info("优惠券收益为：{}",couponInterest);
            if (couponUser != null && couponUser.getCouponType() == 3) {
                couponInterest = couponInterest.subtract(couponUser.getCouponQuota());
            }
            // 优惠券类别
            result.put("couponType", couponUser.getCouponType());
            // 优惠券额度
            result.put("couponQuota", couponUser.getCouponQuota());
            // 优惠券ID
            result.put("couponGrantId", couponUser.getId());
            result.put("projectType",couponUser.getProjectType());
            result.put("earnings", CommonUtils.formatAmount(null, request.getEarnings().add(couponInterest)));
            // app结果页加上面值
            if (couponUser != null && couponUser.getCouponType() == 3) {
                result.put("appEarnings", CommonUtils.formatAmount(null, request.getEarnings().add(couponInterest).add(couponUser.getCouponQuota())));
            }
        }else{
            // 优惠券类别
            result.put("couponType", "");
            // 优惠券额度
            result.put("couponQuota", "");
            // 优惠券ID
            result.put("couponGrantId", "");
            result.put("projectType","");
        }
        return result;

    }

    /**
     * @Description 真正开始加入计划
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 15:04
     */
    private boolean updateAfterPlanRedis(TenderRequest request, HjhPlanVO plan) {
        String accountStr = request.getAccount();
        Integer userId = request.getUser().getUserId();
        String planOrderId = GetOrderIdUtils.getOrderId0(userId);
        // add by liuyang 神策数据统计追加 start
        request.setPlanOrderId(planOrderId);
        // add by liuyang 神策数据统计追加 end
        int nowTime = GetDate.getNowTime10();
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        // 预期年利率
        BigDecimal planApr = plan.getExpectApr();
        // 周期
        Integer planPeriod = plan.getLockPeriod();
        // 还款方式
        String borrowStyle = plan.getBorrowStyle();
        // 预期收益
        BigDecimal earnings = BigDecimal.ZERO;
        if ("endday".equals(borrowStyle)) {
            // 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷365*锁定期；
            earnings = DuePrincipalAndInterestUtils
                    .getDayInterest(new BigDecimal(accountStr), planApr.divide(new BigDecimal("100")), planPeriod)
                    .divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
        } else {
            // 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
            earnings = DuePrincipalAndInterestUtils
                    .getMonthInterest(new BigDecimal(accountStr), planApr.divide(new BigDecimal("100")), planPeriod)
                    .divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);

        }
        // 当大于等于100时 取百位 小于100 时 取十位
        //用户出借金额
        BigDecimal accountDecimal = new BigDecimal(accountStr);
        /*(1)汇计划加入明细表插表开始*/
        // 处理汇计划加入明细表(以下涵盖所有字段)
        HjhAccedeVO planAccede = new HjhAccedeVO();
        planAccede.setAccedeOrderId(planOrderId);
        planAccede.setPlanNid(request.getBorrowNid());
        planAccede.setUserId(userId);
        planAccede.setUserName(request.getUser().getUsername());
        //用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
        planAccede.setUserAttribute(userInfo.getAttribute());
        // 加入金额
        planAccede.setAccedeAccount(accountDecimal);
        //已出借金额(出借时维护)
        planAccede.setAlreadyInvest(BigDecimal.ZERO);
        planAccede.setClient(Integer.parseInt(request.getPlatform()));
        //0自动投标中 2自动投标成功 3锁定中 5退出中 7已退出 99 自动出借异常(出借时维护)
        planAccede.setOrderStatus(0);
        //计息时间(最后放款时维护)
        planAccede.setCountInterestTime(0);
        //协议发送状态0未发送 1已发送
        planAccede.setSendStatus(0);
        planAccede.setLockPeriod(planPeriod);
        //提成计算状态:0:未计算,1:已计算
        planAccede.setCommissionStatus(0);
        //可投金额初始与加入金额一致
        planAccede.setAvailableInvestAccount(accountDecimal);
        //(出借时维护)
        planAccede.setWaitTotal(BigDecimal.ZERO);
        //(出借时维护)
        planAccede.setWaitCaptical(BigDecimal.ZERO);
        //(出借时维护)
        planAccede.setWaitInterest(BigDecimal.ZERO);
        //(退出时维护)
        planAccede.setReceivedTotal(BigDecimal.ZERO);
        //(退出时维护)
        planAccede.setReceivedInterest(BigDecimal.ZERO);
        //(退出时维护)
        planAccede.setReceivedCapital(BigDecimal.ZERO);
        //(退出时维护)
        planAccede.setQuitTime(0);
        //最后回款时间(复审时维护)
        planAccede.setLastPaymentTime(0);
        //实际回款时间(退出时维护)
        planAccede.setAcctualPaymentTime(0);
        //应还总额 = 应还本金 +应还利息
        planAccede.setShouldPayTotal(accountDecimal.add(earnings));
        planAccede.setShouldPayCapital(accountDecimal);
        planAccede.setShouldPayInterest(earnings);
        planAccede.setCreateUser(userId);
        //初始未未删除
        planAccede.setDelFlag(0);
		//汇计划三期要求加入计划存当时的预期年化收益率 LIBIN PC  微服务三端共用此方法
		if(plan.getExpectApr() != null){
			planAccede.setExpectApr(plan.getExpectApr());
		}
        // 给加入明细用的
        request.setPlanOrderId(planOrderId);
        request.setEarnings(earnings);
        request.setAccountDecimal(accountDecimal);
        request.setNowTime(nowTime);
        request.setOrderId(planOrderId);
        if (Validator.isNotNull(userInfo)) {
           // UserVO spreadsUsers = amUserClient.getSpreadsUsersByUserId(userId);
            // 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
            Integer attribute = null;
            if (userInfo != null) {
                // 获取出借用户的用户属性
                attribute = userInfo.getAttribute();
                if (attribute != null) {
                    // 出借人用户属性
                    planAccede.setUserAttribute(attribute);
                    // 如果是线上员工或线下员工，推荐人的userId和username不插
                    if (attribute == 2 || attribute == 3) {
                        EmployeeCustomizeVO employeeCustomize = this.amUserClient.selectEmployeeByUserId(userId);
                        if (employeeCustomize != null) {
                            planAccede.setInviteUserRegionname(employeeCustomize.getRegionName());
                            planAccede.setInviteUserBranchname(employeeCustomize.getBranchName());
                            planAccede.setInviteUserDepartmentname(employeeCustomize.getDepartmentName());
                        }
                    } else if (attribute == 1) {
                        // 有主单
                        SpreadsUserVO spreadsUserVO = amUserClient.querySpreadsUsersByUserId(userId);
                        if (spreadsUserVO!=null) {
                            int refUserId = spreadsUserVO.getSpreadsUserId();
                            // 查找用户推荐人
                            UserVO userss = getUsers(refUserId);
                            if (userss != null) {
                                planAccede.setInviteUserId(userss.getUserId());
                                planAccede.setInviteUserName(userss.getUsername());
                            }
                            // 推荐人信息
                            UserInfoVO refUsers = this.getUsersInfoByUserId(refUserId);
                            // 推荐人用户属性
                            if (refUsers != null) {
                                planAccede.setInviteUserAttribute(refUsers.getAttribute());
                            }
                            // 查找用户推荐人部门
                            EmployeeCustomizeVO employeeCustomize =this.amUserClient.selectEmployeeByUserId(refUserId);
                            if (employeeCustomize != null) {
                                planAccede.setInviteUserRegionname(employeeCustomize.getRegionName());
                                planAccede.setInviteUserBranchname(employeeCustomize.getBranchName());
                                planAccede.setInviteUserDepartmentname(employeeCustomize.getDepartmentName());
                            }
                        }
                    } else if (attribute == 0) {
                        // 无主单
                        SpreadsUserVO spreadsUserVO = amUserClient.querySpreadsUsersByUserId(userId);
                        if (spreadsUserVO != null) {
                            int refUserId = spreadsUserVO.getSpreadsUserId();
                            // 查找推荐人
                            UserVO userss = getUsers(refUserId);
                            if (userss != null) {
                                planAccede.setInviteUserId(userss.getUserId());
                                planAccede.setInviteUserName(userss.getUsername());
                            }
                            // 推荐人信息
                            UserInfoVO refUsers = getUsersInfoByUserId(refUserId);
                            // 推荐人用户属性
                            if (refUsers != null) {
                                planAccede.setInviteUserAttribute(refUsers.getAttribute());
                            }
                        }
                    }
                }
            }
        }
        planAccede.setRequest(request);
        planAccede.setCreateTime(new Date());
        // 插入汇计划加入明细表
        logger.info("插入汇计划加入明细表  planAccede: {} ", JSONObject.toJSONString(planAccede) );
        boolean trenderFlag = amTradeClient.insertHJHPlanAccede(planAccede);
        logger.info("出借明细表插入完毕,userId{},平台{},结果{}", userId, request.getPlatform(), trenderFlag);
        // 优惠券出借开始
        if (trenderFlag) {
            //加入明细表插表成功的前提下，继续
            // 投标成功后,发送CRM绩效统计
            CrmInvestMsgBean crmInvestMsgBean = new CrmInvestMsgBean();
            crmInvestMsgBean.setInvestType(1);
            crmInvestMsgBean.setOrderId(planAccede.getAccedeOrderId());
            //加入明细表插表成功的前提下，继续
            //crm出借推送
            try {
                logger.info("出借成功后,发送CRM投资统计MQ:智投服务订单号:[" + planAccede.getAccedeOrderId() + "].");
                commonProducer.messageSendDelay(new MessageContent(MQConstant.CRM_TENDER_INFO_TOPIC, UUID.randomUUID().toString(), crmInvestMsgBean),2);
            } catch (Exception e) {
                logger.error("发送CRM消息失败:" + e.getMessage());
            }
            // 更新  渠道统计用户累计出借  和  huiyingdai_utm_reg的首投信息 开始
            this.updateUtm(request, plan);
            // 网站累计出借追加
            // 出借、收益统计表
            JSONObject params = new JSONObject();
            params.put("tenderSum", accountDecimal);
            params.put("nowTime", GetDate.getDate(GetDate.getNowTime10()));
            // 出借修改mongodb运营数据
            params.put("type", 3);
            params.put("money", accountDecimal);
            try {
                // 网站累计投资追加
                // 投资修改mongodb运营数据
                commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, UUID.randomUUID().toString(), params));
            } catch (MQException e) {
                logger.error(e.getMessage());
            }
            try {
                // 投标成功后,发送大屏数据统计MQ
                sendScreenDataMQ(request.getUser().getUsername(), userId, planOrderId, new BigDecimal(accountStr), 3,planPeriod,borrowStyle);
            } catch (MQException e) {
                logger.error(e.getMessage());
            }

        }
        AppUtmRegVO appChannelStatisticsDetails = amUserClient.getAppChannelStatisticsDetailByUserId(userId);
        if (appChannelStatisticsDetails != null) {
            logger.info("更新app渠道统计表, userId is: {}", userId);
            Map<String, Object> params = new HashMap<String, Object>();
            // 认购本金
            params.put("accountDecimal", accountDecimal);
            // 出借时间
            params.put("investTime", GetDate.getNowTime10());
            // 项目类型
            params.put("projectType", "智投");
            // 首次投标项目期限
            String investProjectPeriod = "";
            if ("endday".equals(borrowStyle)) {
                investProjectPeriod = planPeriod + "天";
            } else {
                investProjectPeriod = planPeriod + "个月";
            }
            params.put("investProjectPeriod", investProjectPeriod);
            //根据investFlag标志位来决定更新哪种出借
            params.put("investFlag", checkIsNewUserCanInvest2(userId));
            // 用户id
            params.put("userId", userId);
            //压入消息队列
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
                        MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG, UUID.randomUUID().toString(), params));
            } catch (MQException e) {
                logger.error(e.getMessage());
                logger.error("渠道统计用户累计出借推送消息队列失败！！！");
            }
        }

        Integer couponGrantId = request.getCouponGrantId();
        if (couponGrantId != null && couponGrantId.intValue() >0) {
            logger.info("开始优惠券出借,userId{},平台{},优惠券{}", userId, request.getPlatform(), couponGrantId);
            // 优惠券出借校验
            try {
                // 开始使用优惠券
                Map<String, String> params = new HashMap<String, String>();
                params.put("mqMsgId", GetCode.getRandomCode(10));
                // 真实出借金额
                //发送纳觅返现mq  add   tyy2018-12-25
                try {
                    logger.info("纳觅返现加入计划成功planOrderId"+planOrderId);
                    sendReturnCashActivity(userId,planOrderId,new BigDecimal(accountStr),3);

                } catch (Exception e) {
                    logger.error("加入计划 纳觅返现mq出错",e);
                    logger.error(e.getMessage());
                }
                params.put("money", accountStr);
                // 借款项目编号
                params.put("borrowNid", plan.getPlanNid());
                // 平台
                params.put("platform", request.getPlatform());
                // 优惠券id
                params.put("couponGrantId", couponGrantId+"");
                // ip
                params.put("ip", request.getIp());
                // 真实出借订单号
                params.put("ordId", request.getOrderId());
                // 用户编号
                params.put("userId", userId+"");
                params.put("account", request.getAccount());
                params.put("mainTenderNid", request.getMainTenderNid());
                logger.info("加入计划 开始调用优惠券投资：{} ",JSONObject.toJSONString(params));
                commonProducer.messageSend(new MessageContent(MQConstant.HJH_COUPON_TENDER_TOPIC, UUID.randomUUID().toString(), params));

            } catch (Exception e) {
                logger.error("加入计划 优惠券出借出错",e);
                logger.error(e.getMessage());
            }
        }
        return true;
    }
    /**
     * 纳觅返现活动
     * @param userId
     * @param order
     */
    private void sendReturnCashActivity(Integer userId,String order,BigDecimal investMoney,int projectType) throws MQException {
        // 加入到消息队列
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("orderId", order);
        params.put("investMoney", investMoney.toString());
        //来源,1=新手标，2=散标，3=汇计划
        params.put("productType", projectType);
        commonProducer.messageSend(new MessageContent(MQConstant.RETURN_CASH_ACTIVITY_SAVE_TOPIC, UUID.randomUUID().toString(), params));
    }


    /**
     * 投资计划成功后,发送大屏数据统计MQ
     *
     */
    private void sendScreenDataMQ(String username,Integer userId,String orderId,BigDecimal investMoney,Integer projectType,Integer planPeriod,String borrowStyle) throws MQException {
        ScreenDataBean screenDataBean = new ScreenDataBean();
        screenDataBean.setUserId(userId);
        screenDataBean.setMoney(investMoney);
        screenDataBean.setUserName(username);
        screenDataBean.setOperating(1);
        screenDataBean.setOrderId(orderId);
        screenDataBean.setProductType(projectType);
        screenDataBean.setPlanPeriod(planPeriod);
        screenDataBean.setBorrowStyle(borrowStyle);
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SCREEN_DATA_TOPIC, UUID.randomUUID().toString(), screenDataBean), 2);
    }
    /**
     * 更新  渠道统计用户累计出借  和  huiyingdai_utm_reg的首投信息 开始
     * @param request
     * @param plan
     */
    private void updateUtm(TenderRequest request, HjhPlanVO plan) {
        logger.info("加入计划成功  渠道统计用户累计出借  和  huiyingdai_utm_reg的首投信息 开始  userId {}  计划编号 {}", request.getUserId(), plan.getPlanNid());
        //更新汇计划列表成功的前提下
        // 更新渠道统计用户累计出借
        // 出借人信息
        // 更新渠道统计用户累计出借 从mongo里面查询
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", request.getUserId());
        // 认购本金
        params.put("accountDecimal", request.getAccountDecimal());
        // 出借时间
        params.put("investTime", request.getNowTime());
        // 项目类型
        params.put("projectType", "汇计划");
        // 首次投标项目期限
        String investProjectPeriod = "";
        // 还款方式
        String borrowStyle = plan.getBorrowStyle();
        if ("endday".equals(borrowStyle)) {
            investProjectPeriod = plan.getLockPeriod() + "天";
        } else {
            investProjectPeriod = plan.getLockPeriod() + "个月";
        }
        params.put("investProjectPeriod", investProjectPeriod);
        //压入消息队列
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_UTM_REG_TOPIC, UUID.randomUUID().toString(), params));
        } catch (MQException e) {
            logger.error(e.getMessage());
            logger.error("渠道统计用户累计出借推送消息队列失败！！！");
        }
        /*(6)更新  渠道统计用户累计出借  和  huiyingdai_utm_reg的首投信息 结束*/
    }

    /**
     * @Description 检查出借金额
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 15:52
     */
    private void checkTenderMoney(TenderRequest request, HjhPlanVO plan, BankOpenAccountVO OpenAccount, CouponUserVO cuc, AccountVO tenderAccount) {
        String account = request.getAccount();
        // 出借金额必须是整数
        if (StringUtils.isEmpty(account)) {
            account = "0";
            request.setAccount(account);
        }
        if (!(StringUtils.isNotEmpty(account)
                || (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 3)
                || (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 1
                && cuc.getAddFlg() == 1))) {
            throw new CheckException(MsgEnum.ERR_ACTIVITY_NOT_EXIST);
        }
        // 出借金额小数点后超过两位
        if(!request.getPlatform().equals(ClientConstants.WEB_CLIENT+"")){
            // 移动端需要校验
            if (account.contains(".")) {
                String accountSubstr = account.substring(account.indexOf(".") + 1);
                if (StringUtils.isNotEmpty(accountSubstr) && accountSubstr.length() > 2) {
                    // 金额格式错误
                    throw new CheckException(MsgEnum.ERR_FMT_MONEY);
                }
            }
        }
        BigDecimal accountBigDecimal = new BigDecimal(account);
        int compareResult = accountBigDecimal.compareTo(BigDecimal.ZERO);
        if (compareResult < 0) {
            // 加入金额不能为负数
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_NEGATIVE);
        }
        if ((compareResult == 0 && cuc == null)
                || (compareResult == 0 && cuc != null && cuc.getCouponType() == 2)) {
            // 出借金额不能为0
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_ZERO);
        }
        if (compareResult > 0 && cuc != null && cuc.getCouponType() == 1
                && cuc.getAddFlg() == 1) {
            // 出借金额不能为0
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_COUPON_USE_ALONE);
        }
        String balance = RedisUtils.get(RedisConstants.HJH_PLAN + plan.getPlanNid());
        if (StringUtils.isEmpty(balance)) {
            // 您来晚了  下次再来
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE);
        }
        // DB 该计划可投金额
        BigDecimal minInvest = plan.getMinInvestment();// 该计划的最小出借金额
        // 当剩余可投金额小于最低起投金额，不做最低起投金额的限制
        if (minInvest != null && minInvest.compareTo(BigDecimal.ZERO) != 0
                && new BigDecimal(balance).compareTo(minInvest) == -1) {
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
                // 剩余可加入金额为" + balance + "元
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_REMAIN,balance);
            }
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
                // 剩余可加入只剩" + balance + "元，须全部购买"
                //CheckUtil.check();
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_LESS_NEED_BUY_ALL,balance);
            }
        } else {
            if (accountBigDecimal.compareTo(plan.getMinInvestment()) == -1) {
                if (accountBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                    if (cuc != null && cuc.getCouponType() != 3
                            && cuc.getCouponType() != 1) {
                        // plan.getMinInvestment() + "元起投"
                        throw new CheckException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT, plan.getMinInvestment());
                    }
                } else {
                    // plan.getMinInvestment() + "元起投"
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT, plan.getMinInvestment());
                }
            }
        }
        BigDecimal max = plan.getMaxInvestment();
        if (max != null && max.compareTo(BigDecimal.ZERO) != 0
                && accountBigDecimal.compareTo(max) == 1) {
            // 项目最大加入额为" + max + "元
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MAX_INVESTMENT,max);
        }
        if (accountBigDecimal.compareTo(plan.getAvailableInvestAccount()) > 0) {
            // 加入金额不能大于开放额度
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_GREATER_THAN_OPEN_LINE);
        }
        if (tenderAccount.getBankBalance().compareTo(accountBigDecimal) < 0) {
            // 你没钱了
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_NOT_ENOUGH);
        }
        // 调用江西银行接口查询可用金额
        BigDecimal userBankBalance = this.getBankBalancePay(request.getUser().getUserId(),
                OpenAccount.getAccount());
        if (userBankBalance.compareTo(accountBigDecimal) < 0) {
            // 你又没钱了
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_NOT_ENOUGH);
        }
        // redis剩余金额不足判断逻辑
        if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
            // "项目太抢手了！剩余可加入金额只有" + balance + "元"
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_REMAIN,balance);
        }
        // web端有全投
        if(request.getPlatform().equals(ClientConstants.WEB_CLIENT+"")){
            // 出借金额 != 开放额度
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
                logger.info("accountBigDecimal:{}   balance:{} ",accountBigDecimal,balance);
                // 使用递增的逻辑
                if (plan.getInvestmentIncrement() != null
                        && BigDecimal.ZERO.compareTo((accountBigDecimal.subtract(minInvest)).remainder(plan.getInvestmentIncrement())) != 0) {
                    // 加入递增金额须为" + plan.getInvestmentIncrement() + " 元的整数倍
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INTEGER_MULTIPLE,plan.getInvestmentIncrement());
                }
            }
        }else{
            // 开放额度和阀值（1000）判断逻辑
            if (new BigDecimal(balance).compareTo(new BigDecimal(CustomConstants.TENDER_THRESHOLD)) == -1) {
                // 出借金额 != 开放额度
                if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
                    // 使用递增的逻辑
                    if (plan.getInvestmentIncrement() != null
                            && BigDecimal.ZERO.compareTo((accountBigDecimal.subtract(minInvest)).remainder(plan.getInvestmentIncrement())) != 0) {
                        // 加入递增金额须为" + plan.getInvestmentIncrement() + " 元的整数倍
                        throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INTEGER_MULTIPLE,plan.getInvestmentIncrement());
                    }
                }
            } else {
                // (用户出借额度 - 起投额度)%增量 = 0
                if (plan.getInvestmentIncrement() != null
                        && BigDecimal.ZERO.compareTo(accountBigDecimal.subtract(minInvest).remainder(plan.getInvestmentIncrement())) != 0
                        && accountBigDecimal.compareTo(new BigDecimal(balance)) == -1) {
                    // 加入递增金额须为" + plan.getInvestmentIncrement() + " 元的整数倍
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INTEGER_MULTIPLE,plan.getInvestmentIncrement());
                }
            }
        }
        //资金支出校验-智投（userId/出借金额）
        if(!this.capitalExpendituresCheck(request.getUser().getUserId(), accountBigDecimal)){
            throw new CheckException(MsgEnum.ERR_AMT_BANK_BANLANCE_ERR);
        }
    }

    /**
     * @Description 检查用户状态  角色  授权状态 等  是否允许出借
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 11:37
     */
    private void checkUser(UserVO user, UserInfoVO userInfo) {
        if (user == null || userInfo == null) {
            throw new CheckException(MsgEnum.ERR_USER_NOT_EXISTS);
        }
        // 用户未开户
        if (user.getBankOpenAccount() == 0) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 交易密码状态检查
        if (user.getIsSetPassword() == 0) {
            throw new CheckException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }
        // 判断用户是否禁用
        if (user.getStatus() == 1) {// 0启用，1禁用
            throw new CheckException(MsgEnum.ERR_USER_INVALID);
        }
        String roleIsOpen = systemConfig.getRoleIsopen();
        if(StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
            if (userInfo.getRoleId().intValue() != 1) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
            }
        }

        // 自动出借授权
        if (!authService.checkInvesAuthStatus(user.getUserId())) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_AUTO_INVEST);
        }
        // 自动债转授权
        if (!authService.checkCreditAuthStatus(user.getUserId())) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_AUTO_DEBT);
        }
        // 缴费授权
        if (!authService.checkPaymentAuthStatus(user.getUserId())) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_PAYMENT_AUTH);
        }
        // 风险测评校验
        //this.checkEvaluation(user);
    }

}
