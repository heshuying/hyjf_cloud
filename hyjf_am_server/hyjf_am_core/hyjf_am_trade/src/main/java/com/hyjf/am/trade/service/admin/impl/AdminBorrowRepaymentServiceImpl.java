package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.admin.*;
import com.hyjf.am.trade.service.admin.AdminBorrowRepaymentService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.UnnormalRepayUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentServiceImpl, v0.1 2018/7/4 14:33
 */
@Service
public class AdminBorrowRepaymentServiceImpl extends BaseServiceImpl implements AdminBorrowRepaymentService {


    @Override
    public int countBorrowRecover(BorrowRecoverRequest request) {
        return this.borrowRepaymentCustomizeMapper.countBorrowRepayment(request);
    }

    @Override
    public List<AdminBorrowRepaymentCustomize> selectBorrowRecoverList(BorrowRecoverRequest request) {
        return this.borrowRepaymentCustomizeMapper.selectBorrowRepaymentList(request);
    }

    @Override
    public AdminBorrowRepaymentCustomize sumBorrowRecoverList(BorrowRecoverRequest request) {
        return this.borrowRepaymentCustomizeMapper.sumBorrowRepayment(request);
    }

    @Override
    public List<AdminBorrowRepaymentPlanCustomize> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request) {
        return this.borrowRepaymentCustomizeMapper.exportRepayClkActBorrowRepaymentInfoList(request);
    }

    @Override
    public AdminRepayDelayCustomize selectBorrowInfo(String borrowNid) throws ParseException{
        AdminRepayDelayCustomize repayDelayCustomize = new AdminRepayDelayCustomize();
        repayDelayCustomize.setBorrowNid(borrowNid);
        AdminRepayDelayCustomize repayDelay = borrowRepaymentCustomizeMapper.selectBorrowInfo(repayDelayCustomize);
        return repayDelay;
    }

    @Override
    public BorrowRepay getBorrowRepayDelay(String borrowNid, String borrowApr, String borrowStyle) throws ParseException {
        BorrowRepayExample example = new BorrowRepayExample();
        BorrowRepayExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepay> list = this.borrowRepayMapper.selectByExample(example);
        if (list != null && list.size() > 0) {

            BorrowRepay borrowRepay = list.get(0);
            BorrowRepayBean borrowRepayBean = CommonUtils.convertBean(borrowRepay, BorrowRepayBean.class);
            Date nowDate = new Date();
            Date date = new Date(Long.valueOf(borrowRepay.getRepayTime()) * 1000L);
            int distanceDays = GetDate.daysBetween(nowDate, date);
            // 提前还款
            if (distanceDays >= 0) {
                return borrowRepayBean;
            } else {
                // 延迟天数
                int delayDays = borrowRepayBean.getDelayDays().intValue();
                int lateDays = delayDays + distanceDays;
                // 用户延期还款（未逾期）
                if (lateDays >= 0) {
                    delayDays = -distanceDays;
                    calculateRepayDelay(borrowRepayBean, borrowNid, new BigDecimal(borrowApr), delayDays);
                } else {
                    lateDays = -lateDays;
                    // 用户逾期还款
                    calculateRepayLate(borrowRepayBean, borrowNid, new BigDecimal(borrowApr), delayDays, lateDays);
                }
            }
            return borrowRepayBean;
        }
        return new BorrowRepayBean();
    }

    @Override
    public BorrowRepayPlan getBorrowRepayPlanDelay(String borrowNid, String borrowApr, String borrowStyle) throws ParseException{
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andRepayTypeEqualTo("wait");
        cra.andRepayStatusEqualTo(0);
        example.setOrderByClause(" repay_period ASC ");
        List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            BorrowRepayPlan repayPlan = list.get(0);
            BorrowRepayPlanBean borrowRepayBean = CommonUtils.convertBean(repayPlan, BorrowRepayPlanBean.class);
            Date nowDate = new Date();
            Date date = new Date(Long.valueOf(repayPlan.getRepayTime()) * 1000L);
            // 获取实际还款同计划还款时间的时间差
            int distanceDays = GetDate.daysBetween(nowDate, date);
            // 提前还款
            if (distanceDays >= 0) {
                return borrowRepayBean;
            } else {
                // 延迟天数
                int delayDays = repayPlan.getDelayDays().intValue();
                int lateDays = delayDays + distanceDays;
                // 用户延期还款（未逾期）
                if (lateDays >= 0) {
                    delayDays = -distanceDays;
                    calculateRepayPlanDelay(borrowRepayBean, borrowNid, new BigDecimal(borrowApr), delayDays);
                } else {
                    // 用户逾期还款
                    lateDays = -lateDays;
                    calculateRepayPlanLate(borrowRepayBean, borrowNid, new BigDecimal(borrowApr), delayDays, lateDays);
                }
            }
            return borrowRepayBean;
        }
        return new BorrowRepayPlanBean();
    }

    @Override
    public int updateBorrowRepayDelayDays(String borrowNid, String delayDays) throws ParseException{
        AdminRepayDelayCustomize repayDelay = this.selectBorrowInfo(borrowNid);
        // 单期标
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(repayDelay.getBorrowStyle())
                || CustomConstants.BORROW_STYLE_END.equals(repayDelay.getBorrowStyle())) {
            BorrowRepay borrowRepay = this.getBorrowRepay(borrowNid);
            borrowRepay.setDelayDays(Integer.parseInt(delayDays));
            return this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay);
        } else {
            BorrowRepayPlan borrowRepay = this.getBorrowRepayPlan(borrowNid);
            borrowRepay.setDelayDays(Integer.parseInt(delayDays));
            return this.borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepay);
        }
    }

    private BorrowRepayPlan getBorrowRepayPlan(String borrowNid){
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andRepayTypeEqualTo("wait");
        cra.andRepayStatusEqualTo(0);
        example.setOrderByClause(" repay_period ASC ");

        List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return new BorrowRepayPlan();
    }

    /**
     * 统计分期还款用户延期还款的总标
     *
     * @param borrowRepayPlan
     * @param borrowNid
     * @param borrowApr
     * @param delayDays
     * @throws ParseException
     */
    private BorrowRepayPlanBean calculateRepayPlanDelay(BorrowRepayPlanBean borrowRepayPlan, String borrowNid,
                                                        BigDecimal borrowApr, int delayDays) throws ParseException {

        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrowNid,
                borrowRepayPlan.getRepayPeriod());
        // 统计借款用户还款总额
        BigDecimal userAccountTotal = new BigDecimal(0);
        // 统计借款用户总延期利息
        BigDecimal userDelayInterestTotal = new BigDecimal(0);
        if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
            for (int i = 0; i < borrowRecoverPlans.size(); i++) {
                // 获取未还款前用户能够获取的本息和
                BigDecimal userAccount = borrowRecoverPlans.get(i).getRecoverAccount();
                // 获取用户投资项目分期后的投资本金
                BigDecimal userCapital = borrowRecoverPlans.get(i).getRecoverCapital();
                // 计算用户实际获得的本息和
                BigDecimal userAccountFact = UnnormalRepayUtils.delayRepayPrincipalInterest(userAccount, userCapital,
                        borrowApr, delayDays);
                // 计算用户延期利息
                BigDecimal userDelayInterest = UnnormalRepayUtils.delayRepayInterest(userCapital, borrowApr, delayDays);

                borrowRecoverPlans.get(i).setDelayInterest(userDelayInterest);
                borrowRecoverPlans.get(i).setDelayDays(delayDays);

                // 统计总和
                userAccountTotal = userAccountTotal.add(userAccountFact);
                userDelayInterestTotal = userDelayInterestTotal.add(userDelayInterest);
            }
            borrowRepayPlan.setRecoverPlanList(borrowRecoverPlans);
        }
        borrowRepayPlan.setRepayAccountAll(userAccountTotal.add(borrowRepayPlan.getRepayFee()));
        borrowRepayPlan.setRepayAccount(userAccountTotal);
        borrowRepayPlan.setDelayDays(delayDays);
        borrowRepayPlan.setDelayInterest(userDelayInterestTotal);
        return borrowRepayPlan;
}

    /**
     * 统计分期还款用户逾期还款的总标
     *
     * @param borrowRepayPlan
     * @param borrowNid
     * @param borrowApr
     * @param delayDays
     * @param lateDays
     * @throws ParseException
     */
    private BorrowRepayPlanBean calculateRepayPlanLate(BorrowRepayPlanBean borrowRepayPlan, String borrowNid,
                                                       BigDecimal borrowApr, int delayDays, int lateDays) throws ParseException {

        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrowNid,
                borrowRepayPlan.getRepayPeriod());
        // 统计借款用户还款总额
        BigDecimal userAccountTotal = new BigDecimal(0);
        // 统计借款用户总延期利息
        BigDecimal userDelayInterestTotal = new BigDecimal(0);
        // 统计借款用户总逾期利息
        BigDecimal userOverdueInterestTotal = new BigDecimal(0);
        if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
            for (int i = 0; i < borrowRecoverPlans.size(); i++) {
                // 获取未还款前用户能够获取的本息和
                BigDecimal userAccount = borrowRecoverPlans.get(i).getRecoverAccount();
                // 获取用户投资项目分期后的投资本金
                BigDecimal userCapital = borrowRecoverPlans.get(i).getRecoverCapital();
                // 计算用户实际获得的本息和
                BigDecimal userAccountFact = UnnormalRepayUtils.overdueRepayPrincipalInterest(userAccount, userCapital,
                        borrowApr, delayDays, lateDays);
                // 计算用户逾期利息
                BigDecimal userOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(userAccount, lateDays);
                // 计算用户延期利息
                BigDecimal userDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(userCapital, borrowApr,
                        delayDays);
                // 保存相应的延期数据
                borrowRecoverPlans.get(i).setDelayInterest(userDelayInterest);
                borrowRecoverPlans.get(i).setDelayDays(delayDays);
                borrowRecoverPlans.get(i).setLateInterest(userOverdueInterest);
                borrowRecoverPlans.get(i).setLateDays(lateDays);
                // 统计总和
                userAccountTotal = userAccountTotal.add(userAccountFact);
                userDelayInterestTotal = userDelayInterestTotal.add(userDelayInterest);
                userOverdueInterestTotal = userOverdueInterestTotal.add(userOverdueInterest);
            }
            borrowRepayPlan.setRecoverPlanList(borrowRecoverPlans);
        }
        borrowRepayPlan.setRepayAccountAll(userAccountTotal.add(borrowRepayPlan.getRepayFee()));
        borrowRepayPlan.setRepayAccount(userAccountTotal);
        borrowRepayPlan.setDelayDays(delayDays);
        borrowRepayPlan.setDelayInterest(userDelayInterestTotal);
        borrowRepayPlan.setLateDays(lateDays);
        borrowRepayPlan.setLateInterest(userOverdueInterestTotal);
        return borrowRepayPlan;
    }

    /**
     * 查询投资用户分期的详情
     *
     * @param borrowNid
     * @param period
     * @return
     */
    private List<BorrowRecoverPlan> searchBorrowRecoverPlan(String borrowNid, int period) {
        BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
        BorrowRecoverPlanExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andRecoverPeriodEqualTo(period);
        List<BorrowRecoverPlan> borrowRecovers = borrowRecoverPlanMapper.selectByExample(example);
        return borrowRecovers;
    }
    /**
     * 统计单期还款用户延期还款的总标
     *
     * @param repay
     * @param borrowNid
     * @param borrowApr
     * @param delayDays
     */
    private void calculateRepayDelay(BorrowRepayBean repay, String borrowNid, BigDecimal borrowApr, int delayDays) {

        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        // 用户延期
        // 统计借款用户还款总额
        BigDecimal userAccountTotal = new BigDecimal(0);
        // 统计借款用户总延期利息
        BigDecimal userDelayInterestTotal = new BigDecimal(0);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            for (int i = 0; i < borrowRecovers.size(); i++) {
                // 获取未还款前用户能够获取的本息和
                BigDecimal userAccount = borrowRecovers.get(i).getRecoverAccount();
                // 获取用户投资项目分期后的投资本金
                BigDecimal userCapital = borrowRecovers.get(i).getRecoverCapital();
                // 计算用户实际获得的本息和
                BigDecimal userAccountFact = UnnormalRepayUtils.delayRepayPrincipalInterest(userAccount, userCapital,
                        borrowApr, delayDays);
                // 计算用户延期利息
                BigDecimal userDelayInterest = UnnormalRepayUtils.delayRepayInterest(userCapital, borrowApr, delayDays);
                borrowRecovers.get(i).setDelayInterest(userDelayInterest);
                borrowRecovers.get(i).setDelayDays(delayDays);
                // 统计总和
                userAccountTotal = userAccountTotal.add(userAccountFact);
                userDelayInterestTotal = userDelayInterestTotal.add(userDelayInterest);
            }
            repay.setRecoverList(borrowRecovers);
        }
        repay.setRepayAccountAll(userAccountTotal.add(repay.getRepayFee()));
        repay.setRepayAccount(userAccountTotal);
        repay.setDelayDays(delayDays);
        repay.setDelayInterest(userDelayInterestTotal);
    }

    /**
     * 统计单期还款用户逾期还款的总标
     *
     * @param repay
     * @param borrowNid
     * @param borrowApr
     * @param delayDays
     * @param lateDays
     * @throws ParseException
     */
    private void calculateRepayLate(BorrowRepayBean repay, String borrowNid, BigDecimal borrowApr, int delayDays,
                                    int lateDays) throws ParseException {

        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        // 用户逾期
        // 统计借款用户还款总额
        BigDecimal userAccountTotal = new BigDecimal(0);
        // 统计借款用户总延期利息
        BigDecimal userDelayInterestTotal = new BigDecimal(0);
        // 统计借款用户总逾期利息
        BigDecimal userOverdueInterestTotal = new BigDecimal(0);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            for (int i = 0; i < borrowRecovers.size(); i++) {
                // 获取未还款前用户能够获取的本息和
                BigDecimal userAccount = borrowRecovers.get(i).getRecoverAccount();
                // 获取用户投资项目分期后的投资本金
                BigDecimal userCapital = borrowRecovers.get(i).getRecoverCapital();
                // 计算用户实际获得的本息和
                BigDecimal userAccountFact = UnnormalRepayUtils.overdueRepayPrincipalInterest(userAccount, userCapital,
                        borrowApr, delayDays, lateDays);
                // 计算用户逾期利息
                BigDecimal userOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(userAccount, lateDays);
                // 计算用户延期利息
                BigDecimal userDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(userCapital, borrowApr,
                        delayDays);
                borrowRecovers.get(i).setDelayInterest(userDelayInterest);
                borrowRecovers.get(i).setDelayDays(delayDays);
                borrowRecovers.get(i).setLateInterest(userOverdueInterest);
                borrowRecovers.get(i).setLateDays(lateDays);
                // 统计总和
                userAccountTotal = userAccountTotal.add(userAccountFact);
                userDelayInterestTotal = userDelayInterestTotal.add(userDelayInterest);
                userOverdueInterestTotal = userOverdueInterestTotal.add(userOverdueInterest);
            }
            repay.setRecoverList(borrowRecovers);
        }
        repay.setRepayAccountAll(userAccountTotal.add(repay.getRepayFee()));
        repay.setRepayAccount(userAccountTotal);
        repay.setDelayDays(delayDays);
        repay.setDelayInterest(userDelayInterestTotal);
        repay.setLateDays(lateDays);
        repay.setLateInterest(userOverdueInterestTotal);
    }

    /**
     * 根据项目id查询相应的用户的待还款信息
     *
     * @param borrowNid
     * @return
     */
    private List<BorrowRecover> searchBorrowRecover(String borrowNid) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRecover> borrowRecovers = borrowRecoverMapper.selectByExample(example);
        return borrowRecovers;
    }
}