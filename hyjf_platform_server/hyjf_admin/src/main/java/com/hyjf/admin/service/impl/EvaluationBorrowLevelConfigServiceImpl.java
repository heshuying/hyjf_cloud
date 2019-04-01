/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.EvaluationBorrowLevelConfigService;
import com.hyjf.am.response.admin.EvaluationBorrowLevelConfigResponse;
import com.hyjf.am.resquest.admin.EvaluationBorrowLevelConfigRequest;
import com.hyjf.am.vo.admin.EvaluationBorrowLevelConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 风险测评配置:标的等级配置
 *
 * @author liuyang
 * @version EvaluationBorrowLevelConfigServiceImpl, v0.1 2018/12/25 9:41
 */
@Service
public class EvaluationBorrowLevelConfigServiceImpl extends BaseAdminServiceImpl implements EvaluationBorrowLevelConfigService {
    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 获取风险测评配置信用等级配置列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public EvaluationBorrowLevelConfigResponse getEvaluationBorrowLevelConfigList(EvaluationBorrowLevelConfigRequest requestBean) {
        EvaluationBorrowLevelConfigResponse evaluationBorrowLevelConfig = amTradeClient.getEvaluationBorrowLevelConfigList(requestBean);
        return evaluationBorrowLevelConfig;
    }

    /**
     * 查询风险测评配置信用等级配置
     *
     * @param id
     * @return
     */
    @Override
    public EvaluationBorrowLevelConfigVO getEvaluationBorrowLevelConfigById(Integer id) {
        EvaluationBorrowLevelConfigVO evaluationBorrowLevelConfigVO = amTradeClient.getEvaluationBorrowLevelConfigById(id);
        return evaluationBorrowLevelConfigVO;
    }

    /**
     * 更新风险测评配置信用等级配置
     *
     * @param requestBean
     * @return
     */
    @Override
    public EvaluationBorrowLevelConfigResponse updateBorrowLevelConfig(EvaluationBorrowLevelConfigRequest requestBean){
        return  amTradeClient.updateBorrowLevelConfig(requestBean);
    }
}
