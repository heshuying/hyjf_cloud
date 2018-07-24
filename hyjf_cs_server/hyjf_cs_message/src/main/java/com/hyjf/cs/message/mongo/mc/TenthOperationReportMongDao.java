/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Service;

/**
 * 运营报告十大投资
 * @author tanyy
 * @version TenthOperationReportMongDao, v0.1 2018/7/23 10:03
 */
@Service
public class TenthOperationReportMongDao extends BaseMongoDao<TenthOperationReportEntity> {
    @Override
    protected Class<TenthOperationReportEntity> getEntityClass() {
        return TenthOperationReportEntity.class;
    }
}
