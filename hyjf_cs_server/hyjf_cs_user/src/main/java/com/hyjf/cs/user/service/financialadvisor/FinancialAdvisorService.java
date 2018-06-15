/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.financialadvisor;

import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.cs.user.service.BaseService;

import java.util.List;

/**
 * @author zhangqingqing
 * @version FinancialAdvisor, v0.1 2018/6/15 19:11
 */
public interface FinancialAdvisorService extends BaseService{
    List<QuestionCustomizeVO> getNewQuestionList();
}
