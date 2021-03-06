package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ContentAdsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.vo.admin.AdsTypeVO;
import com.hyjf.am.vo.config.ContentAdsBeanVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;

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

    /** 权限 */
    public static final String PERMISSIONS = "contentads";

    @ApiOperation(value = "条件列表查询", notes = "条件列表查询")
    @PostMapping("/searchaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW , ShiroConstants.PERMISSION_SEARCH})
    public AdminResult searchAction(@RequestBody  ContentAdsRequest request){
        logger.info("查询内容中心-广告管理-条件列表查询开始......");
        ContentAdsResponse response = contentAdsService.searchAction(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        ContentAdsBeanVO vo = response.getResult();
        if(vo == null){
            return new AdminResult<>(ListResult.build(new ArrayList(), response.getCount()));
        }else{
            return new AdminResult<>(ListResult.build(response.getResult().getRecordList(), response.getCount()));
        }

    }

    @ApiOperation(value = "获取广告类型下拉列表", notes = "获取广告类型下拉列表")
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
        ContentAdsBeanVO vo = response.getResult();
        if(vo == null){
            return new AdminResult<>(ListResult.build(new ArrayList(), response.getCount()));
        }else{
            return new AdminResult<>(ListResult.build(response.getResult().getAdsTypeList(), response.getCount()));
        }

    }

    @ApiOperation(value = "添加", notes = "添加")
    @PostMapping("/insert")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD )
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

    @ApiOperation(value = "修改根据id查找所需要数据", notes = "修改根据id查找所需要数据")
    @PostMapping("/infoaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH )
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
        ContentAdsBeanVO vo = response.getResult();
        if(vo == null){
            return new AdminResult<>();
        }else{
            return new AdminResult<>(response.getResult().getRecordList().get(0));
        }
    }

    @ApiOperation(value = "修改", notes = "修改")
    @PostMapping("/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY )
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

    @ApiOperation(value = "修改状态", notes = "修改状态")
    @PostMapping("/statusaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY )
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

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE )
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

    @ApiOperation(value = "资料上传", notes = "资料上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD , ShiroConstants.PERMISSION_MODIFY} )
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
