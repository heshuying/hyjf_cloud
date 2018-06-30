/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.credit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description  债转
 * @Author sss
 * @Date 2018/6/29 13:59
 */
@Api(value = "Web端加入计划")
@RestController
@RequestMapping("/web/credit")
public class CreditController {
    /**
     * 首页 > 账户中心 > 资产管理 > 我要转让列表
     * @param
     * @return
     */
    @ApiOperation(value = "可转让列表", notes = "首页 > 账户中心 > 资产管理 > 可转让列表")
    @PostMapping(value = "/creditList", produces = "application/json; charset=utf-8")
    public Object getCredittList(){
        // @RequestBody @Valid CreditListRequest request
        /*WebResult result =  webProjectListService.searchCreditList(request);*/
        return null;
    }
}
