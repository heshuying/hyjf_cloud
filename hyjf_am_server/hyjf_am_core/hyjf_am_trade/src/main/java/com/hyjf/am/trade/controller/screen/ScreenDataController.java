package com.hyjf.am.trade.controller.screen;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.service.screen.ScreenDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author lisheng
 * @version ScreenDataController, v0.1 2019/3/18 14:22
 */
@RestController
@RequestMapping(value = "/am-trade/screen_data")
public class ScreenDataController {
    @Autowired
    ScreenDataService service;
    @PostMapping(value = "/insert_data")
    private IntegerResponse insertScreenData(ScreenDataBean screenDataBean) {
        IntegerResponse response = new IntegerResponse();
        Integer result = service.addUserOperateList(screenDataBean);
        response.setResultInt(result);
        return response;
    }
    @PostMapping(value = "/find_free_money/{userId}")
    private BigDecimalResponse insertScreenData(@PathVariable  Integer userId) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal result = service.findUserFreeMoney(userId);
        response.setResultDec(result);
        return response;
    }
    @PostMapping(value = "/find_year_money/{userId}/{orderId}/{productType}/{investMoney}")
    private BigDecimalResponse findYearMoney(@PathVariable  Integer userId,@PathVariable  String orderId,@PathVariable  Integer productType,@PathVariable BigDecimal investMoney) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal result = service.findYearMoney(userId,orderId,productType,investMoney);
        response.setResultDec(result);
        return response;
    }

}
