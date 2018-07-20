/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import org.springframework.stereotype.Repository;


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
