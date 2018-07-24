/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.OperationReportColumnEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 运营报告
 * @author tanyy
 * @version OperationReportColumnMongDao, v0.1 2018/7/23 10:03
 */
@Repository
public class OperationReportColumnMongDao extends BaseMongoDao<OperationReportColumnEntity> {

    @Override
    protected Class<OperationReportColumnEntity> getEntityClass() {
        return OperationReportColumnEntity.class;
    }

    @Override
    public List<OperationReportColumnEntity> find(Query query){
        return this.mongoTemplate.find(query, getEntityClass());
    }

}
