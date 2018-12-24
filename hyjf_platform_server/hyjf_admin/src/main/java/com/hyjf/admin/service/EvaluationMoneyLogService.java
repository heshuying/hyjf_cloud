package com.hyjf.admin.service;

import com.hyjf.am.response.trade.EvaluationMoneyLogResponse;
import com.hyjf.am.resquest.admin.EvaluationMoneyLogRequest;

public interface EvaluationMoneyLogService {

    /**
     * 获取测评配置,开关操作日志列表
     * 
     * @return
     */
    EvaluationMoneyLogResponse getEvaluationMoneyLogList(EvaluationMoneyLogRequest request);


}