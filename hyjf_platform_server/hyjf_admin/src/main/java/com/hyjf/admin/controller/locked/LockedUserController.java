/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.locked;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.locked.LockedUserService;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cui
 * @version LockedUserController, v0.1 2018/9/25 11:26
 */
@Api(tags = "配置中心-登录失败锁定配置")
@RestController
@RequestMapping("/hyjf-admin/lockeduser/")
public class LockedUserController extends BaseController {

    @Autowired
    private LockedUserService lockedUserService;

    @ApiOperation(value = "前台锁定用户列表",notes = "前台账户锁定用户列表")
    @PostMapping(value = "/frontlist")
    @ResponseBody
    public LockedUserMgrResponse frontList(@RequestBody LockedeUserListRequest request){

        return lockedUserService.getLockedUserList(request,true);

    }

    @ApiOperation(value = "后台锁定用户列表",notes = "后台锁定用户列表")
    @PostMapping(value = "/adminlist")
    @ResponseBody
    public LockedUserMgrResponse adminList(@RequestBody LockedeUserListRequest request){

        return lockedUserService.getLockedUserList(request,false);

    }

    @ApiOperation(value = "前台解锁",notes = "前台解锁")
    @PostMapping(value = "/frontunlock")
    @ResponseBody
    public Response<?> frontUnlock(@RequestBody String lockedUserId, HttpServletRequest request){

        LockedUserInfoVO vo=buildVO(lockedUserId, request);

        return lockedUserService.unlock(vo,false);

    }

    @ApiOperation(value = "后台解锁",notes = "后台解锁")
    @PostMapping(value = "/adminunlock")
    @ResponseBody
    public Response<?> adminUnlock(@RequestBody String lockedUserId,HttpServletRequest request){

        LockedUserInfoVO vo=buildVO(lockedUserId, request);

        return lockedUserService.unlock(vo,false);

    }

    private LockedUserInfoVO buildVO(String lockedUserId, HttpServletRequest request) {
//        AdminSystemVO user=getUser(request);
//
//        String operatorId=user.getId();
//
//        LockedUserInfoVO vo=new LockedUserInfoVO();
//
//        vo.setId(Integer.valueOf(lockedUserId));
//        vo.setOperator(Integer.valueOf(operatorId));

        LockedUserInfoVO vo=new LockedUserInfoVO();
        vo.setId(Integer.valueOf(lockedUserId));
        vo.setOperator(250);

        return vo;
    }
}

