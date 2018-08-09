package com.hyjf.cs.trade.service.repay;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequest;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @author hesy
 * @version RepayManageService, v0.1 2018/6/23 18:02
 */
public interface RepayManageService extends BaseTradeService {
    BigDecimal getUserRepayFeeWaitTotal(Integer userId);

    BigDecimal getOrgRepayFeeWaitTotal(Integer userId);

    BigDecimal getOrgRepayWaitTotal(Integer userId);

    void checkForRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean);

    Integer selectRepayCount(RepayListRequest requestBean);

    Integer selectOrgRepayCount(RepayListRequest requestBean);

    Integer selectOrgRepayedCount(RepayListRequest requestBean);

    boolean checkPassword(Integer userId, String password);

    ProjectBean searchRepayProjectDetail(ProjectBean form) throws NumberFormatException, ParseException;

    RepayBean checkForRepayRequest(RepayRequest requestBean, WebViewUserVO user, int flag);

    Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean);

    boolean checkRepayInfo(Integer userId, String borrowNid);

    Integer addFreezeLog(Integer userId, String orderId, String account, String borrowNid, BigDecimal repayTotal,
                         String userName);

    Integer deleteFreezeLogByOrderId(String orderId);

    Boolean updateBorrowApicron(BorrowApicronVO apicronVO, Integer status);

    BorrowApicronVO selectBorrowApicron(String bankSeqNO);

    BankCallBean batchQuery(BorrowApicronVO apicron);
}
