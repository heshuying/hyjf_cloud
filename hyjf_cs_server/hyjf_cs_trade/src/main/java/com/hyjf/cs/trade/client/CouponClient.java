package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;

import java.util.List;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 16:05
 */
public interface CouponClient {

	/**
	 * @Description 根据优惠券ID和用户ID查询优惠券
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 16:20
	 */
	CouponUserVO getCouponUser(Integer couponGrantId, Integer userId);

	/**
	 * 优惠券投资
	 * @param couponTender
	 * @return
	 */
    boolean updateCouponTender(CouponTenderVO couponTender);

	/**
	 * @Author walter.limeng
	 * @Description  获取汇计划投资列表（优惠券）
	 * @Date 10:37 2018/7/17
	 * @Param orderId 订单ID
	 * @return
	 */
    List<BorrowTenderCpnVO> getBorrowTenderCpnHjhList(String orderId);

	/**
	 * @Author walter.limeng
	 * @Description  优惠券单独投资时用
	 * @Date 10:47 2018/7/17
	 * @Param couponOrderId
	 * @return
	 */
	List<BorrowTenderCpnVO> getBorrowTenderCpnHjhCouponOnlyList(String couponOrderId);

	/**
	 * @Author walter.limeng
	 * @Description  更新放款状态(优惠券)
	 * @Date 10:52 2018/7/17
	 * @Param borrowTenderCpn
	 * @return 
	 */
	Integer updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn);

	/**
	 * @Author walter.limeng
	 * @Description  根据tenderNid查询放款优惠券总数
	 * @Date 13:54 2018/7/17
	 * @Param tenderNid
	 * @return
	 */
    int countByExample(String tenderNid);

    /**
     * @Author walter.limeng
     * @Description  更新还款期
     * @Date 14:15 2018/7/17
     * @Param tenderNid 投资订单编号
     * @Param currentRecoverFlg 0:非还款期，1;还款期
     * @Param period 期数
     * @return
     */
	Integer crRecoverPeriod(String tenderNid, int currentRecoverFlg, int period);
}
