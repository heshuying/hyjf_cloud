/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.admin.service.EvaluationBorrowLevelConfigLogService;
import com.hyjf.am.response.admin.EvaluationBorrowLevelConfigLogResponse;
import com.hyjf.am.response.trade.EvaluationCheckLogResponse;
import com.hyjf.am.resquest.admin.EvaluationBorrowLevelConfigLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 风险测评等级配置日志Service实现类
 *
 * @author liuyang
 * @version EvaluationBorrowLevelConfigLogServiceImpl, v0.1 2018/12/25 15:32
 */
@Service
public class EvaluationBorrowLevelConfigLogServiceImpl extends BaseAdminServiceImpl implements EvaluationBorrowLevelConfigLogService {

    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 风险测评等级配置日志
     *
     * @param requestBean
     * @return
     */
    @Override
    public EvaluationBorrowLevelConfigLogResponse getBorrowLevelConfigLogList(EvaluationBorrowLevelConfigLogRequest requestBean) {
        EvaluationBorrowLevelConfigLogResponse evaluationBorrowLevelConfigLogList = amTradeClient.getBorrowLevelConfigLogList(requestBean);
        return  evaluationBorrowLevelConfigLogList;
    }
}
