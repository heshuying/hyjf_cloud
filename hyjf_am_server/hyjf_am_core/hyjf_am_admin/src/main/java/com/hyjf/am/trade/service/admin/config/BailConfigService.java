/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config;

import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

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
}
