package com.hyjf.admin.controller.manager;

import com.hyjf.admin.beans.request.InstConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.InstConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminInstConfigDetailResponse;
import com.hyjf.am.response.admin.AdminInstConfigListResponse;
import com.hyjf.am.resquest.admin.AdminInstConfigListRequest;
import com.hyjf.am.vo.admin.HjhInstConfigWrapVo;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by xiehuili on 2018/7/5.
 * @version InstConfigController, v0.1 2018/7/5.
 */
@Api(value = "配置中心保证金配置")
@RestController
@RequestMapping("/hyjf-admin/config/instconfig")
public class InstConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "instconfig";
    @Autowired
    private InstConfigService instConfigService;

    @ApiOperation(value = "配置中心保证金配置", notes = "查询配置中心保证金配置")
    @RequestMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhInstConfigVO>> instConfigInit( @RequestBody InstConfigRequestBean adminInstConfigListRequest) {
        AdminInstConfigListRequest adminRequest= new AdminInstConfigListRequest();
        //可以直接使用
        BeanUtils.copyProperties(adminInstConfigListRequest, adminRequest);
        AdminInstConfigListResponse response=instConfigService.instConfigInit(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<HjhInstConfigVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "配置中心保证金配置", notes = "保证金配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult<HjhInstConfigWrapVo>  instConfigInfo(@RequestBody String ids) {
        AdminInstConfigListRequest adminRequest= new AdminInstConfigListRequest();
        adminRequest.setIds(ids);
        AdminInstConfigDetailResponse adminResponse= instConfigService.searchInstConfigInfo(adminRequest);
        if (adminResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());
        }
        return new AdminResult<HjhInstConfigWrapVo>(adminResponse.getResult()) ;
    }

    @ApiOperation(value = "配置中心保证金配置", notes = "保证金配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertInstConfig(HttpServletRequest request, @RequestBody InstConfigRequestBean from) {
        AdminInstConfigListRequest req = new AdminInstConfigListRequest();
        BeanUtils.copyProperties(from, req);
        req.setUserId(Integer.valueOf(this.getUser(request).getId()));
        AdminInstConfigListResponse prs = instConfigService.saveInstConfig(req);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "配置中心保证金配置", notes = "保证金配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateInstConfig(HttpServletRequest request, @RequestBody InstConfigRequestBean from) {
        AdminInstConfigListRequest req = new AdminInstConfigListRequest();
        BeanUtils.copyProperties(from, req);
        req.setUserId(Integer.valueOf(this.getUser(request).getId()));
        AdminInstConfigListResponse prs = instConfigService.updateInstConfig(req);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "配置中心保证金配置", notes = "保证金配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteInstConfig( @RequestBody String ids) {
        AdminInstConfigListRequest req = new AdminInstConfigListRequest();
        AdminInstConfigListResponse prs =new AdminInstConfigListResponse();
        if(StringUtils.isNotBlank(ids)){
            req.setIds(ids);
            prs = instConfigService.deleteInstConfig(req);
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
