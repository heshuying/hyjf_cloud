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
    List<QuestionCustomizeVO> getNewQuestionList();

    UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId);

    /**
     * 计算用户测评分数
     * @param userAnswer
     * @param userId
     * @return
     */
     UserEvalationResultVO answerAnalysis(String userAnswer, Integer userId);

     String sendCoupon(int userId,String platform);

    String checkActivityIfAvailable(String activityId);

    List<EvalationVO> getEvalationRecord();

    Map<String,Object> answerAnalysisAndCoupon(String userAnswer, Integer userId);
}
