package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.task.BorrowUserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version BorrowUserStatisticsController, v0.1 2018/11/20 14:21
 */
@RestController
@RequestMapping("borrow_user_statistics")
public class BorrowUserStatisticsController extends BaseController {

    @Autowired
    private BorrowUserStatisticsService borrowUserStatisticsService;

    /**
     * 累计借款人
     * @return
     */
    @RequestMapping("/count_borrow_user")
    public IntegerResponse countBorrowUser() {
        logger.info("获取累计借款人数据开始......");
        int result = borrowUserStatisticsService.countBorrowUser();
        logger.info("获取累计借款人数据结束......");
        return new IntegerResponse(result);
    }

    /**
     * 当前借款人
     * @return
     */
    @RequestMapping("/count_current_borrow_user")
    public IntegerResponse countCurrentBorrowUser() {
        logger.info("获取当前借款人数据开始......");
        int result = borrowUserStatisticsService.countCurrentBorrowUser();
        logger.info("获取当前借款人数据结束......");
        return new IntegerResponse(result);
    }

    /**
     * 当前投资人
     * @return
     */
    @RequestMapping("/count_current_tender_user")
    public IntegerResponse countCurrentTenderUser() {
        logger.info("获取当前投资人数据开始......");
        int result = borrowUserStatisticsService.countCurrentTenderUser();
        logger.info("获取当前投资人数据结束......");
        return new IntegerResponse(result);
    }

    /**
     * 代还总金额
     * @return
     */
    @RequestMapping("/sum_borrow_user_money")
    public BigDecimalResponse sumBorrowUserMoney() {
        logger.info("获取代还总金额数据开始......");
        BigDecimal result = borrowUserStatisticsService.sumBorrowUserMoney();
        logger.info("获取代还总金额数据结束......");
        return new BigDecimalResponse(result);
    }

    /**
     * 前十大借款人待还金额
     * @return
     */
    @RequestMapping("/sum_borrow_user_money_top_ten")
    public BigDecimalResponse sumBorrowUserMoneyTopTen() {
        logger.info("获取前十大借款人待还金额开始......");
        BigDecimal result = borrowUserStatisticsService.sumBorrowUserMoneyTopTen();
        logger.info("获取前十大借款人待还金额结束......");
        return new BigDecimalResponse(result);
    }

    /**
     * 最大单一借款人待还金额
     * @return
     */
    @RequestMapping("/sum_borrow_user_money_top_one")
    public BigDecimalResponse sumBorrowUserMoneyTopOne() {
        logger.info("获取最大单一借款人待还金额数据开始......");
        BigDecimal result = borrowUserStatisticsService.sumBorrowUserMoneyTopOne();
        logger.info("获取最大单一借款人待还金额数据结束......");
        return new BigDecimalResponse(result);
    }
}
