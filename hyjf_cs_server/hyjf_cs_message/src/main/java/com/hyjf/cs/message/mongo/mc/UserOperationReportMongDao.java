/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.ic.OperationUserReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Service;

/**
 *  用户分析报告
 * @author tanyy
 * @version UserOperationReportMongDao, v0.1 2018/7/23 10:03
 */
@Service
public class UserOperationReportMongDao extends BaseMongoDao<OperationUserReport> {
    @Override
    protected Class<OperationUserReport> getEntityClass() {
        return OperationUserReport.class;
    }
}
