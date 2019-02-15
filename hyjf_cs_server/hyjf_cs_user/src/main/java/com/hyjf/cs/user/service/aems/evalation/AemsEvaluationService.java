/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.evalation;

import com.hyjf.am.vo.user.EvalationCustomizeVO;
import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.Date;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/7 14:06
 * @param 
 * @return 
 **/
public interface AemsEvaluationService extends BaseUserService {

    /**
     * redis获取测评有效时间计算测评到期时间
     *
     * @param beginTime
     * @return
     */
    Date selectEvaluationExpiredTime(Date beginTime);

    /**
     * 获取测评结果
     * @param evalationType
     * @return
     */
    EvalationVO getEvalationByEvalationType(String evalationType);

    /**
     * Aems风险测评更新数据表
     * @param user
     * @param evalationScoreCount
     * @param evalation
     * @param instCode
     * @param channel
     * @return
     */
    int aemsSaveUserEvaluationResults(UserVO user, Integer evalationScoreCount, EvalationVO evalation, String instCode, String channel);
}
