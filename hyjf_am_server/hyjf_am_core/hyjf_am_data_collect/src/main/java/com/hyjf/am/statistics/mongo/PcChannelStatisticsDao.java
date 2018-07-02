/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.am.statistics.bean.PcChannelStatistics;

/**
 * @author fuqiang
 * @version PcChannelStatisticsDao, v0.1 2018/7/2 10:15
 */
@Repository
public class PcChannelStatisticsDao extends BaseMongoDao<PcChannelStatistics> {
    @Override
    protected Class<PcChannelStatistics> getEntityClass() {
        return PcChannelStatistics.class;
    }
}
