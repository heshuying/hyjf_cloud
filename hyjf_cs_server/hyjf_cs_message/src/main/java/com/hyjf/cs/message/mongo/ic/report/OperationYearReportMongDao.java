/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic.report;

import org.springframework.stereotype.Repository;

import com.hyjf.cs.message.bean.ic.report.OperationYearReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;

/**
 * 年度运营报告
 * @author tanyy
 * @version OperationYearReportMongDao, v0.1 2018/7/23 10:03
 */
@Repository
public class OperationYearReportMongDao extends BaseMongoDao<OperationYearReport> {

    @Override
    protected Class<OperationYearReport> getEntityClass() {
        return OperationYearReport.class;
    }
}
