package com.hyjf.am.trade.service.repay;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;

import java.util.List;

/**
 * 还款管理
 * @author hesy
 * @version RepayManageService, v0.1 2018/6/26 18:04
 */
public interface RepayManageService {
    List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean);

    Integer selectRepayCount(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean);

    Integer selectOrgRepayCount(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean);

    Integer selectOrgRepayedCount(RepayListRequest requestBean);
}
