/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.StringResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.task.CalculateInvestInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author yaoyong
 * @version CalculateInvestInterestController, v0.1 2018/9/20 9:53
 * 平台数据统计——统计出借收益
 */
@RestController
@RequestMapping("/am-trade/batch")
public class CalculateInvestInterestController extends BaseController {

    @Autowired
    private CalculateInvestInterestService calculateInvestInterestService;

    @RequestMapping("/calculate_invest_interest")
    public StringResponse calculate() {
        logger.info("平台数据统计定时任务......");
        try {
            //融资期限分布
            Map<String, Object> mapPeriod = calculateInvestInterestService.selectPeriodInfo();
            calculateInvestInterestService.insertDataInfo(mapPeriod);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            String strdate = sdf.format(date);
            if (strdate.equals("01")) {
                // 插入上月出借记录
                calculateInvestInterestService.insertAYearTenderInfo();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("插入出借记录失败");
            return new StringResponse("fail");
        }
        return new StringResponse("success");
    }
}
