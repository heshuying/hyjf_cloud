package com.hyjf.cs.market.controller;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.cs.market.service.Activity51Service;
import com.hyjf.cs.market.vo.Activity51VO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author xiasq
 * @version AbstractActivity51Controller, v0.1 2019/4/22 14:10
 */
public class AbstractActivity51Controller {
    private Logger logger = LoggerFactory.getLogger(AbstractActivity51Controller.class);


    /**
     * 计算进度百分比因子
     */
    protected final BigDecimal MULTIPLY = new BigDecimal(100);
    protected final BigDecimal DIVIDE = new BigDecimal(100000000);
    /**
     * 第一档到末档金额区间
     */
    protected final BigDecimal SUM_AMOUNT_GRADE_1 = new BigDecimal(30000000);
    protected final BigDecimal SUM_AMOUNT_GRADE_2 = new BigDecimal(50000000);
    protected final BigDecimal SUM_AMOUNT_GRADE_3 = new BigDecimal(80000000);
    protected final BigDecimal SUM_AMOUNT_GRADE_4 = new BigDecimal(100000000);

    @Autowired
    protected Activity51Service activity51Service;

    protected BaseResult<Activity51VO> getSumAmount(String status, String statusDesc, String failStatus, String failStatusDesc) {
        BaseResult<Activity51VO> result = new BaseResult(status, statusDesc);
        if (!activity51Service.isActivityTime()) {
            return buildResult(failStatus, failStatusDesc);
        }
        BigDecimal sumAmount = activity51Service.getSumAmount();
        sumAmount = sumAmount == null ? BigDecimal.ZERO : sumAmount;
        String rate = String.format("%.2f", sumAmount.multiply(MULTIPLY).divide(DIVIDE)).concat("%");
        if (sumAmount.compareTo(DIVIDE) == 1) {
            rate = "100.00%";
        }
        logger.info("当前累计出借金额sumAmount: {}, 进度rate: {}", sumAmount, rate);
        result.setData(new Activity51VO(sumAmount, rate, getGradeFromSumAmount(sumAmount)));
        return result;
    }

    protected int getGradeFromSumAmount(BigDecimal sumAmount) {
        // 第一档奖励
        if (sumAmount.compareTo(SUM_AMOUNT_GRADE_4) >= 0) {
            return 4;
        }
        // 第二档奖励
        if (sumAmount.compareTo(SUM_AMOUNT_GRADE_3) >= 0) {
            return 3;
        }
        // 第三档奖励
        if (sumAmount.compareTo(SUM_AMOUNT_GRADE_2) >= 0) {
            return 2;
        }
        // 末等奖励
        if (sumAmount.compareTo(SUM_AMOUNT_GRADE_1) >= 0) {
            return 1;
        }
        return 0;
    }

    protected BaseResult buildResult(String code, String msg) {
        BaseResult result = new BaseResult();
        result.setStatus(code);
        result.setStatusDesc(msg);
        return result;
    }
}
