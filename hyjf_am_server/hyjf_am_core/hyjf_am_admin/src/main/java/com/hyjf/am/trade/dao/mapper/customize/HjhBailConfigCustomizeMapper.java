/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhBailConfigCustomizeMapper, v0.1 2018/9/26 19:10
 */
public interface HjhBailConfigCustomizeMapper {

    /**
     * 获取保证金配置列表数据
     *
     * @param bailConfigRequest
     * @return
     */
    List<BailConfigCustomizeVO> selectHjhBailConfigList(BailConfigRequest bailConfigRequest);

    /**
     * 获取保证金配置详情
     *
     * @param id
     * @return
     */
    BailConfigInfoCustomizeVO selectHjhBailConfigInfo(Integer id);

    /**
     * 获取未配置保证金的资产来源
     *
     * @return
     */
    List<HjhInstConfig> hjhNoUsedInstConfigList();
}
