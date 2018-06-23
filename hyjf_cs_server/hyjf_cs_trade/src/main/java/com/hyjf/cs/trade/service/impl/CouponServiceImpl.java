/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.CouponRealTenderVO;
import com.hyjf.am.vo.trade.CouponTenderVO;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.calculate.*;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CouponClient;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.CouponService;
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

/**
 * @Description 优惠券投资相关
 * @Author sunss
 * @Date 2018/6/23 11:58
 */
@Service
public class CouponServiceImpl extends BaseTradeServiceImpl implements CouponService {
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    private CouponClient couponClient;

    @Autowired
    private AmUserClient amUserClient;

    /**
     * 加入计划  体验金投资
     *
     * @param request
     * @param plan
     * @param account
     * @param cuc
     * @param tenderAccount
     */
    @Override
    public void couponTender(TenderRequest request, HjhPlanVO plan, BankOpenAccountVO account, CouponUserVO cuc, AccountVO tenderAccount) {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        Integer userId = request.getUser().getUserId();
        String accountStr = request.getAccount();
        Map<String, String> validateMap = this.validateCoupon(userId,accountStr,cuc.getId(),request.getPlatform(),plan);
        if (MapUtils.isEmpty(validateMap)) {
            boolean couponSuccess;
            couponSuccess = this.updateCouponTender(request, plan);
        }else{
            throw new CheckException(MsgEnum.COUPON_TENDER_FAIL_ERROR);
        }
    }

