package com.hyjf.am.trade.service.admin.coupon;

import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public interface CouponTenderService {

    /**
     * 汇直投根据条件获取总条数
     * @param request 查询条件对象
     * @return Integer
     */
    Integer countRecord(CouponTenderRequest request);
    /**
     * 汇直投根据条件获取投资总金额
     * @param request 查询条件对象
     * @return String
     */
    String queryInvestTotalHzt(CouponTenderRequest request);

    /**
     * 汇直投根据查询条件获取分页数据
     * @param request 查询条件对象
     * @return List<CouponTenderCustomize>
     */
    List<CouponTenderCustomize> getRecordList(CouponTenderRequest request);

    /**
     * 汇直投根据条件查询优惠券使用详情
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderDetailVo
     */
    CouponTenderDetailVo getCouponTenderDetailCustomize(Map<String,Object> paramMap);

    /**
     * 汇直投回款列表
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return List<CouponRecoverVo>
     */
    List<CouponRecoverVO> getCouponRecoverCustomize(Map<String,Object> paramMap);

    /**
     * 汇计划根据条件获取总条数
     * @param request 查询条件对象
     * @return Integer
     */
    Integer countHjhRecord(CouponTenderRequest request);
    /**
     * 汇计划根据条件获取投资总金额
     * @param request 查询条件对象
     * @return String
     */
    String queryInvestTotalHjh(CouponTenderRequest request);

    /**
     * 汇计划根据查询条件获取分页数据
     * @param request 查询条件对象
     * @return List<CouponTenderCustomize>
     */
    List<CouponTenderCustomize> getRecordListHjh(CouponTenderRequest request);

    /**
     * 汇计划根据条件查询优惠券使用详情
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderDetailVo
     */
    CouponTenderDetailVo getHjhCouponTenderDetailCustomize(Map<String,Object> paramMap);

    /**
     * 汇计划回款列表
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return List<CouponRecoverVo>
     */
    List<CouponRecoverVO> getHjhCouponRecoverCustomize(Map<String,Object> paramMap);

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

    /**
     * 汇计划代金券回款总数
     * @param couponBackMoneyCustomize 查询对象
     * @return Integer
     */
    Integer countRecordHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划代金券回款列表分页数据
     * @param couponBackMoneyCustomize 查询对象
     * @return List<CouponBackMoneyCustomize>
     */
    List<CouponBackMoneyCustomize> getRecordListHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划应还本息总额
     * @param couponBackMoneyCustomize 查询对象
     * @return String
     */
    String queryHjhInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划优惠券回款应回款总金额
     * @param couponBackMoneyCustomize 查询对象
     * @return String
     */
    String queryHjhRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划体验金回款总数
     * @param couponBackMoneyCustomize 查询对象
     * @return Integer
     */
    Integer countRecordHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划验金回款列表分页数据
     * @param couponBackMoneyCustomize 查询对象
     * @return List<CouponBackMoneyCustomize>
     */
    List<CouponBackMoneyCustomize> getRecordListHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize);
    /**
     * 汇计划加息券回款总数
     * @param couponBackMoneyCustomize 查询对象
     * @return Integer
     */
    Integer countRecordHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 汇计划加息券回款列表分页数据
     * @param couponBackMoneyCustomize 查询对象
     * @return List<CouponBackMoneyCustomize>
     */
    List<CouponBackMoneyCustomize> getRecordListHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * count
     * @param request 查询对象
     * @return List<CouponBackMoneyCustomize>
     */
    Integer getRecordListCount(CouponTenderRequest request);
}
