package com.hyjf.admin.controller.mobileclient;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.mobileclient.VersionConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;
import com.hyjf.am.vo.config.VersionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 版本管理
 * @author lisheng
 * @version VersionConfigController, v0.1 2018/7/11 11:24
 */
@Api(tags = "移动客户端-版本管理")
@RestController
@RequestMapping("/hyjf-admin/config/versionconfig")
public class VersionConfigController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    VersionConfigService versionConfigService;
    //权限名称
    private static final String PERMISSIONS = "versionconfig";

    /**
     * 查询列表
     *
     * @return
     */
    @ApiOperation(value = "版本管理:列表查询", notes = "版本管理:列表查询")
    @PostMapping(value = "/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public VersionConfigBeanResponse search(@RequestBody VersionConfigBeanRequest request) {
        VersionConfigBeanResponse recordList = versionConfigService.getRecordList(request);
        return recordList;
    }

    /**
     * 详情查询
     *
     * @return
     */
    @ApiOperation(value = "版本管理:详情查询", notes = "版本管理:详情查询")
    @PostMapping(value = "/searchinfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult<VersionVO> searchinfo(@RequestBody VersionConfigBeanRequest request) {
        VersionConfigBeanResponse recordList = versionConfigService.getRecord(request);
        if (!Response.isSuccess(recordList)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<VersionVO>(recordList.getResult());
    }

    @ApiOperation(value = "版本管理:添加信息", notes = "版本管理:添加信息")
    @PostMapping(value = "/insertinfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<VersionVO> insertinfo(@RequestBody VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = versionConfigService.insertRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);


    }


    @ApiOperation(value = "版本管理:修改信息", notes = "版本管理:修改信息")
    @PostMapping(value = "/updateinfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<VersionVO> updateinfo(@RequestBody VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = versionConfigService.updateRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);

    }


    @ApiOperation(value = "版本管理:刪除信息", notes = "版本管理:刪除信息")
    @PostMapping(value = "/deleteinfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult<VersionVO> deleteinfo(@RequestBody VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = versionConfigService.deleteRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }
}
