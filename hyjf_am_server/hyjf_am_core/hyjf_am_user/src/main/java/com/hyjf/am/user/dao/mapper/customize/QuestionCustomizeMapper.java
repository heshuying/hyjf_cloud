package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.QuestionCustomize;

import java.util.List;

public interface QuestionCustomizeMapper {

    List<QuestionCustomize> getNewQuestionList();

    int countScore(List<String> answerList);

}