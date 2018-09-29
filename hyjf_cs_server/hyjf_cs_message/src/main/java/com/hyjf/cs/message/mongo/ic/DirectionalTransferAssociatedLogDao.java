/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.DirectionalTransferAssociatedLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: DirectionalTransferAssociatedLogDao, v0.1 2018/8/29 19:32
 */
@Repository
public class DirectionalTransferAssociatedLogDao extends BaseMongoDao<DirectionalTransferAssociatedLog> {
    @Override
    protected Class<DirectionalTransferAssociatedLog> getEntityClass() {
        return DirectionalTransferAssociatedLog.class;
    }

    public long getDirectionalTransferLogCount(BindLogListRequest request){
        Query query = new Query();
        Criteria criteria = createCriteria(request);
        query.addCriteria(criteria);
        if(request.getLimitStart()!=-1){
            query.skip(request.getLimitStart()-1).limit(request.getLimitEnd());
        }
        return mongoTemplate.count(query,getEntityClass());
    }
    public List<DirectionalTransferAssociatedLog> searchDirectionalTransferLogList(BindLogListRequest request){
        Query query = new Query();
        Criteria criteria = createCriteria(request);
        query.addCriteria(criteria);
        if(request.getLimitStart()!=-1){
            query.skip(request.getLimitStart()-1).limit(request.getLimitEnd());
        }
        return mongoTemplate.find(query,getEntityClass());
    }
    private Criteria createCriteria(BindLogListRequest request){
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Criteria criteria;
        if(null!=request){
            criteria = Criteria.where("id").ne("").ne(null);
            // 转出账户
            if(StringUtils.isNoneBlank(request.getTurnOutUsername())){
                criteria = criteria.and("turnOutUsername").regex(request.getTurnOutUsername());
            }
            // 转出账户手机
            if (StringUtils.isNoneBlank(request.getTurnOutMobile())) {
                criteria.and("turnOutMobile").regex(request.getTurnOutMobile());
            }
            // 关联状态
            if (StringUtils.isNoneBlank(request.getStatusSearch())) {
                criteria.and("associatedState").is(request.getStatusSearch());
            }
            // 转入账户
            if(StringUtils.isNoneBlank(request.getShiftToUsername())){
                criteria = criteria.and("shiftToUsername").regex(request.getShiftToUsername());
            }
            // 转入账户手机
            if (StringUtils.isNoneBlank(request.getShiftToMobile())) {
                criteria.and("shiftToMobile").regex(request.getShiftToMobile());
            }
            // 关联时间开始和结束
            if (StringUtils.isNoneBlank(request.getStartDate()) && StringUtils.isNoneBlank(request.getEndDate())) {
                criteria = criteria.and("associatedTime").gte(GetDate.stringToDate(request.getStartDate() + " 00:00:00")).lte(GetDate.stringToDate(request.getEndDate() + " 23:59:59"));
            }else if(StringUtils.isNoneBlank(request.getStartDate())){
                criteria = criteria.and("associatedTime").gte(GetDate.stringToDate(request.getStartDate() + " 00:00:00"));
            }else if(StringUtils.isNoneBlank(request.getEndDate())){
                criteria = criteria.and("associatedTime").lte(GetDate.stringToDate(request.getEndDate() + " 23:59:59"));
            }

            return criteria;
        }
        return new Criteria();
    }
}
