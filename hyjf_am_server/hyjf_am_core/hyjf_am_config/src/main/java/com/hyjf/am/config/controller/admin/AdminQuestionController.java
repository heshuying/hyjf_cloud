/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.Answer;
import com.hyjf.am.config.dao.model.auto.Question;
import com.hyjf.am.config.service.QuestionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AnswerResponse;
import com.hyjf.am.response.config.QuestionResponse;
import com.hyjf.am.vo.config.AnswerVO;
import com.hyjf.am.vo.config.QuestionVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author nxl
 * @version AdminQuestionController, v0.1 2018/8/8 14:20
 */
@RestController
@RequestMapping("/am-config/quesiontAndAnswer")
public class AdminQuestionController  extends BaseConfigController {
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/findAllQuestion")
    public QuestionResponse findAllQuestion() {
        logger.info("---findAllQuestion ---  " );
        QuestionResponse response = new QuestionResponse();
        response.setRtn(Response.FAIL);
        List<Question> questionList= questionService.getAllQuestion();
        if(null!=questionList&&questionList.size()>0){
            List<QuestionVO> questionVOList = CommonUtils.convertBeanList(questionList,QuestionVO.class);
            response.setResultList(questionVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    @RequestMapping("/findAllAnswer")
    public AnswerResponse findAllAnswer() {
        logger.info("---findAllAnswer ---  " );
        AnswerResponse response = new AnswerResponse();
        response.setRtn(Response.FAIL);
        List<Answer> questionList= questionService.getAllAnswer();
        if(null!=questionList&&questionList.size()>0){
            List<AnswerVO> questionVOList = CommonUtils.convertBeanList(questionList,AnswerVO.class);
            response.setResultList(questionVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
