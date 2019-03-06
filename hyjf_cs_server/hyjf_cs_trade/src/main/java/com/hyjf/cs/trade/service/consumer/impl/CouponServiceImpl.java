/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.*;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.calculate.*;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.consumer.CouponService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 优惠券出借相关
 * @Author sunss
 * @Date 2018/6/23 11:58
 */
@Service
public class CouponServiceImpl extends BaseTradeServiceImpl implements CouponService {
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 加入计划  体验金出借
     *
     * @param request
     * @param plan
     * @param cuc
     */
    @Override
    public void couponTender(TenderRequest request, HjhPlanVO plan, CouponUserVO cuc, Integer userId) {
        CouponTenderUsedVO couponTender = new CouponTenderUsedVO();
        couponTender.setAccount(request.getAccount());
        couponTender.setBorrowNid(plan.getPlanNid());
        couponTender.setBorrowStyle(plan.getBorrowStyle());
        couponTender.setCouponGrantId(cuc.getId());
        couponTender.setExpectApr(plan.getExpectApr());
        couponTender.setIp(request.getIp());
        couponTender.setMainTenderNid(request.getMainTenderNid());
        couponTender.setPeriod(plan.getLockPeriod());
        couponTender.setPlatform(Integer.parseInt(request.getPlatform()));
        couponTender.setTenderType(CustomConstants.COUPON_TENDER_TYPE_HJH);
        couponTender.setUserId(userId);
        boolean couponSuccess = this.updateCouponTender(couponTender);
        request.setCouponInterest(couponTender.getCouponInterest());
    }

