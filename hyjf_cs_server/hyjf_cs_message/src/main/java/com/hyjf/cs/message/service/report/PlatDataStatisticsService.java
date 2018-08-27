package com.hyjf.cs.message.service.report;

import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.message.bean.ic.BorrowUserStatistic;
import com.hyjf.cs.message.bean.ic.CalculateInvestInterest;
import com.hyjf.cs.message.bean.ic.OperationMongoGroupEntity;
import com.hyjf.cs.message.bean.ic.OperationReportEntity;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平台数据统计Service
 *
 * @author tanyy
 */
@Service
public interface PlatDataStatisticsService extends BaseService {

    /**
     * 获取累计收益累计投资
     * @return
     */
    CalculateInvestInterest selectCalculateInvestInterest();


    /**
     * 查询累计投资
     * @return
     */
    BigDecimal selectTotalInvest();

    /**
     * 查询累计收益
     * @return
     */
    BigDecimal selectTotalInterest();

    /**
     * 获取累计交易笔数
     * @return
     */
    Integer selectTotalTradeSum();
    /**
     * mogo查询单个OperationReportEntity
     * @return
     */
    OperationReportEntity findOneOperationReportEntity();
    /**
     * mogo查询单个OperationMongoGroupEntity
     * @return
     */
    OperationMongoGroupEntity findOneOperationMongoGroupEntity();
    /**
     * mogo查询多个OperationReportEntity
     * @return
     */
    List<OperationReportEntity> findOperationReportEntityList();

}
