/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.CouponCheckService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
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
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoyong
 * @version CouponIssuanceController, v0.1 2018/7/5 10:05
 */
@Api(tags = "VIP中心-优惠券发行")
@RestController
@RequestMapping("/hyjf-admin/coupon/issuance")
public class CouponIssuanceController extends BaseController {
    /**
     * 权限名称
     */
    private static final String PERMISSIONS = "couponconfig";
    @Autowired
    CouponConfigService couponConfigService;
    @Autowired
    CouponCheckService couponCheckService;

    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<CouponConfigCustomizeResponse> init(@RequestBody CouponConfigRequest request) {
        CouponConfigCustomizeResponse ccr = couponConfigService.getRecordList(request);
        List<String> couponStatus = new ArrayList<>();
        couponStatus.add("待审核");
        couponStatus.add("已发行");
        couponStatus.add("审核不通过");
        List<ParamNameVO> couponType = couponCheckService.getParamNameList("COUPON_TYPE");
        ccr.setCouponStatus(couponStatus);
        ccr.setCouponTypes(couponType);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());

        }
        return new AdminResult<>(ccr);
    }


    @ApiOperation(value = "修改页面初始化", notes = "修改页面初始化")
    @RequestMapping(value = "/updateAction/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult updateCouponConfig(@PathVariable String id) {
        CouponConfigRequest couponConfigRequest = new CouponConfigRequest();
        //操作平台
        List<ParamNameVO> clients = couponCheckService.getParamNameList("CLIENT");
        //有效期类型
        List<ParamNameVO> expType = couponCheckService.getParamNameList("COUPON_EXP_TYPE");
        //项目类型
        List<BorrowProjectTypeVO> projectTypes = couponConfigService.getCouponProjectTypeList();

        // 被选中操作平台
        List<String> selectedClientList = new ArrayList<String>();
        // 被选中项目类型
        List<String> selectedProjectList = new ArrayList<String>();
        // 被选中操作平台表示用
        StringBuffer selectedClientDisplayBuffer = new StringBuffer();
        // 被选中项目类别表示用
        StringBuffer selectedProjectDisplayBuffer = new StringBuffer();

        couponConfigRequest.setId(id);
        CouponConfigResponse ccr = couponConfigService.getCouponConfig(couponConfigRequest);
        if (ccr != null) {
            CouponConfigVO configVO = ccr.getResult();
            if (configVO.getAuditUser() != null) {
                String userName = couponConfigService.getAdminInfoByUserId(configVO.getAuditUser());
                configVO.setAuditUser(userName);
            }

            // 被选中操作平台
            String clientSed[] = StringUtils.split(configVO.getCouponSystem(),
                    ",");
            for(int i=0 ; i< clientSed.length;i++){
                selectedClientList.add(clientSed[i]);
                if("-1".equals(clientSed[i])){
                    selectedClientDisplayBuffer.append("全部平台");
                    break;
                }else{
                    for (ParamNameVO paramName : clients) {
                        if(clientSed[i].equals(paramName.getNameCd())){
                            if(i!=0&&selectedClientDisplayBuffer.length()!=0){
                                selectedClientDisplayBuffer.append("/");
                            }
                            selectedClientDisplayBuffer.append(paramName.getName());

                        }
                    }
                }
            }

//            modelAndView.addObject("selectedClientList", selectedClientList);
            String selectedClientDisplay = selectedClientDisplayBuffer
                    .toString();
            selectedClientDisplay = StringUtils.removeEnd(
                    selectedClientDisplay, "/");
//            modelAndView.addObject("selectedClientDisplay",
//                    selectedClientDisplay);
            ccr.setSelectedClientDisplay(selectedClientDisplay);

            // 被选中项目类型    新逻辑 pcc20160715
            String projectSed[] = StringUtils.split(configVO.getProjectType(),
                    ",");
            String selectedProjectDisplay="";
            if(configVO.getProjectType().indexOf("-1")!=-1){
                selectedProjectDisplay="所有汇直投/汇消费/新手汇/尊享汇/汇添金/汇计划项目";
            }else{
                selectedProjectDisplayBuffer.append("所有");
                for (String project : projectSed) {
                    if("1".equals(project)){
                        selectedProjectDisplayBuffer.append("汇直投/");
                    }
                    if("2".equals(project)){
                        selectedProjectDisplayBuffer.append("汇消费/");
                    }
                    if("3".equals(project)){
                        selectedProjectDisplayBuffer.append("新手汇/");
                    }
                    if("4".equals(project)){
                        selectedProjectDisplayBuffer.append("尊享汇/");
                    }
                    if("5".equals(project)){
                        selectedProjectDisplayBuffer.append("汇添金/");
                    }
                    if("6".equals(project)){
                        selectedProjectDisplayBuffer.append("汇计划/");
                    }

                }
                selectedProjectDisplay= selectedProjectDisplayBuffer
                        .toString();
                selectedProjectDisplay = StringUtils.removeEnd(
                        selectedProjectDisplay, "/");
                selectedProjectDisplay=selectedProjectDisplay+"项目";
            }
//            modelAndView.addObject("selectedProjectList", selectedProjectList);
//            modelAndView.addObject("selectedProjectDisplay",
//                    selectedProjectDisplay);
            ccr.setSelectedProjectDisplay(selectedProjectDisplay);
        }
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>(ccr);
    }


    @ApiOperation(value = "保存修改信息", notes = "保存修改信息")
    @PostMapping("/saveAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult saveCouponConfig(@RequestBody CouponConfigRequest request) {
        CouponConfigResponse ccr = couponConfigService.saveCouponConfig(request);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "发行优惠券", notes = "发行优惠券")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertAction(@RequestBody CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse ccr = couponConfigService.insertAction(couponConfigRequest);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "删除优惠券信息", notes = "删除优惠券信息")
    @RequestMapping(value = "/deleteAction/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteAction(@PathVariable String id) {
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


    @ApiOperation(value = "审核页面信息", notes = "审核页面信息")
    @RequestMapping(value = "/auditInfo/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_AUDIT)
    public AdminResult<CouponConfigVO> auditInfo(@PathVariable String id) {
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


    @ApiOperation(value = "保存审核信息", notes = "保存审核信息")
    @PostMapping("/auditUpdateAction")
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

    @ApiOperation(value = "检查编号唯一性", notes = "检查编号唯一性")
    @PostMapping("/checkAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult checkAction(@RequestBody CouponConfigRequest request) {
        Integer couponQuantity = request.getCouponQuantity();
        // 优惠券编号
        String couponCode = request.getCouponCode();
        CouponUserResponse response = couponConfigService.getIssueNumber(couponCode);
        if (response.getCount() > couponQuantity) {
            String message = "修改数量不能小于已发放数量，已发放" + response.getCount() + "张";
            response.setMessage(message);
            return new AdminResult<>(FAIL,response.getMessage());
        }
        return new AdminResult<>(response);
    }

}