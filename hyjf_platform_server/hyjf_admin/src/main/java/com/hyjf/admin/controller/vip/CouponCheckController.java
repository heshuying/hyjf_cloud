/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CouponCheckService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.CouponCheckVO;
import com.hyjf.am.vo.config.ParamNameVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yaoyong
 * @version CouponCheckController, v0.1 2018/7/3 15:57
 */
@Api(tags = "VIP中心-优惠券列表")
@RestController
@RequestMapping("/hyjf-admin/coupon/checklist")
public class CouponCheckController extends BaseController {
    /**
     * 权限名称
     */
    private static final String PERMISSIONS = "couponuser";
    @Autowired
    CouponCheckService couponCheckService;

    @ApiOperation(value = "初始化页面", notes = "初始化页面")
    @PostMapping("/couponInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult couponInit(@RequestBody AdminCouponCheckRequest request) {
        CouponCheckResponse ccr = couponCheckService.serchCouponList(request);
        List<ParamNameVO> couponType = couponCheckService.getParamNameList("COUPON_TYPE");
        ccr.setCouponType(couponType);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());

        }
        return new AdminResult<>(ccr);
    }


    @ApiOperation(value = "删除信息", notes = "删除信息")
    @GetMapping("/deleteAction/{ids}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteCheckList(@PathVariable String ids) {
        AdminCouponCheckRequest acr = new AdminCouponCheckRequest();
        CouponCheckResponse ccr = new CouponCheckResponse();
        String[] split = ids.split(",");
        for (String id : split) {
            acr.setId(id);
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


    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping("/uploadAction")
    public AdminResult uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CouponCheckResponse checkResponse = couponCheckService.uploadFile(request, response);
        if (checkResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(checkResponse)) {
            return new AdminResult<>(FAIL, checkResponse.getMessage());
        }
        return new AdminResult<>(checkResponse.getResult());
    }

    @ApiOperation(value = "下载文件", notes = "下载文件")
    @GetMapping("/downloadAction/{id}")
    public void downloadFile(HttpServletResponse response, @PathVariable String id) {
        couponCheckService.downloadFile(id, response);
    }

    @ApiOperation(value = "审核优惠券", notes = "审核优惠券")
    @PostMapping("/auditAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult checkCoupon(HttpServletRequest request, HttpServletResponse response, @RequestBody AdminCouponCheckRequest checkRequest) {
        CouponCheckResponse ccr = new CouponCheckResponse();
        String remark = checkRequest.getRemark();
        boolean results = false;
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        //审核通过
        if (StringUtils.equals(String.valueOf(checkRequest.getStatus()), "2")) {
            String path = checkRequest.getId();
            String[] split = path.split(",");
            String id = split[0];
            CouponCheckVO couponCheck = couponCheckService.getCouponCheckById(Integer.parseInt(id));
            boolean flag = false;
            if (1 == couponCheck.getStatus()) {
                //审核状态设置为4，为临时状态-审核中，优惠券发送完成之后更改为审核成功，状态为2.
                checkRequest.setStatus(4);
                results = couponCheckService.updateCoupon(checkRequest);
                try {
                    flag = couponCheckService.batchCheck(checkRequest.getId(), response, userId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (flag) {
                    checkRequest.setStatus(2);
                    results = couponCheckService.updateCoupon(checkRequest);
                } else {
                    ccr.setMessage("审核异常，请检查上传的Excel文件！");
                    return new AdminResult<>(FAIL, ccr.getMessage());
                }
            } else {
                ccr.setMessage("请勿重复审核！");
                return new AdminResult<>(FAIL, ccr.getMessage());
            }
            //审核不通过需要填写备注,备注20字以内
        } else if (StringUtils.isNotBlank(remark) && remark.length() <= 20) {
            results = couponCheckService.updateCoupon(checkRequest);
        } else {
            ccr.setMessage("审核不通过需要填写备注,备注20字以内");
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        if (results) {
            ccr.setMessage("审核成功,正在发放优惠券");
            return new AdminResult<>(SUCCESS, ccr.getMessage());
        } else {
            ccr.setMessage("审核失败");
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
    }

}
