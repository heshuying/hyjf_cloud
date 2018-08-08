/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bank.merchant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BankMerchantAccountService;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import com.hyjf.am.vo.admin.BankMerchantAccountInfoVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.http.URLCodec;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountController, v0.1 2018/7/9 16:07
 */
@Api(value = "江西银行商户子账户",tags ="江西银行商户子账户")
@RestController
@RequestMapping("/hyjf-admin/bank/merchant/account")
public class BankMerchantAccountController extends BaseController {

    private static final String RECHARGE_LOG_NAME = "<圈存操作>: ";

    private static final String WITHDRAW_LOG_NAME = "<圈存操作>: ";

    private static final String RECHARGE_METHOD_NAME = "/rechargeCallback";

    private static final String WITHDRAW_METHOD_NAME = "/withdrawCallback";

    private static final String REQUEST_MAPPING = "/hyjf-admin/bank/merchant/account";

    @Autowired
    private BankMerchantAccountService bankMerchantAccountService;

    @Autowired
    SystemConfig systemConfig;
    /**
     * 商户子账户列表
     *
     * @param request
     * @param form
     * @return
     */
    @RequestMapping(value = "init")
    public AdminResult init(HttpServletRequest request, @RequestBody BankMerchantAccountListRequest form) {
        AdminSystemVO adminSystem = getUser(request);
        CheckUtil.check(adminSystem!=null, MsgEnum.ERR_USER_NOT_LOGIN);
        // 账户余额总计
        BigDecimal accountBalanceSum = BigDecimal.ZERO;
        // 可用余额总计
        BigDecimal availableBalanceSum = BigDecimal.ZERO;
        // 冻结金额总计
        BigDecimal frostSum = BigDecimal.ZERO;
        form.setUserId(Integer.parseInt(adminSystem.getId()));
        BankMerchantAccountResponse response = bankMerchantAccountService.selectBankMerchantAccount(form);
        if(response == null||response.getRecordTotal()==0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<BankMerchantAccountVO> recordList = response.getResultList();
        // 算统计数据
        for (int i = 0; i < recordList.size(); i++) {
            BankMerchantAccountVO account = recordList.get(i);
            accountBalanceSum = accountBalanceSum.add(account.getAccountBalance());
            availableBalanceSum = availableBalanceSum.add(account.getAvailableBalance());
            frostSum = frostSum.add(account.getFrost());
        }
        form.setRecordList(recordList);
        form.setAccountBalanceSum(String.valueOf(accountBalanceSum));
        form.setAvailableBalanceSum(String.valueOf(availableBalanceSum));
        form.setFrostSum(String.valueOf(frostSum));
        return new AdminResult(form);
    }

    /**
     * 设置交易密码
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/setPassword")
    public AdminResult setPassword(HttpServletRequest request) {
        String accountCode = request.getParameter("accountCode");
        AdminResult result = bankMerchantAccountService.setPassword(accountCode);
        return result;
    }

    /**
     * 设置交易密码异步回调
     *
     * @return
     */
    @RequestMapping(value = "/passwordBgreturn")
    public String passwordBgreturn(@ModelAttribute BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        bean.convert();
        BankMerchantAccountVO bankMerchantAccount = bankMerchantAccountService.getBankMerchantAccount(bean.getAccountId());
        // 成功或审核中
        if (bankMerchantAccount != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            this.bankMerchantAccountService.updateBankMerchantAccountIsSetPassword(bean.getAccountId(), 1);
        }
        result.setMessage("交易密码设置成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * 重置交易密码
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/resetPassword")
    public AdminResult resetPassword(HttpServletRequest request) {
        String accountCode = request.getParameter("accountCode");
        AdminResult result = bankMerchantAccountService.resetPassword(accountCode);
        return result;
    }

    /**
     * 重置交易密码异步回调
     *
     * @return
     */
    @RequestMapping(value = "/resetPasswordBgreturn")
    public String resetPasswordBgreturn(@ModelAttribute BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        result.setMessage("交易密码修改成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * @Description 调用银行失败原因
     * @Author
     */
    @ApiOperation(value = "admin端-调用银行失败原因", notes = "admin端-调用银行失败原因")
    @RequestMapping("/searchFiledMess")
    @ResponseBody
    public WebResult<Object> searchFiledMess(@RequestParam("logOrdId") String logOrdId) {
        logger.info("调用银行失败原因start,logOrdId:{}", logOrdId);
        WebResult<Object> result = new WebResult<Object>();
        String retMsg = bankMerchantAccountService.getFiledMess(logOrdId);
        Map<String,String> map = new HashedMap();
        map.put("error",retMsg);
        result.setData(map);
        return result;
    }



    /**
     * 圈存和圈提统一弹出框
     * @author zhangyk
     * @date 2018/8/7 10:50
     */
    @ApiOperation(value = "圈存弹出窗" ,tags = "圈存弹出窗" )
    @ResponseBody
    @GetMapping(value = "/rechargeInit/{accountCode}" , produces = "application/json; charset=utf-8")
    public AdminResult rechargeInit(@PathVariable String accountCode){
        AdminResult adminResult = new AdminResult();
        CheckUtil.check(StringUtils.isNotBlank(accountCode),MsgEnum.ERR_OBJECT_REQUIRED,"账户号accountCode");
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        BankMerchantAccountVO bankMerchantAccount =  bankMerchantAccountService.getBankMerchantAccount(accountCode);
        JSONObject data = new JSONObject();

        data.put("availableBalance", df.format(bankMerchantAccount.getAvailableBalance()));
        data.put("account", accountCode);
        // 显示手机号
        BankMerchantAccountInfoVO info=bankMerchantAccountService.getBankMerchantAccountInfoByCode(accountCode);
        data.put("info", info);
        adminResult.setData(data);
        return adminResult;
    }


    /**
     * 统一参数校验
     * @author zhangyk
     * @date 2018/8/7 10:50
     */
    @ApiOperation(value = "统一参数校验(真正调用圈存和圈提操作之前调用)" ,tags = "统一参数校验(真正调用圈存和圈提操作之前调用)" )
    @ResponseBody
    @GetMapping(value = "/checkParam" , produces = "application/json; charset=utf-8")
    public AdminResult checkAction(HttpServletRequest request){
        AdminResult adminResult = new AdminResult();
        JSONObject ret = new JSONObject();
        String amount = request.getParameter("amount");
        String accountCode = request.getParameter("accountCode");
        String msg = bankMerchantAccountService.checkParam(amount, accountCode);
        String checkStatus = "Y";
        if (StringUtils.isNotBlank(msg)) {
            ret.put("info", msg);
            checkStatus = "N";
        }
        ret.put("checkStatus",checkStatus);
        adminResult.setData(ret);
        return adminResult;
    }




    /**
     * 圈存操作
     * @author zhangyk
     * @date 2018/8/7 10:50
     */
    @ApiOperation(value = "圈存操作" ,tags = "圈存操作" )
    @GetMapping(value = "/toRecharge" , produces = "application/json; charset=utf-8")
    public AdminResult toRecharge(HttpServletRequest request){
        AdminResult adminResult = new AdminResult();
        String accountCode = request.getParameter("accountCode");
        String transAmt = request.getParameter("amount");// 交易金额
        CheckUtil.check(StringUtils.isAnyBlank(accountCode,transAmt) , MsgEnum.ERR_OBJECT_REQUIRED,"商户号或者交易金额");
        BankMerchantAccountVO bankMerchantAccount = bankMerchantAccountService.getBankMerchantAccount(accountCode);
        if (bankMerchantAccount == null){
            logger.error("没有查询到对应的商户号[{}]",accountCode);
            throw new RuntimeException("商户号不正确");
        }
        String forgotPwdUrl="";
        if(bankMerchantAccount.getIsSetPassword()==0){
            forgotPwdUrl=systemConfig.getAdminHost() + REQUEST_MAPPING + "/setPassword?accountCode="+accountCode ;
        }else{
            forgotPwdUrl=systemConfig.getAdminHost() + REQUEST_MAPPING +"/resetPassword?accountCode="+accountCode ;
        }

        BankMerchantAccountInfoVO info=bankMerchantAccountService.getBankMerchantAccountInfoByCode(accountCode);

        String bankId = "";// 充值银行卡号
        String userName = ""; // 用户名
        String mobile="";

        String idNo="";
        String idType="";
        String userId ="0"; // 后续待优化，目前先用0代表，平台用户体现应该插入别的表 hyjf_bank_merchant_account_list
        if(info!=null){
            //服务费账户
            mobile=info.getMobile();
            idNo=info.getIdNo();
            idType=info.getIdType();
            userName = info.getAccountName(); // 用户名
            bankId = info.getBankCard();// 提现银行卡号
        } else{
            throw  new  CheckException("1","账户信息不存在");
        }
        // 调用江西银行接口(2.3.3.圈存)
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(0));
        String errorUrl = systemConfig.getAdminFrontHost()+ "/admin/onError";  // TODO: 2018/8/7   后期确认错误页面地址 zyk
        String successUrl = systemConfig.getAdminFrontHost() + "/admin/onSuccess" ; // TODO: 2018/8/7 后期去人成功页面 zyk
        String notifyUrl = systemConfig.getAdminHost() + REQUEST_MAPPING + RECHARGE_METHOD_NAME;

        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId("0");
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_TRANSFERENCE);
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        // 银行存管
        bean.setTxCode(BankCallConstant.TXCODE_CREDIT_FOR_LOADPAGE);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        bean.setAccountId(accountCode);// 存管平台分配的账号
        bean.setCardNo(bankId);// 银行卡号
        bean.setCurrency("156");
        bean.setTxAmount(CustomUtil.formatAmount(transAmt));
        bean.setIdType(idType);// 证件类型25组织机构代码
        bean.setIdNo(idNo);// 证件号
        bean.setName(userName);// 姓名
        bean.setMobile(mobile);// 手机号
        bean.setForgotPwdUrl(forgotPwdUrl);
        bean.setRetUrl(errorUrl);// 商户前台台应答地址(必须)
        bean.setNotifyUrl(notifyUrl); // 商户后台应答地址(必须)
        bean.setSuccessfulUrl(successUrl);

        LogAcqResBean logAcq = new LogAcqResBean();

        logAcq.setCardNo(accountCode);
        bean.setLogAcqResBean(logAcq);
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("userName", userName);
        params.put("ip", CustomUtil.getIpAddr(request));
        params.put("bankId", bankId);
        params.put("client", "0");
        // 用户充值前处理
        int cnt = this.bankMerchantAccountService.updateBeforeRecharge(bean, params,bankMerchantAccount);
        Map<String,Object> map = new HashMap<>();
        if (cnt >0){
            // 继续调用业务
            try {
                map = BankCallUtils.callApiMap(bean);
            } catch (Exception e) {
                logger.error("{}银行接口调用失败！",RECHARGE_LOG_NAME);
                throw new CheckException("1","调用银行接口失败!");
            }
        }else {
            // 重复操作
            throw new CheckException("1","请不要重复操作");
        }
        adminResult.setData(map);
        return adminResult;
    }


    /**
     * 圈存异步回调
     * @author zhangyk
     * @date 2018/8/8 14:17
     */
    @ResponseBody
    @RequestMapping(value = RECHARGE_METHOD_NAME , produces = "application/json; charset=utf-8")
    public BankCallResult rechargeCallBack(HttpServletRequest request, @ModelAttribute BankCallBean bean) {
        logger.info("圈存回调: 接收到参数[{}]",JSON.toJSONString(bean));
        logger.info( "公司账户[{}]充值回调开始", bean.getLogOrderId());
        BankCallResult result = new BankCallResult();
        bean.convert();

        // 发送状态
        String status = BankCallStatusConstant.STATUS_VERTIFY_OK;

        if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            try {
                Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
                // 插值用参数
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", String.valueOf(userId));
                params.put("ip", CustomUtil.getIpAddr(request));

                // 执行充值后处理
                this.bankMerchantAccountService.handlerAfterRecharge(bean, params);
                // 执行结果(成功)
                if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                    status = BankCallStatusConstant.STATUS_SUCCESS;
                } else {
                    status = BankCallStatusConstant.STATUS_FAIL;
                }
                logger.info(bean.getLogOrderId()+ " 成功");
            } catch (Exception e) {
                // 执行结果(失败)
                logger.error("公司账户充值回调处理异常："+e.getMessage());
            }

        } else {
            logger.info("公司账户充值失败 "+bean.get(BankCallParamConstant.PARAM_RETCODE));
            // 执行结果(失败)
            status = BankCallStatusConstant.STATUS_FAIL;
            // 更新提现失败原因
            String reason = bean.getRetMsg();
            if (StringUtils.isNotEmpty(reason)) {
                if (reason.contains("%")) {
                    reason = URLCodec.decodeURL(reason);
                }
            }
            if (StringUtils.isNotEmpty(bean.getLogOrderId())) {
                this.bankMerchantAccountService.updateBankAccountListFailByOrderId(bean.getLogOrderId());
            }
            logger.info(bean.getLogOrderId()+ " 失败");
        }

        // 下面是给pay工程的   与回调业务没有太大关系
        if (BankCallStatusConstant.STATUS_SUCCESS.equals(status)) {
            result.setStatus(true);
            result.setMessage("恭喜您，提现成功");
        } else {
            result.setStatus(false);
            result.setMessage("银行处理中，请稍后查询交易明细");
        }
        return result;
    }




    /**
     * 圈提操作
     * @author zhangyk
     * @date 2018/8/8 15:49
     */
    @ApiOperation(value = "圈提操作" ,tags = "圈提操作" )
    @GetMapping(value = "/withdraw" , produces = "application/json; charset=utf-8")
    public AdminResult withDraw(HttpServletRequest request){
        AdminResult adminResult = new AdminResult();
        JSONObject result = new JSONObject();
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        String accountCode = request.getParameter("accountCode");
        String transAmt = request.getParameter("amount");// 交易金额
        BankMerchantAccountVO bankMerchantAccount = bankMerchantAccountService.getBankMerchantAccount(accountCode);
        String forgotPwdUrl="";
        if(bankMerchantAccount.getIsSetPassword()==0){
            forgotPwdUrl=systemConfig.getAdminHost() + REQUEST_MAPPING + "/setPassword?accountCode="+accountCode ;
        }else{
            forgotPwdUrl=systemConfig.getAdminHost() + REQUEST_MAPPING +"/resetPassword?accountCode="+accountCode ;
        }
        result.put("availableBalance", df.format(bankMerchantAccount.getAvailableBalance()));
        result.put("account", accountCode);


        BankMerchantAccountInfoVO info=bankMerchantAccountService.getBankMerchantAccountInfoByCode(accountCode);

        // 取得手续费 默认1
        String fee = "0";
        // 实际取现金额

        String bankId = "";// 提现银行卡号
        String userName = ""; // 用户名
        String mobile="";
        String idNo="";
        String idType="";
        String userId ="0"; // 后续待优化，目前先用0代表，平台用户体现应该插入别的表 hyjf_bank_merchant_account_list
        if(info!=null){
            //服务费账户
            mobile=info.getMobile();
            idNo=info.getIdNo();
            idType=info.getIdType();
            userName = info.getAccountName(); // 用户名
            bankId = info.getBankCard();// 提现银行卡号
        } else{
            throw  new CheckException("账户信息不存在");
        }

        // 调用汇付接口(提现)
        String errorUrl = systemConfig.getAdminFrontHost()  +"/onError.jsp";
        String notifyUrl = systemConfig.getAdminHost() + REQUEST_MAPPING + "/withDrawCallback";
        String successUrl = systemConfig.getAdminFrontHost() + "/onSuccess.jsp";

        // 调用汇付接口(4.2.2 用户绑卡接口)
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(0));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId("0");
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_CREDIT_FOR_UNLOAD_PAGE);
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        // 银行存管
        bean.setTxCode(BankCallConstant.TXCODE_CREDIT_FOR_UNLOAD_PAGE);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        bean.setAccountId(accountCode);// 存管平台分配的账号
        bean.setCurrency(BankCallConstant.CURRENCY_TYPE_RMB);
        bean.setCardNo(bankId);// 银行卡号
        bean.setIdType(idType);// 证件类型25组织机构代码
        bean.setIdNo(idNo);// 证件号
        bean.setName(userName);// 姓名
        bean.setMobile(mobile);// 手机号

