/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.financialadvisor.impl;

import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.resquest.user.UserEvalationRequest;
import com.hyjf.am.vo.user.ActivityListVO;
import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.cs.user.client.BankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.financialadvisor.CouponCheckUtilDefine;
import com.hyjf.cs.user.service.financialadvisor.FinancialAdvisorService;
import com.hyjf.soa.apiweb.CommonParamBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version FinancialAdvisorImpl, v0.1 2018/6/15 19:11
 */
@Service
public class FinancialAdvisorServiceImpl extends BaseUserServiceImpl implements FinancialAdvisorService {

    @Autowired
    BankOpenClient bankOpenClient;

    @Autowired
    AmUserClient amUserClient;


    @Autowired
    SystemConfig systemConfig;
    @Override
    public List<QuestionCustomizeVO> getNewQuestionList() {
        return null;
    }

    @Override
    public UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId) {
        UserEvalationResultVO userEvalationResult = bankOpenClient.selectUserEvalationResultByUserId(userId);
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
        bankOpenClient.deleteUserEvalationResultByUserId(userId);

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
        int countScore = amUserClient.countScore(answerRequest);
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
//            result = CommonSoaUtils.sendUserCoupon(couponParamBean);//TODO:微服务这种方式是错的
        }
        return result;
    }

    /**
     * 活动是否过期
     */
    @Override
    public String checkActivityIfAvailable(String activityId) {
        if(activityId==null){
            return CouponCheckUtilDefine.ACTIVITYID_IS_NULL;
        }
        ActivityListVO activityList=amUserClient.selectActivityList(new Integer(activityId));
        if(activityList==null){
            return CouponCheckUtilDefine.ACTIVITY_ISNULL;
        }
        if(activityList.getTimeStart()>System.currentTimeMillis()/1000){
            return CouponCheckUtilDefine.ACTIVITY_TIME_NOT_START;
        }
        if(activityList.getTimeEnd()<System.currentTimeMillis()/1000){
            return CouponCheckUtilDefine.ACTIVITY_TIME_END;
        }
        return "";
    }

    public String checkActivityPlatform(String activityId, String platform) {
        if(activityId==null){
            return CouponCheckUtilDefine.ACTIVITYID_IS_NULL;
        }
        ActivityListVO activityList=amUserClient.selectActivityList(new Integer(activityId));
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
            return CouponCheckUtilDefine.PLATFORM_LIMIT.replace("***", selectedClientDisplayBuffer.toString());
        }
        return "";
    }


}
