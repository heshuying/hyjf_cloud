/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.hgreportdata.caijing;

import com.hyjf.cs.message.bean.hgreportdata.caijing.CaiJingPresentationLog;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * @author yaoyong
 * @version CaiJingPresentationLogDao, v0.1 2019/6/6 17:28
 */
@Repository
public class CaiJingPresentationLogDao extends BaseMongoDao<CaiJingPresentationLog> {

    @Override
    protected Class<CaiJingPresentationLog> getEntityClass() {
        return CaiJingPresentationLog.class;
    }
}
