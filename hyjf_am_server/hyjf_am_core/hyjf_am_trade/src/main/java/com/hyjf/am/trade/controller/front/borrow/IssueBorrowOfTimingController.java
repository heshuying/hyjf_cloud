package com.hyjf.am.trade.controller.front.borrow;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.BorrowCustomize;
import com.hyjf.am.trade.service.task.IssueBorrowOfTimingService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiasq
 * @version IssueBorrowOfTimingController, v0.1 2018/7/10 11:23
 * 定时发标
 */
@RestController
@RequestMapping("/am-trade/timing_borrow/issue")
public class IssueBorrowOfTimingController extends BaseController {

    @Autowired
    IssueBorrowOfTimingService issueBorrowOfTimingService;

    /**
     * 散标定时发标 - 不拆分标的
     *
     * @return
     */
    @RequestMapping("/none_split_borrow")
    public String issueBorrowOfTiming() {
        try {
            // 获取定时发标列表
            List<BorrowCustomize> borrowList = this.issueBorrowOfTimingService.queryOntimeTenderList();
            if (CollectionUtils.isNotEmpty(borrowList)) {
                // 如果不为空,循环更新标的状态
                for (BorrowCustomize borrowCustomize : borrowList) {
                    issueBorrowOfTimingService.issueBorrowOfTiming(borrowCustomize);
                }
            }
        } catch (Exception e) {
            logger.error("不拆分标的定时发标失败....", e);
            return "fail";
        }

        return "success";
    }


    /**
     * 汇计划自动发标任务
     *
     * @return
     */
    @RequestMapping("/split_borrow")
    public String issueSplitBorrowOfTiming() {
        try {
            issueBorrowOfTimingService.issueSplitBorrowOfTiming();
        } catch (Exception e) {
            logger.error("汇计划自动发标任务发标失败....", e);
            return "fail";
        }

        return "success";
    }


    /**
     * 汇计划定时发标
     *
     * @return
     */
    @RequestMapping("/hjh_borrow")
    public String issueHjhPlanBorrowOfTiming() {
        try {
            issueBorrowOfTimingService.issueHjhPlanBorrowOfTiming();
        } catch (Exception e) {
            logger.error("汇计划定时发标失败....", e);
            return "fail";
        }
        return "success";
    }
}
