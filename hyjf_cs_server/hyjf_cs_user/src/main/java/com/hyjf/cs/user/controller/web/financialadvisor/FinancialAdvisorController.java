/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.financialadvisor;

import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.financialadvisor.FinancialAdvisorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author zhangqingqing
 * @version FinancialAdvisorController, v0.1 2018/6/15 19:09
 */
@Api(value = "web端风险测评接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class FinancialAdvisorController extends BaseUserController {

    @Autowired
    FinancialAdvisorService financialAdvisorService;
    /**
     *
     * 再测一次
     * @author
     * @param
     * @return
     */
  /*  @RequestMapping("/questionnaireInit")
    public ModelAndView questionnaireInit() {
        //测评问题
        List<QuestionCustomizeVO> list=financialAdvisorService.getNewQuestionList();
        modelAndView.addObject("questionList",list);
        modelAndView.addObject("listSize",list.size());
        //评分标准
        List<Evalation> evalationList = financialAdvisorService.getEvalationRecord();
        modelAndView.addObject("evalationList",evalationList);
        LogUtil.endLog(FinancialAdvisorController.class.toString(), FinancialAdvisorDefine.QUESTIONNAIRE_INIT_ACTION);
        return modelAndView;
    }*/
}
