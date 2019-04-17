package com.hyjf.cs.message.service.report.impl;

import com.hyjf.common.util.GetDateUtils;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.ic.report.OperationGroupReport;
import com.hyjf.cs.message.bean.ic.report.OperationReport;
import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.mongo.ic.TotalInvestAndInterestMongoDao;
import com.hyjf.cs.message.mongo.ic.report.OperationMongDao;
import com.hyjf.cs.message.mongo.ic.report.OperationMongoGroupDao;
import com.hyjf.cs.message.service.report.PlatDataStatisticsService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平台数据统计Service实现类
 *
 * @author tanyy
 */
@Service
public class PlatDataStatisticsServiceImpl extends BaseServiceImpl implements PlatDataStatisticsService {

    @Autowired
    private TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;
    @Autowired
    private OperationMongoGroupDao operationMongoGroupDao;
    @Autowired
    private OperationMongDao operationMongDao;


    @Override
    public BigDecimal selectTotalInvest() {
        TotalInvestAndInterestEntity entity = getTotalInvestAndInterestEntity();
        if (entity != null) {
            return entity.getTotalInvestAmount();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal selectTotalInterest() {
        TotalInvestAndInterestEntity entity = getTotalInvestAndInterestEntity();
        if (entity != null) {
            return entity.getTotalInterestAmount();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public Integer selectTotalTradeSum() {
        TotalInvestAndInterestEntity entity = getTotalInvestAndInterestEntity();
        if (entity != null) {
            return entity.getTotalInvestNum();
        }
        return 0;
    }

    private TotalInvestAndInterestEntity getTotalInvestAndInterestEntity() {
        return totalInvestAndInterestMongoDao.findOne(new Query());
    }
    @Override
    public OperationReport findOneOperationReportEntity(){
        Query query = new Query();
        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "statisticsMonth"));
        return operationMongDao.findOne(query);
    }
    @Override
    public OperationGroupReport findOneOperationMongoGroupEntity(){
        Query query = new Query();
        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "statisticsMonth"));
        return operationMongoGroupDao.findOne(query);
    }

    @Override
    public List<OperationReport> findOperationReportEntityList(){
        Document dbObject = new Document();
        Document fieldsObject = new Document();
        // 指定返回的字段
        fieldsObject.put("statisticsMonth", true);
        fieldsObject.put("accountMonth", true);
        fieldsObject.put("tradeCountMonth", true);
        Query query = new BasicQuery(dbObject, fieldsObject);
        query.limit(12);
        query.with(new Sort(Sort.Direction.DESC, "statisticsMonth"));
        return operationMongDao.find(query);
    }
    @Autowired
    AmConfigClient amTradeClient;
    @Override
    public String selectMinEventTime() {
        String eventTime=amTradeClient.selectMinEventTime();
        return eventTime;
    }

}
