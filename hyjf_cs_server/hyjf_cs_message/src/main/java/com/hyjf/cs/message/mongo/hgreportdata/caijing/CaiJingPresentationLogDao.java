/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.hgreportdata.caijing;

import com.hyjf.am.resquest.admin.CaiJingLogRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.hgreportdata.caijing.CaiJingPresentationLog;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    public List<CaiJingPresentationLog> queryCaiJingLog(CaiJingLogRequest request) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getPresentationTimeStart()) && StringUtils.isNotBlank(request.getPresentationTimeEnd())) {
            Date startDate = GetDate.stringToDate2(request.getPresentationTimeStart());
            Date endDate = GetDate.stringToDate2(request.getPresentationTimeEnd());
            criteria.and("presentationTime").gte(GetDate.getSearchStartTime(startDate))
                    .lte(GetDate.getSearchEndTime(endDate));
        }
        if (StringUtils.isNotBlank(request.getLogType())) {
            criteria.and("logType").is(request.getLogType());
        }
        if (request.getStatus() != null) {
            criteria.and("status").is(request.getStatus());
        }
        query.with(new Sort(Sort.Direction.DESC, "presentationTime"));
        query.addCriteria(criteria);
        if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
            int currPage = request.getCurrPage();
            int pageSize = request.getPageSize();
            int limitStart = (currPage - 1) * pageSize;
            query.skip(limitStart).limit(pageSize);
        }
        return mongoTemplate.find(query, getEntityClass());
    }
}
