/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.QuarterOperationReportEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Service;

/**
 *  季度运营报告
 * @author tanyy
 * @version QuarterOperationReportMongDao, v0.1 2018/7/23 10:03
 */
@Service
public class QuarterOperationReportMongDao extends BaseMongoDao<QuarterOperationReportEntity> {

    @Override
    protected Class<QuarterOperationReportEntity> getEntityClass() {
        return QuarterOperationReportEntity.class;
    }
}
