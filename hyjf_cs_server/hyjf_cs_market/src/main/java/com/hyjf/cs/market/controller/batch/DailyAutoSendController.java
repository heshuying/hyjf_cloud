/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.batch;

import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.DailyAutoSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

/**
 * @author yaoyong
 * @version DailyAutoSendController, v0.1 2018/11/15 17:00
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-market/daily_send")
public class DailyAutoSendController extends BaseMarketController {

    @Autowired
    private DailyAutoSendService dailyAutoSendService;

    @RequestMapping("/send")
    public void send() {
        List<SellDailyDistributionVO> list = dailyAutoSendService.listSellDailyDistribution();
        if (CollectionUtils.isEmpty(list)) {
            logger.info("SellDailyDistribution 没有可用的配置...");
            return;
        }

        for (SellDailyDistributionVO sellDailyDistribution : list) {
            switch (sellDailyDistribution.getTimePoint()) {
                case 1:
                    logger.info("工作日...");
                    // 每个工作日
                    // 判断当天是工作日
                    if (dailyAutoSendService.isWorkdateOnSomeDay(new Date())) {
                        dailyAutoSendService.sendMail(sellDailyDistribution);
                    }
                    break;
                case 2:
                    // 每天
                    logger.info("每日...");
                    dailyAutoSendService.sendMail(sellDailyDistribution);
                    break;
                case 3:
                    logger.info("每月第一个工作日...");
                    // 每月第一个工作日
                    if (dailyAutoSendService.isTodayFirstWorkdayOnMonth()) {
                        dailyAutoSendService.sendMail(sellDailyDistribution);
                    }
                    break;
                default:
                    throw new RuntimeException("错误的配置..");
            }
        }
    }
}
