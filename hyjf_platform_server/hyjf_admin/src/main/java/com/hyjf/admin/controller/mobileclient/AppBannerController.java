package com.hyjf.admin.controller.mobileclient;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.request.AppBannerRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.admin.service.MessagePushNoticesService;
import com.hyjf.admin.service.mobileclient.AppBannerService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsTypeVO;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import com.hyjf.am.vo.market.AppBannerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 广告管理
 *
 * @author lisheng
 * @version AppBannerController, v0.1 2018/7/11 11:27
 */
@Api(tags = "移动客户端-广告管理")
@RestController
@RequestMapping("/hyjf-admin/app/maintenance/banner")
public class AppBannerController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AppBannerService appBannerService;
    @Autowired
    private MessagePushNoticesService messagePushNoticesService;

    @ApiOperation(value = "广告管理页面载入", notes = "广告管理页面载入")
    @PostMapping(value = "/init")
    @ResponseBody
    public AdminResult<AppBannerResponse> init(@RequestBody  AppBannerRequestBean appBannerRequestBean) {
        try {
            AppBannerRequest aprlr = new AppBannerRequest();
            BeanUtils.copyProperties(appBannerRequestBean, aprlr);
            AppBannerResponse prs = appBannerService.getRecordList(aprlr);
            List<AdsTypeVO> adsTypeList = prs.getAdsTypeList();
            if (prs == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(prs)) {
                return new AdminResult<>(FAIL, prs.getMessage());
            }
            AdminResult adminResult = new AdminResult();
            adminResult.setData(prs);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "广告管理添加", notes = "广告管理添加")
    @PostMapping(value = "/add")
    @ResponseBody
    public AdminResult<AdsWithBLOBsVO> add(@RequestBody AppBannerRequestBean form) {
        try {
            AdsWithBLOBsVO adsWithBLOBsVO = new AdsWithBLOBsVO();
            BeanUtils.copyProperties(form, adsWithBLOBsVO);
            AppBannerResponse appBannerResponse = appBannerService.insertRecord(adsWithBLOBsVO);
            if (Response.isSuccess(appBannerResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "广告管理修改", notes = "广告管理修改")
    @PostMapping(value = "/update")
    @ResponseBody
    public AdminResult<AdsWithBLOBsVO> update(@RequestBody AppBannerRequestBean form) {
        try {
            AdsWithBLOBsVO adsWithBLOBsVO = new AdsWithBLOBsVO();
            BeanUtils.copyProperties(form, adsWithBLOBsVO);
            AppBannerResponse appBannerResponse = appBannerService.updateRecord(adsWithBLOBsVO);
            if (Response.isSuccess(appBannerResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "修改状态", notes = "修改状态")
    @PostMapping(value = "/updateStatus")
    @ResponseBody
    public AdminResult<AdsWithBLOBsVO> updateStatus(@RequestBody AppBannerRequestBean form) {
        try {
            AdsWithBLOBsVO adsWithBLOBsVO = new AdsWithBLOBsVO();
            BeanUtils.copyProperties(form, adsWithBLOBsVO);
            AppBannerResponse appBannerResponse = appBannerService.updateStatus(adsWithBLOBsVO);
            if (Response.isSuccess(appBannerResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


    @ApiOperation(value = "删除", notes = "删除")
    @PostMapping(value = "/delete")
    @ResponseBody
    public AdminResult<AdsWithBLOBsVO> deleteBanner(@RequestBody AppBannerRequestBean form) {
        try {
            AdsWithBLOBsVO adsWithBLOBsVO = new AdsWithBLOBsVO();
            BeanUtils.copyProperties(form, adsWithBLOBsVO);
            AppBannerResponse appBannerResponse = appBannerService.deleteAppBanner(adsWithBLOBsVO);
            if (Response.isSuccess(appBannerResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "上传", notes = "上传")
    @PostMapping(value = "/upLoadFile")
    @ResponseBody
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            LinkedList<BorrowCommonImage> borrowCommonImages = messagePushNoticesService.uploadFile(request);
            adminResult.setData(borrowCommonImages);
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


}
