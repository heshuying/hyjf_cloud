package com.hyjf.cs.trade.controller.app.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.WithdrawRuleConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.withdraw.BankWithdrawService;
import com.hyjf.cs.trade.vo.AppWithdrawResultVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pangchengchao
 * @version BankWithdrawController, v0.1 2018/6/12 18:32
 */
@Api(value = "app端-用户提现接口",tags = "app端-用户提现接口")
@Controller
@RequestMapping("/hyjf-app/bank/user/withdraw")
public class AppBankWithdrawController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(AppBankWithdrawController.class);
    @Autowired
    private BankWithdrawService bankWithdrawService;
    @Autowired
    private AuthService authService;
    @Autowired
    SystemConfig systemConfig;
    /**
     * 提现规格URL
     */
    private final String WITHDRAW_RULE_URL = "/user/withdraw/withdrawRule";

    /**
     * logo的路径
     */
    private final String BANK_LOG_LOGO_PATH = "/data/upfiles/filetemp/image/bank_log.png";
    /**
     * 第三方提供的开户行查询url
     */
    private final String THIRD_QUERY_OPEN_BANK_URL = "http://www.tui78.com/bank";
    /**
     * 获取提现信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/getInfoAction")
    @ApiOperation(value = "获取用户提现信息", notes = "获取用户充值信息")
    public AppWithdrawResultVO getCashInfo(@RequestHeader(value = "userId") Integer userId,HttpServletRequest request) {

        AppWithdrawResultVO result = new AppWithdrawResultVO(AppWithdrawResultVO.APP_SUCCESS, AppWithdrawResultVO.SUCCESS_MSG, "/hyjf-app/bank/user/withdraw/getInfoAction");
        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        String token = request.getParameter("token");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // 唯一标识
        String sign = request.getParameter("sign");
        String order = request.getParameter("order");
        // 提现金额
        String getcash = request.getParameter("getcash");
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform)
                || Validator.isNull(token) || Validator.isNull(sign) || Validator.isNull(randomString)
                || Validator.isNull(order)) {
            result.setStatus(CustomConstants.APP_STATUS_FAIL);
            result.setStatusDesc("请求参数非法");
            return result;
        }

        //判断金额格式
        if(Validator.isNotNull(getcash)){
            Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
            Matcher match=pattern.matcher(getcash);
            if(!match.matches()){
                result.setStatus(CustomConstants.APP_STATUS_FAIL);
                result.setStatusDesc("提现金额输入格式有误");
                return result;
            }
        }
        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            result.setStatus(CustomConstants.APP_STATUS_FAIL);
            result.setStatusDesc("请求参数非法");
            return result;
        }

        // 验证Order
        if (!SecretUtil.checkOrder(key, token, randomString, order)) {
            result.setStatus(CustomConstants.APP_STATUS_FAIL);
            result.setStatusDesc("请求参数非法");
            return result;
        }

        // 提现规则静态页面的url

        result.setUrl(super.getFrontHost(systemConfig,platform) + WITHDRAW_RULE_URL);

        // 取得用户iD
        WebViewUserVO user=bankWithdrawService.getUserFromCache(userId);

        // 获取用户信息

        // 服务费授权状态
        if (!authService.checkPaymentAuthStatus(userId)) {
            result.setStatus("1");
            result.setStatusDesc("请先进行服务费授权。");
            return result;
        }
        // 取得用户当前余额
        AccountVO account = this.bankWithdrawService.getAccountByUserId(userId);


        if (account == null) {
            result.setStatus(CustomConstants.APP_STATUS_FAIL);
            result.setStatusDesc("您的账户信息存在异常，请联系客服人员处理。");
            return result;
        }

        // add by liuyang 20190422 节假日提现修改 start
        // 获取提现规则配置
        logger.info("提现金额:[" + getcash + "].");
        String withdrawMoney = "0";
        if (!StringUtils.isBlank(getcash)) {
            withdrawMoney = getcash;
        }
        // 投标金额大于可用余额
        if (new BigDecimal(withdrawMoney).compareTo(account.getBankBalance()) > 0) {
            result.setStatus(CustomConstants.APP_STATUS_FAIL);
            // app4.0 修改提示文案
            result.setStatusDesc("提现金额不能大于可用金额");
            return result;
        }
        WithdrawRuleConfigVO withdrawRuleConfigVO = this.bankWithdrawService.getWithdrawRuleConfig(userId, withdrawMoney);
        if (withdrawRuleConfigVO == null){
            result.setStatus(CustomConstants.APP_STATUS_FAIL);
            String statusDesc = "提现金额超限，请参考提现温馨提示。";
//            // 个人用户
//            if (user.getUserType() == 0 && new BigDecimal(withdrawMoney).compareTo(new BigDecimal(systemConfig.getPersonalWithdrawLimit())) > 0) {
//                // 提现金额> 个人最大提现金额
//                logger.info("个人提现金额超限");
//                statusDesc = "非工作时间提现,超过单笔最大提现金额" + new BigDecimal(systemConfig.getPersonalWithdrawLimit()).divide(new BigDecimal(10000)) + "万元";
//            } else if (user.getUserType() == 1 && new BigDecimal(withdrawMoney).compareTo(new BigDecimal(systemConfig.getCompanyWithdrawLimit())) > 0) {
//                statusDesc = "非工作时间提现,超过单笔最大提现金额" + new BigDecimal(systemConfig.getCompanyWithdrawLimit()).divide(new BigDecimal(10000)) + "万元";
//            } else {
//                statusDesc = "提现金额超限，请参考提现温馨提示。";
//            }

            result.setStatusDesc(statusDesc);
            return result;
        }
        // add by liuyang 20190422 节假日提现修改 end
        result.setTotal(CommonUtils.formatAmount(version, account.getBankBalance()));
        logger.info("提现可用余额："+result.getTotal());
        String phoneNum = "";
        // 取得银行卡信息
        BankCardVO bankCard = bankWithdrawService.getBankCardVOByUserId(userId);
        if (bankCard!=null) {
            // 发卡行的名称
            result.setBank(bankCard.getBank()==null?"":bankCard.getBank());
            // 卡号
            String cardNo = bankCard.getCardNo();
            result.setCardNo(cardNo);
            result.setCardNo_info(BankCardUtil.getFormatCardNo(cardNo));
            JxBankConfigVO banksConfig = bankWithdrawService.getBanksConfigByBankId(bankCard.getBankId());
            if (banksConfig != null && StringUtils.isNotEmpty(banksConfig.getBankIcon())) {
                result.setLogo(systemConfig.getAppFrontHost() + banksConfig.getBankIcon());
            } else {
                // 应前台要求，logo路径给绝对路径
                result.setLogo(systemConfig.getAppFrontHost()  + BANK_LOG_LOGO_PATH);
            }

            BigDecimal feeWithdraw = BigDecimal.ONE;

            String feeTemp = bankWithdrawService.getWithdrawFee(userId, cardNo);
            if (StringUtils.isNotEmpty(feeTemp)) {
                feeWithdraw = new BigDecimal(feeTemp);
            }

            logger.info("提现金额 getcash: {}", getcash);
            //判断金额是否合法优化 add by cwyang 2018-4-2
            boolean legalAmount = isLegalAmount(getcash);
            // 如果提现金额是0
            if ("0".equals(getcash) || StringUtils.isEmpty(getcash) || !CommonUtils.isNumber(getcash) || !legalAmount) {
                result.setButtonWord("提现");
                // 提现手续费
                result.setFee("0.00 元");
                // 提现金额
                result.setBalance("0.00 元");
            } else {
                result.setButtonWord("确认提现".concat(CommonUtils.formatAmount(version, getcash)).concat("元"));

                String balance = "";
                if ((new BigDecimal(getcash).subtract(feeWithdraw)).compareTo(BigDecimal.ZERO) < 0) {
                    balance = "0.00";
                } else {
                    // 扣除手续费后的提现金额
                    BigDecimal transAmt = new BigDecimal(getcash).subtract(feeWithdraw);
                    balance = CommonUtils.formatAmount(version, transAmt);

                    // 大额支付需要传开户行号
                    if (withdrawRuleConfigVO.getPayAllianceCode() == 1) {
                        // 是否是大额提现表示 0:非 1:是
                        result.setIsDisplay("1");
                        // 开户行号
                        String payAllianceCode = bankCard.getPayAllianceCode();
                        logger.info("查询开户行号：{}", payAllianceCode);
                        result.setOpenCardBankCode(payAllianceCode);
                        // 未查到到开户行号，则提供第三方网页引导用户自助查询
                        result.setOpenCardBankCodeUrl(THIRD_QUERY_OPEN_BANK_URL);
                    } else {
                        result.setIsDisplay("0");
                    }
                }
                // 提现手续费
                result.setFee(CommonUtils.formatAmount(version, feeWithdraw) + " 元");
                // 提现金额
                result.setBalance(balance + " 元");
            }

            phoneNum = bankCard.getMobile();

        }else{
            result.setStatus("1");
            result.setStatusDesc("您未绑定银行卡，请先绑卡。");
            return result;
        }

        if (StringUtils.isBlank(phoneNum)) {
            // 如果用户未绑卡则取平台手机号
            phoneNum = user.getMobile();
        }
        result.setPhoneNumber(phoneNum);
        result.setNeedSMSCode("0");
        return result;

    }

    /**
     * 判断金额是否合法
     *
     * @param getcash
     * @return
     */
    private boolean isLegalAmount(String getcash) {

        try {
            BigDecimal amount = new BigDecimal(getcash);
        } catch (Exception e) {
            logger.info("--------提现金额错误---------");
            return false;
        }

        return true;
    }


    /**
     * 获取提现URL -- 提现前参数校验！
     *
     * @param request
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "APP端-获取提现URL", notes = "获取提现URL")
    @PostMapping("/getCashUrl")
    public JSONObject getCashUrl(@RequestHeader(value = "userId") Integer userId,HttpServletRequest request) {
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
        // 获取token
        String token = request.getParameter("token");
        // order
        String order = request.getParameter("order");
        // card 银行卡号
        String cardNo = request.getParameter("cardNo");
        // getcash 提现金额
        String total = request.getParameter("total");
        // 银联行号
        String openCardBankCode = request.getParameter("openCardBankCode");
        String requestStr="/hyjf-app/bank/user/withdraw/getCashUrl";
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(token) || Validator.isNull(sign) || Validator.isNull(randomString)
                || Validator.isNull(order) || Validator.isNull(cardNo) || Validator.isNull(total) || !CommonUtils.isNumber(total)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            ret.put("request", requestStr);
            return ret;
        }
        if (new BigDecimal(total).compareTo(BigDecimal.ONE) <= 0) {
            ret.put("status", "1");
            ret.put("statusDesc", "提现金额需大于1元！");
            ret.put("request", requestStr);
            return ret;
        }

        String transAmt = request.getParameter("total");// 交易金额
        // 取得用户当前余额
        AccountVO account = this.bankWithdrawService.getAccountByUserId(userId);
        // 投标金额大于可用余额
        if (account == null || new BigDecimal(transAmt).compareTo(account.getBankBalance()) > 0) {
            ret.put("status", "1");
            ret.put("statusDesc", "提现金额大于可用金额，请确认后再次提现。");
            ret.put("request", requestStr);
            return ret;
        }
        // 取得用户iD
        WebViewUserVO user = bankWithdrawService.getUserFromCache(userId);
        // add by liuyang 20190422 节假日提现修改 start
        // 获取提现规则配置
        WithdrawRuleConfigVO withdrawRuleConfigVO = this.bankWithdrawService.getWithdrawRuleConfig(userId, total);
        if (withdrawRuleConfigVO == null){
            ret.put("status", CustomConstants.APP_STATUS_FAIL);
            String statusDesc = "提现金额超限，请参考提现温馨提示。";
//            // 个人用户
//            if (user.getUserType() == 0 &&
//                    new BigDecimal(total).compareTo(new BigDecimal(systemConfig.getPersonalWithdrawLimit())) > 0) {
//                // 提现金额> 个人最大提现金额
//                logger.info("个人提现金额超限");
//                statusDesc = "非工作时间提现,超过单笔最大提现金额" + new BigDecimal(systemConfig.getPersonalWithdrawLimit()).divide(new BigDecimal(10000)) + "万元";
//            } else if (user.getUserType() == 1 && new BigDecimal(total).compareTo(new BigDecimal(systemConfig.getCompanyWithdrawLimit())) > 0) {
//                statusDesc = "非工作时间提现,超过单笔最大提现金额" + new BigDecimal(systemConfig.getCompanyWithdrawLimit()).divide(new BigDecimal(10000)) + "万元";
//            } else {
//                statusDesc = "提现金额超限，请参考提现温馨提示。";
//            }
            ret.put("statusDesc",statusDesc);
            ret.put("request", requestStr);
            return ret;
        }
        // add by liuyang 20190422 节假日提现修改 end
        //可用金额
        BigDecimal total2 = account.getBankBalance();
        //可提现金额
        BigDecimal availableCash = null;
        // 获取用户角色
        UserInfoVO usersInfo = bankWithdrawService.getUserInfoByUserId(userId);
        if (usersInfo != null && usersInfo.getRoleId() == 1) {
            int tenderRecord = bankWithdrawService.getTenderRecord(userId);
            if (tenderRecord <= 0) {
                List<AccountRechargeVO> accountRecharges = bankWithdrawService.getRechargeMoney(userId);
                // 当天充值，提现金额为当前余额减去当日充值金额
                if (!CollectionUtils.isEmpty(accountRecharges)) {
                    for (AccountRechargeVO accountRecharge : accountRecharges) {
                        total2 = total2.subtract(accountRecharge.getBalance());
                        availableCash = total2;
                    }
                    if (StringUtils.isNotBlank(transAmt) && new BigDecimal(transAmt).compareTo(availableCash) > 0) {
                        ret.put("status", "1");
                        ret.put("statusDesc", "当天充值资金当天无法提现，请调整取现金额。");
                        ret.put("request", requestStr);
                        return ret;
                    }
                }
            }
        }

        logger.info("开户行号openCardBankCode is :{}", openCardBankCode);

        // 人行通道 提现校验
        // modify by liuyang 20190422 节假日提现修改 start
        String routeCode = "";
        if (withdrawRuleConfigVO.getPayAllianceCode() == 1) {
            // 路由代码
            routeCode = withdrawRuleConfigVO.getRouteCode();
        }
        // 如果提现规则配置需要联行号时,判断前端传过来的联行号是否为空
        if (withdrawRuleConfigVO.getPayAllianceCode() == 1 && StringUtils.isBlank(openCardBankCode)){
            ret.put("status", "1");
            ret.put("statusDesc", "大额提现时,开户行号不能为空");
            ret.put("request", requestStr);
            return ret;
        }
        // modify by liuyang 20190422  节假日提现修改 end

        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            ret.put("request", requestStr);
            return ret;
        }
        // 验证Order
        if (!SecretUtil.checkOrder(key, token, randomString, order)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            ret.put("request", requestStr);
            return ret;
        }

        try {
            /** 提现接口 */
            String withdrawUrl = super.getFrontHost(systemConfig,platform) +"/public/formsubmit?requestType=" +CommonConstant.APP_BANK_REQUEST_TYPE_WITHDRAW;
            ret.put("status", "0");
            ret.put("statusDesc", "成功");
            ret.put("request", requestStr);
            StringBuffer sbUrl = new StringBuffer();
            sbUrl.append(withdrawUrl);
            sbUrl.append("&").append("cardNo").append("=").append(cardNo);
            sbUrl.append("&").append("total").append("=").append(total);
            sbUrl.append("&").append("routeCode").append("=").append(routeCode);
            sbUrl.append("&").append("openCardBankCode").append("=").append(openCardBankCode);
            logger.info("返回提现url为: {}", sbUrl.toString());
            ret.put("url", sbUrl.toString());
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "获取提现URL失败");
        }
        return ret;
    }

    /**
     * 用户银行提现
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date  用户提现调用银行页面
     */
    @ResponseBody
    @ApiOperation(value = "用户银行提现", notes = "用户提现")
    @PostMapping("/userBankWithdraw")
    public AppResult<Object> userBankWithdraw(@RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "sign") String sign,HttpServletRequest request) {
        logger.info("app端提现接口, userId is :{}", userId);
        AppResult<Object> result = new AppResult<Object>();
        String transAmt = request.getParameter("total");// 交易金额
        String cardNo = request.getParameter("cardNo");// 提现银行卡号
        String payAllianceCode = request.getParameter("openCardBankCode");// 银联行号
        // 平台
        String platform = request.getParameter("platform");
        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
        UserVO userVO=bankWithdrawService.getUserByUserId(user.getUserId());
        if(null==userVO){
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }

        if(0==userVO.getIsSetPassword()){
            throw new ReturnMessageException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }
        if(userVO.getBankOpenAccount()==0){
            throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }

        if (!this.authService.checkPaymentAuthStatus(userId)) {
            throw new ReturnMessageException(MsgEnum.ERR_AUTH_USER_PAYMENT);
        }
        // add by liuyang 20190422 节假日提现修改 start
        // 获取提现规则配置
        WithdrawRuleConfigVO withdrawRuleConfigVO = this.bankWithdrawService.getWithdrawRuleConfig(userId, transAmt);
        if (withdrawRuleConfigVO == null){ ;
            throw new ReturnMessageException(MsgEnum.ERR_GET_WITHDRAW_CONFIG);
        }
        // add by liuyang 20190422 节假日提现修改 end
        logger.info("user is :{}", JSONObject.toJSONString(user));
        String ipAddr = CustomUtil.getIpAddr(request);
        logger.info("ipAddr is :{}", ipAddr);
        // (提现)
        String retUrl = super.getFrontHost(systemConfig,platform)+"/user/withdraw/result/handing";
        retUrl += "?token=1&sign=" +sign;
        String bgRetUrl = "http://CS-TRADE/hyjf-app/bank/user/withdraw/userBankWithdrawBgreturn";
        bgRetUrl=splicingParam(bgRetUrl,request);
        String successfulUrl = super.getFrontHost(systemConfig,platform)+"/user/withdraw/result/success";
        successfulUrl +="?token=1&sign=" +sign;
        String forgotPwdUrl=super.getForgotPwdUrl(platform,request,systemConfig);
        BankCallBean bean = bankWithdrawService.getUserBankWithdrawView(userVO,transAmt,cardNo,payAllianceCode,platform,BankCallConstant.CHANNEL_APP,ipAddr,retUrl,bgRetUrl,successfulUrl, forgotPwdUrl,withdrawRuleConfigVO);
        if (null == bean) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }
        try {
            Map<String,Object> data =  BankCallUtils.callApiMap(bean);
            result.setData(data);
        } catch (Exception e) {
            logger.info("app端提现失败");
            logger.error(e.getMessage());
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }

        result.setStatus("000");
        return result;
    }
    private String splicingParam(String bgRetUrl, HttpServletRequest request) {
        String sign=request.getParameter("sign");
        String token=request.getParameter("token");
        StringBuffer sb = new StringBuffer(bgRetUrl);

        if(bgRetUrl.indexOf("?")!=-1){
            sb.append("&sign=").append(sign).append("&token=").append(token);
        }else{
            sb.append("?sign=").append(sign).append("&token=").append(token);
        }
        return sb.toString();
    }
    /**
     * 用户银行提现异步回调
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "用户充值异步回调", notes = "用户充值")
    @ResponseBody
    @PostMapping("/userBankWithdrawBgreturn")
    public String userBankWithdrawBgreturn(HttpServletRequest request,@RequestBody BankCallBean bean) {
        logger.info("[app用户银行提现异步回调开始]");
        logger.info("app端提现银行返回参数, bean is :{}", JSONObject.toJSONString(bean));
        BankCallResult result = new BankCallResult();
        bean.convert();
        // 用户ID
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(userId));
        params.put("ip", CustomUtil.getIpAddr(request));
        // 执行提现后处理
        this.bankWithdrawService.handlerAfterCash(bean, params);
        logger.info( "成功");
        result.setStatus(true);
        logger.info("[app用户银行提现异步回调结束]");
        return JSONObject.toJSONString(result, true);
    }
}
