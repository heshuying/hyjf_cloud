package com.hyjf.am.trade.controller.front.hjh.autoreview;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.service.task.bank.autoreview.BatchAutoReviewHjhService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/13 14:09
 * @Description: BatchAutoReviewHjhController
 */
@RestController
@RequestMapping("/batch/hjhautoreview")
public class BatchAutoReviewHjhController {
    private static final Logger logger = LoggerFactory.getLogger(BatchAutoReviewHjhController.class);
    @Autowired
    private BatchAutoReviewHjhService batchAutoReviewHjhService;

    /**
     * @Author walter.limeng
     * @Description 汇计划自动复审任务
     * @Date 14:15 2018/7/13
     */
    @RequestMapping("/hjhautoreview")
    public void autoReview() {
        logger.info("=========自动放款复审任务开始。");
        try {
            try {
                // 给到期未满标项目发短信
                batchAutoReviewHjhService.sendMsgToNotFullBorrow();
            } catch (Exception e) {
                logger.info("=======给到期未满标项目发短信错误!" + e.getMessage());
            }
            List<Borrow> borrowList = this.batchAutoReviewHjhService.selectAutoReview();

            if (borrowList != null && borrowList.size() > 0) {
                logger.info("--------------复审标的数量:" + borrowList.size());
                for (Borrow borrow : borrowList) {
                    logger.info("--------------复审标的号:" + borrow.getBorrowNid());
                    try {
                        batchAutoReviewHjhService.updateBorrow(borrow);
                    } catch (Exception e) {
                        logger.info("=============自动复审异常!" + e.getMessage());
                    }
                }
            }else{
                logger.info("--------------复审标的数量:0" );
            }
        } catch (Exception e) {
            logger.info("=============自动复审异常!" + e.getMessage());
        }
        logger.info("=========自动放款复审任务结束。");
    }
}
