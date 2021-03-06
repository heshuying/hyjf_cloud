package com.hyjf.admin.service.coupon;

import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/03 16:17
 */
public interface CouponTenderHztService {
    /**
     * 根据条件获取总条数
     * @param couponTenderHRequest 查询条件对象
     * @return Integer
     */
    Integer countRecord(CouponTenderRequest couponTenderHRequest);

    /**
     * 根据条件获取出借总金额
     * @param couponTenderHRequest 查询条件对象
     * @return String
     */
    String queryInvestTotalHzt(CouponTenderRequest couponTenderHRequest);

    /**
     * 根据查询条件获取分页数据
     * @param couponTenderHRequest 查询条件对象
     * @return List<CouponTenderCustomize>
     */
    List<CouponTenderCustomize> getRecordList(CouponTenderRequest couponTenderHRequest);

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
     * @Author walter.limeng
     * @Description  导出汇直投优惠券使用功能
     * @Date 15:53 2018/9/20
     * @Param couponTenderRequest
     * @return
     */
    List<CouponTenderCustomize> exoportRecordList(CouponTenderRequest couponTenderRequest);

    /**
     * @Author walter.wenxin
     * @Description  导出
     * @Date 15:53 2018/9/20
     * @Param couponTenderRequest
     * @return
     */
    CouponTenderResponse getRecordExport(CouponTenderRequest couponTenderRequest);
}
