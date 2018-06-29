package com.hyjf.am.statistics.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.am.statistics.bean.AccountWebList;

/**
 * @author xiasq
 * @version AccountWebListConsumerDao, v0.1 2018/6/19 16:47
 */
@Repository
public class AccountWebListDao extends BaseMongoDao<AccountWebList> {

    @Override
    protected Class<AccountWebList> getEntityClass() {
        return AccountWebList.class;
    }
}
