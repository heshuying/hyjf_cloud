package com.hyjf.pay.mongo;

import com.hyjf.pay.bean.BankLog;

/**
 * @author xiasq
 * @version BankLogMongoDao, v0.1 2018/4/19 12:08
 */
public interface BankLogMongoDao {
    int save(BankLog bankLog);

    BankLog getOneBeanById(int userId);
}
