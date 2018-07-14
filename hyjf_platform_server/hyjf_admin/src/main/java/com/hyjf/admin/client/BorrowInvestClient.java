/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.am.vo.admin.BorrowListCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.user.UserInfoVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestClient, v0.1 2018/7/10 9:18
 */
public interface BorrowInvestClient {
    /**
     * 投资明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomizeVO> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细列表合计
     *
     * @param borrowInvestRequest
     * @return
     */
    String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细导出列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomizeVO> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 获取银行返回码
     *
     * @param retCode
     * @return
     */
    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

    /**
     * 获取用户投资协议
     *
     * @param nid
     * @return
     */
    TenderAgreementVO selectTenderAgreement(String nid);

    /**
     * 标的放款记录
     *
     * @param userId
     * @param borrowNid
     * @param nid
     * @return
     */
    BorrowRecoverVO selectBorrowRecover(Integer userId, String borrowNid, String nid);

    /**
     * 根据ID获取userInfo
     *
     * @param userId
     * @return
     */
    UserInfoVO findUserInfoById(Integer userId);

    /**
     * 获取借款列表
     *
     * @param borrowNid
     * @return
     */
    List<BorrowListCustomizeVO> selectBorrowList(String borrowNid);

    /**
     * 标的投资信息
     *
     * @param borrowInvestRequest
     * @return
     */
    List<WebUserInvestListCustomizeVO> selectUserInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 标的放款记录分期count
     *
     * @param borrowInvestRequest
     * @return
     */
    Integer countProjectRepayPlanRecordTotal(BorrowInvestRequest borrowInvestRequest);

    /**
     * 标的放款记录分期
     *
     * @param borrowInvestRequest
     * @return
     */
    List<WebProjectRepayListCustomizeVO> selectProjectRepayPlanList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 更新标的放款记录
     *
     * @param borrowInvestRequest
     * @return
     */
    Integer updateBorrowRecover(BorrowInvestRequest borrowInvestRequest);
}
