package com.hyjf.am.trade.controller.front.borrow;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.service.task.bank.autoreview.BatchAutoReviewService;
import com.hyjf.am.trade.utils.constant.BorrowSendTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/13 09:25
 * @Description: BatchAutoReviewController
 */
@RestController
@RequestMapping("/batch/borrowautoreview")
public class BatchAutoReviewController {
    private static final Logger logger = LoggerFactory.getLogger(BatchAutoReviewController.class);

    @Autowired
    private BatchAutoReviewService batchAutoReviewService;

    /**
     * @Author walter.limeng
     * @Description   自动复审任务
     * @Date 9:27 2018/7/13
     */
    @RequestMapping("/autoreview")
    public void autoReview() {
        logger.info("------自动复审任务开始定时任务开始------");
        try {
            try {
                // 给到期未满标项目发短信
                batchAutoReviewService.sendMsgToNotFullBorrow();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            // 获取过期时间
            Integer afterTime = batchAutoReviewService.getAfterTime(BorrowSendTypeEnum.FUSHENSEND_CD);
            List<Borrow> borrowList = this.batchAutoReviewService.selectAutoReview();
            if (borrowList != null && borrowList.size() > 0) {
                for (Borrow borrow : borrowList) {
                    try {
                        batchAutoReviewService.updateBorrow(borrow, afterTime);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(BatchAutoReviewController.class.toString(), "autoReview", e);
        }
        logger.info("autoReview自动复审任务结束。");
    }
}
