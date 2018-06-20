/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.customize.QuestionCustomize;

import java.util.List;

/**
 * @author zhangqingqing
 * @version QuestionService, v0.1 2018/6/19 19:10
 */
public interface QuestionService {
    int countScore(List<String> answerList);

    List<QuestionCustomize> getNewQuestionList();
}
