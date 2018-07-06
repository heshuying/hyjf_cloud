/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.hyjf.admin.beans.request.CouponCheckRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CouponCheckService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.CouponCheckVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yaoyong
 * @version CouponCheckController, v0.1 2018/7/3 15:57
 */
@Api(value = "优惠券列表接口")
@RestController
@RequestMapping("/coupon/checkList")
public class CouponCheckController extends BaseController {
    /**权限名称 */
    private static final String PERMISSIONS = "couponuser";
    @Autowired
    CouponCheckService couponCheckService;

    @ApiOperation(value = "优惠券列表", notes = "列表初始化页面")
    @PostMapping("/couponInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponCheckVO>> couponInit(@RequestBody CouponCheckRequestBean requestBean) {
        AdminCouponCheckRequest acr = new AdminCouponCheckRequest();
        BeanUtils.copyProperties(requestBean, acr);
        CouponCheckResponse ccr = couponCheckService.serchCouponList(acr);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());

        }
        return new AdminResult<ListResult<CouponCheckVO>>(ListResult.build(ccr.getResultList(), ccr.getRecordTotal()));
    }


    @ApiOperation(value = "优惠券列表", notes = "删除信息")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteCheckList(@RequestBody String ids) {
        AdminCouponCheckRequest acr = new AdminCouponCheckRequest();
        CouponCheckResponse ccr = new CouponCheckResponse();
        String[] split = ids.split(",");
        for (String id : split) {
            acr.setId(Integer.parseInt(id));
            ccr = couponCheckService.deleteCouponList(acr);
        }
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "优惠券列表", notes = "上传文件")
    @PostMapping("/uploadAction")
    public AdminResult<CouponCheckVO> uploadFile(HttpServletRequest request, HttpServletResponse response) {
        CouponCheckResponse files = couponCheckService.uploadFile(request, response);
        return new AdminResult<CouponCheckVO>(files.getResult());
    }

    @ApiOperation(value = "优惠券列表", notes = "下载文件")
    @PostMapping("/downloadAction")
    public AdminResult downloadFile(HttpServletResponse response, @RequestBody String id) {
        couponCheckService.downloadFile(id, response);
        return new AdminResult<>();
    }

    @ApiOperation(value = "优惠券列表", notes = "审核优惠券")
    @PostMapping("/auditAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult checkCoupon(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponCheckRequestBean requestBean) {
        AdminCouponCheckRequest checkRequest = new AdminCouponCheckRequest();
        BeanUtils.copyProperties(requestBean, request);
        CouponCheckResponse ccr = new CouponCheckResponse();
        String remark = checkRequest.getRemark();
        boolean results = false;
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        //审核通过
        if (StringUtils.equals(String.valueOf(checkRequest.getStatus()), "2")) {
            boolean flag = false;
            try {
                flag = couponCheckService.batchCheck(checkRequest.getId(), response, userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag) {
                results = couponCheckService.updateCoupon(checkRequest);
            }
            //审核不通过需要填写备注,备注20字以内
        } else if (StringUtils.isNotBlank(remark) && remark.length() <= 20) {
            results = couponCheckService.updateCoupon(checkRequest);
        } else {
            ccr.setMessage("审核不通过需要填写备注,备注20字以内");
            return new AdminResult<>(ccr.getResult());
        }
        if (results) {
            ccr.setMessage("审核成功");
        } else {
            ccr.setMessage("审核失败");
        }
        return new AdminResult<>(ccr.getResult());
    }

}
