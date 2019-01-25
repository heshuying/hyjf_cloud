/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.BifaBorrowStatusEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 合规数据上报 BIFA 产品状态更新上报
 * jijun
 */
@Repository
public class BifaBorrowStatusDao extends BaseMongoDao<BifaBorrowStatusEntity> {
    @Override
    protected Class<BifaBorrowStatusEntity> getEntityClass() {
        return BifaBorrowStatusEntity.class;
    }
}
