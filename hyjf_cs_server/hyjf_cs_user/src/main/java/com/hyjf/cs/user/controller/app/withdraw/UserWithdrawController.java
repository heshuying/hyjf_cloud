/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.user.AccountBankVO;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.cs.user.bean.UserWithdrawResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.withdraw.UserWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserWithdrawController, v0.1 2018/7/23 15:10
 */
@Api(value = "app端-获取我的银行卡",tags = "app端-获取我的银行卡")
@RestController
@RequestMapping("/hyjf-app")
public class UserWithdrawController extends BaseUserController {

    @Autowired
    private UserWithdrawService userWithdrawService;

    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation(value = "获取我的银行卡")
    @PostMapping(value = "/bank/user/withdraw/getBankCardAction")
    public JSONObject getBankCardAction(HttpServletRequest request){
        JSONObject ret = checkAppBaseParam(request);
        if (null!= ret&&"1".equals(ret.get("status"))){
            return ret;
        }
        // 唯一标识
        String sign = request.getParameter("sign");
        try {
            ret.put("status", "0");
            ret.put("statusDesc", "成功");
            ret.put("request","/hyjf-app/bank/user/withdraw/getBankCardAction");
            // 取得用户iD
            Integer userId = SecretUtil.getUserId(sign);
            // 取得银行卡信息
            List<AccountBankVO> banks = userWithdrawService.getBankCardByUserId(userId);
            List<UserWithdrawResultBean> bankcards = new ArrayList<>();
            if (banks != null) {
                ret.put("cnt", banks.size() + "");
                for (AccountBankVO bank : banks) {
                    UserWithdrawResultBean bankCardBean = new UserWithdrawResultBean();
                    bankCardBean.setBank(bank.getBank());
                    BankConfigVO bankConfig = userWithdrawService.getBankInfo(bank.getBank());
                    // 应前台要求，logo路径给绝对路径
                    bankCardBean.setLogo(systemConfig.webHost + bankConfig.getAppLogo());
                    // 银行名称 汉字
                    bankCardBean.setBank(bankConfig.getName());
                    bankCardBean.setCardNo(bank.getAccount());
                    // 卡类型
                    bankCardBean.setIsDefault(bank.getCardType());
                    // 0普通提现卡1默认卡2快捷支付卡
                    bankcards.add(bankCardBean);
                }
            } else {
                ret.put("cnt", "0");
            }
            ret.put("banks", bankcards);
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "1");
            ret.put("statusDesc", "获取我的银行卡发生错误");
        }
        return ret;
    }


    /**
     * 获取提现信息
     * @param request
     * @param
     * @return
     */
    @PostMapping(value = "/user/withdraw/getInfoAction")
    public JSONObject getCashInfo(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        JSONObject ret = checkAppBaseParam(request);
        ret.put("request","/hyjf-app/user/withdraw/getInfoAction");
        if (null!= ret&&"1".equals(ret.get("status"))){
            return ret;
        }
        // 版本号
        String version = request.getParameter("version");
        // bankCode 银行编号
        String bankCode = request.getParameter("bankCode");
        // getcash 提现金额
        String getcash = request.getParameter("getcash");
        // 提现规则静态页面的url
        ret.put("url", systemConfig.getAppServerHost() + request.getContextPath() + "/hyjf-app/user/withdraw" + ClientConstants.GET_WITHDRAW_RULE_MAPPING);
        ret =  userWithdrawService.getCashInfo(userId,ret,version,bankCode,getcash);
        return ret;
    }

    /**
     *
     * 获取提现规则H5页面
     *
     * @author renxingchen
     * @return
     */
    @ApiOperation(value = "获取提现规则H5页面",notes = "获取提现规则H5页面")
    @PostMapping(value = "/user/withdraw/getRule")
    public ModelAndView rechargeRule() {
        return new ModelAndView(ClientConstants.WITHDRAW_RULE_PATH);
    }
}
