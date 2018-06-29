package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.util.List;
import java.util.Map;

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

    AccountVO getAccount(int sellerUserId);

    void handle();

    CreditTenderVO selectByAssignNidAndUserId(String logOrderId, Integer userId);

    boolean updateTenderCreditInfo(CreditTenderRequest request);

	List<CreditTenderLogVO> getCreditTenderLogs(String logOrderId, Integer userId);

	List<BorrowCreditVO> getBorrowCreditList(String creditNid, int sellerUserId, String tenderOrderId);

	List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request);

    List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String,Object> params);

    List<TenderToCreditDetailCustomizeVO> selectHJHWebCreditTenderDetail(Map<String,Object> params);
}