        bean.setTxAmount(CustomUtil.formatAmount(transAmt));
        bean.setTxFee(fee);
        bean.setForgotPwdUrl(forgotPwdUrl);
        bean.setRetUrl(errorUrl);// 商户前台台应答地址(必须)
        bean.setNotifyUrl(notifyUrl); // 商户后台应答地址(必须)
        bean.setSuccessfulUrl(successUrl);

        bean.setRouteCode("2"); // 2-人行大额通道
        bean.setCardBankCnaps("313421081204"); // routeCode=2，必输 南昌银行铁路支行

        LogAcqResBean logAcq = new LogAcqResBean();

        logAcq.setCardNo(accountCode);
        logAcq.setFee("0");
        bean.setLogAcqResBean(logAcq);
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("userName", userName);
        params.put("ip", CustomUtil.getIpAddr(request));
        params.put("bankId", bankId);
        params.put("fee", CustomUtil.formatAmount("0"));
        params.put("client", "0");
        // 用户提现前处理
        int cnt = this.bankMerchantAccountService.updateBeforeCash(bean, params,bankMerchantAccount);
        Map<String,Object> map = new HashMap<>();
        if (cnt >0){
            // 继续调用业务
            try {
                map = BankCallUtils.callApiMap(bean);
            } catch (Exception e) {
                logger.error("{}银行接口调用失败！",WITHDRAW_LOG_NAME);
                throw new CheckException("1","调用银行接口失败!");
            }
        }else {
            // 重复操作
            throw new CheckException("1","请不要重复操作");
        }
        adminResult.setData(map);
        return adminResult;

    }

    /**
     * 圈提异步回调
     * @author zhangyk
     * @date 2018/8/8 15:49
     */
    @ApiOperation(value = "提现异步回调", tags = "提现异步回调")
    @ResponseBody
    @RequestMapping(value = WITHDRAW_METHOD_NAME , produces = "application/json; charset=utf-8")
    public BankCallResult withdrawCallBack(HttpServletRequest request, @ModelAttribute BankCallBean bean) {
        logger.info(bean.getLogOrderId()+ " 公司账户提现回调开始");
        BankCallResult result = new BankCallResult();
        bean.convert();
        // 发送状态
        String status = BankCallStatusConstant.STATUS_VERTIFY_OK;

        // 成功或审核中或提现失败
        if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) || "CE999028".equals(bean.getRetCode())) {
            try {
                Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
                // 插值用参数
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", String.valueOf(userId));
                params.put("ip", CustomUtil.getIpAddr(request));

                // 执行提现后处理
                this.bankMerchantAccountService.handlerAfterCash(bean, params);
                // 执行结果(成功)
                if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                    status = BankCallStatusConstant.STATUS_SUCCESS;
                } else {
                    status = BankCallStatusConstant.STATUS_FAIL;
                }
                logger.info(bean.getLogOrderId()+ " 成功");
            } catch (Exception e) {
                // 执行结果(失败)
                logger.error("公司账户提现回调"+e.getMessage());
            }

        } else {
            logger.info("公司账户提现失败 "+bean.get(BankCallParamConstant.PARAM_RETCODE));
            // 执行结果(失败)
            status = BankCallStatusConstant.STATUS_FAIL;
            // 更新提现失败原因
            String reason = bean.getRetMsg();
            if (StringUtils.isNotEmpty(reason)) {
                if (reason.contains("%")) {
                    reason = URLCodec.decodeURL(reason);
                }
            }
            if (StringUtils.isNotEmpty(bean.getLogOrderId())) {
                this.bankMerchantAccountService.updateBankAccountListFailByOrderId(bean.getLogOrderId());
            }
            logger.info(bean.getLogOrderId()+ " 失败");
        }

        if (BankCallStatusConstant.STATUS_SUCCESS.equals(status)) {
            result.setStatus(true);
            result.setMessage("恭喜您，提现成功");
        } else {
            result.setStatus(true);
            result.setMessage("银行处理中，请稍后查询交易明细");
        }
        return result;
    }




    /**
     * 用户同步余额
     */
    @ResponseBody
    @RequestMapping(value = "/synbalance", produces = "application/json; charset=utf-8")
    public JSONObject synbalance(HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        String accountCode = request.getParameter("accountCode");
        // 账户可用余额
        BigDecimal balance = BigDecimal.ZERO;
        // 账户冻结金额
        BigDecimal frost = BigDecimal.ZERO;
        // 账面余额
        BigDecimal currBalance = BigDecimal.ZERO;
        BankCallBean bean = new BankCallBean();
        // 获取共同参数
        String channel = BankCallConstant.CHANNEL_PC;
        // 交易代码
        bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);
        // 交易渠道
        bean.setChannel(channel);
        // 电子账号
        bean.setAccountId(accountCode);
        // 订单号
        // TODO: 2018/8/8 zhangqingqing
        //bean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.valueOf(ShiroUtil.getLoginUserId())));
        // 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // TODO: 2018/8/8 zhangqingqing
        //bean.setLogUserId(String.valueOf(ShiroUtil.getLoginUserId()));
        // 平台
        bean.setLogClient(0);
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean == null) {
                ret.put("status", 1);
                ret.put("message", "更新发生错误,请重新操作!");
                return ret;
            }
            if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                // 账户余额
                balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
                // 账面余额
                currBalance = new BigDecimal(resultBean.getCurrBal().replace(",", ""));
                // 账户冻结金额
                frost = currBalance.subtract(balance);
            } else {
                ret.put("status", 1);
                ret.put("message", "更新发生错误,请重新操作!");
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int cnt = 0;
        try {
            // 更新处理
            cnt = this.bankMerchantAccountService.updateBankMerchantAccount(accountCode, currBalance, balance, frost);
        } catch (Exception e) {
        }
        // 返现成功
        if (cnt > 0) {
            // 成功
            ret.put("status", 0);
            ret.put("message", "更新成功");
        } else {
            // 成功
            ret.put("status", 1);
            ret.put("message", "更新发生错误,请重新操作!");
        }
        return ret;
    }

    /**
     * 发红包
     *
     * @param request
     * @param form
     * @return
     */
    @RequestMapping(value = "/redPocketSendAction")
    public AdminResult redPocketSendAction(HttpServletRequest request, @ModelAttribute RedPocketBean form) {
        AdminResult result = new AdminResult();
        // 查询商户子账户余额
        String merrpAccount = systemConfig.getBANK_MERRP_ACCOUNT();
        // TODO: 2018/8/8
        int loginUserId = 0;//Integer.parseInt(ShiroUtil.getLoginUserId());
        BigDecimal bankBalance = bankMerchantAccountService.getBankBalance(loginUserId, merrpAccount);
        // 画面验证
        // TODO: 2018/8/8 zhangqingqing
       /*this.validatorFieldCheck(modelAndView, form, bankBalance);
        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
            modelAndView.addObject(BankMerchantAccountDefine.RED_POCKET_SEND_FORM, form);
            return modelAndView;
        }*/
        // IP地址
        String ip = CustomUtil.getIpAddr(request);
        // TODO: 2018/8/8
      //  String orderId = GetOrderIdUtils.getOrderId2(Integer.valueOf(ShiroUtil.getLoginUserId()));
        String orderId = "";
        BankCallBean bean = new BankCallBean();
        // 交易代码
        bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);
        // 交易渠道
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(merrpAccount);
        bean.setTxAmount(form.getAmount());
        bean.setForAccountId(form.getUserAccount());
        bean.setDesLineFlag("1");
        bean.setDesLine(orderId);
        // 订单号
        bean.setLogOrderId(orderId);
        // TODO: 2018/8/8
       // bean.setLogUserId(String.valueOf(ShiroUtil.getLoginUserId()));
        // 平台
        bean.setLogClient(0);
        bean.setLogIp(ip);
        BankCallBean resultBean;
        try {
            resultBean = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CheckException("1","请求红包接口失败");
        }
        return result;
    }





}
