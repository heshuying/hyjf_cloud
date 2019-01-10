/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.ChinapnrLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqingqing
 * @version ChinapnrLogDao, v0.1 2019/1/10 17:27
 */
@Repository
public class ChinapnrLogDao  extends BaseMongoDao<ChinapnrLog> {

    /** 状态 999:审核中 */
    public static final String RESPCODE_CHECK = "999";

    /** 状态 000:成功 */
    public static final String RESPCODE_SUCCESS = "000";

    @Override
    protected Class<ChinapnrLog> getEntityClass() {
        return ChinapnrLog.class;
    }

    public List<ChinapnrLog> selectByExampleWithBLOBs(String ordId) {
        List<String> respCode = new ArrayList<String>();
        respCode.add(RESPCODE_SUCCESS);
        respCode.add(RESPCODE_CHECK);
        Query query = new Query();
        Criteria criteria = Criteria.where("ordid").is(ordId).and("msgType").is("Cash").and("respCode").in(respCode);
        query.with(new Sort(Sort.Direction.DESC, "respCode"));
        query.addCriteria(criteria);
        List<ChinapnrLog> exclusiveLog = mongoTemplate.find(query,getEntityClass());
        return exclusiveLog;
    }
}
