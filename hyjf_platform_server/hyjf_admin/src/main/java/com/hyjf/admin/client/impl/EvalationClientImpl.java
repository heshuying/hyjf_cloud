/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.EvalationClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.EvalationResponse;
import com.hyjf.am.response.user.UserEvalationResultResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author nxl
 * @version EvaluationClientImpl, v0.1 2018/6/25 17:28
 */
@Service
public class EvalationClientImpl implements EvalationClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据筛选条件查找(用户测评列表显示)
     *
     * @param request
     * @return
     */
    @Override
    public EvalationResponse selectUserEvalationResultList(EvalationRequest request) {
        EvalationResponse response = restTemplate.
                postForEntity("http://AM-USER/am-user/evaluationManager/selectUserEvalationResultList", request, EvalationResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查找用户测评结果
     *
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO selectEvaluationDetailById(String userId) {
        UserEvalationResultResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/evaluationManager/selectEvaluationDetailById/" + userId, UserEvalationResultResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

}
