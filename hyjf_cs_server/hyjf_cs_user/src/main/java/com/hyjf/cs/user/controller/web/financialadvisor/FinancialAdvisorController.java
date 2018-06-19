/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.financialadvisor;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.user.service.financialadvisor.FinancialAdvisorService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version FinancialAdvisorController, v0.1 2018/6/15 19:09
 */
@Api(value = "web端风险测评接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class FinancialAdvisorController {

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

    /**
     * 插入评测数据并发券
     * @param userAnswer
     * @param userId
     * @return
     */
    public synchronized Map<String,Object> answerAnalysisAndCoupon(String userAnswer, Integer userId){
        Map<String,Object> returnMap  = new HashMap<String,Object>();
        //发放优惠券 start
        UserEvalationResultVO ueResult = financialAdvisorService.selectUserEvalationResultByUserId(userId);
        // 是否已经参加过测评（true：已测评过，false：测评）
        boolean isAdvisor = ueResult != null ? true : false;
        // 发放优惠券 end
        // 1_1,2_8
        UserEvalationResultVO userEvalationResult = financialAdvisorService.answerAnalysis(userAnswer, new Integer(userId));
        // 发放优惠券 start
        if(!isAdvisor){
            String platform = CustomConstants.CLIENT_PC;
            // 发放优惠券
            String result = financialAdvisorService.sendCoupon(userId, platform);
            if(StringUtils.isNotEmpty(result)){
                JSONObject resultObj = JSONObject.parseObject(result);
                if(resultObj.getIntValue("status") == 0 && resultObj.getIntValue("couponCount") > 0){
                    int sendCount = resultObj.getIntValue("couponCount");
                    returnMap.put("sendCount", sendCount);
                }
            }
        }
        returnMap.put("userEvalationResult", userEvalationResult);
        return returnMap;
        // 发放优惠券 end
    }
}
