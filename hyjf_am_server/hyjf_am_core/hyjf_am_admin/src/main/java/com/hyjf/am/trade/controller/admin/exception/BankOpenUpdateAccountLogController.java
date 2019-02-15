package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankOpenAccountLogResponse;
import com.hyjf.am.response.user.OpenAccountEnquiryResponse;
import com.hyjf.am.resquest.admin.OpenAccountEnquiryDefineRequest;
import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.trade.service.admin.exception.BankOpenUpdateAccountLogService;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountLog;
import com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize;
import com.hyjf.am.user.service.admin.exception.BankOpenAccountLogService;
import com.hyjf.am.vo.admin.BankOpenAccountLogVO;
import com.hyjf.am.vo.admin.OpenAccountEnquiryCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version OpenAccountEnquiryController, v0.1 2018/8/21 14:11
 * @Author: Zha Daojian
 */
@RestController
@RequestMapping("/am-trade/bankOpenUpdateAccountLog")
@Api(value = "异常中心-开户掉单-更新资金操作",tags ="异常中心-开户掉单-更新资金操作")
public class BankOpenUpdateAccountLogController extends BaseController {

    @Autowired
    private BankOpenUpdateAccountLogService bankOpenUpdateAccountLogService;

    @ApiOperation(value = "开户掉单，同步保存开户(account)数据", notes = "开户掉单，同步保存开户数据")
    @PostMapping(value = "/updateAccount")
    public OpenAccountEnquiryResponse updateAccount(@RequestBody OpenAccountEnquiryDefineRequest request){
        OpenAccountEnquiryResponse response = bankOpenUpdateAccountLogService.updateAccount(request);
        return response;
    }
}
