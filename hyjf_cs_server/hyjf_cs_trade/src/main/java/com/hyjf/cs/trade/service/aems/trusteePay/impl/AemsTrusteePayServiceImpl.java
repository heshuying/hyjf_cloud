package com.hyjf.cs.trade.service.aems.trusteePay.impl;

import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.aems.trusteePay.AemsTrusteePayService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AemsTrusteePayServiceImpl extends BaseTradeServiceImpl implements AemsTrusteePayService {


    @Autowired
    private SystemConfig systemConfig;


    /**
     * 根据电子账户号查询用户开户信息
     * 
     * @param accountId
     * @return
     */
    @Override
    public BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId) {
        BankOpenAccountVO bankOpenAccountVO = this.amUserClient.selectBankOpenAccountByAccountId(accountId);
        if (bankOpenAccountVO != null){
            return bankOpenAccountVO;
        }
        return null;
    }

    @Override
    public BorrowAndInfoVO selectBorrowByProductId(String productId) {
        return this.amTradeClient.getborrowByProductId(productId);
    }

    @Override
    public Boolean updateTrusteePaySuccess(BankCallBean bean) {
        String nid = bean.getProductId();
        boolean flag = this.amTradeClient.updateAemsTrusteePaySuccess(nid);
        return flag;
    }


    @Override
    public STZHWhiteListVO getSTZHWhiteListByCode(String instCode, String receiptAccountId) {

        STZHWhiteListVO sTZHWhiteListVO = this.amTradeClient.selectStzfWhiteList(instCode,receiptAccountId);
        if (sTZHWhiteListVO != null ) {
            return sTZHWhiteListVO;
        }
        return null;
    }

    @Override
    public CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId) {
        CorpOpenAccountRecordVO corpOpenAccountRecordVO = this.amUserClient.getCorpOpenAccountRecord(userId);
        if(corpOpenAccountRecordVO != null){
            return corpOpenAccountRecordVO;
        }
        return null;
    }

    @Override
    public String getBankRetMsg(String retCode) {
        //如果错误码不为空
        if (StringUtils.isNotBlank(retCode)) {
            BankReturnCodeConfigVO bankReturnCodeConfigVO = this.amUserClient.getBankReturnCodeConfig(retCode);
            if (bankReturnCodeConfigVO != null) {
                String retMsg = bankReturnCodeConfigVO.getErrorMsg();
                if (StringUtils.isNotBlank(retMsg)) {
                    return retMsg;
                } else {
                    return "请联系客服！";
                }
            } else {
                return "请联系客服！";
            }
        } else {
            return "操作失败！";
        }
    }

    @Override
    public BankCallBean queryTrusteePayState(String accountId, String productId, String userid) {
        BankCallBean selectbean = new BankCallBean();
        // 设置共通参数
        setCommonCall(selectbean);
        selectbean.setTxCode(BankCallConstant.TXCODE_TRUSTEE_PAY_QUERY);
        selectbean.setAccountId(accountId);// 电子账号
        selectbean.setProductId(productId); // 标的编号

        // 操作者ID
        selectbean.setLogUserId(userid);
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(userid)));
        selectbean.setLogClient(0);
        selectbean.setLogRemark("受托支付申请查询");
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        return selectbean;
    }

    private void setCommonCall(BankCallBean selectbean) {
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setInstCode(systemConfig.getBankInstcode());// 机构代码
        selectbean.setBankCode(systemConfig.getBankBankcode());// 银行代码
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
        selectbean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
    }
}
