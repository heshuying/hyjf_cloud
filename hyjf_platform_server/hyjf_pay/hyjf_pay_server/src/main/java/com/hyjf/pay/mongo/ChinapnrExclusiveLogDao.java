/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingqing
 * @version ChinapnrExclusiveLogDao, v0.1 2018/7/12 13:49
 */
@Repository
public class ChinapnrExclusiveLogDao extends BaseMongoDao<ChinapnrExclusiveLog>{

    private static final String EXCLUSENDLOG = "ht_chinapnr_exclusive_log";


    @Override
    protected Class<ChinapnrExclusiveLog> getEntityClass() {
        return ChinapnrExclusiveLog.class;
    }

    @Override
    protected String getTableName() {
        return EXCLUSENDLOG;
    }
}
