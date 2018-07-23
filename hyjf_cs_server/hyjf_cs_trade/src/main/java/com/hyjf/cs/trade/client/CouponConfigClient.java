/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.TransferExceptionLogWithBLOBsVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;

import java.util.List;

/**
 * @author yaoy
 * @version CouponConfigClient, v0.1 2018/6/19 18:28
 */
public interface CouponConfigClient {
   CouponConfigVO selectCouponConfig(String couponCode);


   BestCouponListVO selectBestCoupon(MyCouponListRequest request);


   Integer countAvaliableCoupon(MyCouponListRequest request);

   /**
    * 查询汇计划最优优惠券
    * @param request
    * @return
    */
   BestCouponListVO selectHJHBestCoupon(MyCouponListRequest request);

   /**
    *
    * @param couponCode
    * @return
    */
   Integer checkCouponSendExcess(String couponCode);
   /**
    * 查询HJH可用优惠券数量
    * @param request
    * @return
    */
   Integer countHJHAvaliableCoupon(MyCouponListRequest request);

   /**
    * @Author walter.limeng
    * @Description  根据优惠券投资订单编号，取得优惠券信息
    * @Date 11:51 2018/7/17
    * @Param ordId
    * @return
    */
    CouponConfigVO getCouponConfig(String ordId);

    /**
     * @Author walter.limeng
     * @Description  取得体验金收益期限
     * @Date 14:30 2018/7/17
     * @Param tenderNid
     * @return
     */
    Integer getCouponProfitTime(String tenderNid);

    /**
     * @Author walter.limeng
     * @Description  保存CouponRecover
     * @Date 14:38 2018/7/17
     * @Param CouponRecoverVO
     * @return Integer 1:成功  ；0 失败
     */
   Integer insertSelective(CouponRecoverVO cr);

   /**
    * @Author walter.limeng
    * @Description  更新账户信息(投资人)
    * @Date 14:47 2018/7/17
    * @Param account
    * @return
    */
    int updateOfLoansTenderHjh(AccountVO account);

    /**
     * @Author walter.limeng
     * @Description  根据订单ID查询所有的优惠券还款
     * @Date 16:55 2018/7/17
     * @Param orderId
     * @return
     */
    List<CouponTenderCustomizeVO> getCouponTenderListHjh(String orderId);

    /**
     * @Author walter.limeng
     * @Description  更新couponRecover对象
     * @Date 9:31 2018/7/18
     * @Param cr
     * @return
     */
    CouponRecoverVO updateByPrimaryKeySelective(CouponRecoverVO cr);

    /**
     * @Author walter.limeng
     * @Description  根据recoverid查询交易记录
     * @Date 9:41 2018/7/18
     * @Param recoverId
     * @return
     */
    List<TransferExceptionLogVO> selectByRecoverId(int recoverId);

    /**
     * @Author walter.limeng
     * @Description  新增日志
     * @Date 9:52 2018/7/18
     * @Param transferExceptionLog
     * @return
     */
    Integer insertTransferExLog(TransferExceptionLogWithBLOBsVO transferExceptionLog);

    /**
     * @Author walter.limeng
     * @Description  根据borrowNid查询所有优惠券散标还款
     * @Date 16:44 2018/7/18
     * @Param borrowNid
     * @return
     */
    List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description  更新散标还款期数
     * @Date 17:06 2018/7/18
     * @Param tenderNid
     * @Param period
     * @return
     */
    Integer crRecoverPeriod(String tenderNid, int period);
}
