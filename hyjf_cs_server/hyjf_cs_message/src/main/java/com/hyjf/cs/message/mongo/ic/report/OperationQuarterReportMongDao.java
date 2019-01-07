/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic.report;

import com.hyjf.cs.message.bean.ic.report.OperationQuarterReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 *  季度运营报告
 * @author tanyy
 * @version QuarterOperationReportMongDao, v0.1 2018/7/23 10:03
 */
@Repository
public class OperationQuarterReportMongDao extends BaseMongoDao<OperationQuarterReport> {

    @Override
    protected Class<OperationQuarterReport> getEntityClass() {
        return OperationQuarterReport.class;
    }
}
