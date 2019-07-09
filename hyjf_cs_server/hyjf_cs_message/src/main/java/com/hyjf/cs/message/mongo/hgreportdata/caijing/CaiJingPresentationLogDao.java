/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.hgreportdata.caijing;

import com.hyjf.am.resquest.admin.CaiJingLogRequest;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.hgreportdata.caijing.CaiJingPresentationLog;
import com.hyjf.cs.message.bean.hgreportdata.caijing.CaiJingRepotDataLog;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
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
        Document obj = new Document();
//        DBObject obj = new BasicDBObject();
        DBObject object = new BasicDBObject();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getPresentationTimeStart()) && StringUtils.isNotBlank(request.getPresentationTimeEnd())) {
            Date startDate = GetDate.stringToDate2(request.getPresentationTimeStart());
            Date endDate = GetDate.stringToDate2(request.getPresentationTimeEnd());

            object.put("$gte", GetDate.getSearchStartTime(startDate));
            obj.put("presentationTime",object);

            object.put("$lte", GetDate.getSearchEndTime(endDate));
            obj.put("presentationTime",object);

        }
        if (StringUtils.isNotBlank(request.getLogType())) {
            obj.put("logType",request.getLogType());
        }
        if (request.getStatus() != null) {
            obj.put("status",request.getStatus());
        }

        //排除字段不展示
        Document fieldsObject = new Document();
        fieldsObject.put("json", false);

        Query query = new BasicQuery(obj,fieldsObject);

        query.with(new Sort(Sort.Direction.DESC, "presentationTime"));
        query.addCriteria(criteria);
        if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
            int currPage = request.getCurrPage();
            int pageSize = request.getPageSize();
            int limitStart = (currPage - 1) * pageSize;
            query.skip(limitStart).limit(pageSize);
        }


        List<CaiJingRepotDataLog> list = mongoTemplate.find(query, CaiJingRepotDataLog.class);
        List<CaiJingPresentationLog> logVOS = CommonUtils.convertBeanList(list, CaiJingPresentationLog.class);
        return logVOS;
    }
}
