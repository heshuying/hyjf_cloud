/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.assetcenter;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nxl
 * @version AccountdetailController, v0.1 2018/6/29 13:38
 * 后台管理系统，资金中心->资金明细
 */
@Api(value = "资金明细")
@RestController
@RequestMapping("/hyjf-admin/accountDetail")
public class AccountDetailController {
    @ApiOperation(value = "资金明细", notes = "资金明细页面初始化")
    @PostMapping(value = "/accountDetailInit")
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }
}
