/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.user.AccountBankVO;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.withdraw.UserWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserWithdrawController, v0.1 2018/7/23 15:10
 */
@Api(value = "app端-获取我的银行卡",description = "app端-获取我的银行卡")
@RestController
@RequestMapping("/hyjf-app/bank/user/withdraw")
public class UserWithdrawController {

    @Autowired
    private UserWithdrawService userWithdrawService;

    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation(value = "app端-获取我的银行卡")
    @RequestMapping(value = "/getBankCardAction")
    public JSONObject getBankCardAction(HttpServletRequest request){
        JSONObject ret = new JSONObject();

        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // 唯一标识
        String sign = request.getParameter("sign");
        // token
        String token = request.getParameter("token");
        // order
        String order = request.getParameter("order");

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(token) || Validator.isNull(sign) || Validator.isNull(randomString)
                || Validator.isNull(order)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        try {
            ret.put("status", "0");
            ret.put("statusDesc", "成功");
            ret.put("request", UserWithdrawDefine.GET_BANKCARD_REQUEST);
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
                    bankCardBean.setLogo(systemConfig.webHost + bankConfig.getAppLogo());// 应前台要求，logo路径给绝对路径
                    bankCardBean.setBank(bankConfig.getName());// 银行名称 汉字
                    bankCardBean.setCardNo(bank.getAccount());
                    bankCardBean.setIsDefault(bank.getCardType());// 卡类型
                    // 0普通提现卡1默认卡2快捷支付卡
                    bankcards.add(bankCardBean);
                }
            } else {
                ret.put("cnt", "0");
            }
            ret.put("banks", bankcards);
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "获取我的银行卡发生错误");
        }
        return ret;
    }
}
