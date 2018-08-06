/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.customize.QuestionCustomizeMapper;
import com.hyjf.am.config.dao.model.customize.NewAppQuestionCustomize;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;
import com.hyjf.am.config.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version QuestionServiceImpl, v0.1 2018/6/19 19:10
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionCustomizeMapper questionCustomizeMapper;

    @Override
    public int countScore(List<String> answerList) {
        int countScore = questionCustomizeMapper.countScore(answerList);
        return countScore;
    }

    @Override
    public List<QuestionCustomize> getNewQuestionList() {
        List<QuestionCustomize> customizes = questionCustomizeMapper.getNewQuestionList();
        return customizes;
    }

    @Override
    public List<NewAppQuestionCustomize> getNewAppQuestionList() {
        List<NewAppQuestionCustomize> customizes = questionCustomizeMapper.getNewAppQuestionList();
        return customizes;
    }
}
