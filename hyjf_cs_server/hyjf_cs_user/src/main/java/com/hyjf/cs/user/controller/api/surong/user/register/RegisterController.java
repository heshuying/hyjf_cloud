package com.hyjf.cs.user.controller.api.surong.user.register;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.register.RegisterService;
import com.hyjf.cs.user.vo.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author pangchengchao
 * @version RegisterController, v0.1 2018/7/17 14:51
 */
@Api(value = "融东风注册接口")
@Controller
@RequestMapping("/hyjf-api/surong/register")
public class RegisterController extends BaseUserController {

    @Autowired
    RegisterService registerService;

    @ApiOperation(value = "用户注册接口", notes = "融东风用户注册第三方")
    @PostMapping("/registerAction")
    public JSONObject synBalance(HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        // 手机号
        String mobile = request.getParameter("mobile");
        // 登录密码
        String password = request.getParameter("password");
        // 检查参数正确性
        if (Validator.isNull(mobile) || Validator.isNull(password)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 业务逻辑
        try {
            if (Validator.isNull(mobile)) {
                ret.put("status", "1");
                ret.put("statusDesc", "手机号不能为空");
                return ret;
            }
            if (Validator.isNull(password)) {
                ret.put("status", "1");
                ret.put("statusDesc", "密码不能为空");
                return ret;
            }
            if (!Validator.isMobile(mobile)) {
                ret.put("status", "1");
                ret.put("statusDesc", "请输入您的真实手机号码");
                return ret;
            }
            if (password.length() < 6 || password.length() > 16) {
                ret.put("status", "1");
                ret.put("statusDesc", "密码长度6-16位");
                return ret;
            }
            if (Validator.isNumber(password.substring(0, 1))) {
                ret.put("status", "1");
                ret.put("statusDesc", "密码首位必须为字母");
                return ret;
            }
            boolean hasNumber = false;

            for (int i = 0; i < password.length(); i++) {
                if (Validator.isNumber(password.substring(i, i + 1))) {
                    hasNumber = true;
                    break;
                }
            }
            if (!hasNumber) {
                ret.put("status", "1");
                ret.put("statusDesc", "密码必须包含数字");
                return ret;
            }
            if (!Validator.isMobile(mobile)) {
                ret.put("status", "1");
                ret.put("statusDesc", "请输入您的真实手机号码");
                return ret;
            }
            {
                UserVO regUser = registerService.getUsersByMobile(mobile);
                if (regUser != null) {
                    String statusDesc = "注册成功";
                    ret.put("status", "0");
                    ret.put("hyjfName", regUser.getUsername());
                    ret.put("userId", regUser.getUserId());
                    ret.put("statusDesc", statusDesc);
                    ret.put("chinapnrUsrid", "");
                    ret.put("bankcard", "");
                    ret.put("bank", "");
                    ret.put("username", "");
                    ret.put("idcard", "");

                    //查询开户状态
                    BankOpenAccountVO bankOpenAccount = registerService.getBankOpenAccount(regUser.getUserId());
                    if(bankOpenAccount!=null){
                        ret.put("chinapnrUsrid", bankOpenAccount.getAccount());
                    }
                    //查询快捷卡
                    List<BankCardVO> ablist = registerService.getBankOpenAccountById(regUser);
                    if(ablist!=null&&ablist.size()>0){
                        ret.put("bankcard", ablist.get(0).getCardNo());
                        ret.put("bank", ablist.get(0).getBank());
                    }
                    //查询客户信息
                    UserInfoVO userInfoVO = registerService.getUserInfo(regUser.getUserId());
                    if(userInfoVO!=null){
                        ret.put("username", userInfoVO.getTruename());
                        ret.put("idcard", userInfoVO.getIdcard());
                    }
                    //不能修改用户角色信息
                    //registService.updateUser(regUser.getUserId());
                    return ret;
                }
            }
            RegisterRequest register = new RegisterRequest();
            register.setMobile(mobile);
            register.setPassword(password);
            // 注册
            UserVO user = registerService.surongRegister(register,CustomUtil.getIpAddr(request), request.getParameter("platform"));

            if (user != null) {
                String statusDesc = "注册成功";
                ret.put("status", "0");
                ret.put("hyjfName", user.getUsername());
                ret.put("userId",user.getUserId());
                ret.put("chinapnrUsrid", "");
                ret.put("bankcard", "");
                ret.put("bank", "");
                ret.put("username", "");
                ret.put("idcard", "");
                ret.put("statusDesc", statusDesc);
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "注册失败,参数异常");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "注册发生错误,参数异常");
        }
        return ret;
    }
}
