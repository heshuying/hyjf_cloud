/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.evaluation.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.resquest.user.UserEvalationRequest;
import com.hyjf.am.vo.user.ActivityListVO;
import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationBehaviorVO;
import com.hyjf.am.vo.user.UserEvalationResultCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.api.evaluation.ThirdPartyEvaluationRequestBean;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.base.Producer;
import com.hyjf.cs.user.mq.producer.CouponProducer;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import com.hyjf.soa.apiweb.CommonParamBean;

/**
 * @author zhangqingqing
 * @version EvaluationServiceImpl, v0.1 2018/6/15 19:11
 */
@Service
public class EvaluationServiceImpl extends BaseUserServiceImpl implements EvaluationService {

    @Autowired
    AmConfigClient amConfigClient;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AmMarketClient amMarketClient;

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private CouponProducer couponProducer;

    @Override
    public List<QuestionCustomizeVO> getNewQuestionList() {
        List<QuestionCustomizeVO> customizes = amConfigClient.getNewQuestionList();
        return customizes;
    }

    @Override
    public UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId) {
        UserEvalationResultVO userEvalationResult = amUserClient.selectUserEvalationResultByUserId(userId);
        return userEvalationResult;
    }

    /**
     * 计算用户测评分数
     *
     * @param userAnswer
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO answerAnalysis(String userAnswer, Integer userId,String behaviorId) {
        String[] answer = userAnswer.split(",");
        List<String> answerList = new ArrayList<String>();
        List<String> questionList = new ArrayList<String>();
        for (String string : answer) {
            if (string.split("_").length == 2) {
                questionList.add(string.split("_")[0]);
                answerList.add(string.split("_")[1]);
            }
        }
        AnswerRequest answerRequest = new AnswerRequest();
        answerRequest.setResultList(answerList);
        int countScore = amConfigClient.countScore(answerRequest);
        UserEvalationRequest userEvalationRequest = new UserEvalationRequest();
        userEvalationRequest.setUserId(userId);
        userEvalationRequest.setCountScore(countScore);
        userEvalationRequest.setUserAnswer(userAnswer);
        userEvalationRequest.setBehaviorId(behaviorId);
        UserEvalationResultVO userEvalationResult = amUserClient.insertUserEvalationResult(userEvalationRequest);
        return userEvalationResult;
    }


    /**
     * 发放优惠券
     *
     * @param userId
     * @param platform
     * @return
     */
    @Override
    public String sendCoupon(int userId, String platform) {
        String activityId = systemConfig.getActivityId();
        // 活动有效期校验
        String resultActivity = checkActivityIfAvailable(activityId);
        // 终端平台校验
        String resultPlatform = checkActivityPlatform(activityId, platform);
        // String
        String result = StringUtils.EMPTY;
        if (StringUtils.isEmpty(resultActivity) && StringUtils.isEmpty(resultPlatform)) {
            CommonParamBean couponParamBean = new CommonParamBean();
            // 用户编号
            couponParamBean.setUserId(String.valueOf(userId));
            // 评测送加息券
            couponParamBean.setSendFlg(1);
            // 发放优惠券（1张加息券）
            try {
                JSONObject params = new JSONObject();
                params.put("mqMsgId", GetCode.getRandomCode(10));
                params.put("userId", String.valueOf(userId));
                params.put("sendFlg", "1");
                couponProducer.messageSend(new MessageContent(MQConstant.GRANT_COUPON_TOPIC,
                        UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
            } catch (MQException e) {
                e.printStackTrace();

            }
        }
        return result;
    }

    /**
     * 活动是否过期
     */
    @Override
    public String checkActivityIfAvailable(String activityId) {
        if(activityId==null){
            return CommonConstant.ACTIVITYID_IS_NULL;
        }
        ActivityListVO activityList = amMarketClient.selectActivityList(new Integer(activityId));
        if(activityList==null){
            return CommonConstant.ACTIVITY_ISNULL;
        }
        if(activityList.getTimeStart()> GetDate.getNowTime10()){
            return CommonConstant.ACTIVITY_TIME_NOT_START;
        }
        if(activityList.getTimeEnd()<GetDate.getNowTime10()){
            return CommonConstant.ACTIVITY_TIME_END;
        }
        return "";
    }

    @Override
    public List<EvalationVO> getEvalationRecord() {
        List<EvalationVO> evalationVOList = amUserClient.getEvalationRecord();
        return evalationVOList;
    }

    @Override
    public String checkActivityPlatform(String activityId, String platform) {
        if(activityId==null){
            return CommonConstant.ACTIVITYID_IS_NULL;
        }
        ActivityListVO activityList = amMarketClient.selectActivityList(new Integer(activityId));
        if (activityList.getPlatform().indexOf(platform) == -1) {
            // 操作平台
            Map<String, String> clients = CacheUtil.getParamNameMap("CLIENT");
            // 被选中操作平台
            String clientSed[] = StringUtils.split(activityList.getPlatform(), ",");
            StringBuffer selectedClientDisplayBuffer = new StringBuffer();
            for (String client : clientSed) {
                // 被选中的平台编号
                for (String pn : clients.keySet()) {
                    if (StringUtils.equals(clients.get(pn), client)) {
                        // 被选中的平台名称 表示用
                        selectedClientDisplayBuffer.append(clients.get(pn));
                        selectedClientDisplayBuffer.append("/");
                    }
                }
            }
            return ClientConstants.PLATFORM_LIMIT.replace("***", selectedClientDisplayBuffer.toString());
        }
        return  "";
    }

    /**
     * 插入评测数据并发券
     *
     * @param
     * @param
     * @return
     */
    @Override
    public synchronized Map<String, Object> answerAnalysisAndCoupon(String userAnswer, Integer userId,String platform,String behaviorId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //发放优惠券 start
        UserEvalationResultVO ueResult = this.selectUserEvalationResultByUserId(userId);
        // 是否已经参加过测评（true：已测评过，false：测评）
        boolean isAdvisor = ueResult != null ? true : false;
        // 发放优惠券 start
        if (!isAdvisor) {
            // 发放优惠券
            String result = this.sendCoupon(userId, platform);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject resultObj = JSONObject.parseObject(result);
                if (resultObj.getIntValue("status") == 0 && resultObj.getIntValue("couponCount") > 0) {
                    String sendResult = "恭喜您获得"+resultObj.getIntValue("couponCount")+"张加息券，体验投资流程，获取高额收益，可在我的账户-优惠券中查看";
                    int sendCount = resultObj.getIntValue("couponCount");
                    returnMap.put("sendCount", sendCount);
                    returnMap.put("sendResult", sendResult);
                }
            }
        }
        // 1_1,2_8
        UserEvalationResultVO userEvalationResult = this.answerAnalysis(userAnswer, new Integer(userId),behaviorId);
        returnMap.put("userEvalationResult", userEvalationResult);
        return returnMap;
        // 发放优惠券 end
    }

    @Override
    public EvalationVO checkParam(ThirdPartyEvaluationRequestBean thirdPartyFinancialadvisorRequestBean) {
        CheckUtil.check(Validator.isNotNull(thirdPartyFinancialadvisorRequestBean.getTimestamp()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(thirdPartyFinancialadvisorRequestBean.getInstCode()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(thirdPartyFinancialadvisorRequestBean.getChkValue()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(thirdPartyFinancialadvisorRequestBean.getMobile()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(thirdPartyFinancialadvisorRequestBean.getPlatform()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(thirdPartyFinancialadvisorRequestBean.getEvalationType()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isMobile(thirdPartyFinancialadvisorRequestBean.getMobile()), MsgEnum.STATUS_CE000001);
        // 验签
        CheckUtil.check(this.verifyRequestSign(thirdPartyFinancialadvisorRequestBean, BaseDefine.METHOD_SAVE_USER_EVALUATION_RESULTS),
                MsgEnum.ERR_SIGN);
        //根据平常类型查询平常信息
        EvalationVO evalation = amUserClient.getEvalationByEvalationType(thirdPartyFinancialadvisorRequestBean.getEvalationType());
        CheckUtil.check(evalation != null, MsgEnum.STATUS_EV000001);
        return evalation;
    }

    @Override
    public int ThirdPartySaveUserEvaluationResults(UserVO user, Integer evalationScoreCount, EvalationVO evalation, String instCode, String channel) {
        HjhInstConfigVO hjhInstConfig = amTradeClient.selectInstConfigByInstCode(instCode);
        UserEvalationResultVO oldUserEvalationResult = this.selectUserEvalationResultByUserId(user.getUserId());
        UserEvalationResultVO userEvalationResult = new UserEvalationResultVO();
        userEvalationResult.setUserId(user.getUserId());
        userEvalationResult.setScoreCount(evalationScoreCount);
        userEvalationResult.setEvalType(evalation.getEvalType());
        userEvalationResult.setSummary(evalation.getSummary());
        userEvalationResult.setCreateTime(new Date());
        if (hjhInstConfig != null) {
            userEvalationResult.setInstCode(hjhInstConfig.getInstCode());
            userEvalationResult.setInstName(hjhInstConfig.getInstName());
        }
        if (oldUserEvalationResult != null) {
            userEvalationResult.setLasttime(oldUserEvalationResult.getCreateTime());
        }
        int insertCount = amUserClient.saveUserEvaluation(userEvalationResult);
        return insertCount;
    }

    @Override
    public UserEvalationResultCustomizeVO createUserEvalationResult(UserEvalationResultVO userEvalationResult) {
        UserEvalationResultCustomizeVO userEvalationResultCustomize=new UserEvalationResultCustomizeVO();
        userEvalationResultCustomize.setType(userEvalationResult.getEvalType());
        userEvalationResultCustomize.setSummary(userEvalationResult.getSummary());
        userEvalationResultCustomize.setScoreCount(userEvalationResult.getScoreCount());
        return userEvalationResultCustomize;
    }

    @Override
    public Integer insertUserEvalationBehavior(Integer userId, String s) {
        return amUserClient.insertUserEvalationBehavior(userId,s);
    }

    @Override
    public void updateUserEvalationBehavior(UserEvalationBehaviorVO userEvalationBehavior) {
        amUserClient.updateUserEvaluationBehavior(userEvalationBehavior);
    }

}
