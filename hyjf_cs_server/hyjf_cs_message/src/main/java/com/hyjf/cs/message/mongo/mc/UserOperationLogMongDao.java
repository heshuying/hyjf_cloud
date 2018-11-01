/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.UserOperationLogEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Service;

/**
 *
 * @author tanyy
 * @version UserOperationLogMongDao, v0.1 2018/7/23 10:03
 */
@Service
public class UserOperationLogMongDao extends BaseMongoDao<UserOperationLogEntity> {

    @Override
    protected Class<UserOperationLogEntity> getEntityClass() {
        return UserOperationLogEntity.class;
    }
}
