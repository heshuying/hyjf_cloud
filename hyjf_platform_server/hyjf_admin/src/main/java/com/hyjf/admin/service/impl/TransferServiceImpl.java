/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.TransferService;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.response.trade.account.MerchantTransferResponse;
import com.hyjf.am.resquest.admin.MerchantTransferListRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangqingqing
 * @version TransferServiceImpl, v0.1 2018/7/6 17:59
 */
@Service
public class TransferServiceImpl extends BaseAdminServiceImpl implements TransferService {

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public void checkTransfer(String outUserName) {
        UserVO user = getUserByUserName(outUserName);
        CheckUtil.check(null != user, MsgEnum.STATUS_CE000006);
        List<AccountChinapnrVO> chinapnrs = this.amUserClient.searchAccountChinapnrByUserId(user.getUserId());
        CheckUtil.check(chinapnrs != null && chinapnrs.size() == 1, MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        List<AccountVO> accounts = this.amTradeClient.searchAccountByUserId(user.getUserId());
        CheckUtil.check(accounts != null && accounts.size() == 1, MsgEnum.ERR_OBJECT_GET, "正确的余额信息");

    }

    @Override
    public String searchBalance(String outUserName) {
        UserVO user = getUserByUserName(outUserName);
        CheckUtil.check(null != user, MsgEnum.STATUS_CE000006);
        List<AccountChinapnrVO> chinapnrs = this.amUserClient.searchAccountChinapnrByUserId(user.getUserId());
        CheckUtil.check(chinapnrs != null && chinapnrs.size() == 1, MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        List<AccountVO> accounts = this.amTradeClient.searchAccountByUserId(user.getUserId());
        CheckUtil.check(accounts != null && accounts.size() == 1, MsgEnum.ERR_OBJECT_GET, "正确的余额信息");
        if (accounts != null && accounts.size() == 1) {
            AccountVO account = accounts.get(0);
            return account.getBalance().toString();
        }
        return null;
    }

    @Override
    public boolean insertTransfer(MerchantTransferListRequest form) {
        return  amTradeClient.insertTransfer(form);
    }

    @Override
    public MerchantAccountResponse selectMerchantAccountList(Integer status) {
        MerchantAccountResponse merchantAccounts = amTradeClient.selectMerchantAccountList(status);
        return merchantAccounts;
    }

    @Override
    public MerchantTransferResponse selectMerchantTransfer(MerchantTransferListRequest form) {
        return amTradeClient.selectMerchantTransfer(form);
    }

    @Override
    public void checkMerchantTransfer(String outAccountId, String transferAmount, JSONObject ret) {
        if (StringUtils.isNotBlank(outAccountId)) {
            if(StringUtils.isNotBlank(transferAmount)){
                //获取转出账户
                MerchantAccountVO account = amTradeClient.selectMerchantAccountById(Integer.parseInt(outAccountId));
                if (Validator.isNotNull(account)) {
                    if(account.getTransferOutFlg() ==1){
                        //账户子账户类型
                        String subAccountType = account.getSubAccountType();
                        //账户子账户代码
                        String subAccountCode = account.getSubAccountCode();
                        // 调用汇付接口,查询余额
                        ChinapnrBean bean = new ChinapnrBean();
                        // 构建请求参数
                        bean.setVersion(ChinaPnrConstant.VERSION_10); // 版本号(必须)
                        bean.setCmdId(ChinaPnrConstant.CMDID_QUERY_ACCTS); // 消息类型(必须)
                        bean.setMerCustId(systemConfig.getMerCustId()); // 商户客户号
                        // 发送请求获取结果
                        ChinapnrBean resultBean = ChinapnrUtil.callApiBg(bean);
                        String respCode = resultBean == null ? "" : resultBean.getRespCode();
                        // 如果接口调用成功
                        if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(respCode)) {
                            //如果接口返回的账户结果串不为空
                            if (resultBean!=null&&StringUtils.isNotBlank(resultBean.getAcctDetails())) {
                                try {
                                    BigDecimal amount = new BigDecimal(transferAmount);
                                    if(amount.compareTo(BigDecimal.ZERO) > 0){
                                        String acctDetails = resultBean.getAcctDetails();
                                        //转换账户结果串为json数组
                                        JSONArray acctDetailsList = JSONArray.parseArray(acctDetails);
                                        // 遍历所有子账户
                                        boolean flag = false;
                                        for(Object object:acctDetailsList){
                                            JSONObject acctObject = (JSONObject)object;
                                            // 取得公司账户余额(所有子账户余额相加)
                                            String accType= acctObject.getString("AcctType");
                                            // 取得公司账户余额(所有子账户余额相加)
                                            String accCode= acctObject.getString("SubAcctId");
                                            BigDecimal avlBal = StringUtils.isBlank(acctObject.getString("AvlBal")) ? new BigDecimal("0") : acctObject.getBigDecimal("AvlBal");
                                            if(StringUtils.isNotBlank(accType)&&StringUtils.isNotBlank(accCode)&&accType.equals(subAccountType)&&accCode.equals(subAccountCode)){
                                                flag = true;
                                                if(avlBal.compareTo(amount)<0){
                                                    ret.put("info", "转出账户余额不足!");
                                                }else{
                                                    ret.put("status", "y");
                                                }
                                            }
                                        }
                                        if(!flag){
                                            ret.put("info", "配置错误，未查询到子账户信息!");
                                        }
                                    }else{
                                        ret.put("info", "转出金额错误!");
                                    }
                                } catch (Exception e) {
                                    ret.put("info", "汇付余额信息校验失败!");
                                }
                            } else {
                                ret.put("info", "汇付余额信息为空!");
                            }
                        } else {
                            ret.put("info", "子账户汇付余额查询失败!");
                        }
                    }else{
                        ret.put("info", "配置错误，此账户不能转出!");
                    }
                } else {
                    ret.put("info", "未查询到子账户信息!");
                }
            }else{
                ret.put("info", "转账金额不能为空!");
            }
        } else {
            ret.put("info", "转出账号不能为空!");
        }
    }

    @Override
    public JSONObject checkMerchantTransferParam(MerchantTransferListRequest form) {
        JSONObject ret = new JSONObject();
        if (Validator.isNotNull(form.getOutAccountId())) {
            //获取转出账户
            MerchantAccountVO outAccount = amTradeClient.selectMerchantAccountById(form.getOutAccountId());
            if (Validator.isNotNull(outAccount)) {
                if(outAccount.getTransferOutFlg() ==1){
                    if(Validator.isNotNull(form.getInAccountId())){
                        //获取转出账户
                        MerchantAccountVO inAccount = amTradeClient.selectMerchantAccountById(form.getInAccountId());
                        if (Validator.isNotNull(inAccount)) {
                            if(inAccount.getTransferIntoFlg() ==1){
                                if(Validator.isNotNull(form.getTransferAmount())){
                                    //账户子账户类型
                                    String outSubAccountType = outAccount.getSubAccountType();
                                    //账户子账户代码
                                    String outSubAccountCode = outAccount.getSubAccountCode();
                                    //账户名称
                                    String outSubAccountName = outAccount.getSubAccountName();
                                    //账户子账户类型
                                    String inSubAccountType = inAccount.getSubAccountType();
                                    //账户子账户代码
                                    String inSubAccountCode = inAccount.getSubAccountCode();
                                    //账户名称
                                    String inSubAccountName = inAccount.getSubAccountName();
                                    // 调用汇付接口,查询余额
                                    ChinapnrBean bean = new ChinapnrBean();
                                    // 构建请求参数
                                    // 版本号(必须)
                                    bean.setVersion(ChinaPnrConstant.VERSION_10);
                                    // 消息类型(必须)
                                    bean.setCmdId(ChinaPnrConstant.CMDID_QUERY_ACCTS);
                                    // 商户客户号
                                    bean.setMerCustId(systemConfig.getMerCustId());
                                    // 发送请求获取结果
                                    ChinapnrBean resultBean = ChinapnrUtil.callApiBg(bean);
                                    String respCode = resultBean == null ? "" : resultBean.getRespCode();
                                    // 如果接口调用成功
                                    if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(respCode)) {
                                        //如果接口返回的账户结果串不为空
                                        if (resultBean!=null&&StringUtils.isNotBlank(resultBean.getAcctDetails())) {
                                            try {
                                                BigDecimal transferAmount = form.getTransferAmount();
                                                if(transferAmount.compareTo(BigDecimal.ZERO)>0){
                                                    String acctDetails = resultBean.getAcctDetails();
                                                    //转换账户结果串为json数组
                                                    JSONArray acctDetailsList = JSONArray.parseArray(acctDetails);
                                                    // 遍历所有子账户
                                                    boolean outFlag = false;
                                                    boolean inFlag = false;
                                                    for(Object object:acctDetailsList){
                                                        JSONObject acctObject = (JSONObject)object;
                                                        // 取得公司账户余额(所有子账户余额相加)
                                                        String accType= acctObject.getString("AcctType");
                                                        // 取得公司账户余额(所有子账户余额相加)
                                                        String accCode= acctObject.getString("SubAcctId");
                                                        BigDecimal avlBal = StringUtils.isBlank(acctObject.getString("AvlBal")) ? new BigDecimal("0") : acctObject.getBigDecimal("AvlBal");
                                                        if(StringUtils.isNotBlank(accType)&&StringUtils.isNotBlank(accCode)&&accType.equals(outSubAccountType)&&accCode.equals(outSubAccountCode)){
                                                            outFlag = true;
                                                            if(avlBal.compareTo(transferAmount) < 0){
                                                                ret.put("99","转出账户余额不足!");
                                                                //ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "transferAmount", "merchant.transfer.transferAmount.balance","转出账户余额不足!");
                                                            }
                                                            form.setOutAccountCode(outSubAccountCode);
                                                            form.setOutAccountName(outSubAccountName);
                                                        }
                                                        if(StringUtils.isNotBlank(accType)&&StringUtils.isNotBlank(accCode)&&accType.equals(inSubAccountType)&&accCode.equals(inSubAccountCode)){
                                                            inFlag = true;
                                                            form.setInAccountCode(inSubAccountCode);
                                                            form.setInAccountName(inSubAccountName);
                                                        }
                                                    }
                                                    if(!outFlag){
                                                        ret.put("99","配置错误,未查询到转出子账户信息!");
                                                        //ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "outAccountId", "merchant.transfer.outAccountId.error","配置错误，未查询到转出子账户信息!");
                                                    }
                                                    if(!inFlag){
                                                        ret.put("99","配置错误,未查询到转入子账户信息!");
//                                                        ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "inAccountId", "merchant.transfer.inAccountId.error","配置错误，未查询到转入子账户信息!");
                                                    }
                                                    if(form.getOutAccountCode().equals(form.getInAccountCode())){
                                                        ret.put("99","转入账户不能同转出账户相同!");
//                                                        ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "inAccountId", "merchant.transfer.inAccountId.same","转入账户不能同转出账户相同!");
                                                    }
                                                }else{
                                                    ret.put("99","转出金额错误!");
//                                                    ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "transferAmount", "merchant.transfer.transferAmount.amount","转出金额错误!");
                                                }
                                            } catch (Exception e) {
                                                logger.error(e.getMessage());
                                                ret.put("99","汇付余额信息校验失败!");
//                                                ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "transferAmount", "merchant.transfer.transferAmount.success.exception","汇付余额信息校验失败!");
                                            }
                                        } else {
                                            ret.put("99","汇付余额信息为空!");
//                                            ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "transferAmount", "merchant.transfer.transferAmount.success.empty","汇付余额信息为空!");
                                        }
                                    } else {
                                        ret.put("99","子账户汇付余额查询失败!");
//                                        ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "transferAmount", "merchant.transfer.transferAmount.fail","子账户汇付余额查询失败!");
                                    }
                                }else{
                                    ret.put("99","转账金额不能为空!");
//                                    ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "transferAmount", "merchant.transfer.transferAmount.empty","转账金额不能为空!");
                                }
                            }else{
                                ret.put("99","配置错误，此账户不能转入!");
//                                ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "inAccountId", "merchant.transfer.inAccountId.type","配置错误，此账户不能转入!");
                            }
                        }else{
                            ret.put("99","未查询到转入子账户信息!");
//                            ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "inAccountId", "merchant.transfer.inAccountId.null","未查询到转入子账户信息!");
                        }
                    }else{
                        ret.put("99","转入账号不能为空!");
//                        ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "inAccountId", "merchant.transfer.inAccountId.empty","转入账号不能为空!");
                    }
                }else{
                    ret.put("99","配置错误，此账户不能转出!");
//                    ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "outAccountId", "merchant.transfer.outAccountId.type","配置错误，此账户不能转出!");
                }
            }else{
                ret.put("99","未查询到转出子账户信息!");
//                ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "outAccountId", "merchant.transfer.outAccountId.null","未查询到转出子账户信息!");
            }
        } else {
            ret.put("99","转出账号不能为空!");
//            ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "outAccountId", "merchant.transfer.outAccountId.empty","转出账号不能为空!");
        }
        // 说明
        //ValidatorFieldCheckUtil.validateMaxLength("remark", form.getRemark(), 50, true);
        return ret;
    }

    @Override
    public int updateMerchantTransfer(String orderId, int status,String message) {
        return amTradeClient.updateMerchantTransfer(orderId,status,message);
    }


}
