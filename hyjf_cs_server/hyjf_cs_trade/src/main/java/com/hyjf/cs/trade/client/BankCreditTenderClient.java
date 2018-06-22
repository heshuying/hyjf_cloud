package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowWithBLOBsVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.util.List;

/**
 * 债转投资异常
 * @author jun
 * @since 20180619
 */
public interface BankCreditTenderClient {


    List<CreditTenderLogVO> selectCreditTenderLogs();

    List<CreditTenderVO> selectCreditTender(String assignNid);

    BankOpenAccountVO getBankOpenAccount(Integer userId);

    Boolean updateCreditTenderLog(CreditTenderLogVO creditTenderLog);

    CreditTenderLogVO selectCreditTenderLogByOrderId(String logOrderId);

    List<CreditTenderLogVO> selectByOrderIdAndUserId(String assignOrderId, Integer userId);

    boolean deleteByOrderIdAndUserId(String assignOrderId, Integer userId);

    AccountVO getAccount(int sellerUserId);

    BorrowWithBLOBsVO getBorrowByNid(String borrowNid);
}