    /**
     * 体验金投资
     * @param request
     * @param plan
     * @return
     */
    private boolean updateCouponTender(TenderRequest request, HjhPlanVO plan) {
        logger.info("优惠券投资开始。。。。。。券编号：" + request.getCouponGrantId());
        // 调用原子层需要
        CouponTenderVO couponTender = new CouponTenderVO();
        Map<String, Object> retMap = new HashedMap();
        int nowTime = GetDate.getNowTime10();
        String borrowStyle = plan.getBorrowStyle();
        Integer userId = request.getUser().getUserId();
        Integer couponGrantId = request.getCouponGrantId();
        CouponUserVO couponUser = couponClient.getCouponUser(couponGrantId, userId);
        //汇计划只支持按天和按月
        if(!borrowStyle.equals("endday")){
            borrowStyle = "end";
        }
        // 优惠券类别
        int couponType = couponUser.getCouponType();
        // 面值
        BigDecimal couponQuota = couponUser.getCouponQuota();
        // 更新时间
        int updateTime = couponUser.getUserUpdateTime();
        // 排他校验
        if (updateTime != request.getCouponOldTime() || couponUser.getUsedFlag() != 0) {
            logger.info("此优惠券已被使用。。。。。。券编号：" + couponGrantId);
            // 优惠券已被使用
            return false;
        }
        // 生成订单id
        String tenderNid = GetOrderIdUtils.getOrderId2(Integer.valueOf(userId));

        // 投资金额
        BigDecimal accountDecimal = null;
        BigDecimal planApr = null;
        if (couponType == 1) {
            // 体验金 投资资金=体验金面值
            accountDecimal = couponQuota;
            planApr = plan.getExpectApr();
        } else if (couponType == 2) {
            // 加息券 投资资金=真实投资资金
            accountDecimal = new BigDecimal(request.getAccount());
            planApr = couponQuota;
        } else if (couponType == 3) {
            // 代金券 投资资金=体验金面值
            accountDecimal = couponQuota;
            planApr = plan.getExpectApr();
        }
        // 优惠券投资额度
        retMap.put("couponAccount", accountDecimal);
        // 优惠券面值
        retMap.put("couponQuota", couponQuota);
        // 优惠券类别
        retMap.put("couponType", couponType);

        BorrowTenderCpnVO borrowTenderCpn = new BorrowTenderCpnVO();
        borrowTenderCpn.setAccount(accountDecimal);
        borrowTenderCpn.setAccountTender(new BigDecimal(0));
        borrowTenderCpn.setActivityFlag(0);//
        borrowTenderCpn.setAddip(request.getIp());
        borrowTenderCpn.setAutoStatus(0);//
        borrowTenderCpn.setBorrowNid(request.getBorrowNid());
        borrowTenderCpn.setChangePeriod(0);//
        borrowTenderCpn.setChangeUserid(0);
        borrowTenderCpn.setClient(0);
        borrowTenderCpn.setContents("");//
        borrowTenderCpn.setFlag(0);//
        borrowTenderCpn.setIsok(0);
        borrowTenderCpn.setIsReport(0);
        borrowTenderCpn.setChangeStatus(0);
        borrowTenderCpn.setNid(tenderNid);
        borrowTenderCpn.setOrderDate(GetDate.getServerDateTime(1, new Date()));
        borrowTenderCpn.setPeriodStatus(0);//
        /*// 预期本息收益
        borrowTenderCpn.setRecoverAccountAll(new BigDecimal(0));
        // 预期利息
        borrowTenderCpn.setRecoverAccountInterest(new BigDecimal(0));*/
        // 已收本息
        borrowTenderCpn.setRecoverAccountYes(new BigDecimal(0));
        // 已收本金
        borrowTenderCpn.setRecoverAccountCapitalYes(new BigDecimal(0));
        // 已收利息
        borrowTenderCpn.setRecoverAccountInterestYes(new BigDecimal(0));

        BigDecimal recoverAccountWait = BigDecimal.ZERO;
        BigDecimal recoverAccountCapitalWait = BigDecimal.ZERO;
        BigDecimal recoverAccountInterestWait = BigDecimal.ZERO;
        BigDecimal couponInterest =BigDecimal.ZERO;
        if(couponUser.getCouponType()==1){
            couponInterest = this.getInterestDj(accountDecimal,couponUser.getCouponProfitTime(),planApr);
        }else{
            couponInterest =this.calculateCouponInterest(borrowStyle, accountDecimal, planApr,
                    plan.getLockPeriod());
        }

        if (couponType == 1) {
            // 体验金 投资
            // 待收本息
            recoverAccountWait = couponInterest;
            // 待收利息
            recoverAccountInterestWait = couponInterest;
        } else if (couponType == 2) {
            // 加息券 投资
            // 待收本息
            recoverAccountWait = couponInterest;
            // 待收利息
            recoverAccountInterestWait = couponInterest;
        } else if (couponType == 3) {
            // 代金券 投资资金=体验金面值
            // 待收本息
            recoverAccountWait = couponQuota.add(couponInterest);
            // 待收本金
            recoverAccountCapitalWait = couponQuota;
            // 待收利息
            recoverAccountInterestWait = couponInterest;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        recoverAccountInterestWait=new BigDecimal(decimalFormat.format(recoverAccountInterestWait));
        retMap.put("couponInterest", recoverAccountInterestWait);
        borrowTenderCpn.setRecoverAdvanceFee(new BigDecimal(0));
        borrowTenderCpn.setRecoverFee(new BigDecimal(0));
        borrowTenderCpn.setRecoverFullStatus(0);
        borrowTenderCpn.setRecoverLateFee(new BigDecimal(0));
        borrowTenderCpn.setRecoverTimes(0);
        borrowTenderCpn.setRecoverType("");
        borrowTenderCpn.setTenderAwardAccount(new BigDecimal(0));
        borrowTenderCpn.setTenderAwardFee(new BigDecimal(0));
        borrowTenderCpn.setTenderNid(request.getBorrowNid());
        borrowTenderCpn.setUserId(userId);
        borrowTenderCpn.setRemark("");
        borrowTenderCpn.setWebStatus(0);
        borrowTenderCpn.setClient(Integer.parseInt(request.getPlatform()));
        // 投资类别：1：直投类，2：汇添金 3：汇计划
        borrowTenderCpn.setTenderType(3);
        // 单笔投资的融资服务费
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
        // 投资状态 0，未放款，1，已放款
        borrowTenderCpn.setTenderStatus(1);
        // 放款状态 0，未放款，1，已放款
        borrowTenderCpn.setApiStatus(0);
        // 写入网站收支明细
        borrowTenderCpn.setWeb(2);
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        //1   borrowTenderCpn表，插入数据
        couponTender.setBorrowTenderCpn(borrowTenderCpn);
        // 优惠券投资表
        CouponTenderVO ct = new CouponTenderVO();
        // 优惠券用户编号
        ct.setCouponGrantId(Integer.valueOf(couponGrantId));
        // 优惠券投资编号
        ct.setOrderId(tenderNid);
        ct.setAddTime(nowTime);
        ct.setAddUser(String.valueOf(userId));
        ct.setUpdateTime(nowTime);
        ct.setUpdateUser(String.valueOf(userId));
        ct.setDelFlag(0);
        ct.setAttribute(userInfo.getAttribute());
        /* 2   ouponTender表，插入数据！*/
        couponTender.setCouponTender(ct);
        // 优惠券投资与真实投资关联表
        CouponRealTenderVO crt = new CouponRealTenderVO();
        // 优惠券投资编号
        crt.setCouponTenderId(tenderNid);
        // 主单投资编号
        crt.setRealTenderId(request.getMainTenderNid());
        crt.setAddTime(new Date());
        crt.setAddUser(String.valueOf(userId));
        crt.setUpdateTime(new Date());
        crt.setUpdateUser(String.valueOf(userId));
        crt.setDelFlag(0);
        //3   插入 优惠券投资与真实投资关联表
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
        boolean  tenderFlag = couponClient.updateCouponTender(couponTender);
        if(request.getMainTenderNid()==null||request.getMainTenderNid().length()==0){
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            // 借款项目编号
            params.put("orderIdCoupon", tenderNid);
            // TODO: 2018/6/23  如果优惠券单独投资的话就调用进入锁定期  PlanCouponServiceImpl 1323行
            //rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_COUPONLOANS_HJH, JSONObject.toJSONString(params));
        }

        return false;
    }

    /**
     * 优惠券投资校验
     * @param userId
     * @param accountStr
     * @param couponGrantId
     * @param platform
     * @param plan
     * @return
     */
    @Override
    public Map<String, String> validateCoupon(Integer userId, String accountStr, Integer couponGrantId, String platform, HjhPlanVO plan) {
        Map<String, String> result = new HashedMap();
        result.put("status","0");
        if(couponGrantId==null||couponGrantId==0){
            result.put("statusDesc","优惠券id为空");
            return result;
        }
        CouponUserVO couponUser = couponClient.getCouponUser(couponGrantId, userId);
        if(couponUser==null){
            result.put("statusDesc","当前优惠券不存在或已使用");
            return result;
        }
        String config = plan.getCouponConfig();
        // 验证项目加息券或体验金是否可用
        if(couponUser.getCouponType() == 1){
            if(config.indexOf("1") == -1){
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        }else if(couponUser.getCouponType() == 2){
            if(config.indexOf("2") == -1){
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        }else if(couponUser.getCouponType() == 3){
            if(config.indexOf("3") == -1){
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        }
        // 取得优惠券配置
        if (couponUser.getUsedFlag() != 0) {
            result.put("statusDesc","您选择的券已经使用，请重新选择优惠券！");
            return result;
        }
        // 加息券不能单独投资
        if ((StringUtils.isEmpty(accountStr) || StringUtils.equals(accountStr, "0")) && couponUser.getCouponType() == 2) {
            result.put("statusDesc","加息券投资，真实投资金额不能为空！");
            return result;
        }
        // 平台
        if (!StringUtils.contains(couponUser.getCouponSystem(), platform)) {
            result.put("statusDesc","对不起，当前平台不能使用此优惠券！");
            return result;
        }
        // 项目类型
        if (couponUser.getProjectType().indexOf("6")==-1) {
            result.put("statusDesc","对不起，您选择的优惠券不能用于当前类别标的！");
            return result;
        }
        // 项目金额
        // 金额类别
        int tenderQuotaType = couponUser.getTenderQuotaType();
        // 投资金额
        BigDecimal tenderAccount = new BigDecimal(accountStr);
        // 金额范围
        if (tenderQuotaType == 1) {
            // 投资金额最小额度
            BigDecimal tenderQuotaMin = new BigDecimal(couponUser.getTenderQuotaMin());
            // 投资金额最大额度
            BigDecimal tenderQuotaMax = new BigDecimal(couponUser.getTenderQuotaMax());
            // 比较投资金额（-1表示小于,0是等于,1是大于）
            int minCheck = tenderAccount.compareTo(tenderQuotaMin);
            int maxCheck = tenderAccount.compareTo(tenderQuotaMax);
            if (minCheck == -1 || maxCheck == 1) {
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (tenderQuotaType == 2) {
            // 大于等于
            BigDecimal tenderQuota = new BigDecimal(couponUser.getTenderQuota());
            // 比较投资金额（-1表示小于,0是等于,1是大于）
            int chkFlg = tenderAccount.compareTo(tenderQuota);
            if (chkFlg == -1) {
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        }
        // 计划期限
        int planPeriod = plan.getLockPeriod();
        int couponExType = couponUser.getProjectExpirationType();
        int expirationLength =
                couponUser.getProjectExpirationLength() == null ? 0 : couponUser.getProjectExpirationLength();
        int expirationMin =
                couponUser.getProjectExpirationLengthMin() == null ? 0 : couponUser.getProjectExpirationLengthMin();
        int expirationMax =
                couponUser.getProjectExpirationLengthMax() == null ? 0 : couponUser.getProjectExpirationLengthMax();

        // 等于
        if (couponExType == 1) {
            if (expirationLength != planPeriod) {
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (couponExType == 2) {
            // 期限范围
            if (planPeriod < expirationMin || planPeriod > expirationMax) {
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (couponExType == 3) {
            // 大于等于
            if (planPeriod < expirationLength) {
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        } else if (couponExType == 4) {
            // 小于等于
            if (planPeriod > expirationLength) {
                result.put("statusDesc","您选择的优惠券不满足使用条件，请核对后重新选择！");
                return result;
            }
        }
        // 截止时间
        // yyyy-MM-dd 的时间戳
        int nowTime = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf));
        if (couponUser.getEndTime() < nowTime) {
            result.put("statusDesc","当前优惠券无法使用，优惠券已过期！");
            return result;
        }
        // 优惠券不能和本金公用
        if (couponUser.getAddFlg()==1&&!"0".equals(accountStr)) {
            result.put("statusDesc","当前优惠券不能与本金共用！");
            return result;
        }
        return result;
    }

    private BigDecimal getInterestDj(BigDecimal couponQuota, Integer couponProfitTime, BigDecimal borrowApr){
        BigDecimal earnings = new BigDecimal("0");

        earnings =couponQuota.multiply(borrowApr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP))
                .divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(couponProfitTime))
                .setScale(2, BigDecimal.ROUND_DOWN);

        return earnings;

    }

    /*
     * 计算得到优惠券预期收益
     * @param borrowStyle
     * @param couponAccount
     * @param couponRate
     * @param borrowPeriod
     * @return
     */
    private BigDecimal calculateCouponInterest(String borrowStyle,BigDecimal couponAccount,BigDecimal couponRate,Integer borrowPeriod){
        BigDecimal earnings = BigDecimal.ZERO;
        switch (borrowStyle) {
            case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“：预期收益=投资金额*年化收益÷12*月数；
                // 计算预期收益
                earnings = DuePrincipalAndInterestUtils
                        .getMonthInterest(couponAccount, couponRate, borrowPeriod)
                        .divide(new BigDecimal("100"));
                break;
            case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“：预期收益=投资金额*年化收益÷360*天数；
                earnings = DuePrincipalAndInterestUtils
                        .getDayInterest(couponAccount, couponRate, borrowPeriod)
                        .divide(new BigDecimal("100"));
                break;
            case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：预期收益=投资金额*年化收益÷12*月数；
                earnings = BeforeInterestAfterPrincipalUtils
                        .getInterestCount(couponAccount, couponRate, borrowPeriod, borrowPeriod)
                        .divide(new BigDecimal("100"));
                break;
            case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：预期收益=投资金额*年化收益÷12*月数；
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


}
