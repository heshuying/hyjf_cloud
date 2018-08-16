package com.hyjf.cs.message.service.report.impl;

import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.ic.*;
import com.hyjf.cs.message.mongo.ic.TotalInvestAndInterestMongoDao;
import com.hyjf.cs.message.mongo.mc.OperationMongDao;
import com.hyjf.cs.message.mongo.mc.OperationMongoGroupDao;
import com.hyjf.cs.message.service.report.PlatDataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 获取累计投资累计收益
     *
     * @return
     */
    @Override
    public CalculateInvestInterest selectCalculateInvestInterest() {
        return  null;
    }
    

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
    public OperationReportEntity findOneOperationReportEntity(Query query){
        return operationMongDao.findOne(query);
    }
    @Override
    public OperationMongoGroupEntity findOneOperationMongoGroupEntity(Query query){
        return operationMongoGroupDao.findOne(query);
    }

    @Override
    public List<OperationReportEntity> findOperationReportEntityList(Query query){
        return operationMongDao.find(query);
    }

}
