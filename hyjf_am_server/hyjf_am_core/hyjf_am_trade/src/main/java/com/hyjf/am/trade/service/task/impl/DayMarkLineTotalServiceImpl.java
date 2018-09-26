/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.hyjf.am.trade.dao.model.auto.HjhBailConfig;
import com.hyjf.am.trade.dao.model.auto.HjhBailConfigExample;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.DayMarkLineTotalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DayMarkLineTotalServiceImpl, v0.1 2018/9/25 17:48
 */
@Service
public class DayMarkLineTotalServiceImpl extends BaseServiceImpl implements DayMarkLineTotalService {

    /**
     * 获取所有选择日累计的数据(包括已删除)
     *
     * @return
     */
    @Override
    public List<HjhBailConfig> selectAccumulateListAll() {
        HjhBailConfigExample example = new HjhBailConfigExample();
        List<HjhBailConfig> hjhBailConfigList = this.hjhBailConfigMapper.selectByExample(example);
        if (null != hjhBailConfigList && hjhBailConfigList.size() > 0) {
            return hjhBailConfigList;
        }
        return null;
    }

    /**
     * 获取所有选择日累计的数据
     *
     * @return
     */
    @Override
    public List<HjhBailConfig> selectAccumulateList() {
        HjhBailConfigExample example = new HjhBailConfigExample();
        example.createCriteria().andDelFlgEqualTo(0);
        List<HjhBailConfig> hjhBailConfigList = this.hjhBailConfigMapper.selectByExample(example);
        if (null != hjhBailConfigList && hjhBailConfigList.size() > 0) {
            return hjhBailConfigList;
        }
        return null;
    }
}
