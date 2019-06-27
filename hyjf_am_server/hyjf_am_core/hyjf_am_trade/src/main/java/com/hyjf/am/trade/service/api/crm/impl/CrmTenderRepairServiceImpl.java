/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.crm.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.api.crm.CrmTenderRepairService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.InterestInfo;
import com.hyjf.common.validator.Validator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * CRM投资同步修复Service实现类
 *
 * @author liuyang
 * @version CrmTenderRepairServiceImpl, v0.1 2019/3/12 9:55
 */
@Service
public class CrmTenderRepairServiceImpl extends BaseServiceImpl implements CrmTenderRepairService {
    @Override
    public List<BorrowTender> selectCrmBorrowTenderList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        //这里会有一个异常，所以要用try catch捕获异常
        try {
            d = sdf.parse("2019-03-09");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria cra = example.createCriteria();
        cra.andAccedeOrderIdIsNull();
        cra.andCreateTimeGreaterThan(d);
        cra.andCreateTimeLessThanOrEqualTo(new Date());
        List<BorrowTender> list = this.borrowTenderMapper.selectByExample(example);
        return list;
    }


    @Override
    public List<HjhAccede> selectCrmHjhAccedeList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        //这里会有一个异常，所以要用try catch捕获异常
        try {
            d = sdf.parse("2019-03-09");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        cra.andCreateTimeGreaterThan(d);
        cra.andCreateTimeLessThanOrEqualTo(new Date());
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        return list;
    }



