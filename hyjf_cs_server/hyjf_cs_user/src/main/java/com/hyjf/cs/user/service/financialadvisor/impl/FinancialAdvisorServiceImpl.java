/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.financialadvisor.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.resquest.user.UserEvalationRequest;
import com.hyjf.am.vo.user.ActivityListVO;
import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.mq.CouponProducer;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.financialadvisor.FinancialAdvisorService;
import com.hyjf.soa.apiweb.CommonParamBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version FinancialAdvisorImpl, v0.1 2018/6/15 19:11
 */
@Service
public class FinancialAdvisorServiceImpl extends BaseUserServiceImpl implements FinancialAdvisorService {

    @Autowired
    AmConfigClient amConfigClient;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AmMarketClient amMarketClient;

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
     * @param userAnswer
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO answerAnalysis(String userAnswer, Integer userId) {
        UserEvalationResultVO oldUserEvalationResult =this.selectUserEvalationResultByUserId(userId);
        amUserClient.deleteUserEvalationResultByUserId(userId);

        String[] answer = userAnswer.split(",");
        List<String> answerList = new ArrayList<String>();
        List<String> questionList = new ArrayList<String>();
        for (String string : answer) {
            if(string.split("_").length==2){
                questionList.add(string.split("_")[0]);
                answerList.add(string.split("_")[1]);
            }
        }
        AnswerRequest answerRequest = new AnswerRequest();
        answerRequest.setResultList(answerList);
        int countScore = amConfigClient.countScore(answerRequest);
        EvalationVO evalation = amUserClient.getEvalationByCountScore((short) countScore);
        UserEvalationRequest request = new UserEvalationRequest();
        request.setAnswerList(answerList);
        request.setQuestionList(questionList);
        request.setEvalation(evalation);
        request.setCountScore(countScore);
        request.setUserId(userId);
        request.setUserEvalationResultVO(oldUserEvalationResult);
        UserEvalationResultVO userEvalationResult = amUserClient.insertUserEvalationResult(request);
        return userEvalationResult;
    }


    /**
     * 发放优惠券
     * @param userId
     * @param platform
     * @return
     */
    @Override
    public  String sendCoupon(int userId, String platform){
        String activityId = systemConfig.getActivityId();
        // 活动有效期校验
        String resultActivity = checkActivityIfAvailable(activityId);
        // 终端平台校验
        String resultPlatform = checkActivityPlatform(activityId, platform);
        // String
        String result = StringUtils.EMPTY;
        if(StringUtils.isEmpty(resultActivity)&&StringUtils.isEmpty(resultPlatform)){
            CommonParamBean couponParamBean = new CommonParamBean();
            // 用户编号
            couponParamBean.setUserId(String.valueOf(userId));
            // 评测送加息券
            couponParamBean.setSendFlg(1);
            // 发放优惠券（1张加息券）
            try { JSONObject params = new JSONObject();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            params.put("userId", String.valueOf(userId));
            params.put("sendFlg", "1");
                couponProducer.messageSend(new Producer.MassageContent(MQConstant.GRANT_COUPON_TOPIC,JSON.toJSONBytes(params)));
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
        CheckUtil.check(null!=activityId, MsgEnum.ERR_OBJECT_REQUIRED,"活动编号");//活动编号不能为空

        ActivityListVO activityList=amMarketClient.selectActivityList(new Integer(activityId));
        CheckUtil.check(null!=activityList, MsgEnum.ERR_ACTIVITY_NOT_EXIST);
        CheckUtil.check(null!=activityList, MsgEnum.ERR_ACTIVITY_NOT_EXIST);
        CheckUtil.check(activityList.getTimeStart()<=System.currentTimeMillis()/1000, MsgEnum.ERR_ACTIVITY_NOT_START);
        CheckUtil.check(activityList.getTimeEnd()>=System.currentTimeMillis()/1000, MsgEnum.ERR_ACTIVITY_END);
        return "";
    }

    @Override
    public List<EvalationVO> getEvalationRecord() {
        List<EvalationVO> evalationVOList = amUserClient.getEvalationRecord();
        return evalationVOList;
    }

    public String checkActivityPlatform(String activityId, String platform) {
        CheckUtil.check(null!=activityId, MsgEnum.ERR_OBJECT_REQUIRED,"活动编号");//活动编号不能为空
        ActivityListVO activityList=amMarketClient.selectActivityList(new Integer(activityId));
        if(activityList.getPlatform().indexOf(platform)==-1){

            // 操作平台
            Map<String, String> clients = CacheUtil.getParamNameMap("CLIENT");
            // 被选中操作平台
            String clientSed[] = StringUtils.split(activityList.getPlatform(),",");
            StringBuffer selectedClientDisplayBuffer=new StringBuffer();
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
        return "";
    }

    /**
     * 插入评测数据并发券
     * @param
     * @param
     * @return
     */
    @Override
    public synchronized Map<String,Object> answerAnalysisAndCoupon(String userAnswer, Integer userId){
        Map<String,Object> returnMap  = new HashMap<String,Object>();
        //发放优惠券 start
        UserEvalationResultVO ueResult = this.selectUserEvalationResultByUserId(userId);
        // 是否已经参加过测评（true：已测评过，false：测评）
        boolean isAdvisor = ueResult != null ? true : false;
        // 发放优惠券 end
        // 1_1,2_8
        UserEvalationResultVO userEvalationResult = this.answerAnalysis(userAnswer, new Integer(userId));
        // 发放优惠券 start
        if(!isAdvisor){
            String platform = CustomConstants.CLIENT_PC;
            // 发放优惠券
            String result = this.sendCoupon(userId, platform);
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
