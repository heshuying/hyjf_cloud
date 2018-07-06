/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.Utils.ValidatorFieldCheckUtil;
import com.hyjf.admin.beans.request.CouponConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CouponConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.common.util.GetSessionOrRequestUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author yaoyong
 * @version CouponIssuanceController, v0.1 2018/7/5 10:05
 */
@Api(value = "优惠券发行")
@RestController
@RequestMapping("/coupon/issuance")
public class CouponIssuanceController extends BaseController {
    /**
     * 权限名称
     */
    private static final String PERMISSIONS = "couponconfig";
    @Autowired
    CouponConfigService couponConfigService;

    @ApiOperation(value = "优惠券发行", notes = "页面初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponConfigCustomizeVO>> init(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponConfigRequestBean requestBean) {
        CouponConfigRequest couponConfigRequest = new CouponConfigRequest();
        BeanUtils.copyProperties(requestBean, couponConfigRequest);
        CouponConfigCustomizeResponse ccr = couponConfigService.getRecordList(couponConfigRequest);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());

        }
        return new AdminResult<ListResult<CouponConfigCustomizeVO>>(ListResult.build(ccr.getResultList(), ccr.getCount()));
    }


    @ApiOperation(value = "优惠券发行", notes = "修改页面信息")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    @ApiImplicitParam(name = "id", value = "主键")
    public AdminResult<CouponConfigVO> updateCouponConfig(@RequestBody String id) {
        CouponConfigRequest couponConfigRequest = new CouponConfigRequest();
        couponConfigRequest.setId(id);
        CouponConfigResponse ccr = couponConfigService.getCouponConfig(couponConfigRequest);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<CouponConfigVO>(ccr.getResult());
    }


    @ApiOperation(value = "优惠券发行", notes = "保存修改信息")
    @PostMapping("/saveAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult saveCouponConfig(@RequestBody CouponConfigRequestBean requestBean) {
        CouponConfigRequest request = new CouponConfigRequest();
        BeanUtils.copyProperties(requestBean, request);
        CouponConfigResponse ccr = couponConfigService.saveCouponConfig(request);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "优惠券发行", notes = "发行优惠券")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertAction(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse ccr = couponConfigService.insertAction(couponConfigRequest);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "优惠券发行", notes = "删除优惠券信息")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteAction(@RequestBody String id) {
        CouponConfigRequest couponConfigRequest = new CouponConfigRequest();
        couponConfigRequest.setId(id);
        CouponConfigResponse ccr = couponConfigService.deleteAction(couponConfigRequest);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "优惠券发行", notes = "审核页面信息")
    @PostMapping("/auditInfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_AUDIT)
    public AdminResult<CouponConfigVO> auditInfo(@RequestBody String id) {
        CouponConfigRequest ccfr = new CouponConfigRequest();
        ccfr.setId(id);
        CouponConfigResponse response = couponConfigService.getAuditInfo(ccfr);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<CouponConfigVO>(response.getResult());
    }


    @ApiOperation(value = "优惠券发行", notes = "保存审核信息")
    @PostMapping("auditUpdateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_AUDIT)
    public AdminResult updateAudit(HttpServletRequest request, @RequestBody CouponConfigRequest couponConfigRequest) {
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        couponConfigRequest.setAuditUser(userId);
        CouponConfigResponse ccfr = couponConfigService.updatrAudit(couponConfigRequest);
        if (ccfr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccfr)) {
            return new AdminResult<>(FAIL, ccfr.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "优惠券发行", notes = "检查编号唯一性")
    @PostMapping("/checkAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult checkAction(HttpServletRequest request, HttpServletResponse response) {
        CouponUserRequest cur = new CouponUserRequest();
        CouponUserResponse curs = new CouponUserResponse();
        String param = request.getParameter("param");
        // 优惠券编号
        String couponCode = request.getParameter("code");
        JSONObject ret = new JSONObject();
        int issuNum = 0;
        if (StringUtils.isNotEmpty(couponCode)) {
            cur.setCouponCode(couponCode);
            curs = couponConfigService.getIssueNumber(cur);
        }
        if (StringUtils.isNotEmpty(param)) {
            int count = Integer.parseInt(param);
            if (curs.getCount() > count) {
                String message = ValidatorFieldCheckUtil.getErrorMessage(
                        "coupon.quantity", "");
                if("errors.coupon.quantity".equals(message)){
                    message="修改数量不能小于已发放数量，已发放"+issuNum+"张";
                }
                ret.put("info", message);
            }
            }
        // 没有错误时,返回y
        if (!ret.containsKey("info")) {
            ret.put("status","y");
        }
        return new AdminResult<>(ret.toString());
    }

}