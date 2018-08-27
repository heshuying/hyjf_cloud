package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.config.dao.model.customize.NewAppQuestionCustomize;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;

public interface QuestionCustomizeMapper {

    List<QuestionCustomize> getNewQuestionList();

    int countScore(List<String> answerList);

    List<NewAppQuestionCustomize> getNewAppQuestionList();



}