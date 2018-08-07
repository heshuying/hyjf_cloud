/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bank.merchant;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BankMerchantAccountService;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
     * 发红包
     *
     * @param request
     * @param form
     * @return
     */
/*    @RequestMapping(value = "/redPocketSendAction")
    public ModelAndView redPocketSendAction(HttpServletRequest request, @ModelAttribute RedPocketBean form) {
        ModelAndView modelAndView = new ModelAndView(BankMerchantAccountDefine.RED_POCKET_SEND_PATH);
        // 查询商户子账户余额
        String merrpAccount = PropUtils.getSystem(BankCallConstant.BANK_MERRP_ACCOUNT);
        BigDecimal bankBalance = bankMerchantAccountService.getBankBalance(Integer.parseInt(ShiroUtil.getLoginUserId()), merrpAccount);
        // 画面验证
        this.validatorFieldCheck(modelAndView, form, bankBalance);
        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
            modelAndView.addObject(BankMerchantAccountDefine.RED_POCKET_SEND_FORM, form);
            return modelAndView;
        }
        // IP地址
        String ip = CustomUtil.getIpAddr(request);
        String orderId = GetOrderIdUtils.getOrderId2(Integer.valueOf(ShiroUtil.getLoginUserId()));
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
        bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
        bean.setAccountId(merrpAccount);// 电子账号
        bean.setTxAmount(form.getAmount());
        bean.setForAccountId(form.getUserAccount());
        bean.setDesLineFlag("1");
        bean.setDesLine(orderId);
        bean.setLogOrderId(orderId);// 订单号
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(String.valueOf(ShiroUtil.getLoginUserId()));
        bean.setLogClient(0);// 平台
        bean.setLogIp(ip);
        BankCallBean resultBean;
        try {
            resultBean = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            e.printStackTrace();
            ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "amount", "null", "请求红包接口失败");
            return modelAndView;
        }
        if (Validator.isNotNull(resultBean) && BankCallConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
            modelAndView.addObject(ManageUsersDefine.SUCCESS, ManageUsersDefine.SUCCESS);
            return modelAndView;
        }
        ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "amount", "null", "调用红包接口发生错误");
        return modelAndView;
    }*/

    /**
     * @Description 调用银行失败原因
     * @Author
     */
    @ApiOperation(value = "we端-调用银行失败原因", notes = "web端-调用银行失败原因")
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

}
