/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.evaluation;

import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import com.hyjf.cs.user.vo.FinancialAdvisorSumitQO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version WeChatEvaluationController, v0.1 2018/7/3 19:24
 */
@Api(value = "weChat端风险测评接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/weChat/user/evaluation")
public class WeChatEvaluationController {

    @Autowired
    EvaluationService evaluationService;

    @ApiOperation(value = "查询测评结果", notes = "查询测评结果")
    @GetMapping(value = "/value")
    public WeChatResult queryEvaluationResult(@RequestHeader(value = "userId") Integer userId) {
        WeChatResult<Object> result = new WeChatResult<>();
        CheckUtil.check(userId == null, MsgEnum.STATUS_CE000001);
        //测评结果
        UserEvalationResultVO userEvalationResult = evaluationService.selectUserEvalationResultByUserId(userId);
        CheckUtil.check(null != userEvalationResult, MsgEnum.STATUS_EV000003);
        //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
        Long lCreate = GetDate.countDate(userEvalationResult.getCreateTime(), 1, 1).getTime();
        //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
        Long lNow = GetDate.countDate(new Date(), 5, 1).getTime();
        CheckUtil.check(lCreate > lNow, MsgEnum.STATUS_EV000004);
        result.setData(userEvalationResult);
        return result;
    }

    @ApiOperation(value = "用户测评",notes = "用户测评")
    @GetMapping(value = "/question")
    public WeChatResult queryQuestions(@RequestHeader(value = "userId") Integer userId) {
        WeChatResult<Object> result = new WeChatResult<>();
        CheckUtil.check(userId == null, MsgEnum.STATUS_CE000001);
        UserEvalationResultVO userEvalationResult = evaluationService.selectUserEvalationResultByUserId(userId);
        if (userEvalationResult != null) {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(userEvalationResult.getCreateTime(), 1, 1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5, 1).getTime();
            CheckUtil.check(lCreate > lNow, MsgEnum.STATUS_EV000003);
        }
        //已过期需要重新评测或者未测评
        List<QuestionCustomizeVO> list = evaluationService.getNewQuestionList();
        result.setData(list);
        return result;
    }

    @PostMapping(value = "/calculation", produces = "application/json; charset=utf-8")
    public WeChatResult sumitResult(@RequestHeader(value = "userId") Integer userId, @RequestBody FinancialAdvisorSumitQO qo) {
        WeChatResult result = new WeChatResult();
        CheckUtil.check(userId == null, MsgEnum.STATUS_CE000001);
        //答案 "13_48,14_52"
        String userAnswer = qo.getUserAnswer();
        Map<String, Object> returnMap = evaluationService.answerAnalysisAndCoupon(userAnswer, userId, CustomConstants.CLIENT_WECHAT,null);
        result.setData(returnMap.get("userEvalationResult"));
        return result;
    }
}
