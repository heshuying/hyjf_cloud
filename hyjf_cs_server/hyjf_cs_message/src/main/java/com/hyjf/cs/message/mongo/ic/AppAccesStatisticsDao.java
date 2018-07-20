package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.AppAccesStatistics;
import org.springframework.stereotype.Repository;


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
