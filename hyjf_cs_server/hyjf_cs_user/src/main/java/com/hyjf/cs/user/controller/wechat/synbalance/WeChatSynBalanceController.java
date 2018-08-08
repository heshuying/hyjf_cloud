/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.synbalance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.WxSynBalanceResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.interceptor.SignValidate;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.synbalance.SynBalanceService;
import com.hyjf.cs.user.util.RequestUtil;
import com.hyjf.cs.user.util.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wangjun
 * @version WeChatSynBalanceController, v0.1 2018/7/31 9:19
 */
@Api(value = "wechat端我的-刷新按钮", tags = "wechat端我的-刷新按钮")
@RestController
@RequestMapping("/hyjf-wechat/wx/bank/user/synbalance")
public class WeChatSynBalanceController extends BaseUserController {
    @Autowired
    SynBalanceService synBalanceService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    RequestUtil requestUtil;

    @ApiOperation(value = "wechat端我的-刷新", notes = "wechat端我的-刷新")
    @SignValidate
    @PostMapping(value = "/init", produces = "application/json; charset=utf-8")
    public BaseResultBean synBalance(HttpServletRequest request, HttpServletResponse response) {
        WxSynBalanceResultBean result = new WxSynBalanceResultBean();
        // 获取登陆用户userId
        Integer userId = requestUtil.getRequestUserId(request);
        if (Validator.isNull(userId)) {
            result.setEnum(ResultEnum.ERROR_001);
            return result;
        }
        // 校验用户
        UserVO user = synBalanceService.getUsersById(userId);
        if (Validator.isNull(user)) {
            result.setEnum(ResultEnum.ERROR_001);
            return result;
        }
        // 校验用户是否开户
        if (user.getBankOpenAccount() != 1) {
            result.setEnum(ResultEnum.USER_ERROR_200);
            return result;
        }
        /***********同步线下充值记录 start***********/
        BankOpenAccountVO bankOpenAccountVO = synBalanceService.getBankOpenAccount(user.getUserId());
        JSONObject status = synBalanceService.synBalance(bankOpenAccountVO.getAccount(), systemConfig.getInstcode(), "http://CS-TRADE", systemConfig.getAopAccesskey());
        //校验获取余额是否成功
        if ("成功".equals(status.get("statusDesc").toString())) {
            //余额数据
            DecimalFormat df = CustomConstants.DF_FOR_VIEW_V1;
            BigDecimal bankBalance = new BigDecimal(status.get("bankBalance").toString().replaceAll(",", ""));
            BigDecimal bankTotal = new BigDecimal(status.get("bankTotal").toString().replaceAll(",", ""));
            result.setEnum(ResultEnum.SUCCESS4);
            result.setBankBalance(df.format(bankBalance));
            result.setBankTotal(df.format(bankTotal));
        } else {
            //异常
            result.setEnum(ResultEnum.FAIL);
        }
        /***********同步线下充值记录 end***********/
        return result;
    }
}
