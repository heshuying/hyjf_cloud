/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.maintenance;


import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.maintenance.AdminPermissionsService;
import com.hyjf.am.resquest.admin.AdminPermissionsRequest;
import com.hyjf.am.vo.admin.AdminPermissionsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AdminPermissionsController, v0.1 2018/9/5 13:54
 */
@Api(value = "系统中心-功能权限维护",tags = "系统中心-功能权限维护")
@RestController
@RequestMapping("/hyjf-admin/maintenance/permissions")
public class AdminPermissionsController extends BaseController {
    @Autowired
    private AdminPermissionsService adminPermissionsService;

    @ApiOperation(value = "查询权限",notes = "查询权限")
    @PostMapping(value = "/searchpermissions")
    public AdminResult<ListResult<AdminPermissionsVO>> searchPermissions(@RequestBody AdminPermissionsRequest permissionsRequest){
        Integer count = adminPermissionsService.getPermissionsCount(permissionsRequest);
        count = (count == null)?0:count;
        List<AdminPermissionsVO> adminPermissionsVOList = adminPermissionsService.searchPermissionsList(permissionsRequest);
        return new AdminResult<>(ListResult.build(adminPermissionsVOList,count));
    }

    @ApiOperation(value = "添加权限",notes = "添加权限")
    @PostMapping(value = "/addpermission")
    public AdminResult addPermission(@RequestBody AdminPermissionsVO adminPermissionsVO){
        boolean isSuccess = adminPermissionsService.insertPermission(adminPermissionsVO);
        if(isSuccess){
            return new AdminResult(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        }else{
            return new AdminResult(BaseResult.FAIL,BaseResult.FAIL_DESC);
        }

    }

    @ApiOperation(value = "修改权限",notes = "修改权限")
    @PostMapping(value = "/updatepermission")
    public AdminResult updatePermission(@RequestBody AdminPermissionsVO adminPermissionsVO){
        boolean isSuccess = adminPermissionsService.updatePermission(adminPermissionsVO);
        if(isSuccess){
            return new AdminResult(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        }else{
            return new AdminResult(BaseResult.FAIL,BaseResult.FAIL_DESC);
        }
    }

    @ApiOperation(value = "删除权限",notes = "删除权限")
    @PostMapping(value = "/deletepermission")
    public AdminResult deletePermission(@RequestBody AdminPermissionsVO adminPermissionsVO){
        boolean isSuccess = adminPermissionsService.deletePermission(adminPermissionsVO);
        if(isSuccess){
            return new AdminResult(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        }else{
            return new AdminResult(BaseResult.FAIL,BaseResult.FAIL_DESC);
        }
    }
}
