/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.synbalance;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.SynBalanceRequestBean;
import com.hyjf.cs.user.bean.SynBalanceResultBean;
import com.hyjf.cs.user.bean.WxSynBalanceResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.synbalance.SynBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wangjun
 * @version WeChatSynBalanceController, v0.1 2018/7/31 9:19
 */
@Api(value = "weChat端-我的刷新按钮", tags = "weChat端-我的刷新按钮")
@RestController
@RequestMapping("/hyjf-wechat/wx/bank/user/synbalance")
public class WeChatSynBalanceController extends BaseUserController {
    @Autowired
    SynBalanceService synBalanceService;

    @Autowired
    SystemConfig systemConfig;


    @ApiOperation(value = "我的-刷新", notes = "我的-刷新")
    @PostMapping(value = "/init.do")
    public BaseResultBean synBalance(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        logger.info("请求用户ID：" + userId);
        WxSynBalanceResultBean result = new WxSynBalanceResultBean();
        // 获取登陆用户userId
        if (Validator.isNull(userId)) {
            result.setEnum(ResultEnum.ERROR_001);
            return result;
        }
        // 校验用户
        UserVO user = synBalanceService.getUsersById(userId);
        if (Validator.isNull(user)) {
            result.setEnum(ResultEnum.ERROR_004);
            return result;
        }
        // 校验用户是否开户
        if (user.getBankOpenAccount() != 1) {
            result.setEnum(ResultEnum.USER_ERROR_200);
            return result;
        }
        /***********同步线下充值记录 start***********/
        BankOpenAccountVO bankOpenAccountVO = synBalanceService.getBankOpenAccount(user.getUserId());
        String ip = GetCilentIP.getIpAddr(request);
        SynBalanceRequestBean bean = new SynBalanceRequestBean();
        bean.setInstCode(user.getInstCode());
        bean.setAccountId(bankOpenAccountVO.getAccount());
        SynBalanceResultBean resultBean = synBalanceService.synBalance(bean,ip);
        //校验获取余额是否成功
        if ("成功".equals(resultBean.getStatusDesc().toString())) {
            //余额数据
            DecimalFormat df = CustomConstants.DF_FOR_VIEW_V1;
            BigDecimal bankBalance = new BigDecimal(resultBean.getBankBalance().replaceAll(",", ""));
            BigDecimal bankTotal = new BigDecimal(resultBean.getBankTotal().replaceAll(",", ""));
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
