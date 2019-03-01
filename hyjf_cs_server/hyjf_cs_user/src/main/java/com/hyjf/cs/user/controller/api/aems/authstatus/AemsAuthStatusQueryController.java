package com.hyjf.cs.user.controller.api.aems.authstatus;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.AemsAuthStatusQueryRequestBean;
import com.hyjf.cs.user.bean.AemsAuthStatusQueryResultBean;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.aems.auth.AemsAuthService;
import com.hyjf.cs.user.service.aems.authstatus.AemsAuthStatusQueryService;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权状态查询
 * @version AemsAuthStatusQueryController, v0.1 2018/12/6 10:06
 * @Author: Zha Daojian
 */

@Api(value = "api端-AEMS授权状态查询接口",tags = "api端-AEMS授权状态查询接口")
@RestController
@RequestMapping("/hyjf-api/aems/authState")
public class AemsAuthStatusQueryController extends BaseController {

    @Autowired
    private AemsAuthStatusQueryService autoPlusService;
    @Autowired
    private AemsAuthService aemsAuthService;

    /**
     *
     * 授权状态查询
     * @author sunss
     * @param autoStateQuery
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/query")
    @ApiParam(required = true, name = "findDetailById", value = "AEMS授权状态查询接口")
    @ApiOperation(value = "AEMS授权状态查询接口", httpMethod = "POST", notes = "AEMS授权状态查询接口")
    public AemsAuthStatusQueryResultBean sendCode(@RequestBody AemsAuthStatusQueryRequestBean autoStateQuery, HttpServletRequest request, HttpServletResponse response) {

        logger.info("授权状态查询第三方请求参数：" + JSONObject.toJSONString(autoStateQuery));

        AemsAuthStatusQueryResultBean resultBean = new AemsAuthStatusQueryResultBean();
        String channel = BankCallConstant.CHANNEL_PC;
        // 电子账户号
        String accountId = autoStateQuery.getAccountId();

        // 验证请求参数
        // 机构编号
        if (autoStateQuery.checkParmIsNull()) {
            logger.info("请求参数非法");
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("请求参数非法");
            return resultBean;
        }

        // 验签  accountId
        if (!SignUtil.AEMSVerifyRequestSign(autoStateQuery, "/aems/authState/query")) {
            logger.info("----验签失败----");
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000002);
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }
        // 用户ID
        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = this.autoPlusService.selectBankOpenAccountByAccountId(accountId);
        if (bankOpenAccount == null) {
            logger.info("查询用户开户信息失败,用户电子账户号:[" + accountId + "]");
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000004);
            resultBean.setStatusDesc("根据电子账户号查询用户信息失败");
            return resultBean;
        }
        Integer userId = bankOpenAccount.getUserId();
        UserVO user = this.autoPlusService.getUsersById(userId);
        if (user == null) {
            logger.info("查询用户失败:[" + userId + "].");
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000007);
            resultBean.setStatusDesc("查询用户失败");
            return resultBean;
        }
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag != 1) {// 未设置交易密码
            logger.info("-------------------未设置交易密码！"+autoStateQuery.getAccountId()+"！--------------------status"+user.getIsSetPassword());
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_TP000002);
            resultBean.setStatusDesc("未设置交易密码");
            return resultBean;
        }
        //直接查询汇盈授权状态
        // 查询授权数据hyjf_hjh_user_auth
        HjhUserAuthVO userAuth = aemsAuthService.getHjhUserAuthByUserId(userId);
        if(null == userAuth){
            logger.info("授权状态查询接口失败,userId:["+userId+"]授权数据不存在！");
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            resultBean.setStatusDesc("授权状态查询接口失败！");
            return resultBean;
        }
        resultBean = getResultJosn(resultBean,userAuth,accountId);
        logger.info("授权状态查询汇盈返回参数："+JSONObject.toJSONString(resultBean));
        resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc("授权状态查询成功");
        return resultBean;
    }

    // 拼接返回参数
    private AemsAuthStatusQueryResultBean getResultJosn(AemsAuthStatusQueryResultBean resultBean, HjhUserAuthVO retBean,String accountId) {
        resultBean.setAccountId(accountId);
        resultBean.setAgreeWithdrawStatus(String.valueOf(retBean.getAutoWithdrawStatus()));
        resultBean.setAutoBidDeadline(retBean.getAutoBidEndTime());
        resultBean.setAutoBidStatus(String.valueOf(retBean.getAutoInvesStatus()));
        resultBean.setAutoTransferStatus(String.valueOf(retBean.getAutoCreditStatus()));
        resultBean.setPaymentAuthStatus(String.valueOf(retBean.getAutoPaymentStatus()));
        resultBean.setPaymentDeadline(retBean.getAutoPaymentEndTime());
        resultBean.setRepayAuthStatus(String.valueOf(retBean.getAutoRepayStatus()));
        resultBean.setRepayDeadline(retBean.getAutoRepayEndTime());
        return resultBean;
    }
}
