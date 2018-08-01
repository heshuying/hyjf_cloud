/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.ContentLinksRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentLinksService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.config.LinkVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version ContentLinksController, v0.1 2018/7/13 10:39
 */
@Api(value = "内容中心-友情链接", tags = "内容中心-友情链接")
@RestController
@RequestMapping("/hyjf-admin/content/contentlink")
public class ContentLinksController extends BaseController {
    @Autowired
    private ContentLinksService contentLinksService;

    @ApiOperation(value = "内容中心-友情链接列表查询", notes = "内容中心-友情链接列表查询")
    @RequestMapping("/searchaction")
    public AdminResult<ListResult<LinkVO>> searchAction(@RequestBody ContentLinksRequestBean requestBean) {
        LinkResponse response = contentLinksService.searchAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "添加内容中心-友情链接", notes = "添加内容中心-友情链接")
    @RequestMapping("/insert")
    public AdminResult add(@RequestBody ContentLinksRequestBean requestBean) {
        LinkResponse response = contentLinksService.insertAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "修改内容中心-友情链接", notes = "修改内容中心-友情链接")
    @RequestMapping("/update")
    public AdminResult update(@RequestBody ContentLinksRequestBean requestBean) {
        LinkResponse response = contentLinksService.updateAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "修改内容中心-友情链接状态", notes = "修改内容中心-友情链接状态")
    @RequestMapping("/updatestatus")
    public AdminResult updatestatus(@RequestBody ContentLinksRequestBean requestBean) {
        LinkResponse response = contentLinksService.updateStatus(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "删除内容中心-友情链接", notes = "删除内容中心-友情链接")
    @RequestMapping("/delete/{id}")
    public AdminResult delete(@PathVariable Integer id) {
        LinkResponse response = contentLinksService.deleteById(id);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }
}
