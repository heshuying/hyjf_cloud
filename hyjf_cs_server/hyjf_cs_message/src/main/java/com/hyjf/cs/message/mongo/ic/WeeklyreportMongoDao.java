package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.WeeklyreportEntity;
import org.springframework.stereotype.Repository;

/**
 * 周报数据
 * @author lisheng
 * @version WeeklyreportMongoDao, v0.1 2018/7/27 15:59
 */
@Repository
public class WeeklyreportMongoDao extends BaseMongoDao<WeeklyreportEntity> {
    @Override
    protected Class<WeeklyreportEntity> getEntityClass() {
        return WeeklyreportEntity.class;
    }

}
