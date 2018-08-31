package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/30  14:25
 */
public interface DataCenterCouponClient {

    /**
     * 查询数据中心优惠券数据
     * @param requestBean
     * @param type 优惠券类型
     * @return
     */
    List<DataCenterCouponCustomizeVO> getDataCenterCouponList(DadaCenterCouponRequestBean requestBean, String type);

    /**
     * 获取活动title
     * @param activityId 活动id
     * @return
     */
    String getActivityTitle(Integer activityId);

    /**
     * 获取代金券回款列表
     * @param dataCenterCouponCustomize
     * @return
     */
    List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize);

    /**
     * 获取加息券回款列表
     * @param dataCenterCouponCustomize
     * @return
     */
    List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize);
}
