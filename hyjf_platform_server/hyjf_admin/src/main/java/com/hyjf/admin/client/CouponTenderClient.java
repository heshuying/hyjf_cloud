package com.hyjf.admin.client;

import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;

import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public interface CouponTenderClient {

    /**
     * 根据条件获取总条数
     * @param couponTenderRequest 查询条件对象
     * @return CouponTenderResponse
     */
    CouponTenderResponse countRecordHzt(CouponTenderRequest couponTenderRequest);
    /**
     * 根据条件获取投资总金额
     * @param couponTenderRequest 查询条件对象
     * @return CouponTenderResponse
     */
    CouponTenderResponse queryInvestTotalHzt(CouponTenderRequest couponTenderRequest);

    /**
     * 根据查询条件获取分页数据
     * @param couponTenderRequest 查询条件对象
     * @return CouponTenderResponse
     */
    CouponTenderResponse getRecordListHzt(CouponTenderRequest couponTenderRequest);
    /**
     * 根据条件查询优惠券使用详情
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderResponse
     */
    CouponTenderResponse getCouponTenderDetailCustomize(Map<String,Object> paramMap);

    /**
     * 回款列表
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderResponse
     */
    CouponTenderResponse getCouponRecoverCustomize(Map<String,Object> paramMap);

    /**
     * 根据活动ID获取活动title
     * @param activityId 活动ID
     * @return title
     */
    CouponTenderResponse getActivityById(Integer activityId);

    /**
     * 根据用户ID获取admin中用户名
     * @param userId 用户ID
     * @return String admimusername
     */
    CouponTenderResponse getAdminUserByUserId(String userId);

    /**
     * 根据条件获取总条数
     * @param couponTenderRequest 查询条件对象
     * @return CouponTenderResponse
     */
    CouponTenderResponse countRecordHjh(CouponTenderRequest couponTenderRequest);
    /**
     * 根据条件获取投资总金额
     * @param couponTenderRequest 查询条件对象
     * @return CouponTenderResponse
     */
    CouponTenderResponse queryInvestTotalHjh(CouponTenderRequest couponTenderRequest);

    /**
     * 根据查询条件获取分页数据
     * @param couponTenderRequest 查询条件对象
     * @return CouponTenderResponse
     */
    CouponTenderResponse getRecordListHjh(CouponTenderRequest couponTenderRequest);

    /**
     * 根据条件查询优惠券使用详情
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderResponse
     */
    CouponTenderResponse getHjhCouponTenderDetailCustomize(Map<String,Object> paramMap);

    /**
     * 回款列表
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderResponse
     */
    CouponTenderResponse getHjhCouponRecoverCustomize(Map<String,Object> paramMap);

    /**
     * 汇直投代金券汇款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投代金券汇款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投应还本息总额
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     *  汇直投优惠券回款应回款总金额
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投体验金汇款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投体验金汇款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投加息券汇款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇直投加息券汇款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划代金券汇款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse countRecordHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划代金券汇款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse getRecordListHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划应还本息总额
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse queryHjhInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     *  汇计划优惠券回款应回款总金额
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse queryHjhRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划体验金汇款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse countRecordHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划体验金汇款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse getRecordListHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划加息券汇款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse countRecordHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划加息券汇款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    CouponTenderResponse getRecordListHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize);
}
