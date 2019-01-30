/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.BifaCreditTenderInfoEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 合规数据上报 BIFA 转让信息上报
 * jijun
 */
@Repository
public class BifaCreditTenderInfoDao extends BaseMongoDao<BifaCreditTenderInfoEntity> {
    @Override
    protected Class<BifaCreditTenderInfoEntity> getEntityClass() {
        return BifaCreditTenderInfoEntity.class;
    }
}
