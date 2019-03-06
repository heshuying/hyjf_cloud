/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.repay;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AutoReqRepayBorrowCustomize;
import com.hyjf.am.trade.service.front.batch.AutoReqRepayService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liubin
 * @version AutoReqRepayController, v0.1 2018/7/13 19:43
 */
@Api(value = "自动请求还款任务")
@RestController
@RequestMapping("/am-trade/autoReqRepayController")
public class AutoReqRepayController extends BaseController {
    @Autowired
    private AutoReqRepayService autoReqRepayService;

    @RequestMapping("/autoReqRepay")
    public boolean autoReqRepay() {
        try {
            // 取得本日应还款标的列表
            List<AutoReqRepayBorrowCustomize> autoReqRepayBorrowList = this.autoReqRepayService.getAutoReqRepayBorrow();
            if (autoReqRepayBorrowList != null && autoReqRepayBorrowList.size() > 0) {
                logger.info("需要自动请求还款的标的数量：" + autoReqRepayBorrowList.size());
                // 循环请求还款
                for (AutoReqRepayBorrowCustomize autoReqRepayBorrow : autoReqRepayBorrowList) {
                    logger.info("标的" + autoReqRepayBorrow.getBorrowNid() + "自动请求还款开始。");
                    logger.info("[borrowNid=" + autoReqRepayBorrow.getBorrowNid() +
                            ", userId=" + autoReqRepayBorrow.getUserId() +
                            ", username=" + autoReqRepayBorrow.getUsername() +
                            ", repayOrgUserId=" + autoReqRepayBorrow.getRepayOrgUserId() +
                            ", repayOrgUsername=" + autoReqRepayBorrow.getRepayOrgUsername() +
                            ", repayerType=" + autoReqRepayBorrow.getRepayerType() + "]");
                    // 标的请求还款
                    if (this.autoReqRepayService.repayUserBorrowProject(autoReqRepayBorrow)) {
                        logger.info("标的" + autoReqRepayBorrow.getBorrowNid() + "自动请求还款成功。");
                    } else {
                        logger.info("标的" + autoReqRepayBorrow.getBorrowNid() + "自动请求还款失败。");
                    }
                }
            } else {
                logger.info("没有需要自动请求还款的标的。。。");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("自动请求还款失败。");
            return false;
        }
        return true;
    }
}
