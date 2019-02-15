/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.BifaIndexUserInfoEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 合规数据上报 BIFA 借贷用户索引数据上报
 * jijun
 */
@Repository
public class BifaBorrowUserInfoIndexDao extends BaseMongoDao<BifaIndexUserInfoEntity> {
    @Override
    protected Class<BifaIndexUserInfoEntity> getEntityClass() {
        return BifaIndexUserInfoEntity.class;
    }
}
