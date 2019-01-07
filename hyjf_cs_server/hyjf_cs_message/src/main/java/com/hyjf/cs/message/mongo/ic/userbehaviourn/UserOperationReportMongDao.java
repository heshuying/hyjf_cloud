/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic.userbehaviourn;

import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

import com.hyjf.cs.message.bean.ic.userbehaviourn.UserOperationReport;

/**
 *  用户分析报告
 * @author tanyy
 * @version UserOperationReportMongDao, v0.1 2018/7/23 10:03
 */
@Repository
public class UserOperationReportMongDao extends BaseMongoDao<UserOperationReport> {
    @Override
    protected Class<UserOperationReport> getEntityClass() {
        return UserOperationReport.class;
    }
}
