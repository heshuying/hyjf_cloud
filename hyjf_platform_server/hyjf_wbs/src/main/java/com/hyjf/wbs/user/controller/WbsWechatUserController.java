/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.controller;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.hyjf.common.util.GetCilentIP;
import com.hyjf.wbs.WbsConstants;
import com.hyjf.wbs.qvo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.wbs.user.service.WbsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;

/**
 * @author cui
 * @version WbsWechatUserController, v0.1 2019/4/22 10:31
 */
@RestController
@Api(value = "Wechat财富端", tags = "财富端绑定、授权")
@RequestMapping(value = "/hyjf-wbs/wechat/user")
public class WbsWechatUserController {

    private Logger logger=LoggerFactory.getLogger(getClass());

    @Autowired
    private WbsUserService wbsUserService;

    @ApiOperation(value = "Wechat财富端客户绑定", notes = "Wechat财富端客户绑定")
    @ResponseBody
    @RequestMapping("bind")
    public BaseResult bind(HttpServletRequest request, @RequestBody WechatUserBindQO wechatUserBindQO) {

        BaseResult result=new BaseResult();

        wbsUserService.wechatBind(wechatUserBindQO, result);

        return result;
    }


    @ApiOperation(value = "Wechat快速授权获取用户信息",notes = "Wechat快速授权获取用户信息")
    @GetMapping("/userinfo/{assetCustomerId}")
    public BaseResult queryUserInfo(@PathVariable String assetCustomerId){

        BaseResult result=new BaseResult();

        WbsUserAuthInfo wbsUserAuthInfo=wbsUserService.queryUserAuthInfo(assetCustomerId);

        result.setData(wbsUserAuthInfo);

        return result;
    }

    @ApiOperation(value = "Wechat快速授权提交",notes = "Wechat快速授权提交")
    @ResponseBody
    @RequestMapping(value = "authorize",method = RequestMethod.POST)
    public BaseResult authorize(HttpServletRequest request, @RequestBody WbsRegisterMqVO qo){

        BaseResult result=new BaseResult();

        wbsUserService.authorize(qo);

        WbsUserAuthorizeVO vo=new WbsUserAuthorizeVO();
        // TODO by cui
        vo.setRetUrl("");
        result.setData(vo);

        return result;

    }

    @ApiOperation(value = "Wechat纳觅端重定向接口",notes = "Wechat纳觅端重定向接口")
    @ResponseBody
    @RequestMapping(value = "redirect")
    public BaseResult redirect(HttpServletRequest request, @RequestBody WbsRedirectQO qo){
        BaseResult result=new BaseResult();

        String presetProps = qo.getPresetProps();

        WbsUserBindVO webUserBindVO=wbsUserService.redirect(qo, GetCilentIP.getIpAddr(request), WbsConstants.CHANNEL_WEI,presetProps);

        result.setData(webUserBindVO);

        return result;
    }
}
