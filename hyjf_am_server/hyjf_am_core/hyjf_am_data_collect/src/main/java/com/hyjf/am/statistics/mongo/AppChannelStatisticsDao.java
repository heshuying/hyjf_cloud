/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.am.statistics.bean.AppChannelStatistics;

/**
 * @author fuqiang
 * @version AppChannelStatisticsDao, v0.1 2018/7/2 10:53
 */
@Repository
public class AppChannelStatisticsDao extends BaseMongoDao<AppChannelStatistics> {
    @Override
    protected Class<AppChannelStatistics> getEntityClass() {
        return AppChannelStatistics.class;
    }
}
