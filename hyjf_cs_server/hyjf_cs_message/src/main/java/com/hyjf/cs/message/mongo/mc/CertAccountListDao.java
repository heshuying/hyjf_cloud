/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.CertAccountList;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 国家互联网应急中心    CERT  交易明细
 * @author sss
 * @version BaseHgDataReportEntity, v0.1 2018/6/27 10:06
 */
@Repository
public class CertAccountListDao extends BaseMongoDao<CertAccountList> {
    @Override
    protected Class<CertAccountList> getEntityClass() {
        return CertAccountList.class;
    }

    /**
     * 查询未上报的交易明细
     * @return
     */
    public List<CertAccountList> getNotSendAccountList() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        // 查询未上报的是0
        criteria.and("isSend").is(0);
        query.addCriteria(criteria);
        query.limit(50);
        query.with(new Sort(Sort.Direction.ASC, "sentTime"));
        return mongoTemplate.find(query,getEntityClass());
    }

    /**
     * 上报完毕 修改状态为成功
     * @param accountList
     */
    public void updateAccountSuccess(CertAccountList accountList) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("logOrdId").is(accountList.getLogOrdId());
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("isSend",1);
        super.update(query,update);
    }
}
