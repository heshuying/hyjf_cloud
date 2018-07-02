/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.am.statistics.bean.StatisticsTzjUtm;

/**
 * @author fuqiang
 * @version StatisticsTzjUtmDao, v0.1 2018/7/2 11:19
 */
@Repository
public class StatisticsTzjUtmDao extends BaseMongoDao<StatisticsTzjUtm> {
    @Override
    protected Class<StatisticsTzjUtm> getEntityClass() {
        return StatisticsTzjUtm.class;
    }
}
