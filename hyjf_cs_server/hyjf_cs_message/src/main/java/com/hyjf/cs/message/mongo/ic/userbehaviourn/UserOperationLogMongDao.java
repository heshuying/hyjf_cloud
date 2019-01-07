/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic.userbehaviourn;

import com.hyjf.cs.message.bean.ic.userbehaviourn.UserOperationLog;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tanyy
 * @version UserOperationLogMongDao, v0.1 2018/7/23 10:03
 */
@Repository
public class UserOperationLogMongDao extends BaseMongoDao<UserOperationLog> {

    @Override
    protected Class<UserOperationLog> getEntityClass() {
        return UserOperationLog.class;
    }
}
