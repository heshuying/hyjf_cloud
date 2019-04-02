/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch.impl;

import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayLateCustomize;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayLateSetCustomize;
import com.hyjf.am.trade.service.front.batch.BorrowRepayLateService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.util.calculate.UnnormalRepayUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wangjun
 * @version BorrowRepayLateServiceImpl, v0.1 2019/3/21 9:54
 */
@Service
public class BorrowRepayLateServiceImpl extends BaseServiceImpl implements BorrowRepayLateService {
    @Autowired
    SystemConfig systemConfig;

    /**
     * 查询不分期逾期还款的列表
     * @return
     */
    @Override
    public List<BorrowRepayLateCustomize> selectBorrowRepayLate() {
        return borrowRepayLateMapper.selectBorrowRepayLate();
    }

    /**
     * 查询分期逾期还款的列表
     * @return
     */
    @Override
    public List<BorrowRepayLateCustomize> selectBorrowRepayLateByStages() {
        return borrowRepayLateMapper.selectBorrowRepayLateByStages();
    }

    /**
     * 更新期逾期标的信息
     * @param list
     * @param listByStages
     */
    @Override
    public void updBorrowRepayLate(List<BorrowRepayLateCustomize> list, List<BorrowRepayLateCustomize> listByStages) {
        logger.info("开始计算还款逾期利息");
        // 处理不分期逾期数据
        if (!CollectionUtils.isEmpty(list)) {
            // 储存更新过的标的，用于后面更新borrow表跟borrow_repay表的相关状态
            Set<String> borrowSet = new HashSet<>();
            for (BorrowRepayLateCustomize borrowRepayLateCustomize : list) {
                try {
                    logger.info("不分期标的{}开始计算还款逾期利息", borrowRepayLateCustomize.getBorrowNid());
                    ((BorrowRepayLateService) AopContext.currentProxy()).updateBorrowRepayLate(borrowRepayLateCustomize, false);
                    // 添加set数据
                    borrowSet.add(borrowRepayLateCustomize.getBorrowNid());
                } catch (Exception e) {
                    logger.error("不分期标的{}计算还款逾期利息发生异常", borrowRepayLateCustomize.getBorrowNid(), e);
                    continue;
                }
            }
            // 更新borrow表与borrow_repay表的状态
            try {
                ((BorrowRepayLateService) AopContext.currentProxy()).updateBorrowAndRepayStatus(borrowSet, null, false);
            } catch (Exception e) {
                logger.error("不分期标的更新borrow表与borrow_repay表的逾期状态时发生异常");
            }
        }

        // 处理分期逾期数据
        if (!CollectionUtils.isEmpty(listByStages)) {
            // 储存更新过的标的，用于后面更新borrow表跟borrow_repay表的相关状态
            Set<String> borrowSet = new HashSet<>();
            Set<BorrowRepayLateSetCustomize> borrowRepayLateSetCustomizeSet = new HashSet<>();
            for (BorrowRepayLateCustomize borrowRepayLateCustomize : listByStages) {
                try {
                    logger.info("分期标的{},第{}期开始计算还款逾期利息", borrowRepayLateCustomize.getBorrowNid(), borrowRepayLateCustomize.getRecoverPeriod());
                    ((BorrowRepayLateService) AopContext.currentProxy()).updateBorrowRepayLate(borrowRepayLateCustomize, true);
                    // 添加set数据
                    borrowSet.add(borrowRepayLateCustomize.getBorrowNid());
                    BorrowRepayLateSetCustomize borrowRepayLateSetCustomize = new BorrowRepayLateSetCustomize(borrowRepayLateCustomize.getBorrowNid(), borrowRepayLateCustomize.getRecoverPeriod());
                    borrowRepayLateSetCustomizeSet.add(borrowRepayLateSetCustomize);
                } catch (Exception e) {
                    logger.error("分期标的{},第{}期计算还款逾期利息发生异常", borrowRepayLateCustomize.getBorrowNid(), borrowRepayLateCustomize.getRecoverPeriod(), e);
                    continue;
                }
            }
            // 更新borrow表与borrow_repay表的状态
            try {
                ((BorrowRepayLateService) AopContext.currentProxy()).updateBorrowAndRepayStatus(borrowSet, borrowRepayLateSetCustomizeSet, true);
            } catch (Exception e) {
                logger.error("分期标的更新borrow表与borrow_repay表的逾期状态时发生异常");
            }
        }
        logger.info("计算还款逾期利息结束");
    }

