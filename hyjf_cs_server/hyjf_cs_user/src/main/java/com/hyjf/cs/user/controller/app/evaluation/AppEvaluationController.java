/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.evaluation;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationBehaviorVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AppEvaluationController, v0.1 2018/7/4 11:19
 */
@Api(value = "App端风险测评接口",description = "App端-风险测评接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-app/user/financialAdvisor")
public class AppEvaluationController {

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    SystemConfig systemConfig;


    @ApiOperation(value = "app用户测评初始化", notes = "用户测评初始化")
    @PostMapping(value = "/init")
    public ModelAndView init(@RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "sign") String sign, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/financialAdvisor/evaluation");
        modelAndView.addObject("sign", sign);
        String platform = request.getParameter("platform");
        modelAndView.addObject("platform", platform);
        UserEvalationResultVO ueResult = evaluationService.selectUserEvalationResultByUserId(userId);
        if(ueResult==null){
            String activityId = systemConfig.getActivityId();
            // 活动有效期校验
            String resultActivity = evaluationService.checkActivityIfAvailable(activityId);
            // 终端平台校验
            String resultPlatform = evaluationService.checkActivityPlatform(activityId, platform);
            // String
            String couponResult = StringUtils.EMPTY;
            if(StringUtils.isEmpty(resultActivity)&&StringUtils.isEmpty(resultPlatform)){
                couponResult = "首次完成评测，可以获得1张加息券";
            }
            modelAndView.addObject("couponResult", couponResult);
        } else {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(ueResult.getCreateTime(),1,1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5,1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测
                modelAndView.addObject("couponResult", "已过期需要重新评测");
            }
        }
        return modelAndView;
    }

    /**
     * 返回测评问卷
     * @param userId
     * @return
     */
    @ApiOperation(value = "返回测评问卷", notes = "返回测评问卷")
    @PostMapping(value = "/questionnaireInit", produces = "text/html;charset=UTF-8")
    public String questionnaireInit(@RequestHeader(value = "userId") Integer userId) {
        JSONObject ret = new JSONObject();
        // 检查参数
        if (userId == null || userId == 0) {
            Map<String, String> map = new HashMap<>();
            //ifEvaluation是否已经调查表示  1是已调查，0是未调查
            ret.put(AppEvaluationDefine.JSON_IF_EVALUATION_KEY, 0);
            //userError 用户未登录
            ret.put(AppEvaluationDefine.JSON_USER_LOGIN_ERROR_KEY, AppEvaluationDefine.JSON_USER_LOGIN_ERROR_VLUES);
            return JSONObject.toJSONString(ret);
        }
        UserEvalationResultVO userEvalationResult = evaluationService.selectUserEvalationResultByUserId(new Integer(userId));
        if (userEvalationResult != null && userEvalationResult.getId() != 0) {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(userEvalationResult.getCreateTime(), 1, 1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5, 1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测
                ret.put(AppEvaluationDefine.JSON_IF_EVALUATION_KEY, 2);
            } else {
                //ifEvaluation是否已经调查表示  1是已调查，0是未调查
                ret.put(AppEvaluationDefine.JSON_IF_EVALUATION_KEY, 1);
                //userEvalationResult 测评结果
                ret.put(AppEvaluationDefine.JSON_USER_EVALATION_RESULT_KEY, userEvalationResult);
            }
        } else {
            ret.put(AppEvaluationDefine.JSON_IF_EVALUATION_KEY, 0);
        }
        List<QuestionCustomizeVO> list = evaluationService.getNewQuestionList();
        ret.put(AppEvaluationDefine.JSON_QUESRION_LIST_KEY, list);
        return JSONObject.toJSONString(ret);
    }

    /**
     * 统计用户测评结果
     * @param userId
     * @param request
     * @return
     */
    @ApiOperation(value = "统计用户测评结果",notes = "统计用户测评结果")
    @PostMapping(value = "/evaluationResult", produces = "text/html;charset=UTF-8")
    public String evaluationResult(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        // 统计ID
        String behaviorId = request.getParameter("behaviorId");
//        Integer userId =2;
        if (userId == null || userId == 0) {
            //ifEvaluation是否已经调查表示  1是已调查，0是未调查
            ret.put(AppEvaluationDefine.JSON_IF_EVALUATION_KEY, 0);
            //userError 用户未登录
            ret.put(AppEvaluationDefine.JSON_USER_LOGIN_ERROR_KEY, AppEvaluationDefine.JSON_USER_LOGIN_ERROR_VLUES);
            return JSONObject.toJSONString(ret);
        }
        UserEvalationResultVO ueResult = new UserEvalationResultVO();
        String userAnswer = request.getParameter("userAnswer");
        Map<String, Object> returnMap = evaluationService.answerAnalysisAndCoupon(userAnswer, userId, CustomConstants.CLIENT_PC, behaviorId);
        if(null!=returnMap){
            ueResult = (UserEvalationResultVO) returnMap.get("userEvalationResult");
        }
        //ifEvaluation是否已经调查表示  1是已调查，0是未调查
        ret.put(AppEvaluationDefine.JSON_IF_EVALUATION_KEY, 1);
        //userEvalationResult 测评结果
        ret.put(AppEvaluationDefine.JSON_USER_EVALATION_RESULT_KEY, evaluationService.createUserEvalationResult(ueResult));
        return JSONObject.toJSONString(ret);
    }

    /**
     * 开始记录用户行为
     * @param userId
     * @param request
     * @return
     */
    @ApiOperation(value = "开始记录用户行为",notes = "开始记录用户行为")
    @PostMapping(value="/startUserEvaluationBehavior", produces = "text/html;charset=UTF-8")
    public String startUserEvaluationBehavior(@RequestHeader(value = "userId") Integer userId,HttpServletRequest request) {
        JSONObject ret=new JSONObject();
        String platform = request.getParameter("platform");
        if(platform!=null&&platform.length()!=0){
            String platformString="";
            if(CustomConstants.CLIENT_ANDROID.equals(platform)){
                platformString="安卓";
            }
            if(CustomConstants.CLIENT_IOS.equals(platform)){
                platformString="IOS";
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
            Integer id = evaluationService.insertUserEvalationBehavior(userId,sdf.format(new Date())+"移动端"+platformString+"开始答题");
            ret.put("behaviorId",id);
        }
        //ifEvaluation是否已经调查表示  1是已调查，0是未调查
        ret.put("success",1);
        return JSONObject.toJSONString(ret);
    }

    /**
     * 记录用户行为
     * @param request
     * @return
     */
    @ApiOperation(value = "记录用户行为",notes = "记录用户行为")
    @PostMapping(value="/userEvaluationBehavior", produces = "text/html;charset=UTF-8")
    public String userEvaluationBehavior(HttpServletRequest request) {
        JSONObject ret=new JSONObject();
        // 统计ID
        String behaviorId = request.getParameter("behaviorId");
        // 统计内容
        String behavior = request.getParameter("behavior");
        if(behaviorId!=null&&behaviorId.length()!=0){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
            behavior=sdf.format(new Date())+"用户选择问题答案为: "+behavior;
            UserEvalationBehaviorVO userEvalationBehavior=new UserEvalationBehaviorVO();
            userEvalationBehavior.setId(Integer.parseInt(behaviorId));
            userEvalationBehavior.setEndTime(new Date());
            userEvalationBehavior.setBehavior(behavior);
            evaluationService.updateUserEvalationBehavior(userEvalationBehavior);
        }
        //ifEvaluation是否已经调查表示  1是已调查，0是未调查
        ret.put("success",1);
        return JSONObject.toJSONString(ret);
    }

}
