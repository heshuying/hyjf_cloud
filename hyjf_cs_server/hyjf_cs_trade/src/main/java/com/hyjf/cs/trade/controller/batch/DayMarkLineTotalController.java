package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.CouponRepayStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 日、月推标额度定时任务
 * @Author: yinhui
 * @Date: 2018/12/18 15:25
 * @Version 1.0
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/dayMarkLineTotal")
public class DayMarkLineTotalController {

    private static final Logger logger = LoggerFactory.getLogger(DayMarkLineTotalController.class);

    @Autowired
    private CouponRepayStatisticService couponRepayStatisticService;

    @RequestMapping("/updateDayMarkLine")
    public String updateDayMarkLine() {
        logger.info("日、月推标额度定时任务...");
        couponRepayStatisticService.updateDayMarkLine();
        return "success";
    }
}
