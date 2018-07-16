package com.hyjf.admin.controller.mobileclient;

import com.hyjf.admin.client.VersionConfigClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.mobileclient.VersionConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;
import com.hyjf.am.vo.config.AppBorrowImageVO;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.common.cache.CacheUtil;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 版本管理
 * @author lisheng
 * @version VersionConfigController, v0.1 2018/7/11 11:24
 */
@RestController
@RequestMapping("config/versionconfig")
public class VersionConfigController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    VersionConfigService versionConfigService;

    /**
     * 查询列表
     * @return
     */
    @ApiOperation(value = "版本管理列表查询", notes = "版本管理列表查询")
    @PostMapping(value = "/search")
    @ResponseBody
    public AdminResult<ListResult<VersionVO>> search(@RequestBody VersionConfigBeanRequest request) {
        VersionConfigBeanResponse recordList = versionConfigService.getRecordList(request);
        if (!Response.isSuccess(recordList)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<ListResult<VersionVO>>(ListResult.build(recordList.getResultList(), recordList.getRecordTotal()));
    }


    /**
     * 详情查询
     * @return
     */
    @ApiOperation(value = "版本管理详情查询", notes = "版本管理详情查询")
    @PostMapping(value = "/searchinfo")
    @ResponseBody
    public AdminResult<VersionVO> searchinfo(@RequestBody VersionConfigBeanRequest request) {
        VersionConfigBeanResponse recordList = versionConfigService.getRecord(request);
        if (!Response.isSuccess(recordList)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<VersionVO>(recordList.getResult());
    }

    @ApiOperation(value = "添加信息", notes = "添加信息")
    @PostMapping(value = "/insertinfo")
    @ResponseBody
    public AdminResult<VersionVO> insertinfo(@RequestBody VersionConfigBeanRequest request) throws Exception {
        // TODO 校验参数方法
        //this.validatorFieldCheck();
        VersionConfigBeanResponse response = versionConfigService.insertRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }


    @ApiOperation(value = "修改维护信息", notes = "修改维护信息")
    @PostMapping(value = "/updateinfo")
    @ResponseBody
    public AdminResult<VersionVO> updateinfo(@RequestBody VersionConfigBeanRequest request) throws Exception {
        // TODO 校验参数方法
        //this.validatorFieldCheck();
        VersionConfigBeanResponse response = versionConfigService.updateRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }


    @ApiOperation(value = "刪除信息", notes = "刪除信息")
    @PostMapping(value = "/deleteinfo")
    @ResponseBody
    public AdminResult<VersionVO> deleteinfo(@RequestBody VersionConfigBeanRequest request) throws Exception {
        VersionConfigBeanResponse response = versionConfigService.deleteRecord(request);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }
}
