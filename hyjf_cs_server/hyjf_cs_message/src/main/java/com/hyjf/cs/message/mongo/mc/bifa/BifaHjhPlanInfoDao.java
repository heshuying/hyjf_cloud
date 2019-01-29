/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.bifa.BifaHjhPlanInfoEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 合规数据上报 BIFA 智投信息上报
 * jijun
 */
@Repository
public class BifaHjhPlanInfoDao extends BaseMongoDao<BifaHjhPlanInfoEntity> {
    @Override
    protected Class<BifaHjhPlanInfoEntity> getEntityClass() {
        return BifaHjhPlanInfoEntity.class;
    }
}
