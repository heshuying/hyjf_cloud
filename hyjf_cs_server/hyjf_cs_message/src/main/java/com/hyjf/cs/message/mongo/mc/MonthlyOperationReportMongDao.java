/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.MonthlyOperationReportEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Service;

/**
 * 月度运营报告
 * @author tanyy
 * @version MonthlyOperationReportMongDao, v0.1 2018/7/23 10:03
 */
@Service
public class MonthlyOperationReportMongDao extends BaseMongoDao<MonthlyOperationReportEntity> {
    @Override
    protected Class<MonthlyOperationReportEntity> getEntityClass() {
        return MonthlyOperationReportEntity.class;
    }
}
