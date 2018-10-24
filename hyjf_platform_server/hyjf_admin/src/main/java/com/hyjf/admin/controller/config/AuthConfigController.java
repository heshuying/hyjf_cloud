/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.vo.HjhUserAuthConfigCustomizeAPIVO;
import com.hyjf.admin.beans.vo.HjhUserAuthConfigLogCustomizeAPIVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AuthConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminAuthConfigLogResponse;
import com.hyjf.am.response.admin.AdminAuthConfigCustomizeResponse;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.vo.admin.HjhUserAuthConfigCustomizeVO;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author jun
 * @version AuthConfigController, v0.1 2018/10/14 11:36
 */
@Api(value = "配置中心-授权配置", tags = "配置中心-授权配置")
@RestController
@RequestMapping("/hyjf-admin/configCenter/authConfig")
public class AuthConfigController extends BaseController {

    public static final String PERMISSIONS = "authConfig";
    @Autowired
    private AuthConfigService authConfigService;

    @ApiOperation(value = "授权配置列表", notes = "授权配置列表")
    @GetMapping(value = "/authConfigList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhUserAuthConfigCustomizeAPIVO>> getAuthConfigList() {
        AdminAuthConfigCustomizeResponse authConfigResponse = authConfigService.getAuthConfigList();
        if (authConfigResponse == null) {
            return new AdminResult<>(FAIL, "获取授权配置列表失败");
        } else if (!Response.isSuccess(authConfigResponse)) {
            return new AdminResult<>(FAIL, authConfigResponse.getMessage());
        }

        List<HjhUserAuthConfigCustomizeAPIVO> apiList = CommonUtils.convertBeanList(authConfigResponse.getResultList(),HjhUserAuthConfigCustomizeAPIVO.class);
        return new AdminResult<ListResult<HjhUserAuthConfigCustomizeAPIVO>>(ListResult.build(apiList, authConfigResponse.getCount()));
    }

    @ApiOperation(value = "授权配置记录", notes = "授权配置记录")
    @PostMapping(value = "/authConfigLogList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhUserAuthConfigLogCustomizeAPIVO>> getAuthConfigLogList(@RequestBody HjhUserAuthConfigLogCustomizeAPIVO request) {
        AdminAuthConfigLogResponse response = authConfigService.getAuthConfigLogList(CommonUtils.convertBean(request,HjhUserAuthConfigLogCustomizeVO.class));
        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }else if(!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }

        List<HjhUserAuthConfigLogCustomizeAPIVO> apiList = CommonUtils.convertBeanList(response.getResultList(), HjhUserAuthConfigLogCustomizeAPIVO.class);
        return new AdminResult<ListResult<HjhUserAuthConfigLogCustomizeAPIVO>>(ListResult.build(apiList, response.getCount()));

    }


    @ApiOperation(value = "授权配置详情", notes = "授权配置详情")
    @PostMapping(value = "/authConfigDetail")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<HjhUserAuthConfigVO> getAuthConfigDetail(@RequestBody HjhUserAuthConfigCustomizeAPIVO form) {
        com.hyjf.common.util.HjhUserAuthConfigVO ret = null;
        HjhUserAuthConfigVO authConfig = null;
        if (form.getId() != null && form.getId() != 0) {
            Integer id = form.getId();
            switch (id) {
                case 1:
                    ret = CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH);
                    authConfig = this.convertAuthConfig(ret, id);
                    break;
                case 2:
                    ret = CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_REPAYMENT_AUTH);
                    authConfig = this.convertAuthConfig(ret, id);
                    break;
                case 3:
                    ret = CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_TENDER_AUTH);
                    authConfig = this.convertAuthConfig(ret, id);
                    break;
                case 4:
                    ret = CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_AUTO_CREDIT_AUTH);
                    authConfig = this.convertAuthConfig(ret, id);
                    break;
                default:
                    break;
            }
        }

        if (Validator.isNotNull(authConfig)) {
            return new AdminResult<>(authConfig);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


    @ApiOperation(value = "修改授权配置", notes = "修改授权配置")
    @PostMapping("/updateAuthConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateAuthConfig(HttpServletRequest request,@RequestBody HjhUserAuthConfigCustomizeAPIVO form) {

        AdminSystemVO loginUser=getUser(request);
        form.setUpdateUserId(Integer.parseInt(loginUser.getId()));
        form.setUpdateTime(GetDate.getNowTime10());
        form.setIp(GetCilentIP.getIpAddr(request));

        int result = this.authConfigService.updateAuthConfig(CommonUtils.convertBean(form,HjhUserAuthConfigVO.class));
        // 更新redis中的可用余额
        if(result > 0){
            Integer id=form.getId();
            switch (id) {
                case 1:
                    RedisUtils.setObj(CommonUtils.KEY_PAYMENT_AUTH, form);
                    break;
                case 2:
                    RedisUtils.setObj(CommonUtils.KEY_REPAYMENT_AUTH, form);
                    break;
                case 3:
                    RedisUtils.setObj(CommonUtils.KEY_AUTO_TENDER_AUTH, form);
                    break;
                case 4:
                    RedisUtils.setObj(CommonUtils.KEY_AUTO_CREDIT_AUTH, form);
                    break;
                default:
                    break;
            }
        }

        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }


    /**
     * 先从redis缓存里面取,redis里面没有才从数据库里面取
     * @param ret
     * @return
     */
    private HjhUserAuthConfigVO convertAuthConfig(com.hyjf.common.util.HjhUserAuthConfigVO ret, Integer id) {
        HjhUserAuthConfigVO authConfig = new HjhUserAuthConfigVO();
        if (Validator.isNull(ret)){
            AdminAuthConfigResponse response = authConfigService.getAuthConfigById(id);
            authConfig = response.getResult();
            // 根据授权配置id查询配置详情 redis缓存里面没有从 数据库中取
        }else{
            BeanUtils.copyProperties(ret, authConfig);
        }
        return authConfig;
    }


}
