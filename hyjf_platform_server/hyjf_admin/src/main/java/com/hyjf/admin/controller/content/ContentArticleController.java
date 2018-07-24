/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentArticleService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章管理
 *
 * @author yinhui
 * @version ContentArticleController, v0.1 2018/7/17 17:04
 */
@Api(value = "内容中心-文章管理", description = "内容中心-文章管理")
@RestController
@RequestMapping("/hyjf-admin/content/contentarticle")
public class ContentArticleController extends BaseController {

    @Autowired
    private ContentArticleService contentArticleService;

    @ApiOperation(value = "文章管理-条件列表查询", notes = "文章管理-条件列表查询")
    @RequestMapping("/searchaction")
    public AdminResult<ListResult<ContentArticleVO>> searchAction(ContentArticleRequest requestBean) {
        logger.info("查询内容中心-文章管理-条件列表查询开始......");
        ContentArticleResponse response = contentArticleService.searchAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "文章管理-添加", notes = "文章管理-添加")
    @RequestMapping("/insert")
    public AdminResult add(ContentArticleRequest requestBean) {
        ContentArticleResponse response = contentArticleService.inserAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "文章管理-修改", notes = "文章管理-修改")
    @RequestMapping("/update")
    public AdminResult update(ContentArticleRequest requestBean) {
        ContentArticleResponse response = contentArticleService.updateAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "文章管理-删除", notes = "文章管理-删除")
    @RequestMapping("/delete/{id}")
    public AdminResult delete(@PathVariable Integer id) {
        ContentArticleResponse response = contentArticleService.deleteById(id);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
