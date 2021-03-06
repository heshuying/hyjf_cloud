/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config;

import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfig;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfigLog;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;

import java.util.List;

/**
 * 风险测评配置
 *
 * @param
 * @author Zha Daojian
 * @date 2018/12/24 10:28
 * @return
 **/
public interface EvaluationConfigService extends BaseService {

    /**
     * 获取风险测评配置总数
     *
     * @param request
     * @return
     */
    Integer selectEvaluationMoneyCount(EvaluationMoneyRequest request);

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
     * @param request
     * @return
     */
    Integer selectEvaluationCheckCount(EvaluationCheckRequest request);

    /**
     * 获取风险测评-限额配置列表
     *
     * @param request
     * @return
     */
    List<EvaluationConfig> selectEvaluationCheckList(EvaluationCheckRequest request);


    /**
     * 获取风险测评配置总数(操作日志)
     *
     * @param request
     * @return
     */
    Integer selectEvaluationMoneyLogCount(EvaluationMoneyLogRequest request);

    /**
     * 获取风险测评-限额配置列表(操作日志)
     *
     * @param request
     * @return
     */
    List<EvaluationConfigLog> selectEvaluationMoneyLogList(EvaluationMoneyLogRequest request);

    /**
     * 获取风险测评配置总数(操作日志)
     *
     * @param request
     * @return
     */
    Integer selectEvaluationCheckLogCount(EvaluationCheckLogRequest request);

    /**
     * 获取风险测评-限额配置列表(操作日志)
     *
     * @param request
     * @return
     */
    List<EvaluationConfigLog> selectEvaluationCheckLogList(EvaluationCheckLogRequest request);


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

    /**
     * 获取风险测评配置风险测评等级配置数量
     *
     * @param request
     * @return
     */
    Integer getEvaluationBorrowLevelConfigCount(EvaluationBorrowLevelConfigRequest request);

    /**
     * 获取风险测评配置风险测评等级配置列表
     *
     * @param request
     * @return
     */
    List<EvaluationConfig> getEvaluationBorrowLevelConfigList(EvaluationBorrowLevelConfigRequest request);

    /**
     * 更新测评等级配置
     *
     * @param request
     * @return
     */
    boolean updateBorrowLevelConfig(EvaluationBorrowLevelConfigRequest request);

    /**
     * 查询风险测评配置日志件数
     *
     * @param request
     * @return
     */
    Integer getBorrowLevelConfigLogListCount(EvaluationBorrowLevelConfigLogRequest request);

    /**
     * 查询风险测评配置日志列表
     *
     * @param request
     * @return
     */
    List<EvaluationConfigLog> getBorrowLevelConfigLogList(EvaluationBorrowLevelConfigLogRequest request);
}
