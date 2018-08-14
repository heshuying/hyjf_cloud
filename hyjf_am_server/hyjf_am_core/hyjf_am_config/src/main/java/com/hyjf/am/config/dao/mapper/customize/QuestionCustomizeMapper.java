package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.NewAppQuestionCustomize;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;

import java.util.List;

public interface QuestionCustomizeMapper {

    List<QuestionCustomize> getNewQuestionList();

    int countScore(List<String> answerList);

    List<NewAppQuestionCustomize> getNewAppQuestionList();



}