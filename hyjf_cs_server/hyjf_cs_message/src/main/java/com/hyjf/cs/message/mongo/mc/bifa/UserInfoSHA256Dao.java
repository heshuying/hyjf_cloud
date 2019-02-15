/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.BifaUserInfoSHA256Entity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * @author jun
 * @version UserInfoSHA256Dao, v0.1 2019/1/16 15:00
 */
@Repository
public class UserInfoSHA256Dao extends BaseMongoDao<BifaUserInfoSHA256Entity> {
    @Override
    protected Class<BifaUserInfoSHA256Entity> getEntityClass() {
        return BifaUserInfoSHA256Entity.class;
    }
}
