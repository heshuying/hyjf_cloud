/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.evalation;

import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.service.BaseUserService;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/7 14:06
 * @param 
 * @return 
 **/
public interface AemsEvaluationService extends BaseUserService {

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
