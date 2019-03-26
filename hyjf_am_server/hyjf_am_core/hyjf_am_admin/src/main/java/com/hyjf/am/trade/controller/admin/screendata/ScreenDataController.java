package com.hyjf.am.trade.controller.admin.screendata;

import com.hyjf.am.trade.service.BorrowRepayScreenDataService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lisheng
 * @version ScreenDataController, v0.1 2019/3/25 17:59
 */
@RestController
@RequestMapping(value = "/am-trade/screen_data")
public class ScreenDataController extends BaseController {

    @Autowired
    BorrowRepayScreenDataService screenDataService;

    @PostMapping(value = "/add_repay_userList")
    private IntegerResponse addRepayUserList(@RequestBody ScreenDataBean screenDataBean) {
        IntegerResponse response = new IntegerResponse();
        Integer result = screenDataService.addRepayUserList(screenDataBean.getRepaymentPlanVOS());
        response.setResultInt(result);
        return response;
    }

    @GetMapping(value = "/count_repay_userList")
    private IntegerResponse countRepayUserList() {
        IntegerResponse response = new IntegerResponse();
        Integer result = screenDataService.countRepayUserList(GetDate.getFirstDayOfMonthDate(),GetDate.getLastDayOfMonthDate());
        response.setResultInt(result);
        return response;
    }

}
