/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.ChinapnrExclusiveLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingqing
 * @version ChinapnrExclusiveLogDao, v0.1 2019/1/10 15:46
 */
@Repository
public class ChinapnrExclusiveLogDao extends BaseMongoDao<ChinapnrExclusiveLog> {


    protected final Logger logger = LoggerFactory.getLogger(AccountWebListDao.class);

    @Override
    protected Class<ChinapnrExclusiveLog> getEntityClass() {
        return ChinapnrExclusiveLog.class;
    }

    public ChinapnrExclusiveLog queryById(String id){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria = criteria.and("id").is(id);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query,getEntityClass());
    }

    public void updateByPrimaryKeySelective(String uuid, String status) {
        ChinapnrExclusiveLog exclusiveLog = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(uuid)),getEntityClass());
        exclusiveLog.setStatus(status);
        mongoTemplate.save(exclusiveLog);
    }

    /**
     * 更新状态
     * @param record
     * @return
     */
    public void updateByExampleSelective(ChinapnrExclusiveLogWithBLOBsVO record) {
        ChinapnrExclusiveLog exclusiveLog = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(record.getId()).and("updatetime").is(record.getUpdatetime()).and("updateuser").is("callback2")),getEntityClass());
        exclusiveLog.setUpdatetime(String.valueOf(GetDate.getMyTimeInMillis()));
        exclusiveLog.setUpdateuser("callback2");
         mongoTemplate.save(exclusiveLog);
    }

}
