package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SmsNoticeConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.SmsNoticeConfigResponse;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;
import com.hyjf.am.vo.config.SmsNoticeConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xiehuili on 2018/8/14.
 */
@Api(tags = "消息中心-通知配置")
@RestController
@RequestMapping("/hyjf-admin/message/noticeconfig")
public class SmsNoticeConfigController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "noticeconfig";
    @Autowired
    private SmsNoticeConfigService smsNoticeConfigService;

    @ApiOperation(value = "查询通知配置列表", notes = "查询通知配置列表")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initSmsNoticeConfig() {
        SmsNoticeConfigResponse response=smsNoticeConfigService.initSmsNoticeConfig();
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<List<SmsNoticeConfigVO>>(response.getResultList()) ;
    }
    @ApiOperation(value = "查询通知配置详情", notes = "查询通知配置详情")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult  smsNoticeConfigInfo(@RequestBody SmsNoticeConfigRequest adminRequest) {
        SmsNoticeConfigResponse result= smsNoticeConfigService.smsNoticeConfigInfo(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<SmsNoticeConfigVO>(result.getResult()) ;
    }
    @ApiOperation(value = "添加通知配置详情", notes = "添加通知配置详情")
    @PostMapping("/addAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult  insertSmsNoticeConfig(@RequestBody SmsNoticeConfigRequest adminRequest) {
        SmsNoticeConfigResponse result= smsNoticeConfigService.insertSmsNoticeConfig(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<SmsNoticeConfigVO>(result.getResult()) ;
    }

    @ApiOperation(value = "修改通知配置详情", notes = "修改通知配置详情")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult  updateSmsNoticeConfig(@RequestBody SmsNoticeConfigRequest adminRequest) {
        SmsNoticeConfigResponse result= smsNoticeConfigService.updateSmsNoticeConfig(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<SmsNoticeConfigRequest>(adminRequest) ;
    }

    @ApiOperation(value = "关闭通知配置详情", notes = "关闭通知配置详情")
    @PostMapping("/closeAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult  closeSmsNoticeConfig(@RequestBody SmsNoticeConfigRequest adminRequest) {
        SmsNoticeConfigResponse result= smsNoticeConfigService.closeSmsNoticeConfig(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<List<SmsNoticeConfigVO>>(result.getResultList()) ;
    }

    @ApiOperation(value = "启用通知配置详情", notes = "启用通知配置详情")
    @PostMapping("/openAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult  openSmsNoticeConfig(@RequestBody SmsNoticeConfigRequest adminRequest) {
        SmsNoticeConfigResponse result= smsNoticeConfigService.openSmsNoticeConfig(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<List<SmsNoticeConfigVO>>(result.getResultList()) ;
    }

    @ApiOperation(value = "检验通知配置", notes = "检验通知配置")
    @PostMapping("/checkAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public String checkSmsNoticeConfig(HttpServletRequest request, @RequestBody SmsNoticeConfigRequest adminRequest) {
//        String name = request.getParameter("name");
//        String param = request.getParameter("param");
        JSONObject ret = new JSONObject();
        // 检查月数唯一性
//        if ("configName".equals(name)) {
        if (StringUtils.isNotBlank(adminRequest.getName())) {
            int cnt = smsNoticeConfigService.onlyName(adminRequest.getName());
            if (cnt > 0) {
                ret.put("info", "标识已存在，请勿重复输入");
            }
        }
        // 没有错误时,返回y
        if (!ret.containsKey("info")) {
            ret.put("status", "y");
        }
        return ret.toString();
    }
}
