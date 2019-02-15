/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic.report;

import com.hyjf.cs.message.bean.ic.report.OperationActivityReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 运营报告活动
 * @author tanyy
 * @version OperationReportActivityMongDao, v0.1 2018/7/23 10:03
 */
@Repository
public class OperationReportActivityMongDao  extends BaseMongoDao<OperationActivityReport> {

    @Override
    protected Class<OperationActivityReport> getEntityClass() {
        return OperationActivityReport.class;
    }
}
