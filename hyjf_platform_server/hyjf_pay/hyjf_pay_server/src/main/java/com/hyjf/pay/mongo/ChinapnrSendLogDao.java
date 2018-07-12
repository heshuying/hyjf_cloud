/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.ChinapnrSendlog;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingqing
 * @version ChinapnrSendlogDao, v0.1 2018/7/12 13:57
 */
@Repository
public class ChinapnrSendLogDao  extends BaseMongoDao<ChinapnrSendlog> {

    private static final String SENDLOG = "ht_chinapnr_send_log";

    @Override
    protected Class<ChinapnrSendlog> getEntityClass() {
        return ChinapnrSendlog.class;
    }

    @Override
    protected String getTableName() {
        return SENDLOG;
    }
}
