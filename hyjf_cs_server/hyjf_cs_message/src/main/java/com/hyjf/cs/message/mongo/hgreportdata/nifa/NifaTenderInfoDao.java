/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.hgreportdata.nifa;

import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaTenderInfoEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * @author PC-LIUSHOUYI
 * @version NifaTenderInfoDao, v0.1 2018/11/27 19:50
 */
@Repository
public class NifaTenderInfoDao extends BaseMongoDao<NifaTenderInfoEntity> {
    @Override
    protected Class<NifaTenderInfoEntity> getEntityClass() {
        return NifaTenderInfoEntity.class;
    }
}
