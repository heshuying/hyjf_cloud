/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.evalation.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.service.aems.evalation.AemsEvaluationService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/7 14:03
 * @param 
 * @return 
 **/
@Service
public class AemsEvaluationServiceImpl extends BaseUserServiceImpl implements AemsEvaluationService {

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
