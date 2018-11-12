/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.ic.OperationYearReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Service;

/**
 * 年度运营报告
 * @author tanyy
 * @version YearOperationReportMongDao, v0.1 2018/7/23 10:03
 */
@Service
public class YearOperationReportMongDao extends BaseMongoDao<OperationYearReport> {

    @Override
    protected Class<OperationYearReport> getEntityClass() {
        return OperationYearReport.class;
    }
}
