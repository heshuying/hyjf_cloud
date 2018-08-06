/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.riskassesment;

import com.hyjf.am.vo.config.NewAppQuestionCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 风险测评
 * @author zhangqingqing
 * @version AppRiskAssesmentController, v0.1 2018/8/3 17:17
 */
public class AppRiskAssesmentController {

    private final String TOKEN_ISINVALID_STATUS = "Token失效，请重新登录";

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    SystemConfig systemConfig;
    /**
     * 风险测评
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/riskTest")
    public RiskAssesmentResponse getQuestionList(HttpServletRequest request) {

        RiskAssesmentResponse response = new RiskAssesmentResponse();

        String sign = request.getParameter("sign");
        if (sign == null) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc("参数非法");
            return response;
        }
        String platform = request.getParameter("platform");
        Integer userId = null;
        try {
            userId = SecretUtil.getUserId(sign);
        } catch (Exception e) { // token失效
            response.setStatus(CustomConstants.APP_STATUS_FAIL);
            response.setStatusDesc(TOKEN_ISINVALID_STATUS);
            return response;
        }
        if (userId == null || userId == 0) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc("用户未登录");
            return response;
        }

        UserEvalationResultVO ueResult = evaluationService.selectUserEvalationResultByUserId(userId);
        if (ueResult == null) {
            // 未测评
            String activityId = systemConfig.getActivityId();
            // 活动有效期校验
            String resultActivity = evaluationService.checkActivityIfAvailable(activityId);
            // 终端平台校验
            String resultPlatform = evaluationService.checkActivityPlatform(activityId, platform);
            String couponResult = StringUtils.EMPTY;
            if (StringUtils.isEmpty(resultActivity) && StringUtils.isEmpty(resultPlatform)) {
                couponResult = "首次完成评测，可以获得1张加息券";
            }
            response.setCouponResult(couponResult);
            response.setResultStatus("0");
        } else {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(ueResult.getCreateTime(),1,1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5,1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测
                response.setResultStatus("2");
            }
            // 已测评
            response.setResultStatus("1");
            response.setResultType(ueResult.getEvalType());
            response.setResultText(ueResult.getSummary());
        }
        // 返回题目
        List<NewAppQuestionCustomizeVO> list = evaluationService.getNewAppQuestionList();
        for (NewAppQuestionCustomizeVO newAppQuestionCustomize : list) {
            String title = newAppQuestionCustomize.getTitle().substring(newAppQuestionCustomize.getTitle().indexOf(".")+1);
            newAppQuestionCustomize.setTitle(title);
        }
        response.setQuestionList(list);
        response.setStatus(RiskAssesmentResponse.SUCCESS);
        response.setStatusDesc(RiskAssesmentResponse.SUCCESS_MSG);

        return response;
    }

    /**
     * 风险测评提交
     */
    @PostMapping(value = "/riskTest")
    public RiskAssesmentResponse evaluationResult(HttpServletRequest request,
                                                  @RequestBody UserAnswerRequestBean userAnswerRequestBean) {
        RiskAssesmentResponse response = new RiskAssesmentResponse();
        // 检查参数
        String sign = request.getParameter("sign");
        if (sign == null) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc("参数非法");
            return response;
        }
        // 用户ID
        Integer userId = SecretUtil.getUserId(sign);
        if (userId == null || userId == 0) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc(RiskAssesmentResponse.FAIL_MSG);
            return response;
        }
        // 是否已经参加过测评（true：已测评过，false：测评）
        UserEvalationResultVO userEvalationResult = evaluationService.answerAnalysis(userAnswerRequestBean.getUserAnswer(), userId,null);
        evaluationService.updateUser(userId);
        response.setResultStatus("1");
        // userEvalationResult 测评结果
        response.setResultType(userEvalationResult.getEvalType());
        response.setResultText(userEvalationResult.getSummary());
        response.setStatus(RiskAssesmentResponse.SUCCESS);
        response.setStatusDesc(RiskAssesmentResponse.SUCCESS_MSG);
        return response;
    }

    /**
     * 风险空测评提交  -- 跳过测评
     */
    @PostMapping(value = "/riskTestNone")
    public RiskAssesmentResponse skipEvaluate(HttpServletRequest request) {

        RiskAssesmentResponse response = new RiskAssesmentResponse();
        // 检查参数
        String sign = request.getParameter("sign");
        if (sign == null) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc("参数非法");
            return response;
        }
        // 用户ID
        Integer userId = SecretUtil.getUserId(sign);
        if (userId == null || userId == 0) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc(RiskAssesmentResponse.FAIL_MSG);
            return response;
        }
        int countScore = 0;
        UserEvalationResultVO userEvalationResult = evaluationService.skipEvaluate(userId,countScore);
        if (userEvalationResult == null) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc(RiskAssesmentResponse.FAIL_MSG);
            return response;
        }
        // userEvalationResult 测评结果
        response.setResultType(userEvalationResult.getEvalType());
        response.setResultText(userEvalationResult.getSummary());
        evaluationService.updateUser(userId);
        response.setStatus(RiskAssesmentResponse.SUCCESS);
        response.setStatusDesc(RiskAssesmentResponse.SUCCESS_MSG);
        return response;
    }

}