    @Override
    public void updateRepayData(String borrowNid)  {
        logger.info("标的{}开始修复数据", borrowNid);
        // 取标的
        Borrow borrow = getBorrowByNid(borrowNid);
        BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
        if(null == borrow){
            logger.error("标的号错误");
        }

        // 取borrow_repay
        BorrowRepay borrowRepay = getBorrowRepay(borrowNid);


        String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 借款期数
        // 年利率
        BigDecimal borrowApr = borrow.getBorrowApr();
        // 月利率(算出管理费用[上限])
        BigDecimal borrowMonthRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO
                : new BigDecimal(borrow.getManageFeeRate());
        // 月利率(算出管理费用[下限])
        BigDecimal borrowManagerScaleEnd = Validator.isNull(borrowInfo.getBorrowManagerScaleEnd()) ? BigDecimal.ZERO
                : new BigDecimal(borrowInfo.getBorrowManagerScaleEnd());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO
                : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = borrow.getVerifyTime();
        // 项目类型
        Integer projectType = borrowInfo.getProjectType();

        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

        // 投资数据
        List<BorrowTender> borrowTenderList = getBorrowTenderList(borrowNid);
        for (BorrowTender borrowTender : borrowTenderList) {

            // 出借金额
            BigDecimal account = borrowTender.getAccount();
            String ordId = borrowTender.getNid();// 出借订单号

            // 利息
            BigDecimal interestTender = BigDecimal.ZERO;
            // 本金
            BigDecimal capitalTender = BigDecimal.ZERO;
            // 本息
            BigDecimal amountTender = BigDecimal.ZERO;
            // 管理费
            BigDecimal recoverFee = BigDecimal.ZERO;
            // 估计还款时间
            Integer recoverTime = null;
            Integer nowTime = GetDate.getNowTime10();
            // 计算利息
            InterestInfo interestInfo = CalculatesUtil.getInterestInfo(account, borrowPeriod, borrowApr, borrowStyle,
                    nowTime, borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate, borrowVerifyTime);
            if (interestInfo != null) {
                interestTender = interestInfo.getRepayAccountInterest(); // 利息
                capitalTender = interestInfo.getRepayAccountCapital(); // 本金
                amountTender = interestInfo.getRepayAccount(); // 本息
                recoverTime = interestInfo.getRepayTime(); // 估计还款时间
                recoverFee = interestInfo.getFee(); // 总管理费
            }
            // ht_borrow_recover
            BorrowRecover borrowRecover = getBorrowRecoverByTenderNidBidNid(ordId, borrowNid);
            borrowRecover.setRecoverAccount(amountTender); // 预还金额
            borrowRecover.setRecoverInterest(interestTender); // 预还利息
            borrowRecover.setRecoverCapital(capitalTender); // 预还本金

            borrowRecover.setRecoverAccountWait(amountTender); // 未还金额
            borrowRecover.setRecoverInterestWait(interestTender); // 未还利息
            borrowRecover.setRecoverCapitalWait(capitalTender); // 未还本金
            borrowRecover.setRecoverFee(recoverFee); // 账户管理费
            borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover);

            // 更新出借详情表
            borrowTender.setRecoverAccountWait(borrowRecover.getRecoverAccount()); // 待收总额
            borrowTender.setRecoverAccountAll(borrowRecover.getRecoverAccount()); // 收款总额
            borrowTender.setRecoverAccountInterestWait(borrowRecover.getRecoverInterest()); // 待收利息
            borrowTender.setRecoverAccountInterest(borrowRecover.getRecoverInterest()); // 收款总利息
            borrowTender.setRecoverAccountCapitalWait(borrowRecover.getRecoverCapital()); // 待收本金
            borrowTender.setRecoverFee(recoverFee);// 管理费
            borrowTenderMapper.updateByPrimaryKeySelective(borrowTender);


            // ht_borrow 借款表每条累加数据，for循环外更新
            borrow.setRepayAccountAll(borrow.getRepayAccountAll().add(borrowRecover.getRecoverAccount())); // 应还款总额
            borrow.setRepayAccountInterest(borrow.getRepayAccountInterest().add(borrowRecover.getRecoverInterest())); // 总还款利息
            borrow.setRepayAccountCapital(borrow.getRepayAccountCapital().add(borrowRecover.getRecoverCapital())); // 总还款本金
            borrow.setRepayAccountWait(borrow.getRepayAccountWait().add(borrowRecover.getRecoverAccount())); // 未还款总额
            borrow.setRepayAccountInterestWait(borrow.getRepayAccountInterestWait().add(borrowRecover.getRecoverInterest())); // 未还款利息
            borrow.setRepayAccountCapitalWait(borrow.getRepayAccountCapitalWait().add(borrowRecover.getRecoverCapital())); // 未还款本金

            // ht_borrow_repay 累加 for循环外更新
            borrowRepay.setRepayFee(borrowRepay.getRepayFee().add(borrowRecover.getRecoverFee())); // 还款费用
            borrowRepay.setRepayAccount(borrowRepay.getRepayAccount().add(borrowRecover.getRecoverAccount())); // 预还金额
            borrowRepay.setRepayInterest(borrowRepay.getRepayInterest().add(borrowRecover.getRecoverInterest())); // 预还利息
            borrowRepay.setRepayCapital(borrowRepay.getRepayCapital().add(borrowRecover.getRecoverCapital())); // 预还本金

            // 是否分期
            if (isMonth) {
                if (interestInfo != null && interestInfo.getListMonthly() != null) {
                    // 更新分期还款计划表(ht_borrow_recover_plan)
                    for (int j = 0; j < interestInfo.getListMonthly().size(); j++) {
                        // 取borrow_recover_plan
                        BorrowRecoverPlan recoverPlan = getBorrowRecoverPlan(borrowNid, j + 1).get(0);
                        InterestInfo monthly = null;
                        monthly = interestInfo.getListMonthly().get(j);

                        recoverPlan.setRecoverAccount(monthly.getRepayAccount()); // 预还金额
                        recoverPlan.setRecoverInterest(monthly.getRepayAccountInterest()); // 预还利息
                        recoverPlan.setRecoverCapital(monthly.getRepayAccountCapital()); // 预还本金
                        recoverPlan.setRecoverFee(monthly.getFee()); // 预还管理费

                        recoverPlan.setRecoverAccountWait(monthly.getRepayAccount()); // 未还金额
                        recoverPlan.setRecoverCapitalWait(monthly.getRepayAccountCapital()); // 未还本金
                        recoverPlan.setRecoverInterestWait(monthly.getRepayAccountInterest()); // 未还利息


                        this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(recoverPlan);

                        // 更新总的还款计划表(ht_borrow_repay_plan)
                        BorrowRepayPlan repayPlan = getBorrowRepayPlan(borrowNid, j + 1);

                        repayPlan.setRepayFee(repayPlan.getRepayFee().add(recoverPlan.getRecoverFee())); // 还款费用
                        repayPlan.setRepayAccount(repayPlan.getRepayAccount().add(recoverPlan.getRecoverAccount())); // 预还金额
                        repayPlan.setRepayInterest(repayPlan.getRepayInterest().add(recoverPlan.getRecoverInterest())); // 预还利息
                        repayPlan.setRepayCapital(repayPlan.getRepayCapital().add(recoverPlan.getRecoverCapital())); // 预还本金
                        this.borrowRepayPlanMapper.updateByPrimaryKeySelective(repayPlan);

                        // 汇计划二期更新 分期的hyjf_hjh_debt_detail 12-8
                        HjhDebtDetail debtDetail = getDebtDetail(ordId, j + 1, borrowTender.getUserId());
                        debtDetail.setUserId(borrowTender.getUserId());// 出借人用户ID
                        debtDetail.setAccount(monthly.getRepayAccountCapital());// 出借金额或债转承接金额
                        debtDetail.setLoanCapital(monthly.getRepayAccountCapital());// 放款本金(应还本金)
                        debtDetail.setLoanInterest(monthly.getRepayAccountInterest());// 放款利息(应还利息)
                        debtDetail.setRepayTime(monthly.getRepayTime());// 应还时间
                        debtDetail.setRepayInterestWait(monthly.getRepayAccountInterest()); // 待还利息
                        debtDetail.setRepayCapitalWait(monthly.getRepayAccountCapital()); // 待还本金
                        debtDetail.setManageFee(monthly.getFee()); // 账户管理费
                        debtDetail.setBorrowStyle(borrow.getBorrowStyle());// 还款方式
                        hjhDebtDetailMapper.updateByPrimaryKeySelective(debtDetail);
                    }
                }
            } else {
                // 都是分期标的，不分期删除

            }

        }

