/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.report;

import com.hyjf.am.response.trade.OperationReportJobResponse;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.statistics.OperationReportJobService;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tanyy
 * @version OperationReportJobController, v0.1 2018/6/14 16:59
 */
@RestController
@RequestMapping("/am-trade/report/operationreportjob")
public class OperationReportJobController extends BaseController {

    @Autowired
   private OperationReportJobService operationReportJobService;
    /**
     *
     * @Description  按照省份统计出借人的分布
     * @Date 18:33 2018/7/18
     * @Param 上个月的最后一天
     * @return
     */
    @PostMapping("/tendercitygroupbylist")
    public OperationReportJobResponse tenderCityGroupByList(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> resultList = operationReportJobService.getTenderCityGroupBy(request);
        response.setResultList(resultList);
        return response;
    }

    /**
     *
     * @Description  按照性别统计出借人的分布
     *
     * @Param 上个月的最后一天
     * @return
     */
    @PostMapping("/tendersexgroupbylist")
    public OperationReportJobResponse tenderSexGroupByList(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> resultList =operationReportJobService.getTenderSexGroupByList(request);
        response.setResultList(resultList);
        return response;
    }

    /**
     *
     * @Description  出借人按照年龄分布
     *
     * @Param 上个月的最后一天
     * @return
     */
    @PostMapping("/tenderagebyrange")
    public OperationReportJobResponse tenderAgeByRange(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        int count = operationReportJobService.getTenderAgeByRange(request.getDate(),request.getFirstAge(),request.getEndAge());
        response.setCount(count);
        return response;
    }


    /**
     * 月交易金额
     *
     * @param request 统计月的第一天 统计月的最后一天
     *
     *
     */
    @PostMapping("/accountbymonth")
    public OperationReportJobResponse accountByMonth(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        BigDecimal totalAccount = operationReportJobService.getAccountByMonth(request.getBeginDate(),request.getEndDate());
        logger.info("月交易金额：" + totalAccount + ",开始时间：" + request.getBeginDate() + ", 结束时间：" + request.getEndDate() );
        response.setTotalAccount(totalAccount);
        return response;
    }

    /**
     * 月交易笔数
     *
     * @param request 统计月的第一天 统计月的最后一天
     *
     *
     */
    @PostMapping("/tradecountbymonth")
    public OperationReportJobResponse tradeCountByMonth(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        int count  = operationReportJobService.getTradeCountByMonth(request.getBeginDate(),request.getEndDate());
        response.setCount(count);
        return response;
    }

    /**
     * 借贷笔数
     *
     *
     *
     *
     */
    @PostMapping("/loannum")
    public OperationReportJobResponse loanNum(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        int count  = operationReportJobService.getLoanNum(request.getDate());
        response.setCount(count);
        return response;
    }

    /**
     * 人均出借金额
     *
     *
     */
    @PostMapping("/investlastdate")
    public OperationReportJobResponse investLastDate(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        double account  = operationReportJobService.getInvestLastDate(request.getDate());
        response.setAccount(account);
        return response;
    }

    /**
     * 统计出借人总数
     *
     *
     */
    @PostMapping("/tendercount")
    public OperationReportJobResponse tenderCount(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        int count  = operationReportJobService.getTenderCount(request.getDate());
        response.setCount(count);
        return response;
    }

    /**
     * 平均满标时间
     *
     *
     */
    @PostMapping("/fullbillaveragetime")
    public OperationReportJobResponse fullBillAverageTime(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        float count  = operationReportJobService.getFullBillAverageTime(request.getDate());
        response.setFullBillAverage(count);
        return response;
    }



    /**
     * 代偿金额
     *
     *
     */
    @PostMapping("/repaytotal")
    public OperationReportJobResponse repayTotal(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        BigDecimal count  = operationReportJobService.getRepayTotal(request.getDate());
        response.setTotalAccount(count);
        return response;
    }

    /**
     * 代偿金额
     *
     *
     */
    @RequestMapping("/performancesum")
    public OperationReportJobResponse performanceSum() {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getPerformanceSum();
        response.setResultList(list);
        return response;
    }

    /**
     * 当月、季、半年、全年业绩  下面的  成交金额,根据月份计算
     *
     * @return
     */

    @PostMapping("/monthdealmoney")
    public OperationReportJobResponse monthDealMoney(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getMonthDealMoney(request.getStartMonth(),request.getEndMonth());
        response.setResultList(list);
        return response;
    }

    /**
     * 今年这个时候到手收益 和 去年这个时候到手收益 和  出借利率
     *
     * @return
     */

    @PostMapping("/revenueandyield")
    public OperationReportJobResponse revenueAndYield(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getRevenueAndYield(request.getIntervalMonth(),request.getStartMonth(),request.getEndMonth());
        response.setResultList(list);
        return response;
    }

    /**
     * 充值金额、充值笔数
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/rechargemoneyandsum")
    public OperationReportJobResponse rechargeMoneyAndSum(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getRechargeMoneyAndSum(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }

    /**
     * 充值金额、充值笔数
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/completecount")
    public OperationReportJobResponse completeCount(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getCompleteCount(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }

    /**
     * 借款期限
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/borrowperiod")
    public OperationReportJobResponse borrowPeriod(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getBorrowPeriod(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }

    /**
     * 用户分析 - 性别分布
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/sexdistribute")
    public OperationReportJobResponse sexDistribute(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getSexDistribute(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }
    /**
     * 用户分析 - 年龄分布
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/agedistribute")
    public OperationReportJobResponse ageDistribute(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getAgeDistribute(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }

    /**
     * 用户分析 - 金额分布
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/moneydistribute")
    public OperationReportJobResponse moneyDistribute(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getMoneyDistribute(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }
    /**
     * 十大出借人
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/tenmostmoney")
    public OperationReportJobResponse tenMostMoney(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getTenMostMoney(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }
    /**
     * 超活跃，出借笔数最多
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/oneinvestmost")
    public OperationReportJobResponse oneInvestMost(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getOneInvestMost(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }

    /**
     * 大赢家，收益最高
     *
     * @param request 今年间隔月份
     */
    @PostMapping("/oneinterestsmost")
    public OperationReportJobResponse oneInterestsMost(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list  = operationReportJobService.getOneInterestsMost(request.getIntervalMonth());
        response.setResultList(list);
        return response;
    }
    @RequestMapping("/tenderagebyrangelist")
    public OperationReportJobResponse tenderAgeByRangeList(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list = operationReportJobService.getTenderAgeByRangeList(request.getDate(),request.getFirstAge(),request.getEndAge());
        response.setResultList(list);
        return response;
    }
}
