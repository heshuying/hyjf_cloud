/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.financialadvisor.impl;

import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.financialadvisor.FinancialAdvisorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version FinancialAdvisorImpl, v0.1 2018/6/15 19:11
 */
@Service
public class FinancialAdvisorServiceImpl extends BaseUserServiceImpl implements FinancialAdvisorService {

    @Override
    public List<QuestionCustomizeVO> getNewQuestionList() {
        return null;
    }
}
