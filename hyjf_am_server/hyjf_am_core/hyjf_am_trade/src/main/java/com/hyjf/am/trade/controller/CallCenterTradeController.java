/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.callcenter.CallCenterHtjRepaymentResponse;
import com.hyjf.am.response.callcenter.CallCenterHztRepaymentResponse;
import com.hyjf.am.resquest.callcenter.CallCenterRepaymentRequest;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterHtjRepaymentDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterHztRepaymentDetailCustomize;
import com.hyjf.am.trade.service.CallCenterTradeService;
import com.hyjf.am.vo.callcenter.CallCenterHtjRepaymentDetailVO;
import com.hyjf.am.vo.callcenter.CallCenterHztRepaymentDetailVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version CallCenterTradeController, v0.1 2018/6/11 17:52
 */
@Api(value = "呼叫中心TRADE原子层")
@RestController
@RequestMapping("/am-trade/callcenter")
public class CallCenterTradeController {
    @Autowired
    CallCenterTradeService callCenterTradeService;
    private static final Logger logger = LoggerFactory.getLogger(CallCenterTradeController.class);

    @ApiOperation(value = "查询还款明细（直投产品，含承接的债权）")
    @RequestMapping(value = "/getHztRepaymentDetailList",method = RequestMethod.POST)
    public CallCenterHztRepaymentResponse getHztRepaymentDetailList(@RequestBody @Valid CallCenterRepaymentRequest request){
        CallCenterHztRepaymentResponse callCenterHztRepaymentResponse = new CallCenterHztRepaymentResponse();
        List<CallCenterHztRepaymentDetailCustomize> list = callCenterTradeService.getHztRepaymentDetailList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterHztRepaymentDetailVO> callCenterHztRepaymentDetailVOS = CommonUtils.convertBeanList(list,CallCenterHztRepaymentDetailVO.class);
            callCenterHztRepaymentResponse.setResultList(callCenterHztRepaymentDetailVOS);
        }
        return callCenterHztRepaymentResponse;
    }

    @ApiOperation(value = "查询还款明细（汇添金）")
    @RequestMapping(value = "/getHtjRepaymentDetailList", method = RequestMethod.POST)
    public CallCenterHtjRepaymentResponse getHtjRepaymentDetailList(@RequestBody @Valid CallCenterRepaymentRequest request){
        CallCenterHtjRepaymentResponse callCenterHtjRepaymentResponse = new CallCenterHtjRepaymentResponse();
        List<CallCenterHtjRepaymentDetailCustomize> list = callCenterTradeService.getHtjRepaymentDetailList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CallCenterHtjRepaymentDetailVO> callCenterHtjRepaymentDetailVOS = CommonUtils.convertBeanList(list,CallCenterHtjRepaymentDetailVO.class);
            callCenterHtjRepaymentResponse.setResultList(callCenterHtjRepaymentDetailVOS);
        }
        return callCenterHtjRepaymentResponse;
    }
}
