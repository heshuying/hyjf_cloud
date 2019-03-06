/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.exception;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.exception.BindCardExceptionService;
import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.vo.admin.BindCardExceptionCustomizeVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: BindCardExceptionServiceImpl, v0.1 2018/10/9 11:36
 */
@Service
public class BindCardExceptionServiceImpl extends BaseAdminServiceImpl implements BindCardExceptionService {

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public int getBindCardExceptionCount(BindCardExceptionRequest request) {
        return amAdminClient.getBindCardExceptionCount(request);
    }

    @Override
    public List<BindCardExceptionCustomizeVO> searchBindCardExceptionList(BindCardExceptionRequest request) {
        return amAdminClient.searchBindCardExceptionList(request);
    }

    @Override
    public void updateBindCard(Integer userId, String accountId) {
        String respCode = "";
        try {
            BankOpenAccountVO bankOpenAccount = amUserClient.getBankOpenAccountByUserId(userId);
            if (bankOpenAccount != null) {
                UserVO user = amUserClient.getUserByUserId(userId);
                // 调用汇付接口(4.2.2 用户绑卡接口)
                BankCallBean bean = new BankCallBean();
                bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
                bean.setTxCode(BankCallMethodConstant.TXCODE_CARD_BIND_DETAILS_QUERY);
                bean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
                bean.setBankCode(systemConfig.getBANK_BANKCODE());// 银行代码
                bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
                bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
                bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
                bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
                bean.setAccountId(bankOpenAccount.getAccount());// 存管平台分配的账号
                bean.setState("1"); // 查询状态 0-所有（默认） 1-当前有效的绑定卡
                bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
                bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
                bean.setLogUserId(String.valueOf(userId));
                // 调用汇付接口 4.4.11 银行卡查询接口
                BankCallBean bankCallBean = BankCallUtils.callApiBg(bean);
                respCode = bankCallBean == null ? "" : bankCallBean.getRetCode();

                // 如果接口调用成功
                if (BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {

                    String usrCardInfolist = bankCallBean.getSubPacks();
                    JSONArray array = JSONObject.parseArray(usrCardInfolist);
                    if (array != null && array.size() != 0) {
                        List<BankCardVO> bankCardList = new ArrayList<>();
                        for (int j = 0; j < array.size(); j++) {
                            JxBankConfigVO jxBankConfigVO = new JxBankConfigVO();
                            JSONObject obj = array.getJSONObject(j);
                            BankCardVO bank = new BankCardVO();
                            bank.setUserId(bankOpenAccount.getUserId());
                            bank.setUserName(user.getUsername());
                            bank.setStatus(1);// 默认都是1
                            bank.setCardNo(obj.getString("cardNo"));
                            // 根据银行卡号查询银行ID
                            String bankId = amConfigClient.queryBankIdByCardNo(obj.getString("cardNo"));
                            bank.setBankId(bankId == null ? 0 : Integer.valueOf(bankId));
                            if(bankId != null && !"".equals(bankId)){
                                jxBankConfigVO = amConfigClient.selectJxBankConfigByBankId(Integer.valueOf(bankId));
                            }
                            bank.setBank(bankId == null ? "" : jxBankConfigVO.getBankName());
                            // 银行联号
                            String payAllianceCode = "";
                            // 调用江西银行接口查询银行联号
                            BankCallBean payAllianceCodeQueryBean = this.payAllianceCodeQuery(obj.getString("cardNo"), userId);
                            if (payAllianceCodeQueryBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(payAllianceCodeQueryBean.getRetCode())) {
                                payAllianceCode = payAllianceCodeQueryBean.getPayAllianceCode();
                            }
                            // 如果此时银联行号还是为空,根据bankId查询本地存的银联行号
                            if (StringUtils.isBlank(payAllianceCode)) {
                                payAllianceCode = jxBankConfigVO.getPayAllianceCode();
                            }
                            bank.setPayAllianceCode(payAllianceCode);
                            SimpleDateFormat sdf = GetDate.yyyymmddhhmmss;
                            bank.setCreateTime(sdf.parse(obj.getString("txnDate") + obj.getString("txnTime")));
                            bank.setCreateUserId(userId);
                            //bank.setCreateUserName(user.getUsername());
                            bankCardList.add(bank);
                        }

                        BindCardExceptionRequest request = new BindCardExceptionRequest();
                        request.setBankCardVOList(bankCardList);
                        request.setUserId(bankOpenAccount.getUserId());
                        amAdminClient.updateBindCard(request);

                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("更新用户银行卡信息失败,用户ID:" + userId);
        }
    }
    private BankCallBean payAllianceCodeQuery(String cardNo, Integer userId) {
        BankCallBean bean = new BankCallBean();
        String channel = BankCallConstant.CHANNEL_PC;
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        bean.setVersion(BankCallConstant.VERSION_10);// 版本号
        bean.setTxCode(BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY);// 交易代码
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setAccountId(cardNo);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(orderDate);
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("联行号查询");
        bean.setLogClient(0);
        return BankCallUtils.callApiBg(bean);
    }
}
