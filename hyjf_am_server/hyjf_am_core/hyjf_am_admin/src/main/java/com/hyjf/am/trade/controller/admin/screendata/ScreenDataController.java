package com.hyjf.am.trade.controller.admin.screendata;

import com.hyjf.am.response.trade.ScreenDataResponse;
import com.hyjf.am.trade.service.BorrowRepayScreenDataService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/getrechargelist/{startIndex}/{endIndex}")
    private ScreenDataResponse getRechargeList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getRechargeList(startIndex,endIndex);
        response.setResultList(list);
        return response;
    }
    @GetMapping(value = "/getplantenderlist/{startIndex}/{endIndex}")
    private ScreenDataResponse getPlanTenderList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getPlanTenderList(startIndex,endIndex);
        response.setResultList(list);
        return response;
    }
    @GetMapping(value = "/getplanrepaylist/{startIndex}/{endIndex}")
    private ScreenDataResponse getPlanRepayList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getPlanRepayList(startIndex,endIndex);
        response.setResultList(list);
        return response;
    }
    @GetMapping(value = "/getcredittenderlist/{startIndex}/{endIndex}")
    private ScreenDataResponse getCreditTenderList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getCreditTenderList(startIndex,endIndex);
        response.setResultList(list);
        return response;
    }
    @GetMapping(value = "/getwithdrawlist/{startIndex}/{endIndex}")
    private ScreenDataResponse getWithdrawList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getWithdrawList(startIndex,endIndex);
        response.setResultList(list);
        return response;
    }

}
