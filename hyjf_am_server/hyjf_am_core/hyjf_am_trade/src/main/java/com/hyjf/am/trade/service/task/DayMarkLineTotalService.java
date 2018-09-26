/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.HjhBailConfig;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DayMarkLineTotalService, v0.1 2018/9/25 17:47
 */
public interface DayMarkLineTotalService extends BaseService {

    /**
     * 获取所有选择日累计的数据(包括已删除)
     *
     * @return
     */
    List<HjhBailConfig> selectAccumulateListAll();

    /**
     * 获取所有选择日累计的数据
     *
     * @return
     */
    List<HjhBailConfig> selectAccumulateList();
}
