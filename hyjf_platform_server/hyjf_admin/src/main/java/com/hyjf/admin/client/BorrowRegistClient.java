/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistClient, v0.1 2018/6/29 15:34
 */
public interface BorrowRegistClient {
    /**
     * 获取项目类型
     *
     * @return
     */
    List<BorrowProjectTypeVO> selectBorrowProjectList();

    /**
     * 获取还款方式
     *
     * @return
     */
    List<BorrowStyleVO> selectBorrowStyleList();

    /**
     * 获取标的备案列表count
     *
     * @param borrowRegistListRequest
     * @return
     */
    Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    List<BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 统计总额
     *
     * @param borrowRegistListRequest
     * @return
     */
    String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 根据编号获取borrow
     *
     * @param borrowNid
     * @return
     */
    BorrowVO selectBorrowByNid(String borrowNid);

    /**
     * 根据编号获取borrowInfo
     *
     * @param borrowNid
     * @return
     */
    BorrowInfoVO getBorrowInfoByNid(String borrowNid);

    /**
     * 根据userId查询User
     *
     * @param userId
     * @return
     */
    UserVO findUserById(int userId);

    /**
     * 根据userId查询开户信息
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO selectBankOpenAccountById(int userId);

    /**
     * 查询信托信息
     *
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId);

    /**
     * 更新标的信息
     *
     * @param borrowRegistRequest
     * @return
     */
    int updateBorrowRegist(BorrowRegistRequest borrowRegistRequest);

    /**
     * 更新标的信息(受托支付备案)
     *
     * @param borrowRegistRequest
     * @return
     */
    int updateEntrustedBorrowRegist(BorrowRegistRequest borrowRegistRequest);
}
