package com.hyjf.am.trade.service.front.repay;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.trade.bean.repay.ProjectBean;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
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
public interface RepayManageService {
    BigDecimal selectUserRepayFeeWaitTotal(Integer userId);

    BigDecimal selectOrgRepayFeeWaitTotal(Integer userId);

    BigDecimal selectRepayOrgRepaywait(Integer userId);

    List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean);

    Integer selectRepayCount(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean);

    Integer selectOrgRepayCount(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean);

    Integer selectOrgRepayedCount(RepayListRequest requestBean);

    int countUserRepayedListTotal(RepayListRequest projectBean);// 查询记录总数（个人和机构）
    List<RepayListCustomizeVO> searchUserRepayList(RepayListRequest projectBean, int limitStart, int limitEnd);

    boolean updateRepayMoney(RepayBean repay, BankCallBean bean) throws Exception;

    boolean updateBorrowCreditStautus(String borrowNid);

    /**
     * 查询待还款项目信息
     *
     * @param userId
     * @param userName
     * @param roleId
     * @param borrowNid
     * @return
     */
    Borrow searchRepayProject(int userId, String userName, String roleId, String borrowNid);

    /**
     * 校验还款是否重复
     * @param userId
     * @param borrowNid
     * @return
     */
    boolean checkRepayInfo(Integer userId, String borrowNid);

    /**
     * 插入冻结日志信息
     * @param userId
     * @param orderId
     * @param account
     * @param borrowNid
     * @param repayTotal
     * @param userName
     */
    void insertRepayFreezeLof(Integer userId, String orderId, String account, String borrowNid, BigDecimal repayTotal,
                                     String userName);

    /**
	 * 删除还款冻结日志
	 * @param orderId
	 */
	void deleteFreezeTempLogs(String orderId);

    /**
     * 统计用户的相应的还款总额 单期
     *
     * @param userId
     * @param borrowNid
     * @param borrowApr
     * @param borrowStyle
     * @param periodTotal
     * @return
     * @throws ParseException
     */
    BigDecimal searchRepayTotal(int userId, Borrow borrow) throws ParseException;

    /**
     * 计算相应的未分期还款信息
     *
     * @param userId
     * @param borrowNid
     * @param borrowApr
     * @param borrowStyle
     * @param periodTotal
     * @return
     * @throws ParseException
     */
    RepayBean calculateRepay(int userId, Borrow borrow) throws ParseException;

    /**
	 * 根据项目id查询相应的用户的待还款信息
	 *
	 * @param borrowNid
	 * @return
	 */
	List<BorrowRecover> searchBorrowRecover(String borrowNid);

    /**
     * 统计用户的相应还款总额，分期
     * @param userId
     * @param borrow
     * @param borrowApr
     * @param borrowStyle
     * @param periodTotal
     * @return
     * @throws ParseException
     */
    BigDecimal searchRepayByTermTotal(int userId, Borrow borrow, BigDecimal borrowApr, String borrowStyle, int periodTotal) throws ParseException;

    /**
     * 计算多期的总的还款信息
     * @param userId
     * @param borrow
     * @return
     * @throws ParseException
     */
    RepayBean calculateRepayByTerm(int userId, Borrow borrow) throws ParseException;

    boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception;
}
