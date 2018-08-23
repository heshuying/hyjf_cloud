/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.autologin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.NmcfLoginRequest;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.security.util.RSA_Hjs;
import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: sunpeikai
 * @version: ApiUserAutoLoginController, v0.1 2018/8/23 10:37
 */
@Api(value = "api端-第三方用户自动登录",tags = "api端-第三方用户自动登录")
@RestController
@RequestMapping(value = "/api/user")
public class ApiUserAutoLoginController extends BaseUserController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation(value = "获取登录参数",notes = "获取登录参数")
    @PostMapping(value = "/nmcfThirdLogin")
    public JSONObject nmcfThirdLogin(@RequestBody NmcfLoginRequest request){

        JSONObject result = new JSONObject();

        // 验证
        this.checkNmcfPostBean(request);
        // 验签
        String sign = request.getInstCode() + request.getUserId() + (request.getBorrowNid()==null?"":request.getBorrowNid()) + request.getTimestamp() + request.getInstCode();
        CheckUtil.check(ApiSignUtil.verifyByRSA(request.getInstCode(), request.getChkValue(), sign), MsgEnum.ERR_SIGN);
/*		//注释从这里开始
        // 解密
        String nmUserId = bean.getUserId();
        // 数字验证
        CheckUtil.check(Validator.isDigit(nmUserId), "Object.digit", "userId");
        //注释到这里
*/
        // userId 解密
        int nmUserId = this.userIdDecrypt(request);
        // 查询userid
        Integer userId = loginService.getUserIdByBind(nmUserId, Integer.valueOf(request.getInstCode()));

        // userid不存在,跳转登录页面
        if(userId == null) {
            result.put("status","1");
            result.put("statusDesc","userId不存在");
            result.put("reUrl",systemConfig.webHost + "/hyjf-web/user/login");
            return result;
            //return new ModelAndView("redirect:" + CustomConstants.HOST + LoginDefine.CONTROLLOR_REQUEST_MAPPING + LoginDefine.INIT + ".do");
        }

        // 登陆
        //TODO:这里还没做
        //WebViewUser webUser = loginService.getWebViewUserByUserId(userId);
        //WebUtils.sessionLogin(request, response, webUser);

        // 先跳转纳觅传过来的url
        if (request.getRetUrl() != null) {
            result.put("status","000");
            result.put("statusDesc","成功");
            result.put("reUrl",request.getRetUrl());
            return result;
            //return new ModelAndView("redirect:" + bean.getRetUrl());
        } else {
            // 如果纳觅没传url,有borrowNid,跳标的详情,无borowNid,跳个人中心
            if (request.getBorrowNid() == null) {
                result.put("status","000");
                result.put("statusDesc","成功");
                result.put("reUrl",systemConfig.webHost + "/hyjf-web/user/pandect");
                return result;
                //return new ModelAndView("redirect:" + CustomConstants.HOST + PandectDefine.REQUEST_MAPPING + PandectDefine.PANDECT_ACTION + ".do");
            } else {
                result.put("status","000");
                result.put("statusDesc","成功");
                // TODO:这里传参有问题
                result.put("reUrl",systemConfig.webHost + "/hyjf-web/projectlist/getBorrowDetail?borrowNid=" + request.getBorrowNid());
                return result;
                //return new ModelAndView("redirect:" + CustomConstants.HOST + BorrowDefine.REQUEST_MAPPING + BorrowDefine.BORROW_DETAIL_ACTION + ".do?borrowNid=" + bean.getBorrowNid());
            }
        }

    }

    /**
     * 请求参数校验
     * @auth sunpeikai
     * @param
     * @return
     */
    private void checkNmcfPostBean(NmcfLoginRequest bean) {
        //传入信息验证
        CheckUtil.check(Validator.isNotNull(bean.getTimestamp()), MsgEnum.ERR_OBJECT_REQUIRED, "时间戳");
        CheckUtil.check(Validator.isNotNull(bean.getInstCode()),  MsgEnum.ERR_OBJECT_REQUIRED, "机构编号");
        CheckUtil.check(Validator.isNotNull(bean.getUserId()),  MsgEnum.ERR_OBJECT_REQUIRED, "用户ID");
        CheckUtil.check(Validator.isNotNull(bean.getChkValue()),  MsgEnum.ERR_OBJECT_REQUIRED, "签名");
    }

    /**
     * 用户id解密
     * @auth sunpeikai
     * @param
     * @return
     */
    private int userIdDecrypt(NmcfLoginRequest bean){
        // RAC解密
        String str = decrypt(bean.getUserId());
        // 解密结果数字验证
        CheckUtil.check(Validator.isDigit(str), MsgEnum.ERR_OBJECT_DIGIT, "userId");
        // 返回
        return Integer.parseInt(str);
    }

    private String decrypt(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return RSA_Hjs.decrypt(str,"utf-8",systemConfig.getPublickeyhjs());
        } catch (Exception e) {
            CheckUtil.check(false, MsgEnum.ERR_OBJECT_DECRYPT,"用户ID");
        }
        return null;

    }
}
