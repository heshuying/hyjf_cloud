/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.evalation.impl;

import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.user.service.aems.evalation.AemsEvaluationService;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import com.hyjf.cs.user.service.evaluation.impl.EvaluationServiceImpl;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/7 14:03
 * @param 
 * @return 
 **/
@Service
public class AemsEvaluationServiceImpl extends BaseUserServiceImpl implements AemsEvaluationService {


    /**
     * redis获取测评有效时间计算测评到期时间
     *
     * @param beginTime
     * @return
     */
    @Override
    public Date selectEvaluationExpiredTime(Date beginTime) {

        // 测评过期时间key
        boolean isExist = RedisUtils.exists(RedisConstants.REVALUATION_EXPIRED_DAY);
        if (!isExist) {
            logger.error("redis未设定测评有效日！key：" + RedisConstants.REVALUATION_EXPIRED_DAY);
            return null;
        }

        // 从redis获取测评有效日
        String evaluationExpiredDayStr = RedisUtils.get(RedisConstants.REVALUATION_EXPIRED_DAY);
        if (StringUtils.isBlank(evaluationExpiredDayStr)) {
            logger.error("redis测评有效日设置为空！key：" + RedisConstants.REVALUATION_EXPIRED_DAY);
            return null;
        }

        // redis设定为非数字报错
        if (!NumberUtils.isNumber(evaluationExpiredDayStr)) {
            logger.error("redis测评有效日含非数字！key：" + RedisConstants.REVALUATION_EXPIRED_DAY + "========value:" + evaluationExpiredDayStr);
            return null;
        }

        // redis测评到期日计算
        try {
            Integer evaluationExpiredDay = Integer.parseInt(evaluationExpiredDayStr);
            return GetDate.countDate(beginTime, 5, evaluationExpiredDay);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("redis测评有效日格式化失败！key：" + RedisConstants.REVALUATION_EXPIRED_DAY + "========value:" + evaluationExpiredDayStr);
            return null;
        }
    }

    /***Aems风险测评更新数据表
    * @author Zha Daojian
    * @date 2018/12/7 14:21
    * @param user, evalationScoreCount, evalation, instCode, channel
    * @return int
    **/
    @Override
    public int aemsSaveUserEvaluationResults(UserVO user, Integer evalationScoreCount, EvalationVO evalation, String instCode, String channel) {
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


    private UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId) {
        UserEvalationResultVO userEvalationResult = amUserClient.selectUserEvalationResultByUserId(userId);
        return userEvalationResult;
    }

    public EvalationVO getEvalationByEvalationType(String  evalationType){
        EvalationVO evalation = amUserClient.getEvalationByEvalationType(evalationType);
        CheckUtil.check(evalation != null, MsgEnum.STATUS_EV000001);
        return evalation;
    }

}
