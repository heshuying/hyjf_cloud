/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.ic.OperationHalfYearReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Service;

/**
 * 半年度度运营报告
 * @author tanyy
 * @version HalfYearOperationReportMongDao, v0.1 2018/7/23 10:07
 */
@Service
public class HalfYearOperationReportMongDao extends BaseMongoDao<OperationHalfYearReport> {
    @Override
    protected Class<OperationHalfYearReport> getEntityClass() {
        return OperationHalfYearReport.class;
    }
}
