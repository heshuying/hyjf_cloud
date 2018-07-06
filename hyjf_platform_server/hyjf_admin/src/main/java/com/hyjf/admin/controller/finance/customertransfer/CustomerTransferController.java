/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.customertransfer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: sunpeikai
 * @version: CustomerTransferController, v0.1 2018/7/5 18:00
 */
@Api(value = "资金中心-转账管理-用户转账")
@RestController
@RequestMapping(value = "/hyjf-admin/customertransfer")
public class CustomerTransferController extends BaseController {

    @Autowired
    private CustomerTransferService customerTransferService;

    /**
     * 根据userName查询账户余额
     * @auth sunpeikai
     * @param outUserName 用户名
     * @return
     */
    @ApiOperation(value = "查询余额",notes = "根据用户账号查询余额")
    @PostMapping(value = "/searchbalance")
    public JSONObject searchBalanceByUsername(@RequestBody String outUserName){
        JSONObject result = new JSONObject();
        if(StringUtils.isNotEmpty(outUserName)){
            result = customerTransferService.searchBalanceByUsername(outUserName);
        }else{
            result.put("status","error");
            result.put("result","用户账号不能为空");
        }
        return result;
    }

    /**
     * 用户转账
     * @auth sunpeikai
     * @param request 发起转账提交时所用的参数
     * @return
     */
    @ApiOperation(value = "用户转账",notes = "用户转账")
    @PostMapping(value = "/addtransfer")
    public JSONObject addTransfer(@RequestHeader Integer userId,@RequestBody CustomerTransferRequest request){
        JSONObject result = new JSONObject();
        result = customerTransferService.checkCustomerTransferParam(request);
        if(result != null && "00".equals(result.get("status"))){
            AdminSystemVO adminSystemVO = customerTransferService.getAdminSystemByUserId(userId);
            request.setCreateUserId(Integer.valueOf(adminSystemVO.getId()));
            request.setCreateUserName(adminSystemVO.getUsername());
            boolean success = customerTransferService.insertTransfer(request);
            if(success){
                result.put("status","00");
                result.put("result","成功");
            }else{
                result.put("status","error");
                result.put("result","数据插入失败");
            }
        }
        return result;
    }
}