    /**
     * 更新逾期信息
     *
     * @param borrowRepayLateCustomize
     * @param isByStages               是否分期 true:分期 false:不分期
     */
    @Override
    public void updateBorrowRepayLate(BorrowRepayLateCustomize borrowRepayLateCustomize, boolean isByStages) {
        // 计算逾期天数
        int lateDay = DateUtils.getTimeDistanceOfDay(borrowRepayLateCustomize.getRecoverTime(), new Date());

        // 如果已经发生了债转，则对已承接的数据进行计算
        if (borrowRepayLateCustomize.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
            // 转让人真正持有的待还本息，用于计算部分承接时的逾期利息
            BigDecimal borrowRecoverRealAccount = borrowRepayLateCustomize.getRecoverAccount();
            // 逾期还款利息总和，用于更新recover表中的逾期利息(late_interest)
            BigDecimal repayLateInterestSum = BigDecimal.ZERO;

            // 如果没有汇计划加入订单号，查询直投债转，否则查智投债转
            if (Validator.isNull(borrowRepayLateCustomize.getAccedeOrderId())) {
                //查询直投债转还款信息
                List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowRepayLateCustomize.getBorrowNid(), borrowRepayLateCustomize.getTenderNid(), borrowRepayLateCustomize.getRecoverPeriod(), 0);
                if (!CollectionUtils.isEmpty(creditRepayList)) {
                    for (CreditRepay creditRepay : creditRepayList) {
                        // 每有一笔承接，转让人的持有本息就减去当前承接的本息
                        borrowRecoverRealAccount = borrowRecoverRealAccount.subtract(creditRepay.getAssignAccount());
                        // 计算债转还款逾期利息
                        BigDecimal creditRepayLateInterest = UnnormalRepayUtils.repayLateInterest(creditRepay.getAssignAccount(), lateDay, new BigDecimal(systemConfig.getRepayLateRate()));
                        // 累加还款逾期利息
                        repayLateInterestSum = repayLateInterestSum.add(creditRepayLateInterest);

                        // 更新债转表
                        CreditRepay creditRepayForUpdate = new CreditRepay();
                        creditRepayForUpdate.setId(creditRepay.getId());
                        // 债转提前还款状态(3:逾期)
                        creditRepayForUpdate.setAdvanceStatus(3);
                        // 逾期天数
                        creditRepayForUpdate.setLateDays(lateDay);
                        // 逾期利息
                        creditRepayForUpdate.setLateInterest(creditRepayLateInterest);
                        creditRepayMapper.updateByPrimaryKeySelective(creditRepayForUpdate);
                    }
                }
            } else {
                // 查询计划债转
                List<HjhDebtCreditRepay> hjhDebtCreditRepayList = this.selectHjhDebtCreditRepay(borrowRepayLateCustomize.getBorrowNid(), borrowRepayLateCustomize.getTenderNid(), borrowRepayLateCustomize.getRecoverPeriod(), 0);
                if (!CollectionUtils.isEmpty(hjhDebtCreditRepayList)) {
                    for (HjhDebtCreditRepay hjhDebtCreditRepay : hjhDebtCreditRepayList) {
                        // 每有一笔承接，转让人的持有本息就减去当前承接的本息
                        borrowRecoverRealAccount = borrowRecoverRealAccount.subtract(hjhDebtCreditRepay.getRepayAccount());
                        // 计算债转还款逾期利息
                        BigDecimal creditRepayLateInterest = UnnormalRepayUtils.repayLateInterest(hjhDebtCreditRepay.getRepayAccount(), lateDay, new BigDecimal(systemConfig.getRepayLateRate()));
                        // 累加还款逾期利息
                        repayLateInterestSum = repayLateInterestSum.add(creditRepayLateInterest);

                        // 更新计划债转表
                        HjhDebtCreditRepay hjhDebtCreditRepayForUpdate = new HjhDebtCreditRepay();
                        hjhDebtCreditRepayForUpdate.setId(hjhDebtCreditRepay.getId());
                        // 债转提前还款状态(3:逾期)
                        hjhDebtCreditRepayForUpdate.setAdvanceStatus(3);
                        // 逾期天数
                        hjhDebtCreditRepayForUpdate.setLateDays(lateDay);
                        // 逾期利息
                        hjhDebtCreditRepayForUpdate.setRepayLateInterest(creditRepayLateInterest);
                        hjhDebtCreditRepayMapper.updateByPrimaryKeySelective(hjhDebtCreditRepayForUpdate);
                    }
                }
            }

            // 如果不是完全承接，则需要计算转让人剩余持有本息的逾期还款利息
            if (2 != borrowRepayLateCustomize.getCreditStatus()) {
                BigDecimal borrowRecoverRealInterest = UnnormalRepayUtils.repayLateInterest(borrowRecoverRealAccount, lateDay, new BigDecimal(systemConfig.getRepayLateRate()));
                // 累加还款逾期利息
                repayLateInterestSum = repayLateInterestSum.add(borrowRecoverRealInterest);
            }
            // 如果分期，则更新borrow_recover_plan表，否则更新borrow_recover表
            updateRecoverOrRecoverPlan(borrowRepayLateCustomize.getId(), lateDay, repayLateInterestSum, isByStages);
        } else {
            // 没发生债转时，计算逾期利息，直接更新borrow_recover或borrow_recover_plan
            BigDecimal borrowRecoverLateInterest = UnnormalRepayUtils.repayLateInterest(borrowRepayLateCustomize.getRecoverAccount(), lateDay, new BigDecimal(systemConfig.getRepayLateRate()));
            updateRecoverOrRecoverPlan(borrowRepayLateCustomize.getId(), lateDay, borrowRecoverLateInterest, isByStages);
        }
    }

    /**
     * 更新还款逾期标的borrow表与borrow_repay(分期是borrow_repay_plan)的相关状态
     *
     * @param borrowNidSet
     * @param borrowRepayLateSetCustomizeSet
     * @param isByStages
     */
    @Override
    public void updateBorrowAndRepayStatus(Set<String> borrowNidSet, Set<BorrowRepayLateSetCustomize> borrowRepayLateSetCustomizeSet, boolean isByStages) {
        //分期更新borrow表与repay_plan，不分期更新borrow与repay
        if (isByStages) {
            // 更新borrow表
            for (String borrowNid : borrowNidSet) {
                // 更新borrow表的状态为8:逾期
                Borrow borrow = new Borrow();
                borrow.setStatus(8);
                BorrowExample borrowExample = new BorrowExample();
                borrowExample.createCriteria().andBorrowNidEqualTo(borrowNid);
                borrowMapper.updateByExampleSelective(borrow, borrowExample);
            }

            // 更新borrow_repay_plan表
            for (BorrowRepayLateSetCustomize borrowRepayLateSetCustomize : borrowRepayLateSetCustomizeSet) {
                // 更新borrow_repay表的advance_status为3：逾期
                BorrowRepayPlan borrowRepayPlan = new BorrowRepayPlan();
                borrowRepayPlan.setAdvanceStatus(3);
                BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
                borrowRepayPlanExample.createCriteria().andBorrowNidEqualTo(borrowRepayLateSetCustomize.getBorrowNid()).andRepayPeriodEqualTo(borrowRepayLateSetCustomize.getRecoverPeriod());
                borrowRepayPlanMapper.updateByExampleSelective(borrowRepayPlan, borrowRepayPlanExample);
            }
        } else {
            for (String borrowNid : borrowNidSet) {
                // 更新borrow表的状态为8:逾期
                Borrow borrow = new Borrow();
                borrow.setStatus(8);
                BorrowExample borrowExample = new BorrowExample();
                borrowExample.createCriteria().andBorrowNidEqualTo(borrowNid);
                borrowMapper.updateByExampleSelective(borrow, borrowExample);

                // 更新borrow_repay表的advance_status为 3:逾期
                BorrowRepay borrowRepay = new BorrowRepay();
                borrowRepay.setAdvanceStatus(3);
                BorrowRepayExample borrowRepayExample = new BorrowRepayExample();
                borrowRepayExample.createCriteria().andBorrowNidEqualTo(borrowNid);
                borrowRepayMapper.updateByExampleSelective(borrowRepay, borrowRepayExample);
            }
        }
    }

    /**
     * 查询直投债转
     *
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    private List<CreditRepay> selectCreditRepay(String borrowNid, String tenderOrderId, Integer periodNow, int status) {
        CreditRepayExample example = new CreditRepayExample();
        CreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBidNidEqualTo(borrowNid);
        crt.andCreditTenderNidEqualTo(tenderOrderId);
        crt.andRecoverPeriodEqualTo(periodNow);
        crt.andStatusEqualTo(status);
        example.setOrderByClause("id ASC");
        List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

    /**
     * 查询智投债转
     *
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    private List<HjhDebtCreditRepay> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status) {
        HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
        HjhDebtCreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andInvestOrderIdEqualTo(tenderOrderId);
        crt.andRepayPeriodEqualTo(periodNow);
        crt.andRepayStatusEqualTo(status);
        crt.andDelFlagEqualTo(0);
        crt.andRepayAccountGreaterThan(BigDecimal.ZERO);
        example.setOrderByClause("id ASC");
        List<HjhDebtCreditRepay> creditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

    /**
     * 不分期更新borrow_recover表/分期更新borrow_recover_plan表
     *
     * @param id
     * @param lateDay
     * @param repayLateInterest
     * @param isByStages
     */
    private void updateRecoverOrRecoverPlan(Integer id, int lateDay, BigDecimal repayLateInterest, boolean isByStages) {
        // 如果分期，则更新borrow_recover_plan表，否则更新borrow_recover表
        if (isByStages) {
            BorrowRecoverPlan borrowRecoverPlanForUpdate = new BorrowRecoverPlan();
            borrowRecoverPlanForUpdate.setId(id);
            // 3:逾期
            borrowRecoverPlanForUpdate.setAdvanceStatus(3);
            // 逾期天数
            borrowRecoverPlanForUpdate.setLateDays(lateDay);
            // 逾期总利息
            borrowRecoverPlanForUpdate.setLateInterest(repayLateInterest);
            borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlanForUpdate);
        } else {
            BorrowRecover borrowRecoverForUpdate = new BorrowRecover();
            borrowRecoverForUpdate.setId(id);
            // 3:逾期
            borrowRecoverForUpdate.setAdvanceStatus(3);
            // 逾期天数
            borrowRecoverForUpdate.setLateDays(lateDay);
            // 逾期总利息
            borrowRecoverForUpdate.setLateInterest(repayLateInterest);
            borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecoverForUpdate);
        }
    }
}
