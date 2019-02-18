/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.aems.register;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.AemsUserRegisterRequestBean;
import com.hyjf.cs.user.bean.AemsUserRegisterResultBean;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.aems.register.AemsUserRegisterService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.cs.user.vo.RegisterRequest;
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
import java.util.Objects;

/**
 * AEMS用户
 *
 * @author liuyang
 * @version AemsUserRegisterController, v0.1 2018/12/6 9:58
 */
@RestController
@Api(value = "api端AEMS系统用户注册", tags = "api端-AEMS系统用户注册")
@RequestMapping("/hyjf-api/aems/register")
public class AemsUserRegisterController extends BaseUserController {

    @Autowired
    private AemsUserRegisterService aemsUserRegisterService;

    @PostMapping("/register")
    @ApiOperation(value = "AEMS系统用户注册接口", httpMethod = "POST", notes = "AEMS系统用户注册接口")
    public AemsUserRegisterResultBean userRegister(@RequestBody AemsUserRegisterRequestBean aemsUserRegisterRequestBean,
                                                   HttpServletRequest request) {
        logger.info("AEMS注册接口, aemsUserRegisterRequestBean is :{}", JSONObject.toJSONString(aemsUserRegisterRequestBean));

        // 验签
        CheckUtil.check(SignUtil.AEMSVerifyRequestSign(aemsUserRegisterRequestBean,"/aems/register/register"), MsgEnum.STATUS_CE000002);

        AemsUserRegisterResultBean result = new AemsUserRegisterResultBean();
        RegisterRequest registerRequest = new RegisterRequest();
        //切换参数实体(调共用方法)
        BeanUtils.copyProperties(aemsUserRegisterRequestBean, registerRequest);
        registerRequest.setUtmId(aemsUserRegisterRequestBean.getChannel());
        registerRequest.setReffer(aemsUserRegisterRequestBean.getRecommended());
        // 验证参数
        aemsUserRegisterService.apiCheckParam(registerRequest);
        //获取ip
        String ip = GetCilentIP.getIpAddr(request);
        // 身份证号
        String idCard = aemsUserRegisterRequestBean.getIdCard();
        // 真实姓名
        String trueName = aemsUserRegisterRequestBean.getTrueName();
        //验证手机号是否已被注册
        UserVO user = aemsUserRegisterService.getUsersByMobile(registerRequest.getMobile());
        if (null != user) {
            logger.info("用户已存在，执行重复注册逻辑判断, user is : {}", user);
            //result.setStatus(MsgEnum.STATUS_ZC000005.getCode());
            result.setStatusForResponse(MsgEnum.STATUS_ZC000005.getCode());
            result.setStatusDesc(MsgEnum.STATUS_ZC000005.getMsg());
            result.setIsOpenAccount(String.valueOf(user.getBankOpenAccount()));
            if (user.getBankOpenAccount() != null && user.getBankOpenAccount() == 1) {
                UserInfoVO usersInfo = aemsUserRegisterService.getUserInfo(user.getUserId());
                if (StringUtils.isNotBlank(idCard) && StringUtils.isNotBlank(trueName) &&
                        Objects.equals(usersInfo.getTruename(), trueName) && Objects.equals(usersInfo.getIdcard(), idCard)) {
                    result.setStatus(AemsErrorCodeConstant.SUCCESS);
                    result.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
                    result.setStatusDesc("注册成功");
                    return result;
                } else if (StringUtils.isNotBlank(idCard) && StringUtils.isNotBlank(trueName)){
                    result.setStatusForResponse(AemsErrorCodeConstant.STATUS_ZC000026);
                    result.setStatusDesc("身份校验不通过");
                    return result;
                }
            } else if (user.getBankOpenAccount() != null) {
                result.setStatus(AemsErrorCodeConstant.SUCCESS);
                result.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
                return result;
            }
            if (user.getIsSetPassword() != null) {
                result.setIsSetPassword(String.valueOf(user.getIsSetPassword()));
            }
            result.setAutoInvesStatus(aemsUserRegisterService.getAutoInvesStatus(user.getUserId()));
            result.setUserId(user.getUserId());
            return result;
        }
        //调用注册方法
        user = aemsUserRegisterService.apiRegister(aemsUserRegisterRequestBean, registerRequest, ip);
        if (user != null) {
            logger.info("api端注册成功, userId is :{}", user.getUserId());
            //result.setStatus(ErrorCodeConstant.SUCCESS);
            result.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            result.setStatusDesc("注册成功");
            // 用户Id
            result.setUserId(user.getUserId());
            // 用户名
            result.setUserName(user.getUsername());
            result.setIsOpenAccount("0");
        } else {
            logger.error("api端注册失败...");
            result.setStatusForResponse(MsgEnum.STATUS_CE999999.getCode());
            result.setStatusDesc(MsgEnum.ERR_USER_REGISTER.getMsg());
        }
        return result;
    }
}
