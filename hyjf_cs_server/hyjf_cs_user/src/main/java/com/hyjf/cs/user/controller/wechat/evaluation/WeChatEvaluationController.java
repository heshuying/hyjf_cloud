/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.evaluation;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.SimpleResultBean;
import com.hyjf.cs.user.bean.WXBaseResultBean;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import com.hyjf.cs.user.vo.FinancialAdvisorSumitQO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangqingqing
 * @version WeChatEvaluationController, v0.1 2018/7/3 19:24
 */
@Api(value = "weChat端-风险测评接口",tags = "weChat端-风险测评接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-wechat/wx/financiaadvisor")
public class WeChatEvaluationController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatEvaluationController.class);
    @Autowired
    EvaluationService evaluationService;
    @Autowired
    private CommonProducer commonProducer;

    @ApiOperation(value = "查询测评结果", notes = "查询测评结果")
    @GetMapping(value = "/queryevaluation.do")
    public WXBaseResultBean queryEvaluationResult(@RequestHeader(value = "userId") Integer userId) {
        SimpleResultBean<UserEvalationResultVO> resultBean = new SimpleResultBean<>();
        CheckUtil.check(userId != null, MsgEnum.STATUS_CE000001);
        //测评结果
        UserEvalationResultVO userEvalationResult = evaluationService.selectUserEvalationResultByUserId(userId);
        CheckUtil.check(null != userEvalationResult, MsgEnum.STATUS_EV000003);
        //从user表获取用户测评到期日
        UserVO user = evaluationService.getUsersById(userId);
        // 获取评测时间加一年的毫秒数18.2.2评测 19.2.2
        Long lCreate = user.getEvaluationExpiredTime().getTime();
        //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
        Long lNow = System.currentTimeMillis();
        CheckUtil.check(lCreate > lNow, MsgEnum.STATUS_EV000004);
        resultBean.setObject(userEvalationResult);
        return resultBean;
    }

    @ApiOperation(value = "用户测评",notes = "用户测评")
    @GetMapping(value = "/queryquestion.do")
    public WXBaseResultBean queryQuestions(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        SimpleResultBean<List<QuestionCustomizeVO>> resultBean = new SimpleResultBean<>();
        CheckUtil.check(userId != null, MsgEnum.STATUS_CE000001);
        logger.info("用户userId==="+userId);
        UserVO userVO = evaluationService.getUsersById(userId);
        logger.info("用户userVO==="+JSONObject.toJSONString(userVO));
        UserInfoVO userInfoVO =  evaluationService.getUserInfo(userId);
        if(userVO!=null) {
            UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
            userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE12);
            userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
            userOperationLogEntity.setPlatform(1);
            userOperationLogEntity.setRemark("");
            userOperationLogEntity.setOperationTime(new Date());
            userOperationLogEntity.setUserName(userVO.getUsername());
            userOperationLogEntity.setUserRole(String.valueOf(userInfoVO.getRoleId()));
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
            } catch (MQException e) {
                logger.error("保存用户日志失败", e);
            }
        }
        UserEvalationResultVO userEvalationResult = evaluationService.selectUserEvalationResultByUserId(userId);
        if (userEvalationResult != null) {
            //从user表获取用户测评到期日
            UserVO user = evaluationService.getUsersById(userId);
            // 获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = user.getEvaluationExpiredTime().getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = System.currentTimeMillis();
            if (lCreate <= lNow) {
                // 已过期需要重新评测
                resultBean.setResultStatus("2");
            } else {
                // 已测评
                resultBean.setResultStatus("1");
            }
        } else {
            resultBean.setResultStatus("0");
        }
        //已过期需要重新评测或者未测评
        List<QuestionCustomizeVO> lstQuestion = evaluationService.getNewQuestionList();
        List<QuestionCustomizeVO> vo = Lists.newArrayList();
        vo.addAll(lstQuestion);
        resultBean.setObject(vo);
        return resultBean;
    }

    @ApiOperation(value = "计算测评结果",notes = "计算测评结果")
    @PostMapping(value = "/sumitResult.do")
    public WXBaseResultBean sumitResult(@RequestHeader(value = "userId") Integer userId, @RequestBody FinancialAdvisorSumitQO qo) {
        SimpleResultBean<UserEvalationResultVO> resultBean = new SimpleResultBean();
        CheckUtil.check(userId != null, MsgEnum.STATUS_CE000001);
        //答案 "13_48,14_52"
        String userAnswer = qo.getUserAnswer();
        Map<String, Object> returnMap = evaluationService.answerAnalysisAndCoupon(userAnswer, userId, CustomConstants.CLIENT_WECHAT,null);
        if (null!=returnMap) {
            resultBean.setObject((UserEvalationResultVO) returnMap.get("userEvalationResult"));
        }
        return resultBean;
    }

    /**
     * 评分标准接口
     *
     * */
    @ApiOperation(value = "评分标准", httpMethod = "POST", notes = "评分标准")
    @PostMapping(value = "/gradingStandardResult.do")
    public JSONObject gradingStandardResult() {
        JSONObject map = new JSONObject();
        List<EvalationCustomizeVO> evalationCustomizeList = evaluationService.getEvalationRecord();
        map.put("status", "000");
        map.put("statusDesc", "请求成功");
        map.put("evalationList", evalationCustomizeList);
        return map;
    }
}
