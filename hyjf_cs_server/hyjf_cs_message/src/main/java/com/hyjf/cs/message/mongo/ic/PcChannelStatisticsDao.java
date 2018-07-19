/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.PcChannelStatistics;
import org.springframework.stereotype.Repository;


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