        // 更新标的表
        borrowMapper.updateByPrimaryKeySelective(borrow);
        this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay);

    }

    /**
     * 取得借款列表
     * @param borrowNid
     * @return
     */
    private List<BorrowTender> getBorrowTenderList(String borrowNid) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        // 故意查询全部tender,为了计算服务费给部分成功的场景
//		criteria.andStatusNotEqualTo(1);
        example.setOrderByClause(" id asc ");
        List<BorrowTender> list = this.borrowTenderMapper.selectByExample(example);
        return list;
    }

    /**
     * 取得借款计划信息
     *
     * @return
     */
    private BorrowRepayPlan getBorrowRepayPlan(String borrowNid, Integer period) {
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        criteria.andRepayPeriodEqualTo(period);
        List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private HjhDebtDetail getDebtDetail(String tenderOrderId, Integer periodNow, int userId) {
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        example.createCriteria().andOrderIdEqualTo(tenderOrderId).andRepayPeriodEqualTo(periodNow).andUserIdEqualTo(userId);
        List<HjhDebtDetail> detailList = this.hjhDebtDetailMapper.selectByExample(example );
        if (detailList != null && detailList.size() > 0) {
            return detailList.get(0);
        }
        return null;
    }

    private BorrowRecover getBorrowRecoverByTenderNidBidNid(String tenderNid, String bidNid) {
        BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria borrowRecoverCra = borrowRecoverExample.createCriteria();
        borrowRecoverCra.andBorrowNidEqualTo(bidNid).andNidEqualTo(tenderNid);
        List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(borrowRecoverExample);
        if(CollectionUtils.isEmpty(borrowRecoverList)){
            return null;
        }
        return borrowRecoverList.get(0);
    }
}
