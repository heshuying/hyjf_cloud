package com.hyjf.admin.controller.mobileclient;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.request.AppBannerRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushNoticesService;
import com.hyjf.admin.service.mobileclient.AppBannerService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

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
    @Autowired
    private FileUpLoadUtil fileUpLoadUtil;

    @ApiOperation(value = "广告管理页面载入", notes = "广告管理页面载入")
    @PostMapping(value = "/init")
    public AdminResult<AppBannerResponse> init(@RequestBody  AppBannerRequestBean appBannerRequestBean) {
        AppBannerRequest aprlr = new AppBannerRequest();
        BeanUtils.copyProperties(appBannerRequestBean, aprlr);
        AppBannerResponse response = new AppBannerResponse();
        AppBannerResponse prs = appBannerService.getRecordList(aprlr);
        if (prs != null) {
            response=prs;
        }

        AdminResult adminResult = new AdminResult();
        adminResult.setData(response);
        return adminResult;

    }

    @ApiOperation(value = "广告管理修改转跳", notes = "广告管理修改转跳")
    @PostMapping(value = "/infoAction")
    public AdminResult<AppBannerResponse> infoAction(@RequestBody  AdsVO adsVO) {
        AppBannerResponse response = new AppBannerResponse();
        AppBannerResponse prs = appBannerService.getRecordById(adsVO);
        if (prs != null) {
            response=prs;
        }
        AdminResult adminResult = new AdminResult();
        adminResult.setData(response);
        return adminResult;

    }

    @ApiOperation(value = "广告管理添加", notes = "广告管理添加")
    @PostMapping(value = "/add")
    public AdminResult<AdsWithBLOBsVO> add(@RequestBody AdsVO adsVO) {
        AppBannerResponse appBannerResponse = appBannerService.insertRecord(adsVO);
        if (Response.isSuccess(appBannerResponse)) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

    }

    @ApiOperation(value = "广告管理修改", notes = "广告管理修改")
    @PostMapping(value = "/update")
    public AdminResult<AdsWithBLOBsVO> update(@RequestBody AdsVO adsVO) {
        AppBannerResponse appBannerResponse = appBannerService.updateRecord(adsVO);
        if (Response.isSuccess(appBannerResponse)) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

    }

    @ApiOperation(value = "修改状态", notes = "修改状态")
    @PostMapping(value = "/updateStatus")
    public AdminResult<AdsWithBLOBsVO> updateStatus(@RequestBody AdsVO adsVO) {
        AppBannerResponse appBannerResponse = appBannerService.updateStatus(adsVO);
        if (Response.isSuccess(appBannerResponse)) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

    }


    @ApiOperation(value = "删除", notes = "删除")
    @PostMapping(value = "/delete")
    public AdminResult<AdsWithBLOBsVO> deleteBanner(@RequestBody AdsVO adsVO) {
        AppBannerResponse appBannerResponse = appBannerService.deleteAppBanner(adsVO);
        if (Response.isSuccess(appBannerResponse)) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

    }

    @ApiOperation(value = "上传", notes = "上传")
    @PostMapping(value = "/upLoadFile")
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        LinkedList<BorrowCommonImage> borrowCommonImages = fileUpLoadUtil.upLoad(request);
        adminResult.setData(borrowCommonImages);
        adminResult.setStatus(SUCCESS);
        adminResult.setStatusDesc(SUCCESS_DESC);
        return adminResult;
    }


}
