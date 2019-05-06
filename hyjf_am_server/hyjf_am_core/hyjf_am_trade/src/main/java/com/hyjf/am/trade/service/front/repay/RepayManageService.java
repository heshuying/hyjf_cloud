package com.hyjf.am.trade.service.front.repay;

import com.hyjf.am.resquest.trade.BatchRepayTotalRequest;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.user.WebUserRepayTransferRequest;
import com.hyjf.am.trade.bean.repay.ProjectBean;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.customize.WebUserRepayTransferCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserTransferBorrowInfoCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.trade.repay.SponsorLogCustomizeVO;
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

    BigDecimal selectOrgRepayWaitCurrent(RepayListRequest requestBean);

    Integer selectOrgRepayCount(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean);

    Integer selectOrgRepayedCount(RepayListRequest requestBean);

    ProjectBean searchRepayProjectDetail(ProjectBean form, boolean isAllRepay) throws Exception;

    RepayBean calculateRepay(int userId, Borrow borrow) throws ParseException;

    boolean updateRepayMoney(RepayBean repay, BankCallBean bean, boolean isAllRepay) throws Exception;

    boolean updateBorrowCreditStautus(String borrowNid);

    RepayBean searchRepayTotalV2(int userId, Borrow borrow) throws Exception;

    RepayBean searchRepayPlanTotal(int userId, Borrow borrow) throws Exception;

    RepayBean searchRepayByTermTotalV2(int userId, Borrow borrow, BigDecimal borrowApr, String borrowStyle, int periodTotal) throws Exception;

    boolean updateBorrowApicron(BorrowApicron apicron, int status);

    ProjectBean getOrgBatchRepayData(String userId, String startDate, String endDate);

    boolean checkRepayInfo(Integer userId, String borrowNid);

    void insertRepayFreezeLog(Integer userId, String orderId, String account, String borrowNid,
                              BigDecimal repayTotal, String userName);

    void insertRepayOrgFreezeLog(Integer userId, String orderId, String account, String borrowNid, RepayBean repay, String userName, boolean isAllRepay);

    void deleteFreezeTempLogs(String orderId);

    Borrow searchRepayProject(int userId, String userName, String roleId, String borrowNid);

    BigDecimal searchRepayTotal(int userId, Borrow borrow) throws ParseException;

    BigDecimal searchRepayByTermTotal(int userId, Borrow borrow, BigDecimal borrowApr, String borrowStyle, int periodTotal) throws Exception;

    RepayBean calculateRepayByTerm(int userId, Borrow borrow) throws Exception;

    WebUserTransferBorrowInfoCustomize getUserTransferBorrowInfo(String borrowNid);

    int selectUserRepayTransferDetailListTotal(String borrowNid,  String verificationFlag);

    /**
     * 用户待还债转详情列表
     * @return
     */
    List<WebUserRepayTransferCustomize> selectUserRepayTransferDetailList(WebUserRepayTransferRequest repayTransferRequest);

    /**
     *
     * 以hyjf开头:
     *      hyjf123456 的加密第5-8位
     *      hyjf13125253333 的加密第8-11位
     * 其他 :
     *      a**
     *          或
     *      张**
     * @param name
     * @return
     * @Author : huanghui
     */
    String usernameEncryption(String name);

    boolean getFailCredit(String borrowNid);

	Integer selectSponsorLogCount(RepayListRequest requestBean);

	List<SponsorLogCustomizeVO> selectSponsorLog(RepayListRequest requestBean);
}
