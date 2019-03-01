package com.hyjf.am.trade.service.admin.borrow.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowRepaymentService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.BorrowRepayBeanVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.UnnormalRepayUtils;
import org.springframework.beans.BeanUtils;
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
    public int countBorrowRecover(BorrowRepaymentRequest request) {
        return this.borrowRepaymentCustomizeMapper.countBorrowRepayment(request);
    }

    @Override
    public List<AdminBorrowRepaymentCustomize> selectBorrowRepaymentList(BorrowRepaymentRequest request) {
        return this.borrowRepaymentCustomizeMapper.selectBorrowRepaymentList(request);
    }

    @Override
    public BorrowRepayBean getBorrowRepayInfo(String borrowNid, String borrowApr, String borrowStyle) throws ParseException{
        BorrowRepayExample example = new BorrowRepayExample();
        BorrowRepayExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepay> list = this.borrowRepayMapper.selectByExample(example);
        logger.info("list.size():" +list.size());
        if (list != null && list.size() > 0) {
            BorrowRepayBean borrowRepayBean = new BorrowRepayBean();
            BorrowRepay borrowRepay = list.get(0);
            BeanUtils.copyProperties(borrowRepay, borrowRepayBean);
            logger.info("borrowRepayBean:" +JSONObject.toJSON(borrowRepayBean));
            Date nowDate = new Date();
            Date date = new Date(Long.valueOf(borrowRepayBean.getRepayTime()) * 1000L);
            int distanceDays = GetDate.daysBetween(nowDate, date);
            // 提前还款
            if (distanceDays >= 0) {
                // 获取提前还款的阀值
                String repayAdvanceDay = this.getBorrowConfig("REPAY_ADVANCE_TIME");
                int advanceDays = distanceDays;
                // 未大于提前还款的阀值（正常还款）
                if (advanceDays <= Integer.parseInt(repayAdvanceDay)) {
                    // 计算正常还款利息
                    calculateRepay(borrowRepayBean, borrowNid, new BigDecimal(borrowApr), advanceDays);
                } else {// 大于提前还款阀值（提前还款）
                    // 计算提前还款利息
                    calculateRepayAdvance(borrowRepayBean, borrowNid, new BigDecimal(borrowApr), advanceDays);
                }
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
            borrowRepayBean
                    .setRepayTimeStr(GetDate.getDateMyTimeInMillis(borrowRepayBean.getRepayTime()));
            // 判断当前期是否在还款
            BorrowApicronExample exampleBorrowApicron = new BorrowApicronExample();
            BorrowApicronExample.Criteria crtBorrowApicron = exampleBorrowApicron.createCriteria();
            crtBorrowApicron.andBorrowNidEqualTo(borrowNid);
            crtBorrowApicron.andPeriodNowEqualTo(1);
            crtBorrowApicron.andApiTypeEqualTo(1);
            List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(exampleBorrowApicron);

            if (borrowApicrons != null && borrowApicrons.size() > 0) {
                BorrowApicron borrowApicron = borrowApicrons.get(0);
                if (borrowApicron.getRepayStatus() == null) { // 正在还款当前期
                    borrowRepayBean.setBorrowStatus("0");
                } else {// 用户未还款当前期
                    borrowRepayBean.setBorrowStatus("1");
                }
            } else {
                borrowRepayBean.setBorrowStatus("0");
            }
            logger.info("borrowRepayBean:" +JSONObject.toJSON(borrowRepayBean));
            return borrowRepayBean;
        }

        return new BorrowRepayBean();
    }
    /**
     * 统计单期还款用户正常还款的总标
     *
     * @param repay
     * @param borrowNid
     * @param borrowApr
     * @param interestDay
     * @throws ParseException
     */
    private void calculateRepay(BorrowRepayBean repay, String borrowNid, BigDecimal borrowApr, int interestDay)
            throws ParseException {
        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            for (int i = 0; i < borrowRecovers.size(); i++) {
                borrowRecovers.get(i).setChargeDays(interestDay);
            }
            repay.setRecoverList(borrowRecovers);
        }
        // 正常还款
        repay.setRepayAccountAll(repay.getRepayAccount().add(repay.getRepayFee()));
        repay.setChargeDays(interestDay);
    }

    /**
     * 统计单期还款用户提前还款的总标
     *
     * @param repay
     * @param borrowNid
     * @param borrowApr
     * @param interestDay
     * @throws ParseException
     */
    private void calculateRepayAdvance(BorrowRepayBean repay, String borrowNid, BigDecimal borrowApr, int interestDay)
            throws ParseException {

        List<BorrowRecover> borrowRecovers = this.searchBorrowRecover(borrowNid);
        // 用户实际还款额
        BigDecimal userAccountTotal = new BigDecimal(0);
        // 用户提前还款利息
        BigDecimal repayChargeInterest = new BigDecimal(0);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                String recoverTime = GetDate
                        .getDateTimeMyTimeInMillis(borrowRecover.getRecoverTime());
                String createTime = GetDate.getDateTimeMyTimeInMillis(borrowRecover.getCreateTime());
                // 获取这两个时间之间有多少天
                int totalDays = GetDate.daysBetween(createTime, recoverTime);
                // 获取未还款前用户能够获取的本息和
                BigDecimal userAccount = borrowRecover.getRecoverAccount();
                // 获取用户出借项目分期后的出借本金
                BigDecimal userCapital = borrowRecover.getRecoverCapital();
                // 计算用户实际获得的本息和
                BigDecimal userAccountFact = new BigDecimal(0);
                // 计算用户提前还款减少的的利息
                BigDecimal userChargeInterest = new BigDecimal(0);
                // 提前还款不应该大于本次计息时间
                if (totalDays < interestDay) {
                    // 计算出借用户实际获得的本息和
                    userAccountFact = UnnormalRepayUtils.aheadRepayPrincipalInterest(userAccount, userCapital,
                            borrowApr, totalDays);
                    // 用户提前还款减少的利息
                    userChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(userCapital, borrowApr, totalDays);
                } else {
                    // 计算出借用户实际获得的本息和
                    userAccountFact = UnnormalRepayUtils.aheadRepayPrincipalInterest(userAccount, userCapital,
                            borrowApr, interestDay);
                    // 用户提前还款减少的利息
                    userChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(userCapital, borrowApr,
                            interestDay);
                }
                borrowRecovers.get(i).setChargeDays(interestDay);
                borrowRecovers.get(i).setChargeInterest(userChargeInterest);
                // 统计本息总和
                userAccountTotal = userAccountTotal.add(userAccountFact);
                // 统计提前还款减少的利息
                repayChargeInterest = repayChargeInterest.add(userChargeInterest);
            }
            repay.setRecoverList(borrowRecovers);
        }
        repay.setRepayAccount(userAccountTotal);
        repay.setRepayAccountAll(userAccountTotal.add(repay.getRepayFee()));
        repay.setChargeDays(interestDay);
        repay.setChargeInterest(repayChargeInterest);
    }

    @Override
    public BorrowRepayPlanBean getBorrowRepayPlanInfo(String borrowNid, String borrowApr, String borrowStyle) throws ParseException{
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andRepayTypeEqualTo("wait");
        cra.andRepayStatusEqualTo(0);
        example.setOrderByClause(" repay_period ASC ");
        List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);
        logger.info("list.size():" +list.size());
        if (list != null && list.size() > 0) {
            BorrowRepayPlanBean repayPlanBean = new BorrowRepayPlanBean();
            BorrowRepayPlan repayPlan = list.get(0);
            BeanUtils.copyProperties(repayPlan, repayPlanBean);
            logger.info("borrowRepayBean:" +JSONObject.toJSON(repayPlanBean));
            Date nowDate = new Date();
            Date date = new Date(Long.valueOf(repayPlan.getRepayTime()) * 1000L);

            // 获取实际还款同计划还款时间的时间差
            int distanceDays = GetDate.daysBetween(nowDate, date);
            // 提前还款
            if (distanceDays >= 0) {
                // 获取提前还款的阀值
                String repayAdvanceDay = this.getBorrowConfig("REPAY_ADVANCE_TIME");
                int advanceDays = distanceDays;
                // 未大于提前还款的阀值（正常还款）
                if (advanceDays <= Integer.parseInt(repayAdvanceDay)) {
                    // 计算正常还款利息
                    calculateRepayPlan(repayPlanBean, borrowNid, new BigDecimal(borrowApr), advanceDays);
                } else {
                    // 大于提前还款阀值（提前还款）
                    String repayTimeStart = null;
                    // 获取上次提前还款的时间
                    BorrowRepayPlanExample exampleLast = new BorrowRepayPlanExample();
                    BorrowRepayPlanExample.Criteria craLast = exampleLast.createCriteria();
                    craLast.andBorrowNidEqualTo(borrowNid);
                    craLast.andRepayTypeEqualTo("wait_yes");
                    craLast.andRepayStatusEqualTo(1);
                    exampleLast.setOrderByClause(" repay_period DESC ");
                    List<BorrowRepayPlan> listLast = this.borrowRepayPlanMapper.selectByExample(exampleLast);
                    if (listLast != null && listLast.size() > 0) {
                        repayTimeStart = listLast.get(0).getRepayTime()+"";
                    } else {
                        repayTimeStart = GetDate.getDateTimeMyTimeInMillis(repayPlanBean.getCreateTime());
                    }
                    calculateRepayPlanAdvance(repayPlanBean, borrowNid, new BigDecimal(borrowApr), advanceDays,
                            repayTimeStart);
                }
            } else {
                // 延迟天数
                int delayDays = repayPlan.getDelayDays().intValue();
                int lateDays = delayDays + distanceDays;
                // 用户延期还款（未逾期）
                if (lateDays >= 0) {
                    delayDays = -distanceDays;
                    calculateRepayPlanDelay(repayPlanBean, borrowNid, new BigDecimal(borrowApr), delayDays);
                } else {
                    // 用户逾期还款
                    lateDays = -lateDays;
                    calculateRepayPlanLate(repayPlanBean, borrowNid, new BigDecimal(borrowApr), delayDays, lateDays);
                }
            }
            repayPlanBean
                    .setRepayTimeStr(GetDate.getDateMyTimeInMillis(repayPlanBean.getRepayTime()));
            // 如果用户不是还款最后一期
            int repayPeriod = repayPlanBean.getRepayPeriod();
            BorrowApicronExample exampleBorrowApicron = new BorrowApicronExample();
            BorrowApicronExample.Criteria crtBorrowApicron = exampleBorrowApicron.createCriteria();
            crtBorrowApicron.andBorrowNidEqualTo(borrowNid);
            crtBorrowApicron.andApiTypeEqualTo(1);
            crtBorrowApicron.andPeriodNowEqualTo(repayPeriod);
            List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(exampleBorrowApicron);
            // 正在还款当前期
            if (borrowApicrons != null && borrowApicrons.size() > 0) {
                BorrowApicron borrowApicron = borrowApicrons.get(0);
                if (borrowApicron.getRepayStatus() == null) {
                    repayPlanBean.setBorrowStatus("0");
                } else {
                    repayPlanBean.setBorrowStatus("1");
                }
            } else {// 用户当前期未还款
                repayPlanBean.setBorrowStatus("0");
            }
            logger.info("borrowRepayBean:" +JSONObject.toJSON(repayPlanBean));
            return repayPlanBean;
        }
        return new BorrowRepayPlanBean();
    }

    @Override
    public int countBorrowRepaymentPlan(BorrowRepaymentRequest request) {
        return this.borrowRepaymentCustomizeMapper.countBorrowRepaymentPlan(request);
    }

    @Override
    public List<AdminBorrowRepaymentPlanCustomize> selectBorrowRepaymentPlanList(BorrowRepaymentRequest request) {
        return this.borrowRepaymentCustomizeMapper.selectBorrowRepaymentPlanList(request);
    }

    @Override
    public AdminBorrowRepaymentPlanCustomize sumBorrowRepaymentPlanInfo(BorrowRepaymentRequest request) {
        return this.borrowRepaymentCustomizeMapper.sumBorrowRepaymentPlanInfo(request);
    }

    /**
     * 统计分期还款用户正常还款的总标
     *
     * @param borrowRepayPlan
     * @param borrowNid
     * @param borrowRepayPlan
     * @param borrowApr
     * @param interestDay
     * @throws ParseException
     */
    private BorrowRepayPlanBean calculateRepayPlan(BorrowRepayPlanBean borrowRepayPlan, String borrowNid,
                                                   BigDecimal borrowApr, int interestDay) throws ParseException {

        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrowNid,
                borrowRepayPlan.getRepayPeriod());
        if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
            borrowRepayPlan.setChargeDays(interestDay);
            for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                borrowRecoverPlans.get(j).setChargeDays(interestDay);
            }
            borrowRepayPlan.setRepayAccountAll(borrowRepayPlan.getRepayAccount().add(borrowRepayPlan.getRepayFee()));
            borrowRepayPlan.setRecoverPlanList(borrowRecoverPlans);
        }
        return borrowRepayPlan;
    }

    /**
     * 统计分期还款用户提前还款的总标
     *
     * @param repayTimeStart
     * @param borrowNid
     * @param borrowApr
     * @param repayTimeStart
     * @return
     * @throws ParseException
     */
    private BorrowRepayPlanBean calculateRepayPlanAdvance(BorrowRepayPlanBean borrowRepayPlan, String borrowNid,
                                                          BigDecimal borrowApr, int advanceDays, String repayTimeStart) throws ParseException {

        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrowNid, repayPeriod);
        // 用户实际还款额
        BigDecimal repayTotal = new BigDecimal(0);
        // 用户提前还款利息
        BigDecimal repayChargeInterest = new BigDecimal(0);
        Borrow borrow = getBorrowByNid(borrowNid);
        if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
            for (int i = 0; i < borrowRecoverPlans.size(); i++) {
                BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(i);
                String recoverTime = GetDate
                        .getDateTimeMyTimeInMillis(borrowRecoverPlan.getRecoverTime());
                // 获取这两个时间之间有多少天
                int totalDays = GetDate.daysBetween(repayTimeStart, recoverTime);
                // 获取未还款前用户能够获取的本息和
                BigDecimal userAccount = borrowRecoverPlan.getRecoverAccount();
                // 获取用户出借项目分期后的出借本金
                BigDecimal userCapital = borrowRecoverPlan.getRecoverCapital();
                // 用户获得的利息
                // 计算用户实际获得的本息和
                BigDecimal userAccountFact = new BigDecimal(0);
                // 计算用户提前还款减少的的利息
                BigDecimal userChargeInterest = new BigDecimal(0);
                //利息
                BigDecimal userInterest = borrowRecoverPlan.getRecoverInterest();
                // 提前还款不应该大于本次计息时间
                //判断是否为先息后本
                boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle());
                // 提前还款不应该大于本次计息时间
                if (totalDays < advanceDays) {
                    // 计算出借用户实际获得的本息和
                    userAccountFact = UnnormalRepayUtils.aheadRepayPrincipalInterest(userAccount, userCapital,
                            borrowApr, totalDays);

                    userChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(userCapital, borrowApr, totalDays);

                } else {
                    // 计算出借用户实际获得的本息和
                    userAccountFact = UnnormalRepayUtils.aheadRepayPrincipalInterest(userAccount, userCapital,
                            borrowApr, advanceDays);

                    userChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(userCapital, borrowApr,
                            advanceDays);

                }
                if(isStyle){
                    if(advanceDays >= 30){
                        userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,totalDays);
                    }else{
                        userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,advanceDays);
                    }
                }
                borrowRecoverPlans.get(i).setChargeDays(advanceDays);
                borrowRecoverPlans.get(i).setChargeInterest(userChargeInterest);
                repayTotal = repayTotal.add(userAccountFact);
                repayChargeInterest = repayChargeInterest.add(userChargeInterest);
            }
            borrowRepayPlan.setRecoverPlanList(borrowRecoverPlans);
        }
        borrowRepayPlan.setChargeDays(advanceDays);
        borrowRepayPlan.setChargeInterest(repayChargeInterest);
        borrowRepayPlan.setRepayAccount(repayTotal);
        borrowRepayPlan.setRepayAccountAll(repayTotal.add(borrowRepayPlan.getRepayFee()));
        return borrowRepayPlan;
    }

    public Borrow getBorrowByNid(String borrowNid) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<Borrow> list = borrowMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    @Override
    public AdminBorrowRepaymentCustomize sumBorrowRecoverList(BorrowRepaymentRequest request) {
        return this.borrowRepaymentCustomizeMapper.sumBorrowRepayment(request);
    }

    @Override
    public List<AdminBorrowRepaymentPlanCustomize> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request) {
        return this.borrowRepaymentCustomizeMapper.exportRepayClkActBorrowRepaymentInfoList(request);
    }
    @Override
    public int exportRepayClkActBorrowRepaymentInfoListCount(BorrowRepaymentPlanRequest request) {
        return this.borrowRepaymentCustomizeMapper.exportRepayClkActBorrowRepaymentInfoListCount(request);
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
                // 获取用户出借项目分期后的出借本金
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
                // 获取用户出借项目分期后的出借本金
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
     * 查询出借用户分期的详情
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
                // 获取用户出借项目分期后的出借本金
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
                // 获取用户出借项目分期后的出借本金
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
