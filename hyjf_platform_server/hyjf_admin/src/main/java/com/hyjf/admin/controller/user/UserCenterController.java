/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */

@Api(value = "会员管理接口")
@RestController
@RequestMapping("/manager/users")
public class UserCenterController {

    @Autowired
    private UserCenterService userCenterService;

    @ApiOperation(value = "会员管理", notes = "会员管理页面初始化")
    @RequestMapping(value = "/usersInit", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject userManagerInit() {
        JSONObject jsonObject = null;
        // 用户角色
        Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
        // 用户属性
        Map<String, String> userPropertys = CacheUtil.getParamNameMap("USER_PROPERTY");
        // 开户状态
        Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
        // 用户状态
        Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        // 用户类型
        Map<String, String> userTypes = CacheUtil.getParamNameMap("USER_TYPE");
        // 借款人类型
        Map<String, String> borrowTypes = CacheUtil.getParamNameMap("BORROWER_TYPE");
        // 资金来源
        List<HjhInstConfigVO> listHjhInstConfig = userCenterService.selectHjhInstConfigList(null);
        jsonObject.put("userRoles", userRoles);
        jsonObject.put("userPropertys", userPropertys);
        jsonObject.put("accountStatus", accountStatus);
        jsonObject.put("userStatus", userStatus);
        jsonObject.put("registPlat", registPlat);
        jsonObject.put("userTypes", userTypes);
        jsonObject.put("borrowTypes", borrowTypes);
        jsonObject.put("hjhInstConfigList", listHjhInstConfig);
        return jsonObject;

    }

    //会员管理列表查询
    @ApiOperation(value = "会员管理", notes = "会员管理列表查询")
    @RequestMapping(value = "/userslist", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject getUserslist(@RequestBody @Valid UserManagerRequest request) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> mapUserData = userCenterService.selectUserMemberList(request);
        jsonObject.put("userInfo", mapUserData);
        return jsonObject;
    }


}
