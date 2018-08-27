/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Answer;
import com.hyjf.am.config.dao.model.auto.Question;
import com.hyjf.am.config.dao.model.customize.NewAppQuestionCustomize;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;

/**
 * @author zhangqingqing
 * @version QuestionService, v0.1 2018/6/19 19:10
 */
public interface QuestionService {
    int countScore(List<String> answerList);

    List<QuestionCustomize> getNewQuestionList();

    List<NewAppQuestionCustomize> getNewAppQuestionList();
    //查找所有问题
    List<Question> getAllQuestion();
    //查找所有回答
    List<Answer> getAllAnswer();
}
