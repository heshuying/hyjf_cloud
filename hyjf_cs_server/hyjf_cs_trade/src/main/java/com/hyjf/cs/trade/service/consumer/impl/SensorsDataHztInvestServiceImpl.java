/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponRealTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserDepartmentInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.InterestInfo;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.consumer.SensorsDataHztInvestService;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 神策数据统计:散标出借相关Service实现类
 *
 * @author liuyang
 * @version SensorsDataHztInvestServiceImpl, v0.1 2018/10/23 16:23
 */
@Service
public class SensorsDataHztInvestServiceImpl extends BaseServiceImpl implements SensorsDataHztInvestService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 发送神策数据统计
     *
     * @param sensorsDataBean
     * @throws IOException
     * @throws InvalidArgumentException
     */
    @Override
    public void sendSensorsData(SensorsDataBean sensorsDataBean) throws IOException, InvalidArgumentException {
        // log文件存放位置
        String logFilePath = systemConfig.getSensorsDataLogPath();
        // 初始化神策SDK
        SensorsAnalytics sa = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer(logFilePath + "sensorsData.log"));

        // 出借订单号
        String orderId = sensorsDataBean.getOrderId();
        if (StringUtils.isBlank(orderId)) {
            logger.error("出借订单号为空");
            return;
        }
        // 根据出借订单号查询用户出借记录
        BorrowTenderVO borrowTender = this.amTradeClient.selectBorrowTenderByOrderId(orderId);

        if (borrowTender == null) {
            logger.info("根据出借订单号查询用户出借记录失败,出借订单号:[" + orderId + "].");
            return;
        }
        // 标的编号
        String borrowNid = borrowTender.getBorrowNid();
        // 根据标的编号查询借款信息
        RightBorrowVO borrow = this.amTradeClient.getRightBorrowByNid(borrowNid);
        if (borrow == null) {
            logger.error("根据标的编号查询标的信息失败,标的编号:[" + borrowNid + "].");
            return;
        }
        // 根据标的编号查询标的详细信息
        BorrowInfoVO borrowInfoVO = this.amTradeClient.selectBorrowInfoWithBLOBSVOByBorrowId(borrowNid);
        if (borrowInfoVO == null) {
            logger.error("根据标的编号查询标的详情信息失败,标的编号:[" + borrowNid + "].");
            return;
        }
        // 出借用户ID
        Integer userId = borrowTender.getUserId();
        // 事件属性
        Map<String, Object> properties = new HashMap<String, Object>();
        // 订单号
        properties.put("order_id", orderId);
        // 项目类型:新手汇
        if (borrow.getProjectType() == 4) {
            properties.put("project_tag", "新手专享");
        } else {
            properties.put("project_tag", "普通标");
        }
        // 项目编号
        properties.put("project_id", borrowNid);
        // 项目名称
        properties.put("project_name", borrowInfoVO.getProjectName());
        // 项目期限
        properties.put("project_duration", borrow.getBorrowPeriod());
        // 项目期限单位
        if ("endday".equals(borrow.getBorrowStyle())) {
            properties.put("duration_unit", "天");
        } else {
            properties.put("duration_unit", "月");
        }
        // 开标时间
        if (borrow.getOntime() != 0) {
            properties.put("begin_time", GetDate.getDateTimeMyTime(borrow.getOntime()));
        } else {
            properties.put("begin_time", GetDate.getDateTimeMyTime(borrow.getVerifyTime()));
        }

        // 平台类型
        if (borrowTender.getClient() == 0) {
            properties.put("PlatformType", "PC");
        } else if (borrowTender.getClient() == 1) {
            properties.put("PlatformType", "wechat");
        } else if (borrowTender.getClient() == 2) {
            properties.put("PlatformType", "Android");
        } else if (borrowTender.getClient() == 3) {
            properties.put("PlatformType", "iOS");
        }
        // 计算利息相关
        // 借款成功时间
        Integer borrowSuccessTime = borrow.getReverifyTime();
        // 利息
        BigDecimal interestTender = BigDecimal.ZERO;

        // 是否月标(true:月标, false:天标)
        boolean isMonthFlag = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle());
        // 当前时间
        Integer nowTime = GetDate.getNowTime10();
        // 月利率(算出管理费用[上限])
        BigDecimal borrowMonthRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 月利率(算出管理费用[下限])
        BigDecimal borrowManagerScaleEnd = Validator.isNull(borrowInfoVO.getBorrowManagerScaleEnd()) ? BigDecimal.ZERO : new BigDecimal(borrowInfoVO.getBorrowManagerScaleEnd());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 计算利息
        InterestInfo interestInfo = CalculatesUtil.getInterestInfo(borrowTender.getAccount(), borrow.getBorrowPeriod(), borrow.getBorrowApr(), borrow.getBorrowStyle(), nowTime, borrowMonthRate, borrowManagerScaleEnd,
                borrow.getProjectType(), differentialRate, borrowVerifyTime);
        // 历史年回报率
        properties.put("project_apr", borrow.getBorrowApr().divide(new BigDecimal("100")));
        // 项目金额
        properties.put("project_money", borrow.getAccount());
        // 查询还款方式
        BorrowStyleVO borrowStyle = this.amTradeClient.getBorrowStyle(borrow.getBorrowStyle());
        // 还款方式
        properties.put("project_repayment_type", borrowStyle.getName());
        // 出借金额
        properties.put("tender_amount", borrowTender.getAccount());
        // 项目剩余投金额
        properties.put("project_rest_amount", borrow.getBorrowAccountWait());
        // 历史回报金额
        properties.put("expect_income", interestInfo.getRepayAccountInterest());
        // 实际支付金额
        properties.put("pay_amount", borrowTender.getAccount());
        // 支付方式
        properties.put("pay_method", "余额支付");

        // 获取出借时推荐人部门等信息
        // 根据用户ID 查询用户推荐人信息
        SpreadsUserVO spreadsUsers = this.amUserClient.querySpreadsUsersByUserId(userId);
        // 用户没有推荐人
        if (spreadsUsers == null) {
            // 注册时邀请人
            properties.put("inviter", "");
        } else {
            // 推荐人用户ID
            Integer spreadsUserId = spreadsUsers.getSpreadsUserId();
            // 推荐人用户名
            UserVO spreadsUser = this.amUserClient.findUserById(spreadsUserId);
            // 注册时邀请人
            properties.put("inviter", spreadsUser == null ? "" : spreadsUser.getUsername());
        }
        // 用户信息
        UserInfoVO usersInfo = this.amUserClient.findUsersInfoById(userId);
        // 用户属性
        if (usersInfo.getAttribute() == 0) {
            // 当前用户属性
            properties.put("attribute", "无主单");
        } else if (usersInfo.getAttribute() == 1) {
            // 当前用户属性
            properties.put("attribute", "有主单");
        } else if (usersInfo.getAttribute() == 2) {
            // 当前用户属性
            properties.put("attribute", "线下员工");
        } else if (usersInfo.getAttribute() == 3) {
            // 当前用户属性
            properties.put("attribute", "线上员工");
        }

        // 根据用户ID 查询用户部门信息
        UserDepartmentInfoCustomizeVO userDepartmentInfoCustomize = this.amUserClient.queryUserDepartmentInfoByUserId(userId);
        if (userDepartmentInfoCustomize == null) {
            // 分公司
            properties.put("regionName", "");
            // 分部
            properties.put("branchName", "");
            // 团队
            properties.put("departmentName", "");
        } else {
            // 分公司
            properties.put("regionName", StringUtils.isBlank(userDepartmentInfoCustomize.getRegionName()) ? "" : userDepartmentInfoCustomize.getRegionName());
            // 分部
            properties.put("branchName", StringUtils.isBlank(userDepartmentInfoCustomize.getBranchName()) ? "" : userDepartmentInfoCustomize.getBranchName());
            // 团队
            properties.put("departmentName", StringUtils.isBlank(userDepartmentInfoCustomize.getDepartmentName()) ? "" : userDepartmentInfoCustomize.getDepartmentName());
        }

        // 根据加入订单号查询此笔加入是否使用优惠券
        CouponRealTenderVO couponRealTender = this.amTradeClient.selectCouponRealTenderByOrderId(orderId);

        if (couponRealTender != null) {
            // 使用优惠券
            properties.put("use_coupon", true);
            // 优惠券出借ID
            String couponTenderId = couponRealTender.getCouponTenderId();
            logger.info("此笔出借使用了优惠券:优惠券出借ID:[" + couponTenderId + "],出借订单号:[" + orderId + "].");
//            // 根据优惠券出借ID检索优惠券放款信息
//            CouponRecover couponRecover = this.selectCouponRecoverByCouponTenderId(couponTenderId);
//            if (couponRecover == null) {
//                _log.error("根据优惠券出借ID查询优惠券放款信息失败,优惠券出借ID:[" + couponTenderId + "].");
//                return;
//            }
            // 根据优惠券出借ID查询优惠券出借
            CouponTenderVO couponTender = this.amTradeClient.selectCouponTenderByCouponTenderId(couponTenderId);
            if (couponTender == null) {
                logger.error("根据优惠券出借ID获取优惠券出借情况失败,优惠券出借ID:[" + couponTenderId + "].");
                return;
            }
            // 优惠券ID
            Integer couponGrantId = couponTender.getCouponGrantId();
            logger.info("优惠券ID:" + couponGrantId);
            // 根据优惠券ID查询优惠券信息
            CouponUserVO couponUser = this.amTradeClient.selectCouponUserById(couponGrantId);
            if (couponUser == null) {
                logger.error("根据优惠券ID查询优惠券信息失败,优惠券ID:[" + couponGrantId + "]");
                return;
            }
            // 优惠券编号
            String couponCode = couponUser.getCouponCode();
            logger.info("优惠券编号:" + couponCode);
            // 获取优惠券配置信息
            CouponConfigVO couponConfig = this.amTradeClient.selectCouponConfig(couponCode);
            if (couponConfig == null) {
                logger.error("根据优惠券编号查询优惠券配置信息失败,优惠券信息:[" + couponCode + "].");
                return;
            }

            // 根据优惠券出借订单号查询优惠券出借信息
            BorrowTenderCpnVO borrowTenderCpn = this.amTradeClient.selectBorrowTenderCpnByCouponTenderId(couponRealTender.getCouponTenderId());
            if (borrowTenderCpn == null) {
                logger.error("根据优惠券出借订单号查询优惠券出借信息失败,优惠券出借订单号:[" + couponRealTender.getCouponTenderId() + "].");
                return;
            }
            try {
                // 体验金
                if (couponConfig.getCouponType() == 1) {
                    String tenderNid = borrowTenderCpn.getNid();
                    // 优惠券出借订单号
                    logger.info("出借订单号:[" + tenderNid + "].");
                    // 取得体验金收益期限
                    Integer couponProfitTime = this.amTradeClient.getCouponProfitTime(tenderNid);
                    // 计算体验金收益
                    BigDecimal interest = this.getInterestTYJ(borrowTenderCpn.getAccount(), borrow.getBorrowApr(), couponProfitTime);
                    // 体验金按项目期限还款
                    if (couponConfig.getRepayTimeConfig() == 1) {
                        // 计算利息
                        interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrow.getBorrowPeriod(), borrow.getBorrowApr(), borrow.getBorrowStyle(), borrowSuccessTime,
                                borrowMonthRate, borrowManagerScaleEnd, borrow.getProjectType(), differentialRate, borrowVerifyTime);
                        // 体验金的项目如果是分期
                        if (isMonthFlag) {
                            List<InterestInfo> listMonthly = interestInfo.getListMonthly();
                            // 取得最后一次分期的还款时间作为体验金的还款时间
                            interestInfo.setRepayTime(listMonthly.get(listMonthly.size() - 1).getRepayTime());
                        }

                    } else {
                        // 体验金按收益期限还款
                        interestInfo = new InterestInfo();
                        Integer repayTime = GetDate.countDate(borrowSuccessTime, 5, couponProfitTime);
                        interestInfo.setRepayTime(repayTime);
                    }
                    // 体验金收益
                    interestInfo.setRepayAccountInterest(interest);
                    interestTender = interestInfo.getRepayAccountInterest(); // 利息

                } else if (couponConfig.getCouponType() == 2) {
                    // 加息券
                    // 计算利息
                    interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrow.getBorrowPeriod(), couponConfig.getCouponQuota(), borrow.getBorrowStyle(),
                            borrowSuccessTime, borrowMonthRate, borrowManagerScaleEnd, borrow.getProjectType(), differentialRate, borrowVerifyTime);
                    interestTender = interestInfo.getRepayAccountInterest(); // 利息
                } else if (couponConfig.getCouponType() == 3) {
                    // 代金券
                    // 计算利息
                    interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrow.getBorrowPeriod(), borrow.getBorrowApr(), borrow.getBorrowStyle(), borrowSuccessTime,
                            borrowMonthRate, borrowManagerScaleEnd, borrow.getProjectType(), differentialRate, borrowVerifyTime);

                    interestTender = interestInfo.getRepayAccountInterest(); // 利息
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.info("计算优惠券利息错误,出借订单号:[" + orderId + "].");
            }
            // 预计加息金额
            properties.put("expect_add_apr_income", interestTender);
            // 优惠券id
            properties.put("coupon_id", couponUser.getCouponUserCode());

            // 优惠券类型
            if (couponConfig.getCouponType() == 3) {
                properties.put("coupon_type", "代金券");
            } else if (couponConfig.getCouponType() == 2) {
                properties.put("coupon_type", "加息券");
            } else {
                properties.put("coupon_type", "体验金");
            }

            // 体验金面额
            if (couponConfig.getCouponType() == 1) {
                properties.put("coupon_denomination", couponConfig.getCouponQuota());
            }
            // 加息比例
            if (couponConfig.getCouponType() == 2) {
                properties.put("add_apr", couponConfig.getCouponQuota().divide(new BigDecimal("100")));
            }
            // 代金券面额
            if (couponConfig.getCouponType() == 3) {
                properties.put("expect_project_award_income", couponConfig.getCouponQuota());
            }
            Integer isMonth = 1;
            // 判断是否是月标
            if ("endday".equals(borrow.getBorrowStyle())) {
                isMonth = 0;
            } else {
                isMonth = 1;
            }
            // 项目期限限制
            properties.put("project_duration_limit", createProjectExpiration(couponConfig.getProjectExpirationLength(),
                    couponConfig.getProjectExpirationLengthMin(), couponConfig.getProjectExpirationLengthMax(), couponConfig.getProjectExpirationType(), isMonth));
            // 适用出借产品
            properties.put("product_suitable", createProjectTypeString(couponConfig.getProjectType()));
            // 出借金额限制
            properties.put("tender_amount_limit", createCouponTenderMoney(couponConfig.getTenderQuotaType(), couponConfig.getTenderQuotaMin(), couponConfig.getTenderQuotaMax(), couponConfig.getTenderQuota()));
            properties.put("coupon_createtime", couponUser.getCreateTime());
            properties.put("coupon_endtime", GetDate.getDateTimeMyTime(couponUser.getEndTime()));
        } else {
            // 此笔出借未使用优惠券
            properties.put("use_coupon", false);
            // 优惠券id
            properties.put("coupon_id", "");
            // 优惠券类型
            properties.put("coupon_type", "");
            // 体验金面额
            properties.put("coupon_denomination", BigDecimal.ZERO);
            // 代金券面额
            properties.put("expect_project_award_income", BigDecimal.ZERO);
            // 加息比例
            properties.put("add_apr", BigDecimal.ZERO);
            // 预计加息金额
            properties.put("expect_add_apr_income", BigDecimal.ZERO);
            // 项目期限限制
            properties.put("project_duration_limit", "");
            properties.put("product_suitable", "");
            properties.put("tender_amount_limit", "");
        }
        sa.track(String.valueOf(userId), true, "submit_tender", properties);
        sa.shutdown();
    }


    /**
     * 计算体验金收益
     *
     * @param account
     * @param borrowApr
     * @param couponProfitTime
     * @return
     */
    private BigDecimal getInterestTYJ(BigDecimal account, BigDecimal borrowApr, Integer couponProfitTime) {
        BigDecimal interest = BigDecimal.ZERO;
        // 保留两位小数（去位）
        // 应回款=体验金面值*出借标的年化/365*收益期限（体验金发行配置）
        interest = account.multiply(borrowApr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP))
                .divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(couponProfitTime))
                .setScale(2, BigDecimal.ROUND_DOWN);
        return interest;
    }


    /**
     * 获取优惠券项目期限限定信息
     *
     * @param projectExpirationLength
     * @param projectExpirationLengthMin
     * @param projectExpirationLengthMax
     * @param projectExpirationType
     * @param isMonth
     * @return
     */
    private String createProjectExpiration(Integer projectExpirationLength, Integer projectExpirationLengthMin, Integer projectExpirationLengthMax, Integer projectExpirationType, Integer isMonth) {
        // 验证项目期限
        if (isMonth == 0) {
            if (projectExpirationType == 1) {
                return "适用" + projectExpirationLength + "个月的项目";
            } else if (projectExpirationType == 3) {
                return "适用≥" + projectExpirationLength + "个月的项目";
            } else if (projectExpirationType == 4) {
                return "适用≤" + projectExpirationLength + "个月的项目";
            } else if (projectExpirationType == 2) {
                return "适用" + projectExpirationLengthMin + "个月~" + projectExpirationLengthMax + "个月的项目";
            } else if (projectExpirationType == 0) {
                return "不限";
            }
        } else {
            if (projectExpirationType == 1) {
                return "适用" + projectExpirationLength + "个月的项目";
            } else if (projectExpirationType == 3) {
                return "适用≥" + projectExpirationLength + "个月的项目";
            } else if (projectExpirationType == 4) {
                return "适用≤" + projectExpirationLength + "个月的项目";
            } else if (projectExpirationType == 2) {
                return "适用" + projectExpirationLengthMin + "个月~" + projectExpirationLengthMax + "个月的项目";
            } else if (projectExpirationType == 0) {
                return "不限";
            }
        }
        return "不限";
    }
    /**
     * 优惠券项目限定
     *
     * @param projectType
     * @return
     */
    private String createProjectTypeString(String projectType) {
        String projectString = "";
        if (projectType.indexOf("-1") != -1) {
            projectString = "不限";
        } else {
            // 勾选汇直投，尊享汇，融通宝
            if (projectType.indexOf("1") != -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") != -1) {
                projectString = projectString + "散标,";
            }
            //勾选汇直投  未勾选尊享汇，融通宝
            if (projectType.indexOf("1") != -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") == -1) {
                projectString = projectString + "散标,";
            }
            //勾选汇直投，融通宝  未勾选尊享汇
            if (projectType.indexOf("1") != -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") != -1) {
                projectString = projectString + "散标,";
            }
            //勾选汇直投，选尊享汇 未勾选融通宝
            if (projectType.indexOf("1") != -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") == -1) {
                projectString = projectString + "散标,";
            }
            //勾选尊享汇，融通宝  未勾选直投
            if (projectType.indexOf("1") == -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") != -1) {
                projectString = projectString + "散标,";
            }
            //勾选尊享汇  未勾选直投，融通宝
            if (projectType.indexOf("1") == -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") == -1) {
                projectString = projectString + "散标,";
            }
            //勾选尊享汇  未勾选直投，融通宝
            if (projectType.indexOf("1") == -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") != -1) {
                projectString = projectString + "散标,";
            }

            if (projectType.indexOf("3") != -1) {
                projectString = projectString + "新手,";
            }

            if (projectType.indexOf("6") != -1) {
                projectString = projectString + "智投,";
            }
            projectString = org.apache.commons.lang3.StringUtils.removeEnd(projectString, ",");
        }
        return projectString;
    }


    /**
     * 获取优惠券出借金额限定
     *
     * @param tenderQuotaType
     * @param tenderQuotaMin
     * @param tenderQuotaMax
     * @param tenderQuota
     * @return
     */
    private String createCouponTenderMoney(Integer tenderQuotaType, Integer tenderQuotaMin, Integer tenderQuotaMax, Integer tenderQuota) {
        if (tenderQuotaType == 0 || tenderQuotaType == null) {
            return ("出借金额不限");
        } else if (tenderQuotaType == 1) {
            String tenderQuotaMinStr = tenderQuotaMin + "";
            if (tenderQuotaMin >= 10000 && tenderQuotaMin % 10000 == 0) {
                tenderQuotaMinStr = tenderQuotaMin / 10000 + "万";
            }
            String tenderQuotaMaxStr = tenderQuotaMax + "";
            if (tenderQuotaMax >= 10000 && tenderQuotaMax == 0) {
                tenderQuotaMaxStr = tenderQuotaMax / 10000 + "万";
            }
            return (tenderQuotaMinStr + "元~" + tenderQuotaMaxStr + "元可用");

        } else if (tenderQuotaType == 2) {
            String tenderQuotaStr = tenderQuota + "";
            if (tenderQuota >= 10000 && tenderQuota % 10000 == 0) {
                tenderQuotaStr = tenderQuota / 10000 + "万";
            }
            return ("满" + tenderQuotaStr + "元可用");
        }
        return "";
    }
}
