package com.hyjf.admin.service.coupon;

import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/06 10:17
 */
public interface CouponTenderHjhService {
    /**
     * 根据条件获取总条数
     * @param couponTenderRequest 查询条件对象
     * @return Integer
     */
    Integer countRecord(CouponTenderRequest couponTenderRequest);

    /**
     * 根据条件获取投资总金额
     * @param couponTenderRequest 查询条件对象
     * @return String
     */
    String queryInvestTotalHjh(CouponTenderRequest couponTenderRequest);

    /**
     * 根据查询条件获取分页数据
     * @param couponTenderRequest 查询条件对象
     * @return List<CouponTenderCustomize>
     */
    List<CouponTenderCustomize> getRecordList(CouponTenderRequest couponTenderRequest);

    /**
     * 根据条件查询优惠券使用详情
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderDetailVo
     */
    CouponTenderDetailVo getCouponTenderDetailCustomize(Map<String,Object> paramMap);

    /**
     * 回款列表
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return List<CouponRecoverVo>
     */
    List<CouponRecoverVO> getCouponRecoverCustomize(Map<String,Object> paramMap);

    /**
     * 处理优惠券使用平台，使用项目
     * @param detail CouponTenderDetailVo
     * @param map //操作平台
     * @return
     */
    CouponTenderDetailVo dealDetail(CouponTenderDetailVo detail,Map<String, String> map);
}
