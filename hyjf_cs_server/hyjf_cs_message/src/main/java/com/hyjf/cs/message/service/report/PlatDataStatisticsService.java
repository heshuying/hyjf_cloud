package com.hyjf.cs.message.service.report;

import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.message.bean.ic.report.OperationGroupReport;
import com.hyjf.cs.message.bean.ic.report.OperationReport;
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
     * 查询累计出借
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
    OperationReport findOneOperationReportEntity();
    /**
     * mogo查询单个OperationMongoGroupEntity
     * @return
     */
    OperationGroupReport findOneOperationMongoGroupEntity();
    /**
     * mogo查询多个OperationReportEntity  获取12个月的数据
     * @return
     */
    List<OperationReport> findOperationReportEntityList();

    String selectMinEventTime();
}
