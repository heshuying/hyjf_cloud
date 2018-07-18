/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.EvalationService;
import com.hyjf.am.response.user.EvalationResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nxl
 * @version EvaluationServiceImpl, v0.1 2018/6/25 17:24
 */
@Service
public class EvalationServiceImpl implements EvalationService {

    @Autowired
    private AmUserClient evalationClient;
    /**
     * 查找用户测评数据
     * @return
     */
    @Override
    public EvalationResponse selectUserEvalationResultList(EvalationRequest request){
        return  evalationClient.selectUserEvalationResultList(request);
    }

    /**
     * 查找用户测评结果
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO selectUserEvalationResultByUserId(String userId){
        return evalationClient.selectEvaluationDetailById(userId);
    }
}
