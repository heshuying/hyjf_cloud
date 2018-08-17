/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.UserCorner;
import com.hyjf.am.config.service.UserCornerService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.UserCornerResponse;
import com.hyjf.am.vo.config.UserCornerVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: sunpeikai
 * @version: UserCornerController, v0.1 2018/7/26 11:34
 */
@Api(tags ="用户角标")
@RestController
@RequestMapping("/am-config/userCorner")
public class UserCornerController {

    @Autowired
    private UserCornerService userCornerService;

    /**
     * 根据设备唯一标识获取角标数据
     * @auth sunpeikai
     * @param sign 设备唯一标识
     * @return
     */
    @ApiOperation(value = "根据设备唯一标识获取角标数据",notes = "根据设备唯一标识获取角标数据")
    @GetMapping(value = "/getUserCornerBySign/{sign}")
    public UserCornerResponse getUserCornerBySign(@PathVariable @Valid String sign){
        UserCornerResponse response = new UserCornerResponse();
        UserCorner userCorner = userCornerService.getUserCornerBySign(sign);
        if(userCorner != null){
            UserCornerVO userCornerVO = CommonUtils.convertBean(userCorner,UserCornerVO.class);
            response.setResult(userCornerVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 更新用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    @ApiOperation(value = "更新用户角标数据",notes = "更新用户角标数据")
    @PostMapping(value = "/updateUserCorner")
    public UserCornerResponse updateUserCorner(@RequestBody UserCornerVO userCornerVO){
        UserCornerResponse response = new UserCornerResponse();
        Integer successCount = userCornerService.updateUserCorner(userCornerVO);
        response.setSuccessCount(successCount);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 插入一条新的用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    @ApiOperation(value = "插入用户角标数据",notes = "插入用户角标数据")
    @PostMapping(value = "/insertUserCorner")
    public UserCornerResponse insertUserCorner(@RequestBody UserCornerVO userCornerVO){
        UserCornerResponse response = new UserCornerResponse();
        Integer successCount = userCornerService.insertUserCorner(userCornerVO);
        response.setSuccessCount(successCount);
        response.setRtn(Response.SUCCESS);
        return response;
    }
}
