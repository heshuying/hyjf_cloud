package com.hyjf.admin.controller.productcenter.plancenter.planrepay;

import com.hyjf.admin.beans.request.HjhRepayRequestBean;
import com.hyjf.admin.beans.response.HjhRepayResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanRepayService;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 产品中心 --> 汇计划 --> 订单退出
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-订单退出")
@RestController
@RequestMapping(value = "/planrepay")
public class PlanRepayController extends BaseController {

    @Autowired
    private PlanRepayService planRepayService;

    /**
     * 订单退出列表
     * @param repayRequestBean
     * @return
     */
    @ApiOperation(value = "订单退出", notes = "订单退出列表查询")
    @PostMapping(value = "/searchList")
    @ResponseBody
    public AdminResult init(@RequestBody @Valid HjhRepayRequestBean repayRequestBean){

        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        HjhRepayResponseBean repayResponseBean = planRepayService.selectByExample(repayRequest);


        return new AdminResult(repayResponseBean);
    }

    @ApiOperation(value = "还款明细", notes = "订单退出->还款明细")
    @PostMapping(value = "/repaymentDetails")
    public AdminResult repaymentDetails(@RequestBody HjhRepayRequestBean repayRequestBean){

        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        HjhRepayResponseBean repayResponseBean = planRepayService.selectByAccedeOrderId(repayRequestBean.getAccedeOrderIdSrch());

        return new AdminResult(repayResponseBean);
    }

}
