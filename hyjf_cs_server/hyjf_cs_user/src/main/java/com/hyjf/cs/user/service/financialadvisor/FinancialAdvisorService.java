/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.financialadvisor;

import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.am.vo.user.UserEvalationResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version FinancialAdvisor, v0.1 2018/6/15 19:11
 */
public interface FinancialAdvisorService extends BaseUserService {
    /**
     * 测评问题
     * @return
     */
    List<QuestionCustomizeVO> getNewQuestionList();

    /**
     * 测评结果
     * @param userId
     * @return
     */
    UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId);

    /**
     * 计算用户测评分数
     * @param userAnswer
     * @param userId
     * @return
     */
     UserEvalationResultVO answerAnalysis(String userAnswer, Integer userId);

    /**
     * 发放优惠券
     * @param userId
     * @param platform
     * @return
     */
     String sendCoupon(int userId,String platform);

    /**
     * 活动有效期校验
     * @param activityId
     * @return
     */
    String checkActivityIfAvailable(String activityId);

    /**
     * 评分标准
     * @return
     */
    List<EvalationVO> getEvalationRecord();

    /**
     * 插入评测数据并发券
     * @param userAnswer
     * @param userId
     * @return
     */
    Map<String,Object> answerAnalysisAndCoupon(String userAnswer, Integer userId);
}
