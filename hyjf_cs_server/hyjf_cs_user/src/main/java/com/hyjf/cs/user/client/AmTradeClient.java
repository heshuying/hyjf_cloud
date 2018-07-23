/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client;

import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;
import com.hyjf.am.vo.trade.ProductSearchForPageVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmTradeClient, v0.1 2018/6/20 12:46
 */
public interface AmTradeClient {

    HjhInstConfigVO selectInstConfigByInstCode(String instCode);

    AccountVO getAccount(Integer userId);

    List<RecentPaymentListCustomizeVO> selectRecentPaymentList(Integer userId);
    /**
     * 根据userId获得填充userPortrait的info --用户画像定时任务用
     * */
    List<BatchUserPortraitQueryVO> searchInfoForUserPortrait(String userIds);

    Integer countCouponValid(Integer userId);

    List<CouponUserListCustomizeVO> selectCouponUserList(Map<String,Object> mapParameter);

    /**
     * 获取账户信息通过userId范围
     * @param ids
     * @return
     */
    List<AccountVO> getAccountByUserIds(List<Integer> ids);

    /**
     * 获取投资人本金信息
     * @param productSearchForPage
     * @return
     */
    ProductSearchForPageVO selectUserPrincipal(ProductSearchForPageVO productSearchForPage);
}
