/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.config.AppReapyCalendarResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.user.RepayCalendarService;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version RepayCalendarController, v0.1 2018/7/27 14:49
 */
@RestController
@RequestMapping("/am-trade/user")
public class RepayCalendarController extends BaseController {

    @Autowired
    RepayCalendarService repayCalendarService;

    /**
     * 查询回款日历总数
     * @param params
     * @return
     */
    @PostMapping(value = "/repayCalendar/countBorrowRepayment")
    public AppReapyCalendarResponse countRepaymentCalendar(@RequestBody Map<String, Object> params){
        logger.info("请求参数:" + JSONObject.toJSON(params));
        AppReapyCalendarResponse response = new AppReapyCalendarResponse();
        Integer count = this.repayCalendarService.countRepaymentCalendar(params);
        response.setCount(count);
        return response;
    }

    /**
     * 查询回款日历明细
     * @param params
     * @return
     */
    @PostMapping(value = "/repayCalendar/searchRepaymentCalendar")
    public AppReapyCalendarResponse searchRepaymentCalendar(@RequestBody Map<String, Object> params){
        logger.info("请求参数:" + JSONObject.toJSON(params));
        AppReapyCalendarResponse response = new AppReapyCalendarResponse();
        List<AppReapyCalendarResultVO> appReapyCalendarList = this.repayCalendarService.searchRepaymentCalendar(params);

        response.setResultList(appReapyCalendarList);
        return response;
    }

    /**
     * 返回用户最近回款时间戳-秒
     * @param params
     * @return
     */
    @PostMapping(value = "/repayCalendar/searchNearlyRepaymentTime")
    public AppReapyCalendarResponse searchNearlyRepaymentTime(@RequestBody Map<String, Object> params){
        logger.info("请求参数:" + JSONObject.toJSON(params));
        AppReapyCalendarResponse response = new AppReapyCalendarResponse();
        Integer count = this.repayCalendarService.searchNearlyRepaymentTime(params);
        response.setCount(count);
        return response;
    }


}
