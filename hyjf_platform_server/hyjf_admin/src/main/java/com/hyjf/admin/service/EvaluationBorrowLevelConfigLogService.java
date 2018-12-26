/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.EvaluationBorrowLevelConfigLogResponse;
import com.hyjf.am.resquest.admin.EvaluationBorrowLevelConfigLogRequest; /**
 * 风险测评等级配置日志Service
 *
 * @author liuyang
 * @version EvaluationBorrowLevelConfigLogService, v0.1 2018/12/25 15:31
 */
public interface EvaluationBorrowLevelConfigLogService extends BaseAdminService {
    /**
     * 风险测评等级配置日志
     * @param requestBean
     * @return
     */
    EvaluationBorrowLevelConfigLogResponse getBorrowLevelConfigLogList(EvaluationBorrowLevelConfigLogRequest requestBean);
}
