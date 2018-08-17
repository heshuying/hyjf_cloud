package com.hyjf.am.trade.service.front.repay;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.trade.bean.repay.ProjectBean;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * 还款管理
 * @author hesy
 * @version RepayManageService, v0.1 2018/6/26 18:04
 */
public interface RepayManageService extends BaseService {
    BigDecimal selectUserRepayFeeWaitTotal(Integer userId);

    BigDecimal selectOrgRepayFeeWaitTotal(Integer userId);

    BigDecimal selectRepayOrgRepaywait(Integer userId);

    List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean);

    Integer selectRepayCount(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean);

    Integer selectOrgRepayCount(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean);

    Integer selectOrgRepayedCount(RepayListRequest requestBean);

    ProjectBean searchRepayProjectDetail(ProjectBean form, boolean isAllRepay) throws Exception;

    RepayBean calculateRepay(int userId, Borrow borrow) throws ParseException;

    boolean updateRepayMoney(RepayBean repay, BankCallBean bean, boolean isAllRepay) throws Exception;

    boolean updateBorrowCreditStautus(String borrowNid);

    abstract RepayBean searchRepayTotalV2(int userId, Borrow borrow) throws Exception;

    abstract RepayBean searchRepayPlanTotal(int userId, Borrow borrow) throws Exception;

    abstract RepayBean searchRepayByTermTotalV2(int userId, Borrow borrow, BigDecimal borrowApr, String borrowStyle, int periodTotal) throws Exception;

    boolean updateBorrowApicron(BorrowApicron apicron, int status);

    ProjectBean getOrgBatchRepayData(String userId, String startDate, String endDate);
}
