/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.autologin;

import com.hyjf.am.resquest.user.NmcfLoginRequest;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.controller.BaseUserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "获取登录参数",notes = "获取登录参数")
    @PostMapping(value = "/nmcfThirdLogin")
    public void nmcfThirdLogin(@RequestBody NmcfLoginRequest request){

        // 验证
        this.checkNmcfPostBean(request);
        // 验签
/*        String sign = bean.getInstCode() + bean.getUserId() + (bean.getBorrowNid()==null?"":bean.getBorrowNid()) + bean.getTimestamp() + bean.getInstCode();
        CheckUtil.check(ApiSignUtil.verifyByRSA(bean.getInstCode(), bean.getChkValue(), sign), MsgEnum.ERR_SIGN);
		//注释从这里开始
        // 解密
        String nmUserId = bean.getUserId();
        // 数字验证
        CheckUtil.check(Validator.isDigit(nmUserId), "Object.digit", "userId");
        //注释到这里

        Long nmUserId = apiCommonService.RSAdecrypt(bean);
        // 查询userid
        Integer userId = apiCommonService.getUserIdByBind(nmUserId, Integer.valueOf(bean.getInstCode()));

        // userid不存在,跳转登录页面
        if(userId == null) {
            return new ModelAndView("redirect:" + CustomConstants.HOST
                    + LoginDefine.CONTROLLOR_REQUEST_MAPPING + LoginDefine.INIT + ".do");
        }

        // 登陆
        WebViewUser webUser = loginService.getWebViewUserByUserId(userId);
        WebUtils.sessionLogin(request, response, webUser);

        // 先跳转纳觅传过来的url
        if (bean.getRetUrl() != null) {
            return new ModelAndView("redirect:" + bean.getRetUrl());
        } else {
            // 如果纳觅没传url,有borrowNid,跳标的详情,无borowNid,跳个人中心
            if (bean.getBorrowNid() == null) {
                return new ModelAndView("redirect:" + CustomConstants.HOST
                        + PandectDefine.REQUEST_MAPPING + PandectDefine.PANDECT_ACTION + ".do");
            } else {
                return new ModelAndView("redirect:" + CustomConstants.HOST
                        + BorrowDefine.REQUEST_MAPPING + BorrowDefine.BORROW_DETAIL_ACTION + ".do?borrowNid=" + bean.getBorrowNid());
            }
        }*/
    }

    private void checkNmcfPostBean(NmcfLoginRequest bean) {
        //传入信息验证
        CheckUtil.check(Validator.isNotNull(bean.getTimestamp()), MsgEnum.ERR_OBJECT_REQUIRED, "时间戳");
        CheckUtil.check(Validator.isNotNull(bean.getInstCode()),  MsgEnum.ERR_OBJECT_REQUIRED, "机构编号");
        CheckUtil.check(Validator.isNotNull(bean.getUserId()),  MsgEnum.ERR_OBJECT_REQUIRED, "用户ID");
        CheckUtil.check(Validator.isNotNull(bean.getChkValue()),  MsgEnum.ERR_OBJECT_REQUIRED, "签名");
    }
}
