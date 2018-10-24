package com.hyjf.am.trade.service.task.bank.autoreview;

import com.hyjf.am.trade.dao.model.auto.Borrow;

import java.util.List;

/**
 * @Author walter.limeng
 * @Description 汇计划自动复审任务
 * @Date 14:11 2018/7/13
 */
public interface BatchAutoReviewHjhService {
    /**
     * @Author walter.limeng
     * @Description  给到期未满标项目发短信
     * @Date 14:17 2018/7/13
     */
    void sendMsgToNotFullBorrow();

    /**
     * @Author walter.limeng
     * @Description  查询相应的待复审的标的
     * @Date 14:30 2018/7/13
     * @Param
     * @return 
     */
    List<Borrow> selectAutoReview();

    /**
     * @Author walter.limeng
     * @Description 复审
     * @Date 14:34 2018/7/13
     * @Param 
     * @return 
     */
    void updateBorrow(Borrow borrow) throws Exception;
}
