package com.hyjf.cs.trade.controller.web.wirhdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.config.WithdrawRuleConfigRequest;
import com.hyjf.am.vo.config.WithdrawRuleConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.withdraw.BankWithdrawService;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.vo.BankWithdrawVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BankWithdrawController, v0.1 2018/6/12 18:32
 */
@Api(tags = "web端-用户提现接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/withdraw")
public class WebBankWithdrawController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(WebBankWithdrawController.class);
    @Autowired
    private BankWithdrawService bankWithdrawService;
    @Autowired
    private AuthService authService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * @Description 跳转到提现页面
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户银行提现", notes = "用户提现")
    @PostMapping("/toWithdraw")
    public WebResult<Object> toWithdraw(@RequestHeader(value = "userId") int userId) {
        WebViewUserVO user = bankWithdrawService.getUserFromCache(userId);
        CheckUtil.check(null != user, MsgEnum.ERR_OBJECT_GET, "用户信息");
        CheckUtil.check(user.isBankOpenAccount(), MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        WebResult<Object> objectWebResult = bankWithdrawService.toWithdraw(user);

        return objectWebResult;
    }

    /**
     * 用户银行提现校验
     *
     * @param userId
     * @param request
     * @return
     */
    @ApiOperation(value = "用户银行提现校验", notes = "用户银行提现校验")
    @PostMapping("/userBankWithdrawCheck")
    public WebResult<Object> userBankWithdrawCheck(@RequestHeader(value = "userId") Integer userId, @RequestBody WithdrawRuleConfigRequest withdrawRuleConfigRequest, HttpServletRequest request) {
        WebViewUserVO user = bankWithdrawService.getUserFromCache(userId);
        String withdrawMoney = withdrawRuleConfigRequest.getWithdrawMoney();
        logger.info("提现金额:[" + withdrawMoney + "]");
        CheckUtil.check(null != user, MsgEnum.ERR_OBJECT_GET, "用户信息");
        WebResult<Object> objectWebResult = bankWithdrawService.userBankWithdrawCheck(userId, withdrawMoney);
        return objectWebResult;
    }


    /**
     * 用户银行提现
     *
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 用户提现调用银行页面
     */
    @ApiOperation(value = "用户银行提现", notes = "用户提现")
    @PostMapping("/userBankWithdraw")
    @RequestLimit(seconds = 3)

    public WebResult<Object> userBankWithdraw(@RequestHeader(value = "userId") int userId,
                                              @RequestBody @Valid BankWithdrawVO bankWithdrawVO, HttpServletRequest request) {
        logger.info("web端提现接口, userId is :{}", JSONObject.toJSONString(userId));
        WebResult<Object> result = new WebResult<Object>();
        WebViewUserVO user = bankWithdrawService.getUserFromCache(userId);
        UserVO userVO = bankWithdrawService.getUserByUserId(user.getUserId());
        logger.info("user is :{}", JSONObject.toJSONString(user));
        if (!this.authService.checkPaymentAuthStatus(userId)) {
            throw new CheckException(MsgEnum.ERR_AUTH_USER_PAYMENT);
        }
        String ipAddr = CustomUtil.getIpAddr(request);
        logger.info("ipAddr is :{}", ipAddr);
        String retUrl = super.getFrontHost(systemConfig, String.valueOf(ClientConstants.WEB_CLIENT)) + "/user/withdrawError?token=1";

        // add by liuyang 20190422 节假日提现修改 start
        // 获取提现规则配置
        WithdrawRuleConfigVO withdrawRuleConfigVO =  bankWithdrawService.getWithdrawRuleConfig(userId, bankWithdrawVO.getWithdrawmoney());
        if (withdrawRuleConfigVO == null) {
            String statusDesc = "";
            // 个人用户
            if (user.getUserType() == 0 && new BigDecimal(bankWithdrawVO.getWithdrawmoney()).compareTo(new BigDecimal(systemConfig.getPersonalWithdrawLimit())) > 0) {
                // 提现金额> 个人最大提现金额
                logger.info("个人提现金额超限");
                statusDesc = "非工作时间提现,超过单笔最大提现金额" + new BigDecimal(systemConfig.getPersonalWithdrawLimit()).divide(new BigDecimal(10000)) + "万元";
            } else if (user.getUserType() == 1 && new BigDecimal(bankWithdrawVO.getWithdrawmoney()).compareTo(new BigDecimal(systemConfig.getCompanyWithdrawLimit())) > 0) {
                statusDesc = "非工作时间提现,超过单笔最大提现金额" + new BigDecimal(systemConfig.getCompanyWithdrawLimit()).divide(new BigDecimal(10000)) + "万元";
            } else {
                statusDesc = "提现金额超限，请参考提现温馨提示。";
            }
            throw new CheckException(statusDesc);
        }
        // add by liuyang 20190422 节假日提现修改 end
        //                 http://CS-TRADE/hyjf-web/withdraw/userBankWithdrawBgreturn
        String successfulUrl = super.getFrontHost(systemConfig, String.valueOf(ClientConstants.WEB_CLIENT)) + "/user/withdrawSuccess?token=1";
        String bgRetUrl = "http://CS-TRADE/hyjf-web/withdraw/userBankWithdrawBgreturn";
        String forgotPwdUrl = super.getForgotPwdUrl(CommonConstant.CLIENT_PC, request, systemConfig);
        BankCallBean bean = bankWithdrawService.getUserBankWithdrawView(userVO, bankWithdrawVO.getWithdrawmoney(),
                bankWithdrawVO.getWidCard(), bankWithdrawVO.getPayAllianceCode(), CommonConstant.CLIENT_PC, BankCallConstant.CHANNEL_PC, ipAddr, retUrl, bgRetUrl, successfulUrl, forgotPwdUrl,withdrawRuleConfigVO);
        if (null == bean) {
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }
        try {
            Map<String, Object> data = BankCallUtils.callApiMap(bean);
            result.setData(data);
        } catch (Exception e) {
            logger.info("web端提现失败");
            logger.error(e.getMessage());
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }
        return result;
    }

    /**
     * 用户银行提现异步回调
     *
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */

    @ApiOperation(value = "用户银行提现异步回调", notes = "用户提现")
    @PostMapping("/userBankWithdrawBgreturn")
    @ResponseBody
    public String userBankWithdrawBgreturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("[web用户银行提现异步回调开始]");
        logger.info("web端提现银行返回参数, bean is :{}", JSONObject.toJSONString(bean));
        BankCallResult result = new BankCallResult();
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(userId));
        params.put("ip", CustomUtil.getIpAddr(request));
        // 执行提现后处理
        this.bankWithdrawService.handlerAfterCash(bean, params);
        logger.info("成功");
        result.setStatus(true);
        logger.info("[web用户银行提现异步回调结束]");
        return JSONObject.toJSONString(result, true);
    }

    /**
     * @Description web端查询提现失败原因
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "查询提现失败原因", notes = "查询提现失败原因")
    @PostMapping("/seachFiledMess")
    @ResponseBody
    public WebResult<Object> seachUserBankWithdrawErrorMessgae(@RequestBody @Valid BankWithdrawVO bankWithdrawVO) {
        logger.info("查询提现失败原因start,logOrdId:{}", bankWithdrawVO.getLogOrdId());
        WebResult<Object> result = bankWithdrawService.seachUserBankWithdrawErrorMessgae(bankWithdrawVO.getLogOrdId());
        return result;
    }
}
