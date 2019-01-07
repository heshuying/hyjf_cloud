/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic.report;

import com.hyjf.cs.message.bean.ic.report.OperationMonthlyReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 月度运营报告
 * @author tanyy
 * @version OperationMonthlyReportMongDao, v0.1 2018/7/23 10:03
 */
@Repository
public class OperationMonthlyReportMongDao extends BaseMongoDao<OperationMonthlyReport> {
    @Override
    protected Class<OperationMonthlyReport> getEntityClass() {
        return OperationMonthlyReport.class;
    }
}
