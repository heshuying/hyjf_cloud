/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.credit;

import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyCreditListRequest;
import com.hyjf.cs.common.bean.result.WebResult;
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
@Api(value = "Web端加入计划")
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
}
