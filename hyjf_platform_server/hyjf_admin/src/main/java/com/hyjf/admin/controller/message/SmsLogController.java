/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hyjf.admin.interceptor.AuthorityAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.SmsLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.SmsLogResponse;
import com.hyjf.am.response.admin.SmsOntimeResponse;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.SmsLogVO;
import com.hyjf.am.vo.admin.SmsOntimeVO;
import com.hyjf.common.util.AsteriskProcessUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version SmsLogController, v0.1 2018/6/23 15:09
 */
@Api(tags = "消息中心-短信")
@RestController
@RequestMapping("/hyjf-admin/message/smsLog")
public class SmsLogController extends BaseController {

    public static final String PERMISSIONS = "smsLog";
    @Autowired
    private SmsLogService smsLogService;

    @ApiOperation(value = "查询消息中心短信发送记录", notes = "查询消息中心短信发送记录")
    @GetMapping("/smsLogList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<SmsLogVO>> smsLogList(HttpServletRequest request) {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) request.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }

        SmsLogResponse response = smsLogService.smsLogList();
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<SmsLogVO> list = response.getResultList();
        if (!CollectionUtils.isEmpty(list)) {
            if(!isShow){
                //如果没有查看脱敏权限,显示加星
                for(SmsLogVO vo:list){
                    vo.setMobile(AsteriskProcessUtil.getAsteriskedValue(vo.getMobile()));
                }
            }
        }
        return new AdminResult<>(ListResult.build(list, response.getLogCount()));
    }

    @ApiOperation(value = "根据条件查询消息中心短信发送记录", notes = "根据条件查询消息中心短信发送记录")
    @PostMapping("/findSmsLog")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<SmsLogVO>> findSmsLog(@RequestBody SmsLogRequest request, HttpServletRequest servletRequest) {
        // 获取该角色 权限列表
        List<String> perm = (List<String>) servletRequest.getSession().getAttribute("permission");
        //判断权限
        boolean isShow = false;
        for (String string : perm) {
            if (string.equals(PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW)) {
                isShow=true;
            }
        }
        SmsLogResponse response = smsLogService.findSmsLog(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<SmsLogVO> list = response.getResultList();
        if (!CollectionUtils.isEmpty(list)) {
            if(!isShow){
                //如果没有查看脱敏权限,显示加星
                for(SmsLogVO vo:list){
                    vo.setMobile(AsteriskProcessUtil.getAsteriskedValue(vo.getMobile()));
                }
            }
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getLogCount()));
    }

    @ApiOperation(value = "查询定时发送短信列表", notes = "查询定时发送短信列表")
    @PostMapping("/timeinit")
    public AdminResult<ListResult<SmsOntimeVO>> timeinit(@RequestBody SmsLogRequest request) {
        SmsOntimeResponse response = smsLogService.queryTime(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
