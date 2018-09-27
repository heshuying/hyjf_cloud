/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config;

import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigService, v0.1 2018/9/26 18:45
 */
public interface BailConfigService extends BaseService {

    /**
     * 获取保证金配置总数
     *
     * @param bailConfigRequest
     * @return
     */
    Integer selectBailConfigCount(BailConfigRequest bailConfigRequest);

    /**
     * 获取保证金配置列表
     *
     * @param bailConfigRequest
     * @return
     */
    List<BailConfigCustomizeVO> selectBailConfigRecordList(BailConfigRequest bailConfigRequest);

    /**
     * 根据主键获取保证金配置
     *
     * @param id
     * @return
     */
    BailConfigInfoCustomizeVO selectBailConfigById(Integer id);

    /**
     * 未配置保证金的机构编号
     *
     * @return
     */
    List<HjhInstConfig> selectNoUsedInstConfigList();

    /**
     * 周期内发标已发额度
     *
     * @param bailConfigAddRequest
     * @return
     */
    String selectSendedAccountByCyc(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 根据该机构可用还款方式更新可用授信方式
     *
     * @param instCode
     * @return
     */
    Boolean updateBailInfoDelFlg(String instCode);

    /**
     * 添加保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    Boolean insertBailConfig(BailConfigAddRequest bailConfigAddRequest);
}
