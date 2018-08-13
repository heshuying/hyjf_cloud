package com.hyjf.cs.trade.service.repay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BatchRepayDataRequest;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequest;
import com.hyjf.am.resquest.trade.RepayRequestDetailRequest;
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
import java.util.Map;

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

    JSONObject getRepayDetailData(RepayRequestDetailRequest requestBean);

    ProjectBean searchRepayProjectDetail(ProjectBean form) throws NumberFormatException, ParseException;

    void checkForRepayRequest(String borrowNid, String password,  WebViewUserVO user, RepayBean repayBean);

    void checkForRepayRequestOrg(String borrowNid, String password,  WebViewUserVO user, RepayBean repayBean, int flag);

    RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay);

    Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean);

    Boolean updateBorrowCreditStautus(String borrowNid);

    boolean checkRepayInfo(Integer userId, String borrowNid);

    Integer addFreezeLog(Integer userId, String orderId, String account, String borrowNid, BigDecimal repayTotal,
                         String userName);

    Integer deleteFreezeLogByOrderId(String orderId);

    Boolean updateBorrowApicron(BorrowApicronVO apicronVO, Integer status);

    BorrowApicronVO selectBorrowApicron(String bankSeqNO);

    BankCallBean batchQuery(BorrowApicronVO apicron);

    ProjectBean getOrgBatchRepayData(String userId, String startDate, String endDate);
}
