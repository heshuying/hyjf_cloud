package com.hyjf.am.trade.controller.admin.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.TenderCommissionResponse;
import com.hyjf.am.resquest.admin.TenderCommissionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.finance.TenderCommissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version TenderCommissionController, v0.1 2018/8/6 16:41
 * @Author: Zha Daojian
 * 资金中心->直投提成管理
 */

@RestController
@RequestMapping("/am-trade/tenderCommission")
public class TenderCommissionController extends BaseController {

    @Autowired
    private TenderCommissionService tenderCommissionService;
    /**
     * 根据筛选条件查找(查找提成管理)
     *
     * @param request
     * @return
     */
    @RequestMapping("/countTenderCommissionByTenderIdAndTenderType")
    public TenderCommissionResponse countTenderCommissionByTenderIdAndTenderType(@RequestBody TenderCommissionRequest request) {
        logger.info("---setCountTenderCommissionByTenderIdAndTenderType by param---  " + JSONObject.toJSON(request));
        TenderCommissionResponse response = new TenderCommissionResponse();
        int count = tenderCommissionService.countTenderCommissionByTenderIdAndTenderType(request);
        response.setCount(count);
        return response;
    }

    /**
     * 插入提成记录
     * @auth zdj
     * @param
     * @return
     */
    @ApiOperation(value = "插入提成记录",notes = "插入提成记录")
    @PostMapping(value = "/insertTenderCommission")
    public Boolean insertTenderCommission(@RequestBody TenderCommissionRequest request){
        return tenderCommissionService.insertTenderCommission(request);
    }

}