    /**
     * 优惠券出借
     *
     * @param bean
     * @return
     */
    private boolean updateCouponTender(CouponTenderUsedVO bean) {
        Integer couponGrantId = bean.getCouponGrantId();
        Integer userId = bean.getUserId();
        String borrowStyle = bean.getBorrowStyle();
        logger.info("散标优惠券出借组装调用原子层参数开始。。。。。。券编号：" + couponGrantId);
        // 调用原子层需要
        CouponTenderVO couponTender = new CouponTenderVO();
        int nowTime = GetDate.getNowTime10();
        CouponUserVO couponUser = amTradeClient.getCouponUser(couponGrantId, userId);
        CouponConfigVO configVO = amTradeClient.selectCouponConfig(couponUser.getCouponCode());
        //汇计划只支持按天和按月
        if (CustomConstants.COUPON_TENDER_TYPE_HJH.equals(bean.getTenderType())) {
            if (!"endday".equals(borrowStyle)) {
                borrowStyle = "end";
            }
        }
        // 优惠券类别
        int couponType = configVO.getCouponType();
        // 面值
        BigDecimal couponQuota = configVO.getCouponQuota();
        // 排他校验
        if (couponUser.getUsedFlag() != 0) {
            logger.info("此优惠券已被使用。。。。。。券编号：" + couponGrantId);
            // 优惠券已被使用
            return false;
        }
        // 生成订单id
        String tenderNid = GetOrderIdUtils.getOrderId2(Integer.valueOf(userId));
        // 出借金额
        BigDecimal accountDecimal = null;
        BigDecimal planApr = bean.getExpectApr();
        if (couponType == 1) {
            // 体验金 出借资金=体验金面值
            accountDecimal = couponQuota;
        } else if (couponType == 2) {
            // 加息券 出借资金=真实出借资金
            accountDecimal = new BigDecimal(bean.getAccount());
            planApr = couponQuota;
        } else if (couponType == 3) {
            // 代金券 出借资金=体验金面值
            accountDecimal = couponQuota;
        }
        BorrowTenderCpnVO borrowTenderCpn = new BorrowTenderCpnVO();
        borrowTenderCpn.setAccount(accountDecimal);
        borrowTenderCpn.setAccountTender(new BigDecimal(0));
        borrowTenderCpn.setActivityFlag(0);
        borrowTenderCpn.setAddip(bean.getIp());
        borrowTenderCpn.setAutoStatus(0);
        borrowTenderCpn.setBorrowNid(bean.getBorrowNid());
        borrowTenderCpn.setChangePeriod(0);
        borrowTenderCpn.setChangeUserid(0);
        borrowTenderCpn.setClient(0);
        borrowTenderCpn.setContents("");
        borrowTenderCpn.setFlag(0);
        borrowTenderCpn.setIsok(0);
        borrowTenderCpn.setIsReport(0);
        borrowTenderCpn.setChangeStatus(0);
        borrowTenderCpn.setNid(tenderNid);
        borrowTenderCpn.setOrderDate(GetDate.getServerDateTime(1, new Date()));
        borrowTenderCpn.setPeriodStatus(0);
        /*// 预期本息收益*/
        borrowTenderCpn.setRecoverAccountAll(new BigDecimal(0));
        // 预期利息
        borrowTenderCpn.setRecoverAccountInterest(new BigDecimal(0));
        // 已收本息
        borrowTenderCpn.setRecoverAccountYes(new BigDecimal(0));
        // 已收本金
        borrowTenderCpn.setRecoverAccountCapitalYes(new BigDecimal(0));
        // 已收利息
        borrowTenderCpn.setRecoverAccountInterestYes(new BigDecimal(0));

        BigDecimal recoverAccountWait = BigDecimal.ZERO;
        BigDecimal recoverAccountCapitalWait = BigDecimal.ZERO;
        BigDecimal recoverAccountInterestWait = BigDecimal.ZERO;
        BigDecimal couponInterest = BigDecimal.ZERO;
        if (configVO.getCouponType() == 1) {
            couponInterest = this.getInterestDj(accountDecimal, configVO.getCouponProfitTime().intValue(), planApr);
        } else {
            couponInterest = this.calculateCouponInterest(borrowStyle, accountDecimal, planApr,
                    bean.getPeriod());
        }

        if (couponType == 1) {
            // 体验金 出借
            // 待收本息
            recoverAccountWait = couponInterest;
            // 待收利息
            recoverAccountInterestWait = couponInterest;
        } else if (couponType == 2) {
            // 加息券 出借
            // 待收本息
            recoverAccountWait = couponInterest;
            // 待收利息
            recoverAccountInterestWait = couponInterest;
        } else if (couponType == 3) {
            // 代金券 出借资金=体验金面值
            // 待收本息
            recoverAccountWait = couponQuota.add(couponInterest);
            // 待收本金
            recoverAccountCapitalWait = couponQuota;
            // 待收利息
            recoverAccountInterestWait = couponInterest;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        recoverAccountInterestWait = new BigDecimal(decimalFormat.format(recoverAccountInterestWait));
        borrowTenderCpn.setRecoverAdvanceFee(new BigDecimal(0));
        borrowTenderCpn.setRecoverFee(new BigDecimal(0));
        borrowTenderCpn.setRecoverFullStatus(0);
        borrowTenderCpn.setRecoverLateFee(new BigDecimal(0));
        borrowTenderCpn.setRecoverTimes(0);
        borrowTenderCpn.setRecoverType("");
        borrowTenderCpn.setTenderAwardAccount(new BigDecimal(0));
        borrowTenderCpn.setTenderAwardFee(new BigDecimal(0));
        borrowTenderCpn.setTenderNid(bean.getBorrowNid());
        borrowTenderCpn.setUserId(userId);
        borrowTenderCpn.setRemark("");
        borrowTenderCpn.setWebStatus(0);
        borrowTenderCpn.setTenderUserName(bean.getUserName());
        borrowTenderCpn.setClient(bean.getPlatform());
        // 出借类别：1：直投类，2：汇添金 3：汇计划
        borrowTenderCpn.setTenderType(bean.getTenderType());
        // 单笔出借的融资服务费
        borrowTenderCpn.setLoanFee(new BigDecimal("0.00"));
        String remark = StringUtils.EMPTY;
        if (couponType == 1) {
            remark = "体验金<br />编号：" + couponUser.getCouponUserCode();
        } else if (couponType == 2) {
            remark = "加息券<br />编号：" + couponUser.getCouponUserCode();
        } else if (couponType == 3) {
            remark = "代金券<br />编号：" + couponUser.getCouponUserCode();
        }
        borrowTenderCpn.setRemark(remark);
        borrowTenderCpn.setLoanAmount(new BigDecimal("0.00"));
        // 待收本息
        borrowTenderCpn.setRecoverAccountWait(recoverAccountWait);
        // 预期本息收益
        borrowTenderCpn.setRecoverAccountAll(recoverAccountWait);
        // 待收利息
        borrowTenderCpn.setRecoverAccountInterestWait(recoverAccountInterestWait);
        // 预期利息
        borrowTenderCpn.setRecoverAccountInterest(recoverAccountInterestWait);
        // 待收本金
        borrowTenderCpn.setRecoverAccountCapitalWait(recoverAccountCapitalWait);

        // 状态 0，未放款，1，已放款
        borrowTenderCpn.setStatus(1);
        // 出借状态 0，未放款，1，已放款
        borrowTenderCpn.setTenderStatus(1);
        // 放款状态 0，未放款，1，已放款
        borrowTenderCpn.setApiStatus(0);
        // 写入网站收支明细
        borrowTenderCpn.setWeb(2);
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        //1   borrowTenderCpn表，插入数据
        couponTender.setBorrowTenderCpn(borrowTenderCpn);
        // 优惠券出借表
        CouponTenderVO ct = new CouponTenderVO();
        // 优惠券用户编号
        ct.setCouponGrantId(Integer.valueOf(couponGrantId));
        // 优惠券出借编号
        ct.setOrderId(tenderNid);
        ct.setAddTime(nowTime);
        ct.setAddUser(String.valueOf(userId));
        ct.setUpdateTime(nowTime);
        ct.setUpdateUser(String.valueOf(userId));
        ct.setDelFlag(0);
        ct.setAttribute(userInfo.getAttribute());
        /* 2   ouponTender表，插入数据！*/
        couponTender.setCouponTender(ct);
        // 优惠券出借与真实出借关联表
        CouponRealTenderVO crt = new CouponRealTenderVO();
        // 优惠券出借编号
        crt.setCouponTenderId(tenderNid);
        // 主单出借编号
        crt.setRealTenderId(bean.getMainTenderNid());
        crt.setAddTime(new Date());
        crt.setAddUser(String.valueOf(userId));
        crt.setUpdateTime(new Date());
        crt.setUpdateUser(String.valueOf(userId));
        crt.setDelFlag(0);
        //3   插入 优惠券出借与真实出借关联表
        couponTender.setCouponRealTender(crt);
        // 优惠券用户表状态
        CouponUserVO cu = new CouponUserVO();
        cu.setId(Integer.valueOf(couponGrantId));
        cu.setUpdateTime(nowTime);
        cu.setUpdateUser(String.valueOf(userId));
        // 状态更新为 1:已使用
        cu.setUsedFlag(1);
        // 4   修改优惠券用户表状态
        couponTender.setCouponUser(cu);
        logger.info("散标优惠券出借  请求原子层   参数为: {} ",JSONObject.toJSONString(couponTender));
        boolean tenderFlag = amTradeClient.updateCouponTender(couponTender);
        logger.info("散标优惠券出借  请求原子层   结果为: {} ",tenderFlag);
       if (bean.getMainTenderNid() == null || bean.getMainTenderNid().length() == 0) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            // 借款项目编号
            params.put("orderIdCoupon", tenderNid);
            // 如果是计划类的
            if (CustomConstants.COUPON_TENDER_TYPE_HJH.equals(bean.getTenderType())) {
                try{
                    commonProducer.messageSendDelay(new MessageContent(MQConstant.HJH_COUPON_LOAN_TOPIC, UUID.randomUUID().toString(), params),2);
                }catch (Exception e){
                    logger.error("优惠券放款失败  {} ",JSONObject.toJSONString(params));
                    logger.error(e.getMessage());
                }
            }
        }
        // 设置优惠券的预期收益
        bean.setCouponInterest(recoverAccountInterestWait);
        return true;
    }

