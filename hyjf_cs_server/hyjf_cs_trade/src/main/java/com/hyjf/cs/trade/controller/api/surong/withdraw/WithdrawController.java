/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.surong.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.MD5;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.BankCardBean;
import com.hyjf.cs.trade.bean.WithdrawResultBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.withdraw.BankWithdrawService;
import com.hyjf.cs.trade.service.withdraw.RdfWithdrawService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version WithdrawController, v0.1 2018/7/19 14:01
 */

@Api(value = "api端-融东风提现接口",tags = "api端-融东风提现接口")
@Controller
@RequestMapping("/hyjf-api/surong/withdraw")
public class WithdrawController extends BaseController {
    @Autowired
    RdfWithdrawService rdfWithdrawService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    BankWithdrawService bankWithdrawService;


    @ApiOperation(value = "获取提现信息", notes = "获取提现信息")
    @PostMapping("/getInfoAction")
    @ResponseBody
    public JSONObject getInfoAction(HttpServletRequest request, HttpServletResponse response) {
        // ---传入参数---
        String getcash = request.getParameter("getcash"); // 提现金额
        Integer userId = Integer.valueOf(request.getParameter("userId")); // 用户ID
        // ---
        logger.info("【获取提现信息   getcash:+" + getcash + " userId:" + userId + "】");
        JSONObject ret = new JSONObject();
        // 金额显示格式
        DecimalFormat moneyFormat = CustomConstants.DF_FOR_VIEW;
        // 取得用户当前余额
        AccountVO account = rdfWithdrawService.getAccountByUserId(userId);
        if (account == null) {
            ret.put("status", "1");
            ret.put("statusDesc", "你的账户信息存在异常，请联系客服人员处理。");
            return ret;
        } else {
            if (account.getBankBalance() == null) {
                ret.put("total", "0");// 可提现金额
            } else {
                ret.put("total", moneyFormat.format(account.getBankBalance()));// 可提现金额
            }
        }
        // 银联行号
        String openBankCode = "";
        // 路由代码
        String isShowBankCode = "0";
        // 是否是大额提现
        String isLargeWithdrawal = "0";
        // 取得银行卡信息
        List<BankCardVO> banks = rdfWithdrawService.selectBankCardByUserIdAndStatus(userId, 1);
        if (banks!=null&&banks.size() > 0) {
            ret.put("bankCnt", banks.size() + "");
            List<BankCardBean> bankcards = new ArrayList<BankCardBean>();
            for (int j = 0; j < banks.size(); j++) {
                BankCardVO bankCard = banks.get(j);
                BankCardBean bankCardBean = new BankCardBean();
                openBankCode = bankCard.getPayAllianceCode();// 银联行号
                bankCardBean.setIsDefault("2");// 快捷卡
                bankCardBean.setBankCode(bankCard.getBank());// 银行代号?需要工具类
                bankCardBean.setBank(bankCard.getBank());// 银行名称
                bankCardBean.setLogo(systemConfig.webHost + rdfWithdrawService.getBankLogByBankName(bankCard.getBank()));
                bankCardBean.setCardNo(bankCard.getCardNo());
                bankcards.add(bankCardBean);
            }
            ret.put("banks", bankcards);
        } else {
            ret.put("bankCnt", "0");
        }
        // 如果提现金额是0
        if ("0".equals(getcash) || "".equals(getcash)) {
            ret.put("accountDesc", "手续费：0元；实际到账：0元");
        } else {
            String balance = "";
            if ((new BigDecimal(getcash).subtract(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) < 0) {
                balance = "0";
            } else {
                // 扣除手续费后的提现金额
                BigDecimal transAmt = new BigDecimal(getcash).subtract(BigDecimal.ONE);
                balance = moneyFormat.format(new BigDecimal(getcash).subtract(BigDecimal.ONE));
                // 工行或中行 提现金额大于 5万时

                // 扣除手续费
                if ((transAmt.compareTo(new BigDecimal(50000)) > 0) && StringUtils.isNotBlank(openBankCode)) {
                    isShowBankCode = "1";// 路由代码
                    isLargeWithdrawal = "1";// 是否是大额提现表示 0:非 1:是
                }

            }
            ret.put("accountDesc", "手续费：1元；实际到账：" + balance + "元");
            ret.put("balance", balance);
        }
        ret.put("free", "1");
        ret.put("isShowBankCode", isShowBankCode); // 路由代码
        ret.put("openBankCode", openBankCode); // 银联行号
        ret.put("isLargeWithdrawal", isLargeWithdrawal); // 是否是大额提现表示 0:非 1:是
        ret.put("status", "0");
        ret.put("statusDesc", "成功");
        return ret;
    }

    @ApiOperation(value = "提现", notes = "提现")
    @PostMapping(value = "/cash")
    public ModelAndView cash(HttpServletRequest request, HttpServletResponse response){

        String errorPage="redirect:"+systemConfig.getFrontHost() +"/user/openSuccess";
        // ---传入参数---
        String nid = request.getParameter("nid");
        String sign = request.getParameter("sign");
        String mobile = request.getParameter("mobile"); // 手机号
        String transAmt = request.getParameter("getcash"); // 交易金额
        String cardNo = request.getParameter("card"); // 提现银行卡号
        String routeCode = request.getParameter("routeCode");// 路由代码
        String payAllianceCode = request.getParameter("payAllianceCode"); // 银行联号
        String forgetPwd = request.getParameter("forgetPwd"); // 忘记密码
        String platform = request.getParameter("platform"); // 平台
        String from = request.getParameter("from"); // 来自于哪个客户端
        String retUrl = request.getParameter("retUrl"); // 调用方同步回调url
        String callBackUrl = request.getParameter("callBackUrl"); // 调用方异步回调url
        // ---

        /* 调用方加密校验 */
        if (StringUtils.isEmpty(sign)) {
            logger.info("sign值为空---");
            return null;
        }
        String accessKey = systemConfig.getAopAccesskey();
        String miwen = MD5.toMD5Code(accessKey + mobile + transAmt + accessKey);
        if (!miwen.equals(sign)) {
            logger.info("融东风提现sign值非法---" + sign);
            return null;
        }
        /* ^^^调用方加密校验^^^ */

        String message = "";
        UserVO user = bankWithdrawService.findUserByMobile(mobile);
        Integer userId = user.getUserId();
        String userName = user.getUsername();
        ModelAndView modelAndView = new ModelAndView();
        // 检查参数
        JSONObject checkResult = checkParam(request, userId, transAmt, cardNo);
        if (checkResult != null) {
            message = checkResult.getString("statusDesc");
            if ("提现金额需大于1元！".equals(message)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("status", "1");
                map.put("statusDesc", "提现金额需大于1元！");
                modelAndView = new ModelAndView("jsonView", map);
            } else {
                modelAndView = new ModelAndView(errorPage);
                modelAndView.addObject("message", message);
                return modelAndView;
            }
        }
        // 取得用户在汇付天下的客户号
        BankOpenAccountVO accountChinapnrTender = bankWithdrawService.getBankOpenAccount(userId);
        if (accountChinapnrTender == null || Validator.isNull(accountChinapnrTender.getAccount())) {
            message = "您还未开户，请开户后重新操作";
            modelAndView = new ModelAndView(errorPage);
            modelAndView.addObject("message", message);
            return modelAndView;
        }

        // 校验 银行卡号
        BankCardVO bankCard = this.bankWithdrawService.getBankInfo(userId, cardNo);
        if (bankCard == null || Validator.isNull(bankCard.getCardNo())) {
            message = "该银行卡信息不存在，请核实后重新操作";
            modelAndView = new ModelAndView(errorPage);
            modelAndView.addObject("message", message);
            return modelAndView;
        }
        // 取得手续费
        String fee = this.bankWithdrawService.getWithdrawFee(userId, bankCard.getCardNo());
        // 交易金额
        BigDecimal txAmount = new BigDecimal(transAmt).subtract(new BigDecimal(fee));

        // 扣除手续费
        if ((txAmount.compareTo(new BigDecimal(50000)) > 0) && StringUtils.isNotBlank(payAllianceCode)) {
            routeCode = "2";// 路由代码
        }

        // 如果是大额提现,并银联行号为空
        if ("2".equals(routeCode) && Validator.isNull(payAllianceCode)) {
            message = "大额提现时,开户行号不能为空。";
            modelAndView = new ModelAndView(errorPage);
            modelAndView.addObject("message", message);
            return modelAndView;
        }

        // 调用汇付接口(提现)
        String returnUrl = systemConfig.getFrontHost() + "/password/openError"+"?nid=" + nid + "&retUrl=" + retUrl
                + "&from=" + from;// 提现同步回调路径
        String bgRetUrl = systemConfig.webHost + "/hyjf-web/user/password/resetPasswordBgreturn?nid=" + nid + "&callBackUrl="
        + callBackUrl + "&from=" + from;// 提现异步回调路径

        UserVO users = bankWithdrawService.getUserByUserId(userId);
        UserInfoVO usersInfo = bankWithdrawService.getUserInfoByUserId(userId);
        // 调用汇付接口(4.2.2 用户绑卡接口)
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId0(userId));
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_WITHDRAW);
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallMethodConstant.TXCODE_WITHDRAW);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(BankCallConstant.CHANNEL_APP);// 交易渠道
        bean.setAccountId(accountChinapnrTender.getAccount());// 存管平台分配的账号
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
        bean.setIdNo(usersInfo.getIdcard());// 证件号
        bean.setName(usersInfo.getTruename());// 姓名
        bean.setMobile(users.getMobile());// 手机号
        bean.setCardNo((bankCard != null && StringUtils.isNotBlank(bankCard.getCardNo())) ? bankCard.getCardNo() : "");// 银行卡号
        bean.setTxAmount(CustomUtil.formatAmount(txAmount.toString()));
        bean.setTxFee(fee);
        // 大额提现时,走人行通道
        if ("2".equals(routeCode) && StringUtils.isNotBlank(payAllianceCode)) {
            bean.setRouteCode(routeCode);
            bean.setCardBankCnaps(payAllianceCode);
            LogAcqResBean logAcq = new LogAcqResBean();
            logAcq.setPayAllianceCode(payAllianceCode); // 银联行号放到私有域中
            bean.setLogAcqResBean(logAcq);
        }
        String sign_forgetPwd = MD5.toMD5Code(accessKey + mobile + accessKey);
        bean.setForgotPwdUrl(forgetPwd + "?sign=" + sign_forgetPwd + "&mobile=" + mobile);
        bean.setRetUrl(returnUrl);// 商户前台台应答地址(必须)
        bean.setNotifyUrl(bgRetUrl); // 商户后台应答地址(必须)
        LogAcqResBean logAcq = new LogAcqResBean();
        bean.setLogAcqResBean(logAcq);
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(userId));
        params.put("userName", userName);
        params.put("ip", CustomUtil.getIpAddr(request));
        params.put("cardNo", cardNo);
        params.put("client", platform);// 平台类型 0pc 1WX 2AND 3IOS 4other
        params.put("fee", CustomUtil.formatAmount(fee));// 手续费
        // 用户提现前处理
        int cnt = this.bankWithdrawService.updateBeforeCash(bean, params);
        if (cnt > 0) {
            // 跳转到江西银行画面
            try {
                logger.info("【调用江西银行提现接口   orderId:" + bean.getLogOrderId() + "-mobile:" + bean.getMobile() + "cardNo:"
                        + bean.getCardNo() + "-platform:" + bean.getLogClient() + "-txamount:" + bean.getTxAmount()
                        + " -fee:" + bean.getTxFee() + " -routeCode:" + bean.getRouteCode() + " -payAllianceCode:"
                        + bean.getPayAllianceCode() + " 】");
                modelAndView = BankCallUtils.callApi(bean);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            message = "请不要重复操作";
            modelAndView = new ModelAndView(errorPage);
            modelAndView.addObject("message", message);
            return modelAndView;
        }
        return modelAndView;
    }


    /**
     * 检查参数的正确性
     *
     * @param userId
     * @param transAmtStr
     * @param bankId
     * @return
     */
    private JSONObject checkParam(HttpServletRequest request, Integer userId, String transAmtStr, String bankId) {
        // 检查用户是否登录
        if (Validator.isNull(userId)) {
            return jsonMessage("您没有登录，请登录后再进行提现。", "1");
        }
        // 判断用户是否被禁用
        UserVO users = this.bankWithdrawService.getUserByUserId(userId);
        if (users == null || users.getStatus() == 1) {
            return jsonMessage("对不起,该用户已经被禁用。", "1");
        }
        // 检查参数(交易金额是否数字)
        if (Validator.isNull(transAmtStr) || !NumberUtils.isNumber(transAmtStr)) {
            return jsonMessage("请输入提现金额。", "1");
        }
        // 检查参数(交易金额是否大于0)
        BigDecimal transAmt = new BigDecimal(transAmtStr);
        if (transAmt.compareTo(BigDecimal.ONE) <= 0) {
            return jsonMessage("提现金额需大于1元！", "1");
        }
        // 取得用户当前余额
        AccountVO account = this.bankWithdrawService.getAccountByUserId(userId);
        // 投标金额大于可用余额
        if (account == null || transAmt.compareTo(account.getBankBalance()) > 0) {
            return jsonMessage("提现金额大于可用余额，请确认后再次提现。", "1");
        }
        // 调用共通接口验证当前支出金额与银行剩余可用金额关系 by liushouyi
        if (!this.bankWithdrawService.capitalExpendituresCheck(userId,transAmt)) {
            return jsonMessage("账户余额不同步。", "1");
        }
        /*// 检查参数(银行卡ID是否数字)
        if (Validator.isNotNull(bankId) && !NumberUtils.isNumber(bankId)) {
            return jsonMessage("银行卡号不正确，请确认后再次提现。", "1");
        }*/
        // 检查参数(由于可能存在特殊的以04开头的银行卡,所以不再采用是否是数字格式进行校验)
        if (Validator.isNull(bankId)) {
            return jsonMessage("请输入提现银行卡。", "1");
        }
        return null;
    }


    /**
     * 用户提现后处理 同步
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @GetMapping("/return")
    public ModelAndView cashReturn(HttpServletRequest request, @ModelAttribute BankCallBean bean) {
        bean.convert();
        logger.info("--↓↓ 提现同步回调Start ↓↓--orderId: " + bean.getLogOrderId() + " nid=" + request.getParameter("nid")
                + " -retCode:" + bean.getRetCode());
        String logOrderId = bean.getLogOrderId();
        AccountWithdrawVO accountwithdraw = bankWithdrawService.getAccountWithdrawByOrdId(logOrderId);
        /* 给调用方返回数据的封装 --begin */
        WithdrawResultBean withdrawResult = new WithdrawResultBean();
        withdrawResult.setCallBackAction(request.getParameter("retUrl"));
        String accessKey = systemConfig.getAopAccesskey();
        String nid = request.getParameter("nid");
        String miwen = MD5.toMD5Code(accessKey + nid + accessKey);
        withdrawResult.set("nid", nid);
        withdrawResult.set("sign", miwen);
        withdrawResult.set("status", "3");
        ModelAndView modelAndView = new ModelAndView("/surong/callback_post");
        /* 给调用方返回数据的封装 --end */
        if (accountwithdraw != null) {
            DecimalFormat df = new DecimalFormat("########0.00");
            withdrawResult.set("total", df.format(accountwithdraw.getTotal()));
            withdrawResult.set("balance", df.format(accountwithdraw.getCredited()));
            withdrawResult.set("message", "提现成功");
            logger.info("--↑↑ 提现同步回调End ↑↑-- nid=" + request.getParameter("nid") + "---提现成功");
            modelAndView.addObject("callBackForm", withdrawResult);
            return modelAndView;
        } else {
            withdrawResult.set("message", "银行处理中，请稍后查询交易明细");
            logger.info("--↑↑ 提现同步回调End ↑↑-- nid=" + request.getParameter("nid") + "---提现后同步处理结束：处理中");
            modelAndView.addObject("callBackForm", withdrawResult);
            return modelAndView;
        }

    }

    /**
     * 用户提现后处理 异步
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @ResponseBody
    @RequestMapping("/callback")
    public Object cashCallBack(HttpServletRequest request, @ModelAttribute BankCallBean bean) {
        // 发送状态
        String status = BankCallStatusConstant.STATUS_VERTIFY_OK;
        // 结果返回码
        String retCode = "";
    	if(bean == null){
    		retCode = "" ;
            status = BankCallStatusConstant.STATUS_FAIL;
            logger.info("提现后异步处理结束：提现失败：");
    	}
        bean.convert();
        logger.info("--↓↓ 提现异步回调Start ↓↓-- orderId: " + bean.getLogOrderId() + " nid=" + request.getParameter("nid")
                + " -retCode:" + bean.getRetCode());
        BankCallResult bankCallResult = new BankCallResult();
        String error = "0";
        String message = "";
        // 结果返回码
        /*String retCode = bean == null ? "" : bean.getRetCode();*/
        if(bean.getRetCode() != null){
        	retCode = bean.getRetCode();
        }
        // 提现成功 大额提现是返回 CE999028
        if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(retCode) || "CE999028".equals(retCode)) {
            // 执行结果(成功)
            status = BankCallStatusConstant.STATUS_SUCCESS;
        } else {
            // 执行结果(失败)
            status = BankCallStatusConstant.STATUS_FAIL;
        }
        // 更新提现状态
        try {
            Integer userId = Integer.parseInt(bean.getLogUserId());
            // 插值用参数
            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", String.valueOf(userId));
            params.put("ip", CustomUtil.getIpAddr(request));
            // 执行提现后处理
            this.bankWithdrawService.handlerAfterCash(bean, params);
        } catch (Exception e) {
            // 执行结果(失败)
            status = BankCallStatusConstant.STATUS_FAIL;
            logger.info("提现后异步处理结束：提现失败：" + e);
        }
        if (BankCallStatusConstant.STATUS_SUCCESS.equals(status)) {
            error = "0";
            message = "恭喜您，提现成功";
        } else {
            error = "1";
            message = "很遗憾，提现失败";
        }
        bankCallResult.setErrorCode(error);
        bankCallResult.setMessage(message);
        bankCallResult.setStatus(true);
        // 融东风专用参数返回封装
        String accessKey = systemConfig.getAopAccesskey();
        String nid = request.getParameter("nid");
        String miwen = MD5.toMD5Code(accessKey + nid + accessKey);
        Map<String, String> param = new HashMap<>();
        param.put("nid", nid);
        param.put("sign", miwen);
        param.put("message", message);
        param.put("status", "1");
        if ("0".equals(error)) {
            bankWithdrawService.getWithdrawResult(Integer.parseInt(bean.getLogUserId()), bean.getLogOrderId(), param);
            String result = HttpDeal.post(request.getParameter("callBackUrl"), param);
            logger.info("【提现异步向融东风发送数据结果:url=" + request.getParameter("callBackUrl") + "---result=" + result
                    + "---status=" + param.get("status") + "---cardNo=" + param.get("cardId") + "---total="
                    + param.get("total") + "---balance=" + param.get("balance") + "---fee=" + param.get("fee")
                    + "---nid=" + nid + "】");
        } else {
            String result = HttpDeal.post(request.getParameter("callBackUrl"), param);
            logger.info(
                    "【提现异步向融东风发送数据结果:url=" + request.getParameter("callBackUrl") + "---result=" + result + "---提现失败】");
        }
        return bankCallResult;
    }

    /**
     * 组成返回信息
     *
     * @param message
     * @param status
     * @return
     */
    public JSONObject jsonMessage(String message, String status) {
        JSONObject jo = null;
        if (Validator.isNotNull(message)) {
            jo = new JSONObject();
            jo.put(CustomConstants.APP_STATUS_DESC, message);
            jo.put(CustomConstants.APP_STATUS, status);
        }
        return jo;
    }
}
