/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.ChinapnrLog;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingqing
 * @version ChinapnrLogDao, v0.1 2018/7/12 14:12
 */
@Repository
public class ChinapnrLogDao extends BaseMongoDao<ChinapnrLog> {


    private static final String CHINAPNRLOG = "ht_chinapnr_log";

    @Override
    protected Class<ChinapnrLog> getEntityClass() {
        return ChinapnrLog.class;
    }

    @Override
    protected String getTableName() {
        return CHINAPNRLOG;
    }
}
