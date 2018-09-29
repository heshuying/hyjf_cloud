package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PartnerConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminPartnerConfigDetailResponse;
import com.hyjf.am.resquest.admin.AdminPartnerConfigListRequest;
import com.hyjf.am.vo.admin.HjhInstConfigWrapVo;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by xiehuili on 2018/7/5.
 * @version PartnerConfigController, v0.1 2018/7/5.
 */
@Api(tags ="配置中心-合作机构配置")
@RestController
@RequestMapping("/hyjf-admin/config/partnerconfig")
public class PartnerConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "partnerconfig";
    @Autowired
    private PartnerConfigService partnerConfigService;

    @ApiOperation(value = "查询配置中心合作机构配置", notes = "查询配置中心合作机构配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhInstConfigWrapVo>> instConfigInit( @RequestBody AdminPartnerConfigListRequest adminRequest) {
        AdminPartnerConfigDetailResponse response=partnerConfigService.partnerConfigInit(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<HjhInstConfigWrapVo>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "合作机构配置详情页面", notes = "合作机构配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult<HjhInstConfigWrapVo>  instConfigInfo(@RequestBody AdminPartnerConfigListRequest adminRequest) {
        AdminPartnerConfigDetailResponse adminResponse= partnerConfigService.searchPartnerConfigInfo(adminRequest);
        if (adminResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());
        }
        return new AdminResult<HjhInstConfigWrapVo>(adminResponse.getResult()) ;
    }

    @ApiOperation(value = "合作机构配置添加", notes = "合作机构配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertInstConfig(HttpServletRequest request, @RequestBody AdminPartnerConfigListRequest req) {
        AdminSystemVO user = getUser(request);
        req.setUserId(Integer.valueOf(user.getId()));//为了接口测试用
        req.setUserId(3);
        AdminPartnerConfigDetailResponse prs = partnerConfigService.savePartnerConfig(req);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "合作机构配置修改", notes = "合作机构配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateInstConfig(HttpServletRequest request, @RequestBody AdminPartnerConfigListRequest req) {
        AdminSystemVO user = getUser(request);
        req.setUserId(Integer.valueOf(user.getId()));
//        req.setUserId(3);//为了接口测试用
        AdminPartnerConfigDetailResponse prs = partnerConfigService.updatePartnerConfig(req);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "合作机构配置删除", notes = "合作机构配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteInstConfig( @RequestBody AdminPartnerConfigListRequest req) {
        AdminPartnerConfigDetailResponse prs =new AdminPartnerConfigDetailResponse();
        if(StringUtils.isNotBlank(req.getIds())){
            prs = partnerConfigService.deletePartnerConfig(req);
        }
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }



}
