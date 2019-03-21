package com.hyjf.am.trade.controller.screen;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.RepayResponse;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.screen.ScreenDataService;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lisheng
 * @version ScreenDataController, v0.1 2019/3/18 14:22
 */
@RestController
@RequestMapping(value = "/am-trade/screen_data")
public class ScreenDataController extends BaseController {
    @Autowired
    ScreenDataService service;

    @PostMapping(value = "/insert_data")
    private IntegerResponse insertScreenData(@RequestBody ScreenDataBean screenDataBean) {
        IntegerResponse response = new IntegerResponse();
        Integer result = service.addUserOperateList(screenDataBean);
        response.setResultInt(result);
        return response;
    }

    @GetMapping(value = "/find_free_money/{userId}")
    private BigDecimalResponse insertScreenData(@PathVariable  Integer userId) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal result = service.findUserFreeMoney(userId);
        response.setResultDec(result);
        return response;
    }

    @GetMapping(value = "/find_year_money/{userId}/{orderId}/{productType}/{investMoney}")
    private BigDecimalResponse findYearMoney(@PathVariable  Integer userId,@PathVariable  String orderId,@PathVariable  Integer productType,@PathVariable BigDecimal investMoney) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal result = service.findYearMoney(userId,orderId,productType,investMoney);
        response.setResultDec(result);
        return response;
    }

    @PostMapping(value = "/add_repay_userList")
    private IntegerResponse addRepayUserList(@RequestBody ScreenDataBean screenDataBean) {
        IntegerResponse response = new IntegerResponse();
        Integer result = service.addRepayUserList(screenDataBean.getRepaymentPlanVOS());
        response.setResultInt(result);
        return response;
    }



}
