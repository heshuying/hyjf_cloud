/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.config.dao.mapper.auto.AnswerMapper;
import com.hyjf.am.config.dao.mapper.auto.QuestionMapper;
import com.hyjf.am.config.dao.mapper.customize.QuestionCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.Answer;
import com.hyjf.am.config.dao.model.auto.AnswerExample;
import com.hyjf.am.config.dao.model.auto.Question;
import com.hyjf.am.config.dao.model.auto.QuestionExample;
import com.hyjf.am.config.dao.model.customize.NewAppQuestionCustomize;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;
import com.hyjf.am.config.service.QuestionService;

/**
 * @author zhangqingqing
 * @version QuestionServiceImpl, v0.1 2018/6/19 19:10
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionCustomizeMapper questionCustomizeMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    AnswerMapper answerMapper;

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
    //查找所有问题
    @Override
    public List<Question> getAllQuestion(){
        QuestionExample example = new QuestionExample();
        QuestionExample.Criteria cra = example.createCriteria();
        List<Question> templateList = this.questionMapper.selectByExample(example);
        return templateList;
    }
    //查找所有回答
    @Override
    public List<Answer> getAllAnswer(){
        AnswerExample example = new AnswerExample();
        AnswerExample.Criteria cra = example.createCriteria();
        List<Answer> templateList = this.answerMapper.selectByExample(example);
        return templateList;
    }
}
