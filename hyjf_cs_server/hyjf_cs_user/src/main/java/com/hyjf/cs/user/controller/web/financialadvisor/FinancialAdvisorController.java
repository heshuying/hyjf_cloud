/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.financialadvisor;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.financialadvisor.FinancialAdvisorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
     *
     * 再测一次
     * @author
     * @param
     * @return
     */
    @ApiOperation(value = "风险测评接口", notes = "端风险测评接口")
    @PostMapping(value = "/questionnaireInit", produces = "application/json; charset=utf-8")
    public Map<String,Object> questionnaireInit() {
        Map<String,Object> result = new HashMap<>();
        //测评问题
        List<QuestionCustomizeVO> list=financialAdvisorService.getNewQuestionList();
        result.put("questionList",list);
        result.put("listSize",list.size());
        //评分标准
        List<EvalationVO> evalationList = financialAdvisorService.getEvalationRecord();
        result.put("evalationList",evalationList);
        return result;
    }

    /**
     * 插入评测数据并发券
     * @param
     * @param
     * @return
     */
    public synchronized Map<String,Object> answerAnalysisAndCoupon(String userAnswer,Integer userId){
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

    /**
     *
     * 网站改版风险测评页面初始化
     * @author pcc
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "网站改版风险测评页面初始化", notes = "网站改版风险测评页面初始化")
    @PostMapping(value="financialAdvisorInit" ,  produces = "application/json; charset=utf-8")
    public  WebResult<Map<String,Object>> financialAdvisorInit(@RequestHeader(value = "token", required = true) String token) {
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();
        CheckUtil.check(null!=token, MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.financialAdvisorService.getUsers(token);
        // 用户ID
        Integer userId = user.getUserId();
        //userError 用户未登录
        CheckUtil.check(userId != null && userId!= 0, MsgEnum.ERR_USER_NOT_LOGIN);

        //测评结果
        UserEvalationResultVO userEvalationResult=financialAdvisorService.selectUserEvalationResultByUserId(userId);
        //已测评
        if(userEvalationResult != null && userEvalationResult.getId() != 0){
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(userEvalationResult.getCreateTime(),1,1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5,1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测
                //测评问题
                List<QuestionCustomizeVO> list=financialAdvisorService.getNewQuestionList();
                resultMap.put("questionList",list);
                //评分标准
                List<EvalationVO> evalationList = financialAdvisorService.getEvalationRecord();
                resultMap.put("evalationList",evalationList);
            } else {
                //测评结果
                resultMap.put("userEvalationResult",userEvalationResult);
            }
        }else{
            //测评问题
            List<QuestionCustomizeVO> list=financialAdvisorService.getNewQuestionList();
            resultMap.put("questionList",list);
            //评分标准
            List<EvalationVO> evalationList = financialAdvisorService.getEvalationRecord();
            resultMap.put("evalationList",evalationList);
        }
        result.setData(resultMap);
        return result;
    }

    /**
     * 用户测评结果
     * @author pcc
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "用户测评结果", notes = "用户测评结果")
    @ApiImplicitParam(name = "param",value = "{userAnswer:string}", dataType = "Map")
    @PostMapping(value = "evaluationResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> evaluationResult( @RequestHeader(value = "token") String token,@RequestBody Map<String,String> param) {
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();
        CheckUtil.check(null!=token, MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.financialAdvisorService.getUsers(token);
        // 用户ID
        Integer userId = user.getUserId();
        //userError 用户未登录
        CheckUtil.check(userId != null && userId!= 0, MsgEnum.ERR_USER_NOT_LOGIN);
        //用户答案
        String userAnswer = param.get("userAnswer");
        Map<String,Object> returnMap = this.answerAnalysisAndCoupon(userAnswer, userId);
        //优惠券发放
        if(returnMap.get("sendCount") != null){
            int sendCount = (int)returnMap.get("sendCount");
            resultMap.put("sendCount", sendCount);
        }
        UserEvalationResultVO userEvalationResult = (UserEvalationResultVO) returnMap.get("userEvalationResult");
        // userEvalationResult 测评结果
        resultMap.put("userEvalationResult", userEvalationResult);
        result.setData(resultMap);
        return result;

    }
}
