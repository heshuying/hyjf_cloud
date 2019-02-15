package com.hyjf.cs.trade.service.aems.trusteePay;

import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

public interface AemsTrusteePayService extends BaseTradeService {
    
    /**
     * 根据电子账户号查询用户开户信息
     * 
     * @param accountId
     * @return
     */
    BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId);

    /**
     * 
     * 检查标的是否存在
     * @author sss
     * @param productId
     * @return
     */
    BorrowAndInfoVO selectBorrowByProductId(String productId);

    /**
     * 
     * 受托支付请求成功后修改表字段
     * @author sunss
     * @param bean
     */
    Boolean updateTrusteePaySuccess(BankCallBean bean);


    /**
     * 
     * 根据机构编号和收款人电子帐户 查询
     * @author sss
     * @param instCode
     * @param receiptAccountId
     * @return
     */
    STZHWhiteListVO getSTZHWhiteListByCode(String instCode, String receiptAccountId);

    /**
     * 
     * 根据用户ID查询企业用户信息
     * @author sss
     * @param userId
     * @return
     */
    CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId);

    BankCallBean queryTrusteePayState(String accountId, String productId, String valueOf);

    /** 返回码错误信息 */
    public String getBankRetMsg(String retCode);
    
}
