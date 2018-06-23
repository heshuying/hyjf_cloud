/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */

@Api(value = "会员管理接口")
@RestController
@RequestMapping("/usersManager")
public class UserCenterController {

    @Autowired
    private UserCenterService userCenterService;

    @ApiOperation(value = "会员管理", notes = "会员管理页面初始化")
    @RequestMapping(value = "/usersInit", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject = new JSONObject();
        // 用户角色
       /* Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
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
        Map<String, String> borrowTypes = CacheUtil.getParamNameMap("BORROWER_TYPE");*/
        // 资金来源
        List<HjhInstConfigVO> listHjhInstConfig = userCenterService.selectInstConfigAll();
        /*jsonObject.put("userRoles", userRoles);
        jsonObject.put("userPropertys", userPropertys);
        jsonObject.put("accountStatus", accountStatus);
        jsonObject.put("userStatus", userStatus);
        jsonObject.put("registPlat", registPlat);
        jsonObject.put("userTypes", userTypes);
        jsonObject.put("borrowTypes", borrowTypes);*/
        jsonObject.put("hjhInstConfigList", listHjhInstConfig);
        return jsonObject;

    }

    //会员管理列表查询
    @ApiOperation(value = "会员管理", notes = "会员管理列表查询")
    @RequestMapping(value = "/userslist", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getUserslist(@RequestBody @Valid UserManagerRequest request) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> mapUserData = userCenterService.selectUserMemberList(request);
        jsonObject.put("userInfo", mapUserData);
        return jsonObject;
    }

    //会员详情
    @ApiOperation(value = "会员管理", notes = "会员详情")
    @RequestMapping(value = "/getUserdetail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getUserdetail(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        String strUserId=map.get("userId");
        UserManagerDetailVO userManagerDetailVO = userCenterService.selectUserDetail(strUserId);
        jsonObject.put("userDetailInfo",userManagerDetailVO);
        //vip

        // 获取测评信息
        UserEvalationResultVO userEvalationResultInfo = userCenterService.getUserEvalationResult(strUserId);
        if (null != userEvalationResultInfo && null != userEvalationResultInfo.getCreateTime()) {
            //获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = GetDate.countDate(userEvalationResultInfo.getCreateTime(),1,1).getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = GetDate.countDate(new Date(), 5,1).getTime();
            if (lCreate <= lNow) {
                //已过期需要重新评测2已过期、1有效
                jsonObject.put("isEvalation", "2");
            } else {
                jsonObject.put("isEvalation", "1");
            }
        }
        jsonObject.put("userEvalationResult",userEvalationResultInfo);
        //用户开户信息
        UserBankOpenAccountVO userBankOpenAccountVO = userCenterService.selectBankOpenAccountByUserId(strUserId);
        jsonObject.put("userBankOpenAccount",userBankOpenAccountVO);
        //公司信息
        CorpOpenAccountRecordVO corpOpenAccountRecordVO = userCenterService.selectCorpOpenAccountRecordByUserId(strUserId);
        jsonObject.put("enterpriseInformation",corpOpenAccountRecordVO);
        //第三方平台绑定信息
        BindUserVo bindUserVo = userCenterService.selectBindeUserByUserI(strUserId);
        jsonObject.put("bindUsers",bindUserVo);
        //电子签章
        CertificateAuthorityVO certificateAuthorityVO = userCenterService.selectCertificateAuthorityByUserId(strUserId);
        jsonObject.put("certificateAuthority",certificateAuthorityVO);
        //文件服务器

        //
        return jsonObject;
    }
}
