package com.hyjf.admin.controller.content;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentAdsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.vo.admin.AdsTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 内容中心-广告管理
 * @author yinhui
 * Created by yinhui on 2018/7/19.
 */
@Api(tags = "内容中心-广告管理")
@RestController
@RequestMapping("/hyjf-admin/content/contentads")
public class ContentAdsController extends BaseController {

    @Autowired
    private ContentAdsService contentAdsService;

    @ApiOperation(value = "广告管理-条件列表查询", notes = "广告管理-条件列表查询")
    @PostMapping("/searchaction")
    public AdminResult searchAction(@RequestBody  ContentAdsRequest request){
        logger.info("查询内容中心-广告管理-条件列表查询开始......");
        ContentAdsResponse response = contentAdsService.searchAction(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResult().getRecordList(), response.getCount()));
    }

    @ApiOperation(value = "广告管理-获取广告类型下拉列表", notes = "广告管理-获取广告类型下拉列表")
    @GetMapping("/adstypelist")
    public AdminResult<ListResult<AdsTypeVO>> adsTypeList(){
        logger.info("查询内容中心-广告管理-条件列表查询开始......");
        ContentAdsResponse response = contentAdsService.adsTypeList();
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResult().getAdsTypeList(), response.getCount()));
    }

    @ApiOperation(value = "广告管理-添加", notes = "广告管理-添加")
    @PostMapping("/insert")
    public AdminResult insert(@RequestBody ContentAdsRequest request){
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

    @ApiOperation(value = "广告管理-修改根据id查找所需要数据", notes = "广告管理-修改根据id查找所需要数据")
    @PostMapping("/infoaction")
    public AdminResult infoaction(@RequestBody ContentAdsRequest request){
        logger.info("修改内容中心-广告管理开始......");

        Integer ids = request.getAds().getId();
        if(ids == null || ids < 0){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        ContentAdsResponse response = contentAdsService.infoaction(ids);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response.getResult().getRecordList().get(0));
    }

    @ApiOperation(value = "广告管理-修改", notes = "广告管理-修改")
    @PostMapping("/update")
    public AdminResult update(@RequestBody ContentAdsRequest request){
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

    @ApiOperation(value = "广告管理-修改状态", notes = "广告管理-修改状态")
    @PostMapping("/statusaction")
    public AdminResult statusaction(@RequestBody ContentAdsRequest request){
        logger.info("修改广告管理状态开始......");
        Integer ids = request.getAds().getId();
        if(ids == null || ids < 0){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        ContentAdsResponse response = contentAdsService.statusaction(ids);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "广告管理-删除", notes = "广告管理-删除")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public AdminResult delete(@PathVariable Integer id){
        logger.info("删除内容中心-广告管理开始......");
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
