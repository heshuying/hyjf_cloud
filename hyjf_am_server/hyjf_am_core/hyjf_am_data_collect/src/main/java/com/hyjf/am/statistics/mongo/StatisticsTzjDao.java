/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.am.statistics.bean.StatisticsTzj;

/**
 * @author fuqiang
 * @version StatisticsTzjDao, v0.1 2018/7/2 11:12
 */
@Repository
public class StatisticsTzjDao extends BaseMongoDao<StatisticsTzj> {
    @Override
    protected Class<StatisticsTzj> getEntityClass() {
        return StatisticsTzj.class;
    }
}
