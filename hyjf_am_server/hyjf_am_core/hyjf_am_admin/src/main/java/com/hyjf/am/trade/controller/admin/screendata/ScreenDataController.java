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
        logger.info("用户画像投屏数据充值数据修复数量：{}",list ==null?"0":list.size() );
        response.setResultList(list);
        return response;
    }
    @GetMapping(value = "/getplantenderlist/{startIndex}/{endIndex}")
    private ScreenDataResponse getPlanTenderList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getPlanTenderList(startIndex,endIndex);
        logger.info("用户画像投屏数据计划加入数据修复数量：{}",list ==null?"0":list.size() );
        response.setResultList(list);
        return response;
    }
    @GetMapping(value = "/getplanrepaylist/{startIndex}/{endIndex}")
    private ScreenDataResponse getPlanRepayList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getPlanRepayList(startIndex,endIndex);
        logger.info("用户画像投屏数据计划退出数据修复数量：{}",list ==null?"0":list.size() );
        response.setResultList(list);
        return response;
    }
    @GetMapping(value = "/getcredittenderlist/{startIndex}/{endIndex}")
    private ScreenDataResponse getCreditTenderList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getCreditTenderList(startIndex,endIndex);
        logger.info("用户画像投屏数据债转承接数据修复数量：{}",list ==null?"0":list.size() );
        response.setResultList(list);
        return response;
    }
    @GetMapping(value = "/getwithdrawlist/{startIndex}/{endIndex}")
    private ScreenDataResponse getWithdrawList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getWithdrawList(startIndex,endIndex);
        logger.info("用户画像投屏数据提现数据修复数量：{}",list ==null?"0":list.size() );
        response.setResultList(list);
        return response;
    }

    @GetMapping(value = "/getborrowrecoverlist/{startIndex}/{endIndex}")
    private ScreenDataResponse getBorrowRecoverList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getBorrowRecoverList(startIndex,endIndex);
        logger.info("用户画像投屏数据散标回款数据修复数量：{}",list ==null?"0":list.size() );
        response.setResultList(list);
        return response;
    }

    @GetMapping(value = "/getborrowtenderlist/{startIndex}/{endIndex}")
    private ScreenDataResponse getBorrowTenderList(@PathVariable Integer startIndex, @PathVariable Integer endIndex ) {
        ScreenDataResponse response = new ScreenDataResponse();
        List<ScreenDataBean> list = screenDataService.getBorrowTenderList(startIndex,endIndex);
        logger.info("用户画像投屏数据散标投资数据修复数量：{}",list ==null?"0":list.size() );
        response.setResultList(list);
        return response;
    }

}
