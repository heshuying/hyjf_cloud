/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic.report;

import org.springframework.stereotype.Repository;

import com.hyjf.cs.message.bean.ic.report.OperationHalfYearReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;

/**
 * 半年度度运营报告
 * @author tanyy
 * @version OperationHalfYearReportMongDao, v0.1 2018/7/23 10:07
 */
@Repository
public class OperationHalfYearReportMongDao extends BaseMongoDao<OperationHalfYearReport> {
    @Override
    protected Class<OperationHalfYearReport> getEntityClass() {
        return OperationHalfYearReport.class;
    }
}
