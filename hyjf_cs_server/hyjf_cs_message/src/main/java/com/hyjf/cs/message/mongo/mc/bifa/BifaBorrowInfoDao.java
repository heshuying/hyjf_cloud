/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.BifaBorrowInfoEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * @author jun
 * @version BifaBorrowInfoDao, v0.1 2019/1/16 10:08
 */
@Repository
public class BifaBorrowInfoDao extends BaseMongoDao<BifaBorrowInfoEntity> {
    @Override
    protected Class<BifaBorrowInfoEntity> getEntityClass() {
        return BifaBorrowInfoEntity.class;
    }
}
