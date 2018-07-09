/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.am.statistics.bean.StatisticsTzjHour;

/**
 * @author fuqiang
 * @version StatisticsTzjHourDao, v0.1 2018/7/2 11:12
 */
@Repository
public class StatisticsTzjHourDao extends BaseMongoDao<StatisticsTzjHour> {
    @Override
    protected Class<StatisticsTzjHour> getEntityClass() {
        return StatisticsTzjHour.class;
    }
}
