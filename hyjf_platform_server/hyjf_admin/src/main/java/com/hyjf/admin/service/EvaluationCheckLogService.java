package com.hyjf.admin.service;

import com.hyjf.am.response.trade.EvaluationCheckLogResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckLogRequest;

public interface EvaluationCheckLogService {

    /**
     * 获取测评配置,开关列表
     * 
     * @return
     */
    EvaluationCheckLogResponse getEvaluationCheckLogList(EvaluationCheckLogRequest request);

}