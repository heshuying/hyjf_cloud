package com.hyjf.admin.controller.content;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentAdsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.vo.config.ContentAdsBeanVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内容中心-广告管理
 * @author yinhui
 * Created by yinhui on 2018/7/19.
 */
@Api(value = "内容中心-广告管理", tags = "内容中心-广告管理")
@RestController
@RequestMapping("/hyjf-admin/content/contentads")
public class ContentAdsController extends BaseController {

    @Autowired
    private ContentAdsService contentAdsService;

    @ApiOperation(value = "广告管理-条件列表查询", notes = "广告管理-条件列表查询")
    @RequestMapping("/searchaction")
    public AdminResult<ListResult<ContentAdsBeanVO>> searchAction(ContentAdsRequest request){
        logger.info("查询内容中心-广告管理-条件列表查询开始......");
        ContentAdsResponse response = contentAdsService.searchAction(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "广告管理-添加", notes = "广告管理-添加")
    @RequestMapping("/insert")
    public AdminResult insert(ContentAdsRequest request){
        logger.info("添加内容中心-广告管理开始......");
        ContentAdsResponse response = contentAdsService.inserAction(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "广告管理-修改", notes = "广告管理-修改")
    @RequestMapping("/update")
    public AdminResult update(ContentAdsRequest request){
        logger.info("修改内容中心-广告管理开始......");
        ContentAdsResponse response = contentAdsService.updateAction(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "广告管理-修改", notes = "广告管理-修改")
    @RequestMapping("/delete/{id}")
    public AdminResult delete(@PathVariable Integer id){
        logger.info("修改内容中心-广告管理开始......");
        ContentAdsResponse response = contentAdsService.deleteById(id);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
