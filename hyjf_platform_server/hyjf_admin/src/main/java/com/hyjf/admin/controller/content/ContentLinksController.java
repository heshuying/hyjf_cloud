/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentAdsService;
import com.hyjf.admin.service.ContentLinksService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.resquest.admin.ContentLinksRequest;
import com.hyjf.am.vo.config.LinkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * @author yinhui
 * @version ContentLinksController, v0.1 2018/7/13 10:39
 */
@Api(tags = "内容中心-友情链接")
@RestController
@RequestMapping("/hyjf-admin/content/contentlink")
public class ContentLinksController extends BaseController {
    @Autowired
    private ContentLinksService contentLinksService;

    @Autowired
    private ContentAdsService contentAdsService;

    @ApiOperation(value = "内容中心-友情链接列表查询", notes = "内容中心-友情链接列表查询")
    @PostMapping("/searchaction")
    public AdminResult<ListResult<LinkVO>> searchAction(@RequestBody ContentLinksRequest requestBean) {
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
    @PostMapping("/insert")
    public AdminResult add(@RequestBody ContentLinksRequest requestBean) {
        LinkResponse response = contentLinksService.insertAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "内容中心-友情链接  迁移到查看详细画面", notes = "内容中心-友情链接  迁移到查看详细画面")
    @PostMapping("/infoInfoAction")
    public AdminResult infoInfoAction(@RequestBody ContentLinksRequest requestBean) {
        LinkResponse response = contentLinksService.infoInfoAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response.getResult());
    }

    @ApiOperation(value = "修改内容中心-友情链接", notes = "修改内容中心-友情链接")
    @PostMapping("/update")
    public AdminResult update(@RequestBody ContentLinksRequest requestBean) {
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
    @PostMapping("/updatestatus")
    public AdminResult updatestatus(@RequestBody ContentLinksRequest requestBean) {
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
    @DeleteMapping("/delete/{id}")
    public AdminResult delete(@PathVariable Integer id) {
        int num = contentLinksService.deleteById(id);
        if (num <= 0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }

    @ApiOperation(value = "资料上传", notes = "资料上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public  AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            LinkedList<BorrowCommonImage> borrowCommonImages = contentAdsService.uploadFile(request);
            adminResult.setData(borrowCommonImages);
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }
}
