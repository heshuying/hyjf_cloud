/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.financialadvisor;

import com.hyjf.am.vo.user.*;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.financialadvisor.FinancialAdvisorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 再测一次
     *
     * @param
     * @return
     * @author
     */
    @ApiOperation(value = "再测一次", notes = "再测一次")
    @PostMapping(value = "/questionnaireInit", produces = "application/json; charset=utf-8")
    public Map<String, Object> questionnaireInit() {
        Map<String, Object> result = new HashMap<>();
        //测评问题
        List<QuestionCustomizeVO> list = financialAdvisorService.getNewQuestionList();
        result.put("questionList", list);
        result.put("listSize", list.size());
        //评分标准
        List<EvalationVO> evalationList = financialAdvisorService.getEvalationRecord();
        result.put("evalationList", evalationList);
        return result;
    }

    /**
     * 网站改版风险测评页面初始化
     *
     * @param
     * @param
     * @return
     * @author pcc
     */
    @ApiOperation(value = "网站改版风险测评页面初始化", notes = "网站改版风险测评页面初始化")
    @PostMapping(value = "/financialAdvisorInit", produces = "application/json; charset=utf-8")
    public WebResult<Map<String, Object>> financialAdvisorInit(@RequestHeader(value = "token", required = true) String token) {
        WebResult<Map<String, Object>> result = new WebResult<>();
        Map<String, Object> resultMap = new HashMap<>();
        CheckUtil.check(null != token, MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.financialAdvisorService.getUsers(token);
        // 用户ID
        Integer userId = user.getUserId();
        //userError 用户未登录
        CheckUtil.check(userId != null && userId != 0, MsgEnum.ERR_USER_NOT_LOGIN);

        //测评结果
        UserEvalationResultVO userEvalationResult = financialAdvisorService.selectUserEvalationResultByUserId(userId);
        //已测评
        if (userEvalationResult != null && userEvalationResult.getId() != 0) {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(userEvalationResult.getCreateTime(), 1, 1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5, 1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测
                //测评问题
                List<QuestionCustomizeVO> list = financialAdvisorService.getNewQuestionList();
                resultMap.put("questionList", list);
                //评分标准
                List<EvalationVO> evalationList = financialAdvisorService.getEvalationRecord();
                resultMap.put("evalationList", evalationList);
            } else {
                //测评结果
                resultMap.put("userEvalationResult", userEvalationResult);
            }
        } else {
            //测评问题
            List<QuestionCustomizeVO> list = financialAdvisorService.getNewQuestionList();
            resultMap.put("questionList", list);
            //评分标准
            List<EvalationVO> evalationList = financialAdvisorService.getEvalationRecord();
            resultMap.put("evalationList", evalationList);
        }
        result.setData(resultMap);
        return result;
    }

    /**
     * 用户测评结果
     *
     * @param
     * @param
     * @return
     * @author pcc
     */
    @ApiOperation(value = "用户测评结果", notes = "用户测评结果")
    @ApiImplicitParam(name = "param", value = "{userAnswer:string}", dataType = "Map")
    @PostMapping(value = "/evaluationResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String, Object>> evaluationResult(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String, String> param) {
        WebResult<Map<String, Object>> result = new WebResult<>();
        Map<String, Object> resultMap = new HashMap<>();
        //userError 用户未登录
        CheckUtil.check(userId != null && userId != 0, MsgEnum.ERR_USER_NOT_LOGIN);
        //用户答案
        String userAnswer = param.get("userAnswer");
        Map<String, Object> returnMap = financialAdvisorService.answerAnalysisAndCoupon(userAnswer, userId);
        //优惠券发放
        if (returnMap.get("sendCount") != null) {
            int sendCount = (int) returnMap.get("sendCount");
            resultMap.put("sendCount", sendCount);
        }
        UserEvalationResultVO userEvalationResult = (UserEvalationResultVO) returnMap.get("userEvalationResult");
        // userEvalationResult 测评结果
        resultMap.put("userEvalationResult", userEvalationResult);
        result.setData(resultMap);
        /**
         * 调用重新登录接口
         */
        WebViewUserVO webUser = financialAdvisorService.getWebViewUserByUserId(userId);
        if (null != webUser) {
            financialAdvisorService.setToken(webUser);
        }
        return result;
    }
}
