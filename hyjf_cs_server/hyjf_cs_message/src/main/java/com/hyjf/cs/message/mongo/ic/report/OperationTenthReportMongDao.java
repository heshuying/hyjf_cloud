/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic.report;

import org.springframework.stereotype.Repository;

import com.hyjf.cs.message.bean.ic.report.OperationTenthReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;

/**
 * 运营报告十大出借
 * @author tanyy
 * @version OperationTenthReportMongDao, v0.1 2018/7/23 10:03
 */
@Repository
public class OperationTenthReportMongDao extends BaseMongoDao<OperationTenthReport> {
    @Override
    protected Class<OperationTenthReport> getEntityClass() {
        return OperationTenthReport.class;
    }
}
