/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.riskassesment;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.config.NewAppQuestionCustomizeVO;
import com.hyjf.am.vo.user.EvalationCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.result.RiskAssesmentResponse;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import com.hyjf.cs.user.vo.UserAnswerRequestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 风险测评
 * @author zhangqingqing
 * @version AppRiskAssesmentController, v0.1 2018/8/3 17:17
 */
@Api(value = "app端-风险测评",tags = "app端-风险测评（新）")
@RestController
@RequestMapping("/hyjf-app/user")
public class AppRiskAssesmentController extends BaseUserController {

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    CommonProducer commonProducer;
    /**
     * 风险测评
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "风险测评")
    @GetMapping(value = "/riskTest")
    public RiskAssesmentResponse getQuestionList(@RequestHeader(value = "userId")Integer userId,HttpServletRequest request) {

        RiskAssesmentResponse response = new RiskAssesmentResponse();
        String platform = request.getParameter("platform");
        if (userId == null || userId == 0) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc("用户未登录");
            return response;
        }
        UserInfoVO userInfoVO =  evaluationService.getUserInfo(userId);
        UserVO userVO = evaluationService.getUsersById(userId);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE12);
        userOperationLogEntity.setIp(com.hyjf.cs.user.util.GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(Integer.valueOf(platform));
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(userVO.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(userInfoVO.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
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
            //从user表获取用户测评到期日
            UserVO user = evaluationService.getUsersById(userId);
            // 获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = user.getEvaluationExpiredTime().getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = System.currentTimeMillis();
            if (lCreate <= lNow) {
                //已过期需要重新评测
                response.setResultStatus("2");
            }
            // 已测评
            response.setResultStatus("1");
            response.setResultType(ueResult.getEvalType());
            // 测评金额上限增加（获取评分标准对应的上限金额并拼接）
            String revaluationMoney,evalType;
            List<EvalationCustomizeVO> evalationMoney = evaluationService.getEvalationRecord();
            for(EvalationCustomizeVO evalationCustomizeVO:evalationMoney){
                revaluationMoney = evalationCustomizeVO.getRevaluationMoney();
                evalType = evalationCustomizeVO.getEvalType();
                if(evalType.equals(ueResult.getEvalType())){
                    response.setRevaluationMoney(revaluationMoney);
                }
            }
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
    @ApiOperation(value = "风险测评提交")
    @PostMapping(value = "/riskTest")
    public RiskAssesmentResponse evaluationResult(@RequestHeader(value = "userId")Integer userId,@RequestBody UserAnswerRequestBean userAnswerRequestBean) {
        logger.info("app端风险测评参数："+userId);
        RiskAssesmentResponse response = new RiskAssesmentResponse();
        // 检查参数
        if (userId == null) {
            response.setStatus(RiskAssesmentResponse.FAIL);
            response.setStatusDesc(RiskAssesmentResponse.FAIL_MSG);
            return response;
        }
        // 是否已经参加过测评（true：已测评过，false：测评）
        UserEvalationResultVO userEvalationResult = evaluationService.answerAnalysis(userAnswerRequestBean.getUserAnswer(), userId,null);
        evaluationService.updateUser(userId);

        // add 合规数据上报 埋点 liubin 20181122 start
        // 推送数据到MQ 用户信息修改（风险测评）
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.USERINFO_CHANGE_TAG, UUID.randomUUID().toString(), params),
                MQConstant.HG_REPORT_DELAY_LEVEL);
        // add 合规数据上报 埋点 liubin 20181122 end

        response.setResultStatus("1");
        // userEvalationResult 测评结果
        response.setResultType(userEvalationResult.getEvalType());
        // 测评金额上限增加（获取评分标准对应的上限金额并拼接）
        String revaluationMoney,evalType;
        List<EvalationCustomizeVO> evalationMoney = evaluationService.getEvalationRecord();
        for(EvalationCustomizeVO evalationCustomizeVO:evalationMoney){
            revaluationMoney = evalationCustomizeVO.getRevaluationMoney();
            evalType = evalationCustomizeVO.getEvalType();
            if(evalType.equals(userEvalationResult.getEvalType())){
                response.setRevaluationMoney(revaluationMoney);
            }
        }
        response.setResultText(userEvalationResult.getSummary());
        response.setStatus(RiskAssesmentResponse.SUCCESS);
        response.setStatusDesc(RiskAssesmentResponse.SUCCESS_MSG);
        return response;
    }

    /**
     * 风险空测评提交  -- 跳过测评
     */
    @ApiOperation(value = "风险空测评提交  -- 跳过测评")
    @PostMapping(value = "/riskTestNone")
    public RiskAssesmentResponse skipEvaluate(@RequestHeader(value = "userId")Integer userId) {
        logger.info("app端风险测评参数："+userId);
        RiskAssesmentResponse response = new RiskAssesmentResponse();
        // 检查参数
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
