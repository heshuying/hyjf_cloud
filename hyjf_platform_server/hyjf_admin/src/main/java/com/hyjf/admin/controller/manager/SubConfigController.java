package com.hyjf.admin.controller.manager;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SubConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import com.hyjf.am.vo.trade.SubCommissionListConfigVo;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/9.
 * @version SubConfigController, v0.1 2018/7/9.
 */
@Api(tags ="配置中心-分账名单配置")
@RestController
@RequestMapping("/hyjf-admin/config/subconfig")
public class SubConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "subconfig";

    @Autowired
    private SubConfigService subConfigService;
    @ApiOperation(value = "查询分账名单配置", notes = "查询分账名单配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<SubCommissionListConfigVo>> subConfigInit(HttpServletRequest request, @RequestBody AdminSubConfigRequest adminRequest) {
        AdminSubConfigResponse resList=subConfigService.selectSubConfigListByParam(adminRequest);
        if(resList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());

        }
        return new AdminResult<ListResult<SubCommissionListConfigVo>>(ListResult.build(resList.getResultList(), resList.getRecordTotal())) ;
    }
    @ApiOperation(value = "查询分账名单配置", notes = "查询分账名单配置")
    @PostMapping("/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<ListResult<SubCommissionListConfigVo>> subConfigSearch(HttpServletRequest request, @RequestBody AdminSubConfigRequest adminRequest) {
        AdminSubConfigResponse resList=subConfigService.selectSubConfigListByParam(adminRequest);
        if(resList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());

        }
        return new AdminResult<ListResult<SubCommissionListConfigVo>>(ListResult.build(resList.getResultList(), resList.getRecordTotal())) ;
    }
    @ApiOperation(value = "分账名单配置页面", notes = "分账名单配置页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult<SubCommissionListConfigVo>  subConfigInfo( @RequestBody AdminSubConfigRequest adminRequest) {
        AdminSubConfigResponse result= subConfigService.selectSubConfigInfo(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<SubCommissionListConfigVo>(result.getResult()) ;
    }
    @ApiOperation(value = "分账名单配置添加", notes = "分账名单配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertSubConfig(HttpServletRequest request, @RequestBody AdminSubConfigRequest adminRequest) {
         adminRequest.setUserId(Integer.valueOf(this.getUser(request).getId()));
//        adminRequest.setUserId(3);
        // 表单校验(双表校验)
        JSONObject json =new JSONObject();
        //表单字段校验
        json = this.validatorFieldCheck(json, adminRequest);
        if (json.size() > 0) {
            return new AdminResult<>(FAIL, json.toString());
        }
        AdminSubConfigResponse result= subConfigService.insertSubConfig(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "分账名单配置修改", notes = "分账名单配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateSubConfig(HttpServletRequest request, @RequestBody AdminSubConfigRequest adminRequest) {
        adminRequest.setUserId(Integer.valueOf(this.getUser(request).getId()));
//        adminRequest.setUserId(3);
        AdminSubConfigResponse result= subConfigService.updateSubConfig(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "分账名单配置删除", notes = "分账名单配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteSubConfig(HttpServletRequest request, @RequestBody AdminSubConfigRequest adminRequest) {
        adminRequest.setUserId(Integer.valueOf(this.getUser(request).getId()));
        AdminSubConfigResponse result=null;
        if(adminRequest.getId() != null){
            result= subConfigService.deleteSubConfig(adminRequest);
        }
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "用户名信息", notes = "用户名信息")
    @PostMapping("/updateSelectAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public Map<String, Object>  updateSelectAction(HttpServletRequest request,@RequestBody AdminSubConfigRequest adminRequest) {
        Map<String, Object> userMapNullMap=new HashMap();
        UserInfoCustomizeResponse userMap=subConfigService.userMap(adminRequest);
        if (userMap!=null&&Response.isSuccess(userMap)) {
            UserInfoCustomizeVO user =userMap.getResult();
            if (user == null) {
                userMapNullMap.put("info", "您输入的用户名无对应信息，请重新输入");
                userMapNullMap.put("status", "n");
                return userMapNullMap;
            }else {
                if (StringUtils.isNotBlank(user.getOpen())) {
                    if ("未开户".equals((String)user.getOpen().toString().trim())) {
                        userMapNullMap.put("info", "无法获取用户信息");
                        userMapNullMap.put("status", "n");
                        return userMapNullMap;
                    }
                }
                if (user.getStatus() != null) {
                    if (user.getStatus().intValue() ==1) {
                        userMapNullMap.put("info", "该用户已被禁用");
                        userMapNullMap.put("status", "n");
                        return userMapNullMap;
                    }
                }
            }
            userMapNullMap.put("status", "y");
            return userMapNullMap;
        }else {
            userMapNullMap.put("info", "异常！");
            userMapNullMap.put("status", "n");
            return userMapNullMap;
        }
    }

    @ApiOperation(value = "用户名信息", notes = "用户名信息")
    @PostMapping("/selectAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult  selectAction(HttpServletRequest request,@RequestBody AdminSubConfigRequest adminRequest) {
        Map<String, Object> userMapNullMap=new HashMap();
        UserInfoCustomizeResponse userMap=subConfigService.userMap(adminRequest);
        AdminSubConfigResponse res=subConfigService.subconfig(adminRequest);
        if (userMap!=null&&Response.isSuccess(userMap)) {
            UserInfoCustomizeVO user =userMap.getResult();
            if(user != null){
                if (Response.isSuccess(res)&&!CollectionUtils.isEmpty(res.getResultList())) {
                    userMapNullMap.put("info", "该用户白名单信息已经存在");
                    userMapNullMap.put("status", "n");
                    return new AdminResult<Map>(userMapNullMap);
                }else {
                    if (StringUtils.isNotBlank(user.getOpen())) {
                        if ("未开户".equals((String)user.getOpen().toString().trim())) {
                            userMapNullMap.put("info", "无法获取用户信息");
                            userMapNullMap.put("status", "n");
                            return new AdminResult<Map>(userMapNullMap);
                        }
                    }
                    if (user.getStatus() != null) {
                        if (user.getStatus().intValue() ==1) {
                            userMapNullMap.put("info", "该用户已被禁用");
                            userMapNullMap.put("status", "n");
                            return new AdminResult<Map>(userMapNullMap);
                        }
                    }
                    userMapNullMap.put("status", "y");
                    userMapNullMap.put("user",userMap);
                    return new AdminResult<Map>(userMapNullMap);
                }
            }else{
                userMapNullMap.put("info", "您输入的用户名无对应信息，请重新输入");
                userMapNullMap.put("status", "n");
                return new AdminResult<Map>(userMapNullMap);
            }
        }else {
            userMapNullMap.put("info", "异常！");
            userMapNullMap.put("status", "n");
            return new AdminResult<Map>(userMapNullMap);
        }
    }


    /**
     * 画面校验
     *
     * @param jsonObject
     * @param form
     */
    private JSONObject validatorFieldCheck(JSONObject jsonObject, AdminSubConfigRequest form) {
        // 用户名
        if(StringUtils.isBlank(form.getUsername())){
            jsonObject.put("用户名","用户名不能为空！");
            return jsonObject;
        }
        //姓名
        if(StringUtils.isBlank(form.getTruename())){
            jsonObject.put("姓名","姓名不能为空！");
            return jsonObject;
        }
        //用户角色
        if(StringUtils.isBlank(form.getRoleName())){
            jsonObject.put("用户角色","用户角色不能为空！");
            return jsonObject;
        }
        //用户类型
        if(StringUtils.isBlank(form.getUserType())){
            jsonObject.put("用户类型","用户类型不能为空");
            return jsonObject;
        }
        //银行开户状态
        if(StringUtils.isBlank(form.getBankOpenAccount())){
            jsonObject.put("银行开户状态","银行开户状态不能为空");
            return jsonObject;
        }
        //江西银行电子账号
        if(StringUtils.isBlank(form.getAccount())){
            jsonObject.put("江西银行电子账号","江西银行电子账号不能为空");
            return jsonObject;
        }
        // 用户状态
        if(StringUtils.isBlank(String.valueOf(form.getStatus()))){
            jsonObject.put("用户状态","用户状态不能为空");
            return jsonObject;
        }
        return jsonObject;
    }

}
