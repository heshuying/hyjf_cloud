/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.EvaluationBorrowLevelConfigResponse;
import com.hyjf.am.resquest.admin.EvaluationBorrowLevelConfigRequest;
import com.hyjf.am.vo.admin.EvaluationBorrowLevelConfigVO;

/**
 * 风险测评配置:信用等级配置Service
 *
 * @author liuyang
 * @version EvaluationBorrowLevelConfigService, v0.1 2018/12/25 9:39
 */
public interface EvaluationBorrowLevelConfigService extends BaseAdminService {
    /**
     * 获取风险测评配置信用等级配置列表
     *
     * @param requestBean
     * @return
     */
    EvaluationBorrowLevelConfigResponse getEvaluationBorrowLevelConfigList(EvaluationBorrowLevelConfigRequest requestBean);

    /**
     * 查询风险测评配置信用等级配置
     *
     * @param id
     * @return
     */
    EvaluationBorrowLevelConfigVO getEvaluationBorrowLevelConfigById(Integer id);

    /**
     * 更新风险测评配置信用等级配置
     *
     * @param requestBean
     * @return
     */
    EvaluationBorrowLevelConfigResponse updateBorrowLevelConfig(EvaluationBorrowLevelConfigRequest requestBean);
}
