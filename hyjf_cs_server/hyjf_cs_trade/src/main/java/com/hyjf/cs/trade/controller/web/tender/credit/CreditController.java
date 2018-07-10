/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.credit;

import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyCreditListRequest;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.CreditDetailsRequestBean;
import com.hyjf.cs.trade.bean.TenderBorrowCreditCustomize;
import com.hyjf.cs.trade.service.MyCreditListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description  债转
 * @Author sss
 * @Date 2018/6/29 13:59
 */
@Api(value = "Web端债转投资")
@RestController
@RequestMapping("/web/credit")
public class CreditController {

    @Autowired
    private MyCreditListService creditListService;

    /**
     * 首页 > 账户中心 > 资产管理 > 我要转让列表
     * @param
     * @return
     */
    @ApiOperation(value = "我要债转列表页 获取参数", notes = "首页 > 账户中心 > 资产管理 > 可转让列表")
    @PostMapping(value = "/creditListInit", produces = "application/json; charset=utf-8")
    public WebResult getCreditList(@RequestBody MyCreditListRequest request,
            @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.getCreditListData(request,userId);
        return result;
    }

    /**
     * 首页 > 账户中心 > 资产管理 > 我要转让列表
     * @param
     * @return
     */
    @ApiOperation(value = "我要债转列表页分页查询", notes = "首页 > 账户中心 > 资产管理 > 可转让列表")
    @PostMapping(value = "/creditListData", produces = "application/json; charset=utf-8")
    public WebResult creditListData(@RequestBody MyCreditListQueryRequest request,
                                    @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.getCreditList(request,userId);
        return result;
    }

    @ApiOperation(value = "用户中心查询投资可债转详情", notes = "点击列表的转让按钮")
    @PostMapping(value = "/tenderToCreditDetail", produces = "application/json; charset=utf-8")
    public WebResult tenderToCreditDetail(@RequestBody CreditDetailsRequestBean request,
                                    @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.tenderToCreditDetail(request,userId);
        return result;
    }

    @ApiOperation(value = "用户中心验证投资人当天是否可以债转 每天三次", notes = "用户中心验证投资人当天是否可以债转")
    @PostMapping(value = "/tender_able_to_credit", produces = "application/json; charset=utf-8")
    public WebResult tenderAbleToCredit(@RequestBody CreditDetailsRequestBean request,
                                          @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.tenderAbleToCredit(request,userId);
        return result;
    }

    @ApiOperation(value = "用户中心检查是否可以债转", notes = "用户中心检查是否可以债转")
    @PostMapping(value = "/check_can_credit", produces = "application/json; charset=utf-8")
    public WebResult checkCanCredit(@RequestBody CreditDetailsRequestBean request,
                                        @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result =  creditListService.checkCanCredit(request,userId);
        return result;
    }

    @ApiOperation(value = "用户中心债转提交保存", notes = "用户中心债转提交保存")
    @PostMapping(value = "/save_tender_to_credit", produces = "application/json; charset=utf-8")
    public WebResult saveTenderToCredit(@RequestBody TenderBorrowCreditCustomize request,
                                    @RequestHeader(value = "userId",required = false) Integer userId){
        request.setPlatform(Integer.parseInt(CommonConstant.CLIENT_PC));
        WebResult result =  creditListService.saveTenderToCredit(request,userId);
        return result;
    }

    @ApiOperation(value = " 用户中心查询 债转详细预计服务费计算", notes = " 用户中心查询 债转详细预计服务费计算")
    @PostMapping(value = "/except_credit_fee", produces = "application/json; charset=utf-8")
    public WebResult getExpectCreditFee(@RequestBody TenderBorrowCreditCustomize request,
                                        @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result = creditListService.getExpectCreditFee(request,userId);
        return result;
    }

    @ApiOperation(value = "发送短信验证码（ajax请求） 短信验证码数据保存", notes = "发送短信验证码（ajax请求） 短信验证码数据保存")
    @PostMapping(value = "/send_code", produces = "application/json; charset=utf-8")
    public WebResult sendCode(@RequestBody TenderBorrowCreditCustomize request,
                                        @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result = creditListService.sendCreditCode(request,userId);
        return result;
    }

    @ApiOperation(value = "短信验证码校验", notes = "短信验证码校验")
    @PostMapping(value = "/check_code", produces = "application/json; charset=utf-8")
    public WebResult checkCode(@RequestBody TenderBorrowCreditCustomize request,
                              @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result = creditListService.checkCode(request,userId);
        return result;
    }


}
