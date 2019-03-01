/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.evaluation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import com.hyjf.cs.user.vo.FinancialAdvisorSumitQO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author zhangqingqing
 * @version EvaluationController, v0.1 2018/6/15 19:09
 */
@Api(value = "web端风险测评接口",tags = "web端-风险测评接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user")
public class WebEvaluationController extends BaseUserController {

    @Autowired
    EvaluationService evaluationService;
    @Autowired
    CommonProducer commonProducer;
    /**
     * 测评问题以及评分标准
     * @return
     */
    @ApiOperation(value = "测评问题以及评分标准", notes = "测评问题以及评分标准")
    @PostMapping(value = "/questionnaireInit", produces = "application/json; charset=utf-8")
    public WebResult<Map<String, Object>> questionnaireInit(HttpServletRequest request,@RequestHeader(value = "userId") int userId) {
        WebResult<Map<String, Object>> webResult = new WebResult<>();
        Map<String, Object> result = new HashMap<>();
        //测评问题
        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(12);
        userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(0);
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(user.getRoleId());
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        List<QuestionCustomizeVO> list = evaluationService.getNewQuestionList();
        result.put("questionList", list);
        result.put("listSize", list.size());
        //评分标准
        List<EvalationCustomizeVO> evalationList = evaluationService.getEvalationRecord();
        result.put("evalationList", evalationList);
        webResult.setData(result);
        return webResult;
    }

    /**
     * 网站改版风险测评页面初始化
     * @param userId
     * @return
     */
    @ApiOperation(value = "网站改版风险测评页面初始化", notes = "网站改版风险测评页面初始化")
    @PostMapping(value = "/financialAdvisorInit", produces = "application/json; charset=utf-8")
    public WebResult<Map<String, Object>> financialAdvisorInit(HttpServletRequest request,@RequestHeader(value = "userId") Integer userId) {
        WebResult<Map<String, Object>> result = new WebResult<>();
        Map<String, Object> resultMap = new HashMap<>();
        CheckUtil.check(null != userId, MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.evaluationService.getUsersById(userId);
        //userError 用户未登录
        CheckUtil.check(userId != null && userId != 0, MsgEnum.ERR_USER_NOT_LOGIN);
        WebViewUserVO users = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(12);
        userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(0);
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(users.getUsername());
        userOperationLogEntity.setUserRole(users.getRoleId());
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        //测评结果
        UserEvalationResultVO userEvalationResult = evaluationService.selectUserEvalationResultByUserId(userId);
        //已测评
        if (userEvalationResult != null && userEvalationResult.getId() != 0) {
            // 获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = user.getEvaluationExpiredTime().getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = System.currentTimeMillis();
            if (lCreate <= lNow) {
                //已过期需要重新评测
                //测评问题
                List<QuestionCustomizeVO> list = evaluationService.getNewQuestionList();
                resultMap.put("questionList", list);
                //评分标准
                List<EvalationCustomizeVO> evalationList = evaluationService.getEvalationRecord();
                resultMap.put("evalationList", evalationList);
            } else {
                //测评结果
                resultMap.put("userEvalationResult", userEvalationResult);
            }
        } else {
            //测评问题
            List<QuestionCustomizeVO> list = evaluationService.getNewQuestionList();
            resultMap.put("questionList", list);
            //评分标准
            List<EvalationCustomizeVO> evalationList = evaluationService.getEvalationRecord();
            resultMap.put("evalationList", evalationList);
        }
        result.setData(resultMap);
        return result;
    }

    /**
     * 用户测评结果
     * @param userId
     * @param qo
     * @return
     */
    @ApiOperation(value = "用户测评结果", notes = "用户测评结果")
    @PostMapping(value = "/evaluationResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String, Object>> evaluationResult(@RequestHeader(value = "userId") Integer userId, @RequestBody FinancialAdvisorSumitQO qo) {
        WebResult<Map<String, Object>> result = new WebResult<>();
        Map<String, Object> resultMap = new HashMap<>();
        //userError 用户未登录
        CheckUtil.check(userId != null && userId != 0, MsgEnum.ERR_USER_NOT_LOGIN);
        //用户答案
        String userAnswer = qo.getUserAnswer();
        Map<String, Object> returnMap = evaluationService.answerAnalysisAndCoupon(userAnswer, userId, CustomConstants.CLIENT_PC,null);
        //优惠券发放
        UserEvalationResultVO userEvalationResult = new UserEvalationResultVO();
        if (null!=returnMap) {
            if (returnMap.get("sendCount") != null){
                int sendCount = (int) returnMap.get("sendCount");
                resultMap.put("sendCount", sendCount);
            }
            userEvalationResult = (UserEvalationResultVO) returnMap.get("userEvalationResult");
            resultMap.put("revaluationMoney", returnMap.get("revaluationMoney"));
        }
        // userEvalationResult 测评结果
        resultMap.put("userEvalationResult", userEvalationResult);
        result.setData(resultMap);
        /**
         * 调用重新登录接口
         */
        WebViewUserVO webUser = evaluationService.getWebViewUserByUserId(userId);
        if (null != webUser) {
            evaluationService.setToken(webUser);
        }
        return result;
    }
}
