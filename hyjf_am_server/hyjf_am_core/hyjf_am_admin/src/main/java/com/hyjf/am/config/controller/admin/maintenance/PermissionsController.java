/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.maintenance;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.AdminPermissions;
import com.hyjf.am.config.service.PermissionsService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminPermissionsResponse;
import com.hyjf.am.resquest.admin.AdminPermissionsRequest;
import com.hyjf.am.vo.admin.AdminPermissionsVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PermissionsController, v0.1 2018/9/5 14:21
 */
@RestController
@RequestMapping(value = "/am-admin/permissions")
public class PermissionsController extends BaseConfigController {

    @Autowired
    private PermissionsService permissionsService;

    /**
     * 查询权限数量
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getPermissionsCount")
    public AdminPermissionsResponse getPermissionsCount(@RequestBody AdminPermissionsRequest request){
        AdminPermissionsResponse response = new AdminPermissionsResponse();
        int count = permissionsService.getPermissionsCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 查询权限列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/searchPermissionsList")
    public AdminPermissionsResponse searchPermissionsList(@RequestBody AdminPermissionsRequest request){
        AdminPermissionsResponse response = new AdminPermissionsResponse();
        Integer count = permissionsService.getPermissionsCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchPermissionsList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<AdminPermissions> adminPermissionsList = permissionsService.searchPermissionsList(request);
        logger.debug(JSON.toJSONString(adminPermissionsList));
        if(!CollectionUtils.isEmpty(adminPermissionsList)){
            List<AdminPermissionsVO> adminPermissionsVOList = CommonUtils.convertBeanList(adminPermissionsList,AdminPermissionsVO.class);
            response.setResultList(adminPermissionsVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 检查数据库是否已存在该权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/isExistsPermission")
    public BooleanResponse isExistsPermission(@RequestBody AdminPermissionsVO adminPermissionsVO){
        BooleanResponse response = new BooleanResponse();
        boolean exist = permissionsService.isExistsPermission(adminPermissionsVO);
        response.setResultBoolean(exist);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 插入权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/insertPermission")
    public AdminPermissionsResponse insertPermission(@RequestBody AdminPermissionsVO adminPermissionsVO){
        AdminPermissionsResponse response = new AdminPermissionsResponse();
        int count = permissionsService.insertPermission(adminPermissionsVO);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 修改权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/updatePermission")
    public AdminPermissionsResponse updatePermission(@RequestBody AdminPermissionsVO adminPermissionsVO){
        AdminPermissionsResponse response = new AdminPermissionsResponse();
        int count = permissionsService.updatePermission(adminPermissionsVO);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据uuid查询权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @GetMapping(value = "/searchPermissionByUuid/{uuid}")
    public AdminPermissionsResponse searchPermissionByUuid(@PathVariable String uuid){
        AdminPermissionsResponse response = new AdminPermissionsResponse();
        AdminPermissions adminPermissions = permissionsService.searchPermissionByUuid(uuid);
        if(adminPermissions != null){
            AdminPermissionsVO adminPermissionsVO = CommonUtils.convertBean(adminPermissions,AdminPermissionsVO.class);
            response.setResult(adminPermissionsVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 删除权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @GetMapping(value = "/deletePermission/{uuid}")
    public AdminPermissionsResponse deletePermission(@PathVariable String uuid){
        AdminPermissionsResponse response = new AdminPermissionsResponse();
        int count = permissionsService.deletePermission(uuid);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }
}
