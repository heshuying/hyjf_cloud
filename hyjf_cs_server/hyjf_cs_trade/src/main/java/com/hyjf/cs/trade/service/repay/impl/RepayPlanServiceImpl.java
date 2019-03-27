package com.hyjf.cs.trade.service.repay.impl;

import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.InterestInfo;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.BorrowRepayPlanCsVO;
import com.hyjf.cs.trade.bean.repay.RepayPlanBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.repay.RepayPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepayPlanServiceImpl implements RepayPlanService {

    @Autowired
    private AmTradeClient borrowClient;

    @Override
    public List<BorrowRepayPlanCsVO> getRepayPlan(String borrowNid) {
        BorrowAndInfoVO borrow = borrowClient.selectBorrowByNid(borrowNid);
        String borrowStyle = borrow.getBorrowStyle();
        Integer projectType = borrow.getProjectType();
        BigDecimal yearRate = borrow.getBorrowApr();
        Integer totalMonth = borrow.getBorrowPeriod();
        BigDecimal account = borrow.getAccount();
        Integer time = borrow.getReverifyTime();
        if (time == null) {
            time = (int) (System.currentTimeMillis() / 1000);
        }
        List<BorrowRepayPlanCsVO> repayPlans = new ArrayList<BorrowRepayPlanCsVO>();
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 月利率(算出管理费用[上限])
        BigDecimal borrowMonthRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 月利率(算出管理费用[下限])
        BigDecimal borrowManagerScaleEnd = Validator.isNull(borrow.getBorrowManagerScaleEnd()) ? BigDecimal.ZERO : new BigDecimal(borrow.getBorrowManagerScaleEnd());
        // 按月计息到期还本还息和按天计息，到期还本还息
        if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
            InterestInfo info = CalculatesUtil.getInterestInfo(account, totalMonth, yearRate, borrowStyle, time, borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate,
                    borrowVerifyTime);
            if (info != null) {
                String repayTime = "-";
                // 通过复审
                if (borrow.getStatus() >= 3 && borrow.getReverifyStatus() > 0) {
                    repayTime = GetDate.formatDate(GetDate.getDate(info.getRepayTime() * 1000L));
                }
                BorrowRepayPlanCsVO plan = new BorrowRepayPlanCsVO();
                plan.setRepayPeriod("1");
                plan.setRepayTime(repayTime);
                plan.setRepayType("本息");
                plan.setRepayTotal(info.getRepayAccount().toString());
                repayPlans.add(plan);
            }
        } else {// 等额本息和等额本金和先息后本
            InterestInfo info = CalculatesUtil.getInterestInfo(account, totalMonth, yearRate, borrowStyle, time, borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate,
                    borrowVerifyTime);
            if (info.getListMonthly() != null) {
                String repayTime = "-";
                for (int i = 0; i < info.getListMonthly().size(); i++) {
                    InterestInfo sub = info.getListMonthly().get(i);
                    // 通过复审
                    if (borrow.getStatus() >= 3 && borrow.getReverifyStatus() > 0) {
                        repayTime = GetDate.formatDate(GetDate.getDate(sub.getRepayTime() * 1000L));
                    }
                    String repayType = "本息";
                    if (i < info.getListMonthly().size() - 1 && borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
                        repayType = "利息";
                    }
                    BorrowRepayPlanCsVO plan = new BorrowRepayPlanCsVO();
                    plan.setRepayPeriod(sub.getMontyNo() + "");
                    plan.setRepayTime(repayTime);
                    plan.setRepayType(repayType);
                    plan.setRepayTotal(sub.getRepayAccount().toString());
                    repayPlans.add(plan);
                }
            }
        }
        return repayPlans;
    }


    @Override
    public List<RepayPlanBean> getAppRepayPlan(String borrowNid) {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        BorrowAndInfoVO borrow = borrowClient.selectBorrowByNid(borrowNid);
        String borrowStyle = borrow.getBorrowStyle();
        Integer projectType = borrow.getProjectType();
        BigDecimal yearRate = borrow.getBorrowApr();
        Integer totalMonth = borrow.getBorrowPeriod();
        BigDecimal account = borrow.getAccount();
        Integer time = borrow.getReverifyTime();
        if (time == null) {
            time = (int) (System.currentTimeMillis() / 1000);
        }
        List<RepayPlanBean> repayPlans = new ArrayList<RepayPlanBean>();
        // 月利率(算出管理费用[上限])
        BigDecimal borrowMonthRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 月利率(算出管理费用[下限])
        BigDecimal borrowManagerScaleEnd = Validator.isNull(borrow.getBorrowManagerScaleEnd()) ? BigDecimal.ZERO : new BigDecimal(borrow.getBorrowManagerScaleEnd());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 按月计息到期还本还息和按天计息，到期还本还息
        if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
            InterestInfo info = CalculatesUtil.getInterestInfo(account, totalMonth, yearRate, borrowStyle, time, borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate,
                    borrowVerifyTime);
            if (info != null) {
                String repayTime = "-";
                // 通过复审
                if (borrow.getReverifyStatus() == 6) {
                    repayTime = GetDate.formatDate(GetDate.getDate(info.getRepayTime() * 1000L));
                }
                RepayPlanBean plan = new RepayPlanBean(repayTime, df.format(info.getRepayAccountCapital().add(info.getRepayAccountInterest())), "第1期");
                repayPlans.add(plan);
            }
        } else {// 等额本息和等额本金和先息后本
            InterestInfo info = CalculatesUtil.getInterestInfo(account, totalMonth, yearRate, borrowStyle, time, borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate,
                    borrowVerifyTime);
            if (info.getListMonthly() != null) {
                String repayTime = "-";
                for (int i = 0; i < info.getListMonthly().size(); i++) {
                    InterestInfo sub = info.getListMonthly().get(i);
                    // 通过复审
                    if (borrow.getReverifyStatus() == 6) {
                        repayTime = GetDate.formatDate(GetDate.getDate(sub.getRepayTime() * 1000L));
                    }

                    RepayPlanBean plan = new RepayPlanBean(repayTime, df.format(sub.getRepayAccount()), "第"+(i+1)+"期");
                    repayPlans.add(plan);
                }
            }
        }
        return repayPlans;
    }
}
