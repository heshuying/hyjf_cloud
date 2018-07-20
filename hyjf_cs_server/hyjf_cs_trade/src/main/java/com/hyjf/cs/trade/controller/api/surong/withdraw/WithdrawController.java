/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.surong.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.BankCardBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.RdfWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nxl
 * @version WithdrawController, v0.1 2018/7/19 14:01
 */

@Api(value = "融东风提现接口")
@Controller
@RequestMapping("/surong/withdraw")
public class WithdrawController extends BaseController {
    @Autowired
    private RdfWithdrawService rdfWithdrawService;
    @Autowired
    private SystemConfig systemConfig;


    @ApiOperation(value = "获取提现信息", notes = "获取提现信息")
    @RequestMapping("/getInfoAction")
    @ResponseBody
    public JSONObject getCashInfo(HttpServletRequest request, HttpServletResponse response) {
        // ---传入参数---
        String getcash = request.getParameter("getcash"); // 提现金额
        Integer userId = Integer.valueOf(request.getParameter("userId")); // 用户ID
        // ---
        logger.info("【获取提现信息   getcash:+" + getcash + " userId:" + userId + "】");
        JSONObject ret = new JSONObject();
        // 金额显示格式
        DecimalFormat moneyFormat = null;
        moneyFormat = CustomConstants.DF_FOR_VIEW;
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
        // 银行类型
        String bankType = "";
        // 银联行号
        String openBankCode = "";
        // 路由代码
        String isShowBankCode = "0";
        // 是否是大额提现
        String isLargeWithdrawal = "0";
        // 取得银行卡信息
        List<BankCardVO> banks = rdfWithdrawService.selectBankCardByUserIdAndStatus(userId, 1);
        if (banks.size() > 0) {
            ret.put("bankCnt", banks.size() + "");
            List<BankCardBean> bankcards = new ArrayList<BankCardBean>();
            for (int j = 0; j < banks.size(); j++) {
                BankCardVO bankCard = banks.get(j);
                BankCardBean bankCardBean = new BankCardBean();
                openBankCode = bankCard.getPayAllianceCode();// 银联行号
                bankType = String.valueOf(bankCard.getBankId()); // 银行类型
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

}
