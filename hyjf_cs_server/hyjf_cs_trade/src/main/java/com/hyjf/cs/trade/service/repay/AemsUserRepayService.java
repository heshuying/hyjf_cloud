package com.hyjf.cs.trade.service.repay;

import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.bean.AemsRepayResultBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.service.BaseTradeService;

public interface AemsUserRepayService extends BaseTradeService {

    /**
     * 根据accountId获取用户信息
     * @param accountId
     * @return
     */
    UserVO getUserByAccountId(String accountId);

    /**
     * 根据还款人id，项目编号查询相应的项目
     * @param userId
     * @param roleId
     * @param borrowNid
     * @return
     */
    BorrowInfoVO searchRepayProject(Integer userId, String roleId, String borrowNid);

    /**
     * 服务费授权和还款授权校验
     * @param userId
     * @return
     */
    boolean checkPaymentAuthStatus(Integer userId);

    /**
     * 获取还款信息
     * @param userId
     * @param roleId
     * @param borrowNid
     * @param isAllRepay
     * @return
     */
    RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay);

    /**
     * 借款人还款校验
     * @param productId
     * @param user
     * @param bankOpenAccountVO
     * @param repay
     */
    String checkForRepayRequest(String productId, UserVO user, BankOpenAccountVO bankOpenAccountVO,  RepayBean repay);

    AemsRepayResultBean getBalanceFreeze(UserVO user, String productId, RepayBean repay, String orderId, String account, AemsRepayResultBean resultBean, boolean isAllRepay);
}
