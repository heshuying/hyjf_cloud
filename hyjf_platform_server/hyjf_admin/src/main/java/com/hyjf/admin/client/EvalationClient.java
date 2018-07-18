/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.user.EvalationResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.vo.user.UserEvalationResultVO;
/**
 * @author nxl
 * @version EvaluationClient, v0.1 2018/6/25 17:27
 */
public interface EvalationClient {
    /**
     * 根据筛选条件查找(用户测评列表显示)
     * @author nxl
     * @param request
     * @return
     */
    EvalationResponse selectUserEvalationResultList(EvalationRequest request);
    /**
     * 查找用户测评结果
     * @param userId
     * @return
     * @author nxl
     */
    UserEvalationResultVO selectEvaluationDetailById(String userId);
}
