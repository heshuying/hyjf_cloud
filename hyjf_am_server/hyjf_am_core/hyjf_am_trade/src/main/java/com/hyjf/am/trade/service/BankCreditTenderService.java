/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.dao.model.customize.trade.TenderCreditCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.TenderToCreditDetailCustomize;
import com.hyjf.am.vo.trade.CreditPageVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.TenderCreditCustomizeVO;

/**
 * 银行债转异常处理
 * @author jun
 * @since 20180620
 */
public interface BankCreditTenderService {

	List<CreditTenderLog> selectCreditTenderLogs();

    List<CreditTender> selectCreditTender(String assignNid);

    int updateCreditTenderLog(CreditTenderLogVO creditTenderLog);

    CreditTenderLog selectCreditTenderLogByOrderId(String logOrderId);

    List<CreditTenderLog> selectByOrderIdAndUserId(String assignOrderId, Integer userId);

    int deleteByOrderIdAndUserId(String assignOrderId, Integer userId);

    CreditTender selectByAssignNidAndUserId(String assignNid, Integer userId);

    boolean updateTenderCreditInfo(CreditTenderRequest request) throws Exception;

	List<BorrowCredit> getBorrowCreditList(BorrowCreditRequest request);

    List<CreditTender> getCreditTenderList(CreditTenderRequest request);

	List<TenderToCreditDetailCustomize> selectWebCreditTenderDetailForContract(Map<String, Object> params);

    List<TenderToCreditDetailCustomize> selectHJHWebCreditTenderDetail(Map<String,Object> params);

    /**
     * 获取我要转让页面的金额
     * @param userId
     * @return
     */
    CreditPageVO selectCreditPageMoneyTotal(Integer userId);

    /**
     * 查询我可转让列表数量
     * @param request
     * @return
     */
    int searchCreditListCount(MyCreditListQueryRequest request);

    /**
     * 查询我可转让列表数量
     * @param request
     * @return
     */
    List<TenderCreditCustomize> searchCreditList(MyCreditListQueryRequest request);
}
