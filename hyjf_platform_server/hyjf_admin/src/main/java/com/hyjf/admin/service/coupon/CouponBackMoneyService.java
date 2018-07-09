package com.hyjf.admin.service.coupon;

import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;

import java.util.List;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/06 15:17
 */
public interface CouponBackMoneyService {
    /**
     * 汇直投代金券回款总数
     * @param couponBackMoneyCustomize 查询对象
     * @return Integer
     */
    Integer countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投代金券回款列表分页数据
     * @param couponBackMoneyCustomize 查询对象
     * @return List<CouponBackMoneyCustomize>
     */
    List<CouponBackMoneyCustomize> getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投应还本息总额
     * @param couponBackMoneyCustomize 查询对象
     * @return String
     */
    String queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投优惠券回款应回款总金额
     * @param couponBackMoneyCustomize 查询对象
     * @return String
     */
    String queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投体验金回款总数
     * @param couponBackMoneyCustomize 查询对象
     * @return Integer
     */
    Integer countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投验金回款列表分页数据
     * @param couponBackMoneyCustomize 查询对象
     * @return List<CouponBackMoneyCustomize>
     */
    List<CouponBackMoneyCustomize> getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize);
    /**
     * 汇直投加息券回款总数
     * @param couponBackMoneyCustomize 查询对象
     * @return Integer
     */
    Integer countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投加息券回款列表分页数据
     * @param couponBackMoneyCustomize 查询对象
     * @return List<CouponBackMoneyCustomize>
     */
    List<CouponBackMoneyCustomize> getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize);
}
