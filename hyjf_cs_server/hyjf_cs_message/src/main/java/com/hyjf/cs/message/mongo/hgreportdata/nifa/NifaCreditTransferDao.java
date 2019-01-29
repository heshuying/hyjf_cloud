/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.hgreportdata.nifa;

import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaCreditTransferEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * @author PC-LIUSHOUYI
 * @version NifaCreditTenderDao, v0.1 2018/11/30 14:45
 */
@Repository
public class NifaCreditTransferDao extends BaseMongoDao<NifaCreditTransferEntity> {
    @Override
    protected Class<NifaCreditTransferEntity> getEntityClass() {
        return NifaCreditTransferEntity.class;
    }
}
