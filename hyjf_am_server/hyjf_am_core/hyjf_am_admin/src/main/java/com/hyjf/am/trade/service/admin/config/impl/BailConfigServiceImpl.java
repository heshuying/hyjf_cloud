/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config.impl;

import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.trade.dao.model.auto.HjhBailConfigExample;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.admin.config.BailConfigService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigServiceImpl, v0.1 2018/9/26 18:45
 */
@Service
public class BailConfigServiceImpl  extends BaseServiceImpl implements BailConfigService {

    /**
     * 获取保证金配置总数
     *
     * @param bailConfigRequest
     * @return
     */
    @Override
    public Integer selectBailConfigCount(BailConfigRequest bailConfigRequest) {
        HjhBailConfigExample example = new HjhBailConfigExample();
        example.createCriteria().andDelFlgEqualTo(0);
        return hjhBailConfigMapper.countByExample(example);
    }

    /**
     * 获取保证金配置列表
     *
     * @param bailConfigRequest
     * @return
     */
    @Override
    public List<BailConfigCustomizeVO> selectBailConfigRecordList(BailConfigRequest bailConfigRequest) {
        return hjhBailConfigCustomizeMapper.selectHjhBailConfigList(bailConfigRequest);
    }

    /**
     * 根据主键获取保证金配置
     *
     * @param id
     * @return
     */
    @Override
    public BailConfigInfoCustomizeVO selectBailConfigById(Integer id) {
        return hjhBailConfigCustomizeMapper.selectHjhBailConfigInfo(id);
    }

    /**
     * 未配置保证金的机构编号
     *
     * @return
     */
    @Override
    public List<HjhInstConfig> selectNoUsedInstConfigList() {
        return hjhBailConfigCustomizeMapper.hjhNoUsedInstConfigList();
    }
}
