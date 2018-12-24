/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config;

import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.resquest.admin.EvaluationCheckRequest;
import com.hyjf.am.resquest.admin.EvaluationMoneyRequest;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfig;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;

import java.util.List;

/**
 * 风险测评配置
 * @author Zha Daojian
 * @date 2018/12/24 10:28
 * @param 
 * @return 
 **/
public interface EvaluationConfigService extends BaseService {

    /**
     * 获取风险测评配置总数
     *
     * @param bailConfigRequest
     * @return
     */
    Integer selectEvaluationMoneyCount(EvaluationMoneyRequest bailConfigRequest);

    /**
     * 获取风险测评-限额配置列表
     *
     * @param request
     * @return
     */
    List<EvaluationConfig> selectEvaluationMoneyList(EvaluationMoneyRequest request);

    /**
     * 获取风险测评配置总数
     *
     * @param bailConfigRequest
     * @return
     */
    Integer selectEvaluationCheckCount(EvaluationCheckRequest bailConfigRequest);

    /**
     * 获取风险测评-限额配置列表
     *
     * @param request
     * @return
     */
    List<EvaluationConfig> selectEvaluationCheckList(EvaluationCheckRequest request);


    /**
     * 根据主键获取风险测评-开关配置
     *
     * @param id
     * @return
     */
    EvaluationConfig selectEvaluationCheckById(Integer id);


    /**
     * 根据主键获取风险测评-限额配置
     *
     * @param id
     * @return
     */
    EvaluationConfig selectEvaluationMoneyById(Integer id);

    /**
     * 根据该机构可用还款方式更新可用授信方式
     *
     * @param request
     * @return
     */
    Boolean updateEvaluationMoney(EvaluationMoneyRequest request);
    Boolean updateEvaluationCheck(EvaluationCheckRequest request);

}
