/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.hjh.HjhCalculateFairValueService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 汇计划加入订单计算公允价值Service实现类
 *
 * @author liuyang
 * @version HjhCalculateFairValueService, v0.1 2018/6/27 13:52
 */
@Service
public class HjhCalculateFairValueServiceImpl extends BaseServiceImpl implements HjhCalculateFairValueService {

    /**
     * 根据加入订单号查询计划加入订单
     *
     * @param accedeOrderId
     * @return
     */
    @Override
    public HjhAccede selectHjhAccedeByAccedeOrderId(String accedeOrderId) {
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        cra.andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 计算加入订单的公允价值
     *
     * @param hjhAccede
     * @param calculateType
     */
    @Override
    public void calculateFairValue(HjhAccede hjhAccede, Integer calculateType) throws RuntimeException {

        // 当前时间
        Integer liquidationShouldTime = GetDate.getNowTime10();
        // 加入订单当前持有的债权价值
        BigDecimal totalFairValue = BigDecimal.ZERO;
        // 计划加入订单号
        String accedeOrderId = hjhAccede.getAccedeOrderId();
        // 根据计划加入订单号查询当前持有有效债权
        List<HjhDebtDetail> debtDetails = this.hjhDebtDetailCustomizeMapper.selectDebtDetailForLiquidation(accedeOrderId);
        // 如果没有当前有效债权,只更新加入订单的状态
        if (debtDetails != null && debtDetails.size() > 0) {
            //  债权价值
            BigDecimal creditValue = BigDecimal.ZERO;
            // 提前还款债权价值减扣部分
            BigDecimal advanceCreditValue = BigDecimal.ZERO;
            // 持有天数
            int holdDays = 0;
            // 剩余天数
            int remainDays = 0;
            // 当前期计息天数
            int duringDays = 0;
            // 循环有效债权信息
            for (HjhDebtDetail hjhDebtDetail : debtDetails) {
                logger.info("计算加入订单的当前持有债权价值,智投编号:[" + hjhDebtDetail.getPlanNid() + "],计划加入订单号:" + hjhDebtDetail.getPlanOrderId() + "]," +
                        "出借订单号或承接订单号:[" + hjhDebtDetail.getOrderId() + "].");
                // 债权原标编号
                String borrowNid = hjhDebtDetail.getBorrowNid();
                // 根据标的号查询标的信息
                Borrow borrow = this.getBorrowByNid(borrowNid);
                if (borrow == null) {
                    throw new RuntimeException("根据标的编号查询标的详情失败,标的编号:[" + borrowNid + "].");
                }
                // 根据标的编号查询标的详情
                BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
                if (borrowInfo == null) {
                    throw new RuntimeException("根据标的编号查询标的详情失败,标的编号:[" + borrowNid + "].");
                }

                // 查询完全承接的债权
                HjhDebtCredit assignComplete = this.selectHjhDebtCreditAssignComplete(hjhDebtDetail.getBorrowNid(), hjhDebtDetail.getOrderId());
                if (assignComplete != null) {
                    // 如果债权已被转让出去,就不进行转让,跳出此次循环
                    // 一笔加入订单 既有完全承接 又有正在还款的还款的债权,此时 不清算
                    logger.info("债权已被清算出,债权出借订单号:[" + hjhDebtDetail.getInvestOrderId() + "],项目编号:[" + hjhDebtDetail.getBorrowNid() + "].");
                    continue;
                }
                // 不分期项目
                if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(hjhDebtDetail.getBorrowStyle())) {
                    // 查询当前所处的计息期数的债权信息
                    // 应还日期>= 当前日期 未还款的债权
                    HjhDebtDetail hjhDebtDetailCur = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurRepayPeriod(hjhDebtDetail.getOrderId());
                    // 如果取不到债权说明有逾期的债权
                    if (hjhDebtDetailCur != null) {
                        // 剩余未还本金
                        BigDecimal capital = hjhDebtDetailCur.getRepayCapitalWait();
                        // 待收利息
                        BigDecimal interest = hjhDebtDetailCur.getRepayInterestWait();
                        // 应还时间
                        Integer repayTime = hjhDebtDetailCur.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetailCur.getLoanTime();
                        // 当前期计息天数 =  放款日期到还款日 + 1 天
                        try {
                            duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        // 持有天数 =  放款日期 到 清算日 - 1 天
                        try {
                            holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime));
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle())) {
                            // 按天计息,到期还本还息
                            remainDays = borrow.getBorrowPeriod() - holdDays;
                        } else {
                            // 按月计息,到期还本还息
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetail.getRepayTime())) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        }
                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 计算债权价值 = 剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        // F=Ar+I+Ir/D*Dr
                        creditValue = (interest.multiply(new BigDecimal(holdDays)).divide(new BigDecimal(duringDays), 8, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN));
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                    } else {
                        // 应还日期 < 当前日期 未还款的债权 延期或逾期 (不分期项目延期或逾期)
                        // 是否是逾期债权
                        // 剩余未还本金
                        BigDecimal capital = hjhDebtDetail.getRepayCapitalWait();
                        // 待收利息
                        BigDecimal interest = hjhDebtDetail.getRepayInterestWait();
                        // 应还时间
                        Integer repayTime = hjhDebtDetail.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetail.getLoanTime();
                        try {
                            // 当前期计息天数  =  放款日期到还款日 + 1 天
                            duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        try {
                            // 持有天数 = 放款日到还款日 + 1
                            holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        // 按天计息,到期还本还息
                        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(hjhDebtDetail.getBorrowStyle())) {
                            // 剩余天数 = 标的期限 - 持有天数
                            remainDays = borrow.getBorrowPeriod() - holdDays;
                        } else {
                            // 按月计息,到期还本还息
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetail.getRepayTime()));
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        }
                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 计算债权价值 = 剩余未还本金 + 当前期未还款期次的待收收益/当前期计息天数*当前期持有天数
                        // F=Ar+I+Ir/D*Dr
                        creditValue = (interest.multiply(new BigDecimal(holdDays)).divide(new BigDecimal(duringDays), 8, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN));
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                    }
                } else {
                    // 分期项目
                    // 查询当前所处的计息期数的债权信息
                    // 应还日期 >= 当前日期 未还款的债权
                    HjhDebtDetail hjhDebtDetailCur = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurRepayPeriod(hjhDebtDetail.getOrderId());
                    if (hjhDebtDetailCur != null) {
                        // 应还时间
                        Integer repayTime = hjhDebtDetailCur.getRepayTime();
                        // 放款时间
                        Integer loanTime = hjhDebtDetailCur.getLoanTime();
                        // 剩余天数 清算日到本期应还日
                        try {
                            remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(hjhDebtDetailCur.getRepayTime()));
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        // 如果是第一期尚未还款
                        if (hjhDebtDetailCur.getRepayPeriod() == 1) {
                            // 如果第一期尚未还款
                            Integer liquidationBeforeTime = liquidationShouldTime - 60 * 60 * 24;
                            // 持有时间是放款时间至清算日前一天
                            try {
                                holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(hjhDebtDetailCur.getLoanTime()), GetDate.timestamptoStrYYYYMMDD(liquidationBeforeTime)) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                            // 当前期计息天数
                            try {
                                duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        } else {
                            // 如果不是第一期
                            // 查询上一期还款的债权详情
                            BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(hjhDebtDetailCur.getBorrowNid(), hjhDebtDetailCur.getInvestOrderId(), hjhDebtDetailCur.getRepayPeriod() - 1);
                            // 清算日前一天
                            Integer liquidationBeforeTime = liquidationShouldTime - 60 * 60 * 24;
                            // 还款日后一天
                            Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                            // 持有期是上一期应还时间的后一天至当清算日前一天
                            try {
                                holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(liquidationBeforeTime)) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                            if (holdDays < 0) {
                                holdDays = 0;
                            }
                            // 当前期计息天数
                            try {
                                duringDays = GetDate.daysBetween(repayPreTime, repayTime) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        }
                        // 最后一期还款的信息
                        BorrowRepayPlan borrowRepayPlan = this.getLastPeriodBorrowRepayPlan(borrowNid, hjhDebtDetailCur.getBorrowPeriod());
                        if (borrowRepayPlan != null) {
                            // 剩余期限 当前日期 到 最后一期还款日
                            try {
                                remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(borrowRepayPlan.getRepayTime())) + 1;
                            } catch (ParseException e) {
                                logger.error(e.getMessage());
                            }
                        }
                        if (remainDays < 0) {
                            remainDays = 0;
                        }
                        // 计算待垫付的利息
                        creditValue = ((hjhDebtDetailCur.getRepayInterestWait().multiply(new BigDecimal(holdDays))).divide(new BigDecimal(duringDays), 2, BigDecimal.ROUND_DOWN));
                        // 检索是否有之前期有逾期
                        List<HjhDebtDetail> overdueDetailList = this.selectOverdueDetailList(hjhDebtDetailCur.getOrderId(), hjhDebtDetailCur.getRepayPeriod());
                        if (overdueDetailList != null && overdueDetailList.size() > 0) {
                            for (HjhDebtDetail overdueDetail : overdueDetailList) {
                                creditValue = creditValue.add(overdueDetail.getRepayInterestWait());
                            }
                        }
                        // 计算真实的债权总额、本金、利息
                        // 如果当前期不是最后一期 则需要查询当前期到最后一期的债权信息
                        List<HjhDebtDetail> hjhDebtDetailsNoRepay = this.hjhDebtDetailCustomizeMapper.selectDebtDetailNoRepay(hjhDebtDetailCur.getOrderId());
                        BigDecimal capital = new BigDecimal(0);
                        BigDecimal interest = new BigDecimal(0);
                        if (hjhDebtDetailsNoRepay != null && hjhDebtDetailsNoRepay.size() > 0) {
                            for (HjhDebtDetail debtDetailNoRepay : hjhDebtDetailsNoRepay) {
                                capital = capital.add(debtDetailNoRepay.getRepayCapitalWait());
                                interest = interest.add(debtDetailNoRepay.getRepayInterestWait());
                            }
                        }
                        // 查询当前时间落在哪一期
                        // 应还日期 >= 当前日期 的债权
                        HjhDebtDetail currentPeriodDebtDetail = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurPeriod(hjhDebtDetail.getOrderId());
                        // 如果不为空
                        if (currentPeriodDebtDetail != null) {
                            // 当前期计息期间
                            Integer currentPeriodDuringDays = 0;
                            // 判断是否提前还款
                            // 如果债权已还款
                            if (currentPeriodDebtDetail.getRepayStatus() == 1) {
                                // 债权应还时间
                                Integer repayTimeDebtDetail = currentPeriodDebtDetail.getRepayTime();
                                // 应还时间 > 当前清算时间
                                if (repayTimeDebtDetail > liquidationShouldTime) {
                                    // 应还时间到清算日天数
                                    int currentPeriodAdvanceDays = 0;
                                    try {
                                        currentPeriodAdvanceDays = GetDate.daysBetween(repayTimeDebtDetail, liquidationShouldTime);
                                    } catch (ParseException e) {
                                        logger.error(e.getMessage());
                                    }
                                    // 提前还款，债权价值计算减扣
                                    // 计算当前期计息天数
                                    // 如果是第一期尚未还款
                                    if (currentPeriodDebtDetail.getRepayPeriod() == 1) {
                                        // 如果第一期尚未还款
                                        // 当前期计息天数的计算
                                        // 当前期放款时间
                                        Integer currentPeriodLoanTime = currentPeriodDebtDetail.getLoanTime();
                                        // 当前期计息期间 = 放款时间 到 应还款时间
                                        try {
                                            currentPeriodDuringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(currentPeriodLoanTime), GetDate.timestamptoStrYYYYMMDD(repayTimeDebtDetail)) + 1;
                                        } catch (ParseException e) {
                                            logger.error(e.getMessage());
                                        }
                                    } else {
                                        // 如果不是第一期
                                        // 查询上一期还款的债权详情
                                        BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(currentPeriodDebtDetail.getBorrowNid(), currentPeriodDebtDetail.getInvestOrderId(), currentPeriodDebtDetail.getRepayPeriod() - 1);
                                        // 还款日后一天
                                        Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                        // 当前期计息期间 = 上一期应还时间 到 应还款时间
                                        try {
                                            currentPeriodDuringDays = GetDate.daysBetween(repayPreTime, repayTimeDebtDetail) + 1;
                                        } catch (ParseException e) {
                                            logger.error(e.getMessage());
                                        }
                                    }
                                    // 提前还款，债权价值计算
                                    // 提前还款债权价值减扣部分
                                    advanceCreditValue = ((currentPeriodDebtDetail.getRepayInterestYes().multiply(new BigDecimal(currentPeriodAdvanceDays))).divide(new BigDecimal(currentPeriodDuringDays), 2, BigDecimal.ROUND_DOWN));
                                }
                            }
                        }
                        // 债权价值
                        BigDecimal fairValue = capital.add(creditValue).subtract(advanceCreditValue);
                        // 加入订单的债权价值
                        totalFairValue = totalFairValue.add(fairValue);
                    } else {
                        // 有未还款 最后一期或者往前逾期
                        // 三个月计划 包含 2个月 标的
                        // 取最后一期债权信息
                        HjhDebtDetail lastTermDebtDetail = this.selectLastTermDebtDetail(hjhDebtDetail);
                        if (lastTermDebtDetail != null) {
                            // 应还时间
                            Integer repayTime = lastTermDebtDetail.getRepayTime();
                            // 放款时间
                            Integer loanTime = lastTermDebtDetail.getLoanTime();
                            // 如果是第一期尚未还款
                            if (lastTermDebtDetail.getRepayPeriod() == 1) {
                                // 如果第一期尚未还款
                                // 持有时间是放款时间至还款日
                                try {
                                    holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(lastTermDebtDetail.getLoanTime()), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                                // 当前期计息天数
                                try {
                                    duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(loanTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                            } else {
                                // 如果不是第一期
                                // 查询上一期还款的债权详情
                                BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(lastTermDebtDetail.getBorrowNid(), lastTermDebtDetail.getInvestOrderId(), lastTermDebtDetail.getRepayPeriod() - 1);
                                // 还款日后一天
                                Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                // 持有期是上一期应还时间的后一天至当清算日前一天
                                try {
                                    holdDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                                if (holdDays < 0) {
                                    holdDays = 0;
                                }
                                // 当前期计息天数
                                try {
                                    duringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(repayPreTime), GetDate.timestamptoStrYYYYMMDD(repayTime)) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                            }
                            // 最后一期还款的信息
                            BorrowRepayPlan borrowRepayPlan = this.getLastPeriodBorrowRepayPlan(borrowNid, lastTermDebtDetail.getBorrowPeriod());
                            if (borrowRepayPlan != null) {
                                // 剩余期限 当前日期 到 最后一期还款日
                                try {
                                    remainDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(liquidationShouldTime), GetDate.timestamptoStrYYYYMMDD(borrowRepayPlan.getRepayTime())) + 1;
                                } catch (ParseException e) {
                                    logger.error(e.getMessage());
                                }
                            }
                            if (remainDays <= 0) {
                                remainDays = 0;
                            }
                            // 计算待垫付的利息
                            creditValue = ((lastTermDebtDetail.getRepayInterestWait().multiply(new BigDecimal(holdDays))).divide(new BigDecimal(duringDays), 2, BigDecimal.ROUND_DOWN));
                            // 检索是否有之前期有逾期
                            List<HjhDebtDetail> overdueDetailList = this.selectOverdueDetailList(lastTermDebtDetail.getOrderId(), lastTermDebtDetail.getRepayPeriod());
                            if (overdueDetailList != null && overdueDetailList.size() > 0) {
                                for (HjhDebtDetail overdueDetail : overdueDetailList) {
                                    creditValue = creditValue.add(overdueDetail.getRepayInterestWait());
                                }
                            }
                            // 未还款期数
                            Integer unRepayPeriod = 0;
                            // 如果当前期不是最后一期 则需要查询当前期到最后一期的债权信息
                            List<HjhDebtDetail> hjhDebtDetailsNoRepay = this.hjhDebtDetailCustomizeMapper.selectDebtDetailNoRepay(lastTermDebtDetail.getOrderId());
                            BigDecimal capital = new BigDecimal(0);
                            BigDecimal interest = new BigDecimal(0);
                            BigDecimal total = new BigDecimal(0);
                            if (hjhDebtDetailsNoRepay != null && hjhDebtDetailsNoRepay.size() > 0) {
                                for (HjhDebtDetail debtDetailNoRepay : hjhDebtDetailsNoRepay) {
                                    capital = capital.add(debtDetailNoRepay.getRepayCapitalWait());
                                    interest = interest.add(debtDetailNoRepay.getRepayInterestWait());
                                    total = capital.add(interest);
                                    unRepayPeriod++;
                                }
                            }
                            // 查询当前时间落在哪一期
                            // 应还日期 >= 当前日期 的债权
                            HjhDebtDetail currentPeriodDebtDetail = this.hjhDebtDetailCustomizeMapper.selectDebtDetailCurPeriod(hjhDebtDetail.getOrderId());
                            // 如果不为空
                            if (currentPeriodDebtDetail != null) {
                                // 当前期计息期间
                                Integer currentPeriodDuringDays = 0;
                                // 判断是否提前还款
                                // 如果债权已还款
                                if (currentPeriodDebtDetail.getRepayStatus() == 1) {
                                    // 债权实际还款时间
                                    Integer repayActionTime = currentPeriodDebtDetail.getRepayActionTime();
                                    // 债权应还时间
                                    Integer repayTimeDebtDetail = currentPeriodDebtDetail.getRepayTime();
                                    // 应还时间 > 当前清算时间
                                    if (repayTimeDebtDetail > liquidationShouldTime) {
                                        // 应还时间到清算日天数
                                        int currentPeriodAdvanceDays = 0;
                                        try {
                                            currentPeriodAdvanceDays = GetDate.daysBetween(repayTimeDebtDetail, liquidationShouldTime);
                                        } catch (ParseException e) {
                                            logger.error(e.getMessage());
                                        }
                                        // 提前还款，债权价值计算减扣
                                        // 计算当前期计息天数
                                        // 如果是第一期尚未还款
                                        if (currentPeriodDebtDetail.getRepayPeriod() == 1) {
                                            // 如果第一期尚未还款
                                            // 当前期计息天数的计算
                                            // 当前期放款时间
                                            Integer currentPeriodLoanTime = currentPeriodDebtDetail.getLoanTime();
                                            // 当前期计息期间 = 放款时间 到 应还款时间
                                            try {
                                                currentPeriodDuringDays = GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(currentPeriodLoanTime), GetDate.timestamptoStrYYYYMMDD(repayTimeDebtDetail)) + 1;
                                            } catch (ParseException e) {
                                                logger.error(e.getMessage());
                                            }
                                        } else {
                                            // 如果不是第一期
                                            // 查询上一期还款的债权详情
                                            BorrowRecoverPlan borrowRecoverPlan = this.selectLastPeriodRecoverPlan(currentPeriodDebtDetail.getBorrowNid(), currentPeriodDebtDetail.getInvestOrderId(), currentPeriodDebtDetail.getRepayPeriod() - 1);
                                            // 还款日后一天
                                            Integer repayPreTime = Integer.valueOf(borrowRecoverPlan.getRecoverTime()) + 60 * 60 * 24;
                                            // 当前期计息期间 = 上一期应还时间 到 应还款时间
                                            try {
                                                currentPeriodDuringDays = GetDate.daysBetween(repayPreTime, repayTimeDebtDetail) + 1;
                                            } catch (ParseException e) {
                                                logger.error(e.getMessage());
                                            }
                                        }
                                        // 提前还款，债权价值计算
                                        // 提前还款债权价值减扣部分
                                        advanceCreditValue = ((currentPeriodDebtDetail.getRepayInterestYes().multiply(new BigDecimal(currentPeriodAdvanceDays))).divide(new BigDecimal(currentPeriodDuringDays), 2, BigDecimal.ROUND_DOWN));
                                    }
                                }
                            }
                            // 债权价值
                            BigDecimal fairValue = capital.add(creditValue);
                            // 加入订单的债权价值
                            totalFairValue = totalFairValue.add(fairValue);
                        }
                    }
                }
            }
        }

        // 计算计划订单的可转让的债权价值
        BigDecimal creditFairValue = this.selectCreditFairValueByAccedeOrderId(accedeOrderId);

        // 清算服务费率=（计划订单公允价值（发起清算时）-加入金额-预期收益）/计划订单可转让持有的债权价值总和。
        // 如果计划订单的可转让的债权价值为0 的话,说明该计划订单的债权价值已被完全承接完成,那么此时清算服务费率不应默认为0,应该为计划
        BigDecimal serviceRate = hjhAccede.getLqdServiceApr();
        // 加入金额
        BigDecimal accedeAccount = hjhAccede.getAccedeAccount();
        // 应还利息
        BigDecimal shouldPayInterest = hjhAccede.getShouldPayInterest();
        // 清算剩余利息
        // 计划订单的债权价值 + 计划订单的可用余额 + 计划订单的冻结金额  - 加入金额 -预期收益
        BigDecimal liquidationInterest = (totalFairValue.add(hjhAccede.getFrostAccount()).add(hjhAccede.getAvailableInvestAccount()).subtract(accedeAccount).subtract(shouldPayInterest));
        // 清算剩余利息小于0时,不收服务费,清算服务率为0
        if(liquidationInterest.compareTo(BigDecimal.ZERO)<=0){
            serviceRate = BigDecimal.ZERO;
        }
        if (liquidationInterest.compareTo(BigDecimal.ZERO) > 0 && creditFairValue.compareTo(BigDecimal.ZERO) > 0) {
            serviceRate = (liquidationInterest.divide(creditFairValue, 12, BigDecimal.ROUND_DOWN)).setScale(8, BigDecimal.ROUND_DOWN);
        }

        // 如果清算服务费率 > 1 按1收取服务费
        if (serviceRate.compareTo(BigDecimal.ONE) > 0) {
            serviceRate = BigDecimal.ONE;
        }
        // 清算进度
        BigDecimal lqdProgress = hjhAccede.getLqdProgress();
        if (calculateType == 1) {
            // 计划订单开始清算时持有标的债权价值
            BigDecimal liquidationFairValue = hjhAccede.getLiquidationFairValue();
            // 清算进度=（计划订单开始清算时持有标的债权价值-计划订单当前持有的标的债权价值）/计划订单开始清算时持有标的的债权价值
            if (liquidationFairValue.compareTo(totalFairValue) > 0) {
                lqdProgress = ((liquidationFairValue.subtract(totalFairValue)).divide(liquidationFairValue, 8, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN);
            } else {
                // 计划订单开始清算时持有标的债权价值-计划订单当前持有的标的债权价值 < 0
                lqdProgress = BigDecimal.ZERO;
            }
        }
        // 未防止跟计划订单的退出并发问题
        hjhAccede = this.getHjhAccedeByOrderId(accedeOrderId);
        if (hjhAccede.getOrderStatus() == 7) {
            // 如果是计划订单已退出,不去做后续更新操作
            logger.info("计划订单已退出,计划加入订单号:" + accedeOrderId + "].");
            return;
        }
        // 清算时
        if (calculateType == 0) {
            HjhAccede newHjhAccede = new HjhAccede();
            newHjhAccede.setAccedeOrderId(hjhAccede.getAccedeOrderId());
            newHjhAccede.setLiquidationFairValue(totalFairValue);// 清算时计划订单的公允价值
            newHjhAccede.setFairValue(totalFairValue.add(hjhAccede.getFrostAccount()).add(hjhAccede.getAvailableInvestAccount()));// 计划加入订单的公允价值
            newHjhAccede.setLqdServiceApr(serviceRate);// 清算服务费率
            newHjhAccede.setLqdProgress(BigDecimal.ZERO);// 清算进度
            // 更新计划加入订单
            boolean isHjhAccedeUpdateFlag = this.hjhDebtDetailCustomizeMapper.updateLiquidationHjhAccede(newHjhAccede) > 0 ? true : false;
            if (!isHjhAccedeUpdateFlag) {
                throw new RuntimeException("清算时,更新计划订单失败,计划加入订单号:[" + accedeOrderId + "].");
            }
        } else if (calculateType == 1) {
            HjhAccede newHjhAccede = new HjhAccede();
            newHjhAccede.setAccedeOrderId(hjhAccede.getAccedeOrderId());
            newHjhAccede.setFairValue(totalFairValue.add(hjhAccede.getFrostAccount()).add(hjhAccede.getAvailableInvestAccount()));// 计划加入订单的公允价值
            newHjhAccede.setLqdServiceApr(serviceRate);// 清算服务费率
            newHjhAccede.setLqdProgress(lqdProgress);// 清算进度
            // 更新计划加入订单
            boolean isAccedeUpdateFlag = this.hjhDebtDetailCustomizeMapper.updateCalculateHjhAccede(newHjhAccede) > 0 ? true : false;
            if (!isAccedeUpdateFlag) {
                throw new RuntimeException("计算时,更新计划订单失败,计划加入订单号:[" + accedeOrderId + "].");
            }
        }
    }


    /**
     * 查询完全承接的出让
     *
     * @param borrowNid
     * @param investOrderId
     * @return
     */
    private HjhDebtCredit selectHjhDebtCreditAssignComplete(String borrowNid, String investOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andInvestOrderIdEqualTo(investOrderId);
        cra.andCreditStatusEqualTo(2);
        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询上一期还款信息
     *
     * @param borrowNid
     * @param investOrderId
     * @param repayPeriod
     * @return
     */
    private BorrowRecoverPlan selectLastPeriodRecoverPlan(String borrowNid, String investOrderId, int repayPeriod) {
        BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
        BorrowRecoverPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andNidEqualTo(investOrderId);
        cra.andRecoverPeriodEqualTo(repayPeriod);
        List<BorrowRecoverPlan> list = this.borrowRecoverPlanMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据项目编号,借款期限检索最后一期还款信息
     *
     * @param borrowNid
     * @param borrowPeriod
     * @return
     */
    private BorrowRepayPlan getLastPeriodBorrowRepayPlan(String borrowNid, Integer borrowPeriod) {
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andRepayPeriodEqualTo(borrowPeriod);
        List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 检索当前期逾期的债权
     *
     * @param orderId
     * @param repayPeriod
     * @return
     */
    private List<HjhDebtDetail> selectOverdueDetailList(String orderId, Integer repayPeriod) {
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        HjhDebtDetailExample.Criteria cra = example.createCriteria();
        cra.andOrderIdEqualTo(orderId);
        cra.andStatusEqualTo(1);
        cra.andRepayStatusEqualTo(0);
        cra.andRepayPeriodLessThan(repayPeriod);
        List<HjhDebtDetail> list = this.hjhDebtDetailMapper.selectByExample(example);
        return list;
    }

    /**
     * 取最后一期债权信息
     *
     * @param hjhDebtDetail
     * @return
     */
    private HjhDebtDetail selectLastTermDebtDetail(HjhDebtDetail hjhDebtDetail) {
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        HjhDebtDetailExample.Criteria cra = example.createCriteria();
        cra.andOrderIdEqualTo(hjhDebtDetail.getOrderId());
        cra.andStatusEqualTo(1);
        cra.andRepayStatusEqualTo(0);
        cra.andRepayPeriodEqualTo(hjhDebtDetail.getBorrowPeriod());
        List<HjhDebtDetail> list = this.hjhDebtDetailMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    /**
     * 根据加入订单号查询已转让的剩余债权价值总和
     *
     * @param accedeOrderId
     * @return
     */
    private BigDecimal selectCreditFairValueByAccedeOrderId(String accedeOrderId) {
        //  剩余可转让债权价值 = 转让中总的债权价值 - 已承接债权价值
        BigDecimal creditFairValue = BigDecimal.ZERO;
        // 转让中的总的债权价值
        BigDecimal totalCreditFairValue = BigDecimal.ZERO;
        // 已成承接的债权价值
        BigDecimal totalAssignCreditFairValue = BigDecimal.ZERO;
        // 根据加入订单号查询正在承接中的债转
        List<HjhDebtCredit> creditList = this.selectHjhDebtCreditList(accedeOrderId);
        //  如果不为空
        if (creditList != null && creditList.size() > 0) {
            for (int i = 0; i < creditList.size(); i++) {
                HjhDebtCredit hjhDebtCredit = creditList.get(i);
                totalCreditFairValue = totalCreditFairValue.add(hjhDebtCredit.getLiquidationFairValue());
                // 债转编号
                String creditNid = hjhDebtCredit.getCreditNid();
                // 根据债转编号查询已承接的债权价值
                BigDecimal assignCreditFairValue = this.selectAssignCreditFairValue(creditNid);
                totalAssignCreditFairValue = totalAssignCreditFairValue.add(assignCreditFairValue);
            }
        }
        // 剩余可转让债权价值 = 转让中总的债权价值 - 已承接债权价值
        creditFairValue = totalCreditFairValue.subtract(totalAssignCreditFairValue);
        return creditFairValue;
    }

    /**
     * 根据承接订单号查询正在转让中的债转
     *
     * @param accedeOrderId
     * @return
     */
    private List<HjhDebtCredit> selectHjhDebtCreditList(String accedeOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria cra = example.createCriteria();
        cra.andPlanOrderIdEqualTo(accedeOrderId);
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(0);
        statusList.add(1);
        cra.andCreditStatusIn(statusList);
        List<HjhDebtCredit> creditList = this.hjhDebtCreditMapper.selectByExample(example);
        return creditList;
    }

    /**
     * 根据债转编号查询已承接的债权价值
     *
     * @param creditNid
     * @return
     */
    private BigDecimal selectAssignCreditFairValue(String creditNid) {
        BigDecimal assignFairValue = BigDecimal.ZERO;
        List<HjhDebtCreditTender> hjhDebtCreditTenderList = this.selectCreditTenderList(creditNid);
        if (hjhDebtCreditTenderList != null && hjhDebtCreditTenderList.size() > 0) {
            for (int i = 0; i < hjhDebtCreditTenderList.size(); i++) {
                HjhDebtCreditTender hjhDebtCreditTender = hjhDebtCreditTenderList.get(i);
                assignFairValue = assignFairValue.add(hjhDebtCreditTender.getAssignPrice());
            }
        }
        return assignFairValue;
    }

    /**
     * 根据债转编号查询债转承接记录
     *
     * @param creditNid
     * @return
     */
    private List<HjhDebtCreditTender> selectCreditTenderList(String creditNid) {
        HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
        HjhDebtCreditTenderExample.Criteria cra = example.createCriteria();
        cra.andCreditNidEqualTo(creditNid);
        List<HjhDebtCreditTender> hjhDebtCreditTenderList = this.hjhDebtCreditTenderMapper.selectByExample(example);
        return hjhDebtCreditTenderList;
    }

    /**
     * 根据加入订单号查询计划加入订单
     *
     * @param accedeOrderId
     * @return
     */
    private HjhAccede getHjhAccedeByOrderId(String accedeOrderId) {
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        cra.andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
