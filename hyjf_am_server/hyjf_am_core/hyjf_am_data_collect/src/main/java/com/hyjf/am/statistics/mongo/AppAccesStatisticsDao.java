package com.hyjf.am.statistics.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.am.statistics.bean.AppAccesStatistics;

/**
 * @author xiasq
 * @version AppAccessStaticsDao, v0.1 2018/6/19 16:47
 */
@Repository
public class AppAccesStatisticsDao extends BaseMongoDao<AppAccesStatistics> {

    @Override
    protected Class<AppAccesStatistics> getEntityClass() {
        return AppAccesStatistics.class;
    }


}
