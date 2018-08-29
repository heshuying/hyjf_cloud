package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.AccountWebList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

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

    public Integer countAccountWebList(String nid, String trade) {
        Query query = new Query();
        Criteria criteria =  Criteria.where("id").gt(0);
        if(StringUtils.isNoneBlank(nid)){
            criteria = criteria.and("ordid").is(nid);
        }
        if(StringUtils.isNoneBlank(trade)){
            criteria = criteria.and("trade").is(trade);
        }
        query.addCriteria(criteria);
        Long count = mongoTemplate.count(query,getEntityClass());
        return count.intValue();
    }

    public long queryWebCount(AccountWebListVO accountWebList){
        Query query = new Query();
        Criteria criteria = createCriteria(accountWebList);
        query.addCriteria(criteria);
        return mongoTemplate.count(query,getEntityClass());
    }

    public List<AccountWebList> queryWebList(AccountWebListVO accountWebList,int start,int end){
        Query query = new Query();
        Criteria criteria = createCriteria(accountWebList);
        query.with(new Sort(Sort.Direction.DESC, "createTime","id"));
        query.addCriteria(criteria);
        return mongoTemplate.find(query.skip(start-1).limit(end),getEntityClass());
    }

    public List<AccountWebList> queryAccountWebList(AccountWebListVO accountWebList){
        Query query = new Query();
        Criteria criteria = createCriteria(accountWebList);
        query.with(new Sort(Sort.Direction.DESC, "createTime","id"));
        query.addCriteria(criteria);
        return mongoTemplate.find(query,getEntityClass());
    }

    public Criteria createCriteria(AccountWebListVO accountWebList){
        Criteria criteria = new Criteria();
        if(null!=accountWebList){
            criteria = Criteria.where("id").ne("").ne(null);
            if(StringUtils.isNotBlank(accountWebList.getOrdid())){
                criteria = criteria.and("ordid").is(accountWebList.getOrdid());
            }
            if(StringUtils.isNotBlank(accountWebList.getBorrowNid())){
                criteria = criteria.and("borrowNid").is(accountWebList.getBorrowNid());
            }
            if(StringUtils.isNotBlank( accountWebList.getTruenameSearch())){
                criteria = criteria.and("truename").is(accountWebList.getTruenameSearch());
            }
            if (StringUtils.isNotBlank(accountWebList.getUsernameSearch())){
                criteria = criteria.and("username").is(accountWebList.getUsernameSearch());
            }
            if (StringUtils.isNotBlank(accountWebList.getTradeTypeSearch())){
                criteria = criteria.and("trade").is(accountWebList.getTradeTypeSearch());
            }
            if (StringUtils.isNotBlank(accountWebList.getStartDate())){
                Integer begin = GetDate.dateString2Timestamp(accountWebList.getStartDate());
                criteria = criteria.and("createTime").gte(begin);
            }
            if (StringUtils.isNotBlank(accountWebList.getEndDate())){
                Integer end = GetDate.dateString2Timestamp(accountWebList.getEndDate());
                criteria = criteria.and("createTime").lte(end);
            }
            return criteria;
        }
        return new Criteria();
    }

    public int selectBorrowInvestAccount(AccountWebListVO accountWebList){
        int total = 0;
        Aggregation aggregation = Aggregation.newAggregation(
                match(createCriteria(accountWebList)),
                Aggregation.group("id").sum("amount").as("account")
        );
        AggregationResults<Integer> ar = mongoTemplate.aggregate(aggregation,getEntityClass(), Integer.class);
        List<Integer> list = ar.getMappedResults();
        if(list.size() > 0){
            total += list.get(0);
        }
        return total;
    }
}
