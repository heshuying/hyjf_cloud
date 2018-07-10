/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.subcommission;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.SubCommissionService;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: sunpeikai
 * @version: SubCommissionController, v0.1 2018/7/10 9:28
 */
@Api(value = "资金中心-平台账户分佣")
@RestController
@RequestMapping(value = "/hyjf-admin/subcommission")
public class SubCommissionController {

    @Autowired
    private SubCommissionService subCommissionService;

    /**
     * 平台账户分佣
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "平台账户分佣",notes = "平台账户分佣列表查询")
    @PostMapping(value = "/subcommissionlist")
    public JSONObject subCommissionList(){
        return null;
    }

    /**
     * 查询发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "发起账户分佣",notes = "发起账户分佣所需的detail信息")
    @PostMapping(value = "/searchdetails")
    public JSONObject searchDetails(@RequestHeader(value = "userId")Integer userId){
        return subCommissionService.searchDetails(userId);
    }

    /**
     * 发起账户分佣
     * @auth sunpeikai
     * @param request 插入数据参数
     * @return
     */
    @ApiOperation(value = "发起账户分佣",notes = "发起账户分佣")
    @PostMapping(value = "/subcommission")
    public JSONObject subCommission(@RequestHeader(value = "userId")Integer loginUserId,@RequestBody SubCommissionRequest request){
        return null;
        //return subCommissionService.searchDetails(userId);
    }
}
