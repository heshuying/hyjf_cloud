/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.hgreportdata.nifa;

import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaBorrowInfoEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * @author PC-LIUSHOUYI
 * @version NifaBorrowInfoDao, v0.1 2018/11/27 19:49
 */
@Repository
public class NifaBorrowInfoDao extends BaseMongoDao<NifaBorrowInfoEntity> {
    @Override
    protected Class<NifaBorrowInfoEntity> getEntityClass() {
        return NifaBorrowInfoEntity.class;
    }
}
