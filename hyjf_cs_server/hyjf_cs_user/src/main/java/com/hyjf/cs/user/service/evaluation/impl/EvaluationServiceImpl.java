/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.evaluation.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.resquest.user.UserEvalationRequest;
import com.hyjf.am.vo.config.NewAppQuestionCustomizeVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.trade.EvaluationConfigVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.ThirdPartyEvaluationRequestBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    CommonProducer commonProducer;

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
        int countScore = 0;
        if (!Strings.isNullOrEmpty(userAnswer)) {
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
            countScore = amConfigClient.countScore(answerRequest);
        }

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
            // 发放优惠券（1张加息券）
            try {
                JSONObject params = new JSONObject();
                params.put("mqMsgId", GetCode.getRandomCode(10));
                params.put("userId", String.valueOf(userId));
                params.put("sendFlg", "11");
                commonProducer.messageSend(new MessageContent(MQConstant.GRANT_COUPON_TOPIC,
                        UUID.randomUUID().toString(), params));
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
    public List<EvalationCustomizeVO> getEvalationRecord() {
        List<EvalationCustomizeVO> evalationVOList = amUserClient.getEvalationRecord();
        //查询测评配置
        EvaluationConfigVO evalConfig = new EvaluationConfigVO();
        List<EvaluationConfigVO> evalConfigList = amTradeClient.selectEvaluationConfig(evalConfig);
        if(evalConfigList != null && evalConfigList.size() > 0){
            evalConfig = evalConfigList.get(0);
        }
        for(EvalationCustomizeVO evalStr : evalationVOList){
            //初始化金额返回值
            String revaluation_money;
            switch (evalStr.getEvalType()) {
                case "保守型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getConservativeEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getConservativeEvaluationSingleMoney());
                    }
                    evalStr.setRevaluationMoney(StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()));
                    break;
                case "稳健型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getSteadyEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getSteadyEvaluationSingleMoney());
                    }
                    evalStr.setRevaluationMoney(StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()));
                    break;
                case "成长型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_GROWTH) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_GROWTH);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getGrowupEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getGrowupEvaluationSingleMoney());
                    }
                    evalStr.setRevaluationMoney(StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()));
                    break;
                case "进取型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getEnterprisingEvaluationSinglMoney() == null ? "0" : String.valueOf(evalConfig.getEnterprisingEvaluationSinglMoney());
                    }
                    evalStr.setRevaluationMoney(StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()));
                    break;
                default:
                    evalStr.setRevaluationMoney("0");
            }
        }
        return evalationVOList;
    }

    @Override
    public String checkActivityPlatform(String activityId, String platform) {
        if(activityId==null){
            return CommonConstant.ACTIVITYID_IS_NULL;
        }
        ActivityListVO activityList = amMarketClient.selectActivityList(new Integer(activityId));
        if (activityList!=null&&activityList.getPlatform().indexOf(platform) == -1) {
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
        UserEvalationResultVO ueResultIs = this.selectUserEvalationResultByUserId(userId);
        // 是否已经参加过测评（true：已测评过，false：测评）
        boolean isAdvisor = ueResultIs != null ? true : false;
        // 发放优惠券 start
        if (!isAdvisor) {
            // 发放优惠券
            String result = this.sendCoupon(userId, platform);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject resultObj = JSONObject.parseObject(result);
                if (resultObj.getIntValue("status") == 0 && resultObj.getIntValue("couponCount") > 0) {
                    String sendResult = "恭喜您获得"+resultObj.getIntValue("couponCount")+"张加息券，体验出借流程，获取高额收益，可在我的账户-优惠券中查看";
                    int sendCount = resultObj.getIntValue("couponCount");
                    returnMap.put("sendCount", sendCount);
                    returnMap.put("sendResult", sendResult);
                }
            }
        }
        // 1_1,2_8
        UserEvalationResultVO userEvalationResult = this.answerAnalysis(userAnswer, userId,behaviorId);
        returnMap.put("revaluationMoney", "");
        returnMap.put("evalType", "");
        //查询测评配置
        EvaluationConfigVO evalConfig = new EvaluationConfigVO();
        List<EvaluationConfigVO> evalConfigList = amTradeClient.selectEvaluationConfig(evalConfig);
        if(evalConfigList != null && evalConfigList.size() > 0){
            evalConfig = evalConfigList.get(0);
        }
        //查询redis中的类型和返回增加金额上限
        if(userEvalationResult != null){
            //从redis中获取测评类型和上限金额
            String revaluation_money = null;
            String eval_type = userEvalationResult.getEvalType();
            switch (eval_type){
                case "保守型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if("0".equals(revaluation_money)){
                        revaluation_money = evalConfig.getConservativeEvaluationSingleMoney() == null ? "0": String.valueOf(evalConfig.getConservativeEvaluationSingleMoney());
                    }
                    break;
                case "稳健型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if("0".equals(revaluation_money)){
                        revaluation_money = evalConfig.getSteadyEvaluationSingleMoney() == null ? "0": String.valueOf(evalConfig.getSteadyEvaluationSingleMoney());
                    }
                    break;
                case "成长型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_GROWTH) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_GROWTH);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if("0".equals(revaluation_money)){
                        revaluation_money = evalConfig.getGrowupEvaluationSingleMoney() == null ? "0": String.valueOf(evalConfig.getGrowupEvaluationSingleMoney());
                    }
                    break;
                case "进取型":
                    //从redis获取金额（单笔）
                    revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE);
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if("0".equals(revaluation_money)){
                        revaluation_money = evalConfig.getEnterprisingEvaluationSinglMoney() == null ? "0": String.valueOf(evalConfig.getEnterprisingEvaluationSinglMoney());
                    }
                    break;
                default:
                    revaluation_money = "0";
            }
            returnMap.put("revaluationMoney", StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()));
            returnMap.put("evalType", eval_type);
        }
        userEvalationResult.setEvalType((String) returnMap.get("evalType"));
        userEvalationResult.setRevaluationMoney((String) returnMap.get("revaluationMoney"));
        returnMap.put("userEvalationResult", userEvalationResult);

        // add 合规数据上报 埋点 liubin 20181122 start
        // 推送数据到MQ 用户信息修改（风险测评）
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.USERINFO_CHANGE_TAG, UUID.randomUUID().toString(), params),
                MQConstant.HG_REPORT_DELAY_LEVEL);
        // add 合规数据上报 埋点 liubin 20181122 end

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

    @Override
    public List<NewAppQuestionCustomizeVO> getNewAppQuestionList() {
        List<NewAppQuestionCustomizeVO> result = amConfigClient.getNewAppQuestionList();
        return result;
    }

    /**
     * 修改user表的风险测评标志
     * @param userId
     */
    @Override
    public void updateUser(int userId){
        //修改users表标志位
        UserVO users = this.getUsersById(userId);
        if (users != null) {
            users.setIsEvaluationFlag(1);
            // 获取测评到期日期
            Date evaluationExpiredTime = selectEvaluationExpiredTime(new Date());
            users.setEvaluationExpiredTime(evaluationExpiredTime);
            this.updateUserByUserId(users);
        }
    }

    /**
     * redis获取测评有效时间计算测评到期时间
     * redis获取到有效日用redis的、redis获取不到默认有效180天
     *
     * @param beginTime
     * @return
     */
    private Date selectEvaluationExpiredTime(Date beginTime) {
        // 测评过期时间key
        boolean isExist = RedisUtils.exists(RedisConstants.REVALUATION_EXPIRED_DAY);
        if (!isExist) {
            logger.error("redis未设定测评有效日！key：" + RedisConstants.REVALUATION_EXPIRED_DAY);
            return GetDate.countDate(beginTime, 5, 180);
        }

        // 从redis获取测评有效日
        String evaluationExpiredDayStr = RedisUtils.get(RedisConstants.REVALUATION_EXPIRED_DAY);
        if (org.apache.commons.lang3.StringUtils.isBlank(evaluationExpiredDayStr)) {
            logger.error("redis测评有效日设置为空！key：" + RedisConstants.REVALUATION_EXPIRED_DAY);
            return GetDate.countDate(beginTime, 5, 180);
        }

        // redis设定为非数字报错
        if (!NumberUtils.isNumber(evaluationExpiredDayStr)) {
            logger.error("redis测评有效日含非数字！key：" + RedisConstants.REVALUATION_EXPIRED_DAY + "========value:" + evaluationExpiredDayStr);
            return GetDate.countDate(beginTime, 5, 180);
        }

        // redis测评到期日计算
        try {
            Integer evaluationExpiredDay = Integer.parseInt(evaluationExpiredDayStr);
            return GetDate.countDate(beginTime, 5, evaluationExpiredDay);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("redis测评有效日格式化失败！key：" + RedisConstants.REVALUATION_EXPIRED_DAY + "========value:" + evaluationExpiredDayStr);
            return GetDate.countDate(beginTime, 5, 180);
        }
    }

    @Override
    public UserEvalationResultVO skipEvaluate(Integer userId, int countScore) {
        UserEvalationResultVO userEvalationResult = amUserClient.skipEvaluate(userId,countScore);
        return userEvalationResult;
    }

}