    /**
     * 优惠券出借校验
     * @param userId
     * @param accountStr
     * @param couponGrantId
     * @param platform
     * @param period
     * @param config
     * @param nowType  -1    所有散标/新手/智投项目   1  散标    3  新手   6  智投
     * @return
     */
    @Override
    public Map<String, String> validateCoupon(Integer userId, String accountStr, Integer couponGrantId, String platform, Integer period, String config,String nowType) {
        logger.info("开始调用优惠券出借校验  {}   {}   {}   {}",couponGrantId ,userId ,period,config);
        Map<String, String> result = new HashedMap();
        result.put("status", "0");
        if (couponGrantId == null || couponGrantId == 0) {
            result.put("statusDesc", "优惠券id为空");
            return result;
        }
        CouponUserVO couponUser = amTradeClient.getCouponUser(couponGrantId, userId);
        CouponConfigVO configVO = amTradeClient.selectCouponConfig(couponUser.getCouponCode());
        logger.info("configVO:"+JSONObject.toJSONString(configVO));
        if (couponUser == null) {
            result.put("statusDesc", "当前优惠券不存在或已使用");
            return result;
        }
        // 验证项目加息券或体验金是否可用    1：体验金，2：加息券 3代金券
        if (configVO.getCouponType() == 1) {
            if (config.indexOf("1") == -1) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (configVO.getCouponType() == 2) {
            if (config.indexOf("2") == -1) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (configVO.getCouponType() == 3) {
            /*if (config.indexOf("3") == -1) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }*/
        }
        // 取得优惠券配置
        if (couponUser.getUsedFlag() != 0) {
            result.put("statusDesc", "您选择的券已经使用，请重新选择优惠券！");
            return result;
        }
        // 加息券不能单独出借
        if ((StringUtils.isEmpty(accountStr) || StringUtils.equals(accountStr, "0")) && configVO.getCouponType() == 2) {
            result.put("statusDesc", "加息券出借，真实出借金额不能为空！");
            return result;
        }
        // 平台
        if (!StringUtils.contains(configVO.getCouponSystem(), platform)) {
            result.put("statusDesc", "对不起，当前平台不能使用此优惠券！");
            return result;
        }
        // 项目类型
        if (configVO.getProjectType().indexOf("-1") == -1&&configVO.getProjectType().indexOf(nowType) == -1) {
            result.put("statusDesc", "对不起，您选择的优惠券不能用于当前类别标的！");
            return result;
        }
        // 项目金额
        // 金额类别
        int tenderQuotaType = configVO.getTenderQuotaType();
        // 出借金额
        BigDecimal tenderAccount = new BigDecimal(accountStr);
        // 金额范围
        if (tenderQuotaType == 1) {
            // 出借金额最小额度
            BigDecimal tenderQuotaMin = new BigDecimal(configVO.getTenderQuotaMin());
            // 出借金额最大额度
            BigDecimal tenderQuotaMax = new BigDecimal(configVO.getTenderQuotaMax());
            // 比较出借金额（-1表示小于,0是等于,1是大于）
            int minCheck = tenderAccount.compareTo(tenderQuotaMin);
            int maxCheck = tenderAccount.compareTo(tenderQuotaMax);
            if (minCheck == -1 || maxCheck == 1) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (tenderQuotaType == 2) {
            // 大于等于
            BigDecimal tenderQuota = new BigDecimal(configVO.getTenderQuota());
            // 比较出借金额（-1表示小于,0是等于,1是大于）
            int chkFlg = tenderAccount.compareTo(tenderQuota);
            if (chkFlg == -1) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        }
        // 期限
        int planPeriod = period;
        int couponExType = configVO.getProjectExpirationType();
        int expirationLength =
                configVO.getProjectExpirationLength() == null ? 0 : configVO.getProjectExpirationLength();
        int expirationMin =
                configVO.getProjectExpirationLengthMin() == null ? 0 : configVO.getProjectExpirationLengthMin();
        int expirationMax =
                configVO.getProjectExpirationLengthMax() == null ? 0 : configVO.getProjectExpirationLengthMax();

        // 等于
        if (couponExType == 1) {
            if (expirationLength != planPeriod) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (couponExType == 2) {
            // 期限范围
            if (planPeriod < expirationMin || planPeriod > expirationMax) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (couponExType == 3) {
            // 大于等于
            if (planPeriod < expirationLength) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (couponExType == 4) {
            // 小于等于
            if (planPeriod > expirationLength) {
                result.put("statusDesc", "您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        }
        // 截止时间
        // yyyy-MM-dd 的时间戳
        int nowTime = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf));
        if (couponUser.getEndTime() < nowTime) {
            result.put("statusDesc", "当前优惠券无法使用，优惠券已过期！");
            return result;
        }
        // 优惠券不能和本金公用
        if (configVO.getAddFlag()!=null&&configVO.getAddFlag() == 1 && !"0".equals(accountStr)) {
            result.put("statusDesc", "当前优惠券不能与本金共用！");
            return result;
        }
        return null;
    }

    /**
     * 散标出借优惠券使用
     *
     * @param couponGrantId
     * @param borrow
     * @param bean
     */
    @Override
    public void borrowTenderCouponUse(String couponGrantId, BorrowAndInfoVO borrow, BankCallBean bean, BorrowInfoVO borrowInfoVO,String nowType) {
        boolean isUsed = RedisUtils.tranactionSet(RedisConstants.COUPON_TENDER_KEY+couponGrantId, 300);
        if (!isUsed) {
            logger.error("当前优惠券正在使用....");
            return;
        }
        // 金额
        String account = bean.getTxAmount();
        String borrowNid = borrow.getBorrowNid();
        String ordId = bean.getLogOrderId();
        Integer userId = Integer.parseInt(bean.getLogUserId());
        Integer platform = bean.getLogClient();
        String config = "";
        // 加息券标识（0：禁用，1：可用）    1：体验金，2：加息券 3代金券
        int interestCoupon = borrowInfoVO.getBorrowInterestCoupon();
        // 体验金标识（0：禁用，1：可用）
        int moneyCoupon = borrowInfoVO.getBorrowTasteMoney();
        if (interestCoupon == 1) {
            config += "2,";
        }
        if (moneyCoupon == 1) {
            config += "1,";
        }
        Map<String, String> validateMap = this.validateCoupon(userId, account, Integer.parseInt(couponGrantId), platform + "", borrow.getBorrowPeriod(), config,nowType);
        logger.info("优惠券出借校验完毕  结果：{}" , validateMap);
        if (MapUtils.isEmpty(validateMap)) {
            CouponTenderUsedVO couponTender = new CouponTenderUsedVO();
            couponTender.setAccount(account);
            couponTender.setBorrowNid(borrowNid);
            couponTender.setBorrowStyle(borrow.getBorrowStyle());
            couponTender.setCouponGrantId(Integer.parseInt(couponGrantId));
            couponTender.setExpectApr(borrow.getBorrowApr());
            couponTender.setIp(bean.getLogIp());
            couponTender.setMainTenderNid(ordId);
            couponTender.setPeriod(borrow.getBorrowPeriod());
            couponTender.setPlatform(bean.getLogClient());
            couponTender.setTenderType(CustomConstants.COUPON_TENDER_TYPE_HZT);
            couponTender.setUserId(userId);
            couponTender.setUserName(bean.getLogUserName());
            // 开始使用优惠券出借
            this.updateCouponTender(couponTender);
        }
    }

    /**
     * 计算预期收益
     *
     * @param couponQuota
     * @param couponProfitTime
     * @param borrowApr
     * @return
     */
    @Override
    public BigDecimal getInterestDj(BigDecimal couponQuota, Integer couponProfitTime, BigDecimal borrowApr) {
        BigDecimal earnings = new BigDecimal("0");

        earnings = couponQuota.multiply(borrowApr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP))
                .divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(couponProfitTime))
                .setScale(2, BigDecimal.ROUND_DOWN);

        return earnings;

    }

    /**
     * 计算得到优惠券预期收益
     * @param borrowStyle
     * @param couponAccount
     * @param couponRate
     * @param borrowPeriod
     * @return
     */
    @Override
    public BigDecimal calculateCouponInterest(String borrowStyle,BigDecimal couponAccount,BigDecimal couponRate,Integer borrowPeriod){
        BigDecimal earnings = BigDecimal.ZERO;
        switch (borrowStyle) {
            case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“：预期收益=出借金额*年化收益÷12*月数；
                // 计算预期收益
                earnings = DuePrincipalAndInterestUtils
                        .getMonthInterest(couponAccount, couponRate, borrowPeriod)
                        .divide(new BigDecimal("100"));
                break;
            case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“：预期收益=出借金额*年化收益÷360*天数；
                earnings = DuePrincipalAndInterestUtils
                        .getDayInterest(couponAccount, couponRate, borrowPeriod)
                        .divide(new BigDecimal("100"));
                break;
            case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：预期收益=出借金额*年化收益÷12*月数；
                earnings = BeforeInterestAfterPrincipalUtils
                        .getInterestCount(couponAccount, couponRate, borrowPeriod, borrowPeriod)
                        .divide(new BigDecimal("100"));
                break;
            case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：预期收益=出借金额*年化收益÷12*月数；
                earnings = AverageCapitalPlusInterestUtils
                        .getInterestCount(couponAccount, couponRate, borrowPeriod)
                        .divide(new BigDecimal("100"));
                break;
            case CalculatesUtil.STYLE_PRINCIPAL:// 还款方式为”等额本金“

                earnings = AverageCapitalUtils
                        .getInterestCount(couponAccount, couponRate, borrowPeriod)
                        .divide(new BigDecimal("100"));
                break;
            default:
                break;
        }
        return earnings;
    }

    /**
     * 获取预期收益
     *
     * @param borrowStyle
     * @param couponType
     * @param borrowApr
     * @param couponQuota
     * @param money
     * @param borrowPeriod
     * @return
     */
    @Override
    public BigDecimal getInterest(String borrowStyle, Integer couponType, BigDecimal borrowApr, BigDecimal couponQuota, String money, Integer borrowPeriod) {
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
            // 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
            case CalculatesUtil.STYLE_END:
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getMonthInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            // 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷360*天数；
            case CalculatesUtil.STYLE_ENDDAY:
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getDayInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            // 还款方式为”先息后本“：历史回报=出借金额*年化收益÷12*月数；
            case CalculatesUtil.STYLE_ENDMONTH:
                // 计算历史回报
                earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            // 还款方式为”等额本息“：历史回报=出借金额*年化收益÷12*月数；
            case CalculatesUtil.STYLE_MONTH:
                // 计算历史回报
                earnings = AverageCapitalPlusInterestUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            // 还款方式为”等额本金“
            case CalculatesUtil.STYLE_PRINCIPAL:
                earnings = AverageCapitalUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            default:
                break;
        }
        if (couponType == 3) {
            earnings = earnings.add(couponQuota);
        }
        return earnings;
    }


}
