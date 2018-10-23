/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.message.AppAccesStatisticsResponse;
import com.hyjf.am.response.message.BorrowUserStatisticResponse;
import com.hyjf.am.response.message.OperationReportEntityResponse;
import com.hyjf.am.response.trade.CalculateInvestInterestResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.am.vo.datacollect.BorrowUserStatisticVO;
import com.hyjf.am.vo.datacollect.OperationReportEntityVO;
import com.hyjf.cs.market.client.CsMessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version AmDataCollectImpl, v0.1 2018/7/18 13:56
 */
@Service
public class CsMessageClientImpl implements CsMessageClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowUserStatisticVO selectBorrowUserStatistic() {
        BorrowUserStatisticResponse response = restTemplate.getForObject(
                "http://CS-MESSAGE/cs-message/operation_report_job/getBorrowUserStatistic",
                BorrowUserStatisticResponse.class);
        return response.getResult();
    }

    @Override
    public String getTotalInvestmentAmount() {
        CalculateInvestInterestResponse response = restTemplate.getForObject(
                "http://CS-MESSAGE/cs-message/search/gettotalinvestmentamount",
                CalculateInvestInterestResponse.class);
        if (response != null) {
            BigDecimal interestSum = response.getInterestSum();
            if (interestSum != null) {
                return String.valueOf(interestSum.divide(new BigDecimal("100000000")));
            }
        }
        return null;
    }

        /**
         * 累计投资总额
         * @return
         */
        @Override
        public BigDecimal selectTenderSum() {
            BigDecimalResponse tenderSum = restTemplate.getForObject(
                    "http://CS-MESSAGE/cs-message/totalinvestandinterest/selectTenderSum",
                    BigDecimalResponse.class);
            return tenderSum.getResultDec();
        }
        /**
         * 累计收益
         * @return
         */
        @Override
        public BigDecimal selectInterestSum() {
            BigDecimalResponse interestSum = restTemplate.getForObject(
                    "http://CS-MESSAGE/cs-message/totalinvestandinterest/selectInterestSum",
                    BigDecimalResponse.class);
            return interestSum.getResultDec();
        }
        /**
         * 累计投资笔数
         * @return
         */
        @Override
        public int selectTotalTenderSum() {
            IntegerResponse totalTenderSum = restTemplate.getForObject(
                    "http://CS-MESSAGE/cs-message/totalinvestandinterest/selectTotalTenderSum",
                    IntegerResponse.class);
            return totalTenderSum.getResultInt();
        }

    @Override
    public OperationReportEntityVO getOperationReport(int month) {
        OperationReportEntityResponse response = restTemplate.getForObject(
                "http://CS-MESSAGE/cs-message/operation_report_job/findOneOperationMongDaoByMonth/"+month,
                OperationReportEntityResponse.class);
        return response.getResult();
    }

    /**
     * 根据开始时间、结束时间和来源查询数据
     * @return
     */
    @Override
    public List<AppAccesStatisticsVO> getAppAccesStatisticsVO(AppChannelStatisticsRequest request) {
        AppAccesStatisticsResponse response = restTemplate.postForObject(
                "http://CS-MESSAGE/cs-message/app_channel_statistics/getAccessNumber",request,
                AppAccesStatisticsResponse.class);

        return response.getResultList();
    }

}
