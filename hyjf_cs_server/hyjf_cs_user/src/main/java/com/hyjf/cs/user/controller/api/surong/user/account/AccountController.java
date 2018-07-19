package com.hyjf.cs.user.controller.api.surong.user.account;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.MD5;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.recharge.RdfRechargeService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.account.RdfAccountService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(value = "融东风用户账户接口")
@Controller
@RequestMapping("/surong/account")
public class AccountController extends BaseUserController{
     
	@Autowired
	private RdfAccountService rdfAccountService;
    @Autowired
    private RdfRechargeService rdfRechargeService;
	@Autowired
	private SystemConfig systemConfig;

    @ApiOperation(value = "获取用户余额", notes = "获取用户余额")
    @RequestMapping("/getBalance")
    @ResponseBody
    public Object getBalance(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        String sign = request.getParameter("sign");
        if(!checkSign(mobile,sign)){
            return null;
        }
        String balance=rdfAccountService.getBalance(mobile);
        Map<String, String> result = new HashMap<>();
        result.put("balance", balance);
        UserVO user = rdfRechargeService.findUserByMobile(mobile);
        Integer isSetPassword = user.getIsSetPassword();
        result.put("isSetPassword", isSetPassword==null?"0":isSetPassword.toString());
        logger.info("mobile:"+mobile+" -balance:"+result.get("balance")+" -isSetPassword:"+result.get("isSetPassword"));
        return result;
    }


    @ApiOperation(value = "获取绑卡信息", notes = "获取绑卡信息")
    @RequestMapping("/getBankCard")
    @ResponseBody
    public Object getCard(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        String sign = request.getParameter("sign");
        if(!checkSign(mobile,sign)){
            return null;
        }
        BankCardVO bankCard = rdfAccountService.getBankCard(mobile);
        if(bankCard == null){
            return null;
        }
        Map<String, String> result = new HashMap<>();
        result.put("cardNo", bankCard.getCardNo());
        result.put("bankName", bankCard.getBank());
        return result;
    }

    @ApiOperation(value = "获取线下充值信息", notes = "获取线下充值信息")
    @RequestMapping("/getOfflineRechargeInfo")
    @ResponseBody
    public Object offLineRechageInfo(HttpServletRequest request, HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        String sign = request.getParameter("sign");
        if(!checkSign(mobile,sign)){
            return null;
        }
        Map<String, String> result = new HashMap<>();
        UserVO user = rdfRechargeService.findUserByMobile(mobile);
        // 根据用户Id查询用户卡户信息
        BankOpenAccountVO bankOpenAccount = this.rdfAccountService.getBankOpenAccount(user.getUserId());
        if (bankOpenAccount != null && StringUtils.isNotEmpty(bankOpenAccount.getAccount())) {
            result.put("account", bankOpenAccount.getAccount());
        }
        // 根据用户Id获取用户信息
        UserInfoVO usersInfo = this.rdfAccountService.getUserInfo(user.getUserId());
        if (usersInfo != null) {
            result.put("userName", usersInfo.getTruename());
        }
        return result;
    }



    private boolean checkSign(String mobile,String sign){
        String accessKey = systemConfig.getAopAccesskey();
        String miwen =  MD5.toMD5Code(accessKey + mobile + accessKey);
        if(!miwen.equals(sign)){
            return false;
        }
        return true;
    }

	
}
