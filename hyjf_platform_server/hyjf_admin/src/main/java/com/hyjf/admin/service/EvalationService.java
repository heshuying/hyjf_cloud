/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.response.EvalationInitResponseBean;
import com.hyjf.am.response.user.EvalationResultResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.vo.user.UserEvalationResultVO;

/**
 * @author nxl
 * @version EvaluationService, v0.1 2018/6/25 17:23
 */
public interface EvalationService {
    /**
     * 查找用户测评数据
     * @return
     */
    EvalationResultResponse selectUserEvalationResultList(EvalationRequest request);

    /**
     * 查找用户测评结果
     * @param userId
     * @return
     */
    UserEvalationResultVO selectUserEvalationResultByUserId(String userId);
    /**
     * 用户测评初始化
     * @return
     */
    public EvalationInitResponseBean initUserManaget();
}
