/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.BifaOperationDataEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 北互金运营数据
 * @author jun
 * @version BifaOperationDataDao, v0.1 2018/11/30 12:48
 */
@Repository
public class BifaOperationDataDao extends BaseMongoDao<BifaOperationDataEntity> {
    @Override
    protected Class<BifaOperationDataEntity> getEntityClass() {
        return BifaOperationDataEntity.class;
    }
}
