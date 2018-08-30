package com.hyjf.am.trade.controller.admin.datacenter;

import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.service.ActivityService;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.admin.DataCenterCouponCustomizeResponse;
import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.resquest.trade.DadaCenterCouponCustomizeRequest;
import com.hyjf.am.resquest.trade.DataCenterCouponRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.DataCenterCouponCustomize;
import com.hyjf.am.trade.service.admin.coupon.DataCenterCouponService;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinhui
 * @version DataCenterCouponController, v0.1 2018/7/19 14:28
 */
@RestController
@RequestMapping("/am-admin/datacenter/coupon")
public class DataCenterCouponController extends BaseController {
    @Autowired
    private DataCenterCouponService couponService;

    @Autowired
    private ActivityService activityService;

    /**
     * 查询数据中心优惠券列表
     * @param request
     * @return
     */
    @RequestMapping("/getdatacentercouponlist")
    public DataCenterCouponResponse getDataCenterCouponJXList(@RequestBody DataCenterCouponRequest request) {
        DataCenterCouponResponse response = new DataCenterCouponResponse();
        List<DataCenterCouponCustomize> list = couponService.getDataCenterCouponList(request);
        List<DataCenterCouponCustomizeVO> voList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            voList = CommonUtils.convertBeanList(list, DataCenterCouponCustomizeVO.class);
            response.setResultList(voList);
            return response;
        }
        return null;
    }

    /**
     * @auth walter.limeng
     * 根据活动ID获取活动title
     * @param activityId 活动ID
     * @return CouponTenderResponse
     */
    @RequestMapping("/hztgetactivitytitle/{activityId}")
    public CouponTenderResponse getActivityTitle(@PathVariable int activityId){
        ActivityList activityList = activityService.selectActivityList(activityId);
        CouponTenderResponse response = new CouponTenderResponse();
        if(null != activityList){
            response.setAttrbute(activityList.getTitle());
        }
        return response;
    }

    /**
     * 获取代金券列表
     * @param request
     * @return
     */
    @RequestMapping("/get_record_list_dj")
    public DataCenterCouponCustomizeResponse getRecordListDJ(@RequestBody DadaCenterCouponCustomizeRequest request) {
        DataCenterCouponCustomizeResponse response = new DataCenterCouponCustomizeResponse();
        List<DataCenterCouponCustomize> list = couponService.getRecordListDJ(request);
        if (!org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
            List<DataCenterCouponCustomizeVO> voList = CommonUtils.convertBeanList(list, DataCenterCouponCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取加息券列表
     * @param request
     * @return
     */
    @RequestMapping("/get_record_list_jx")
    public DataCenterCouponCustomizeResponse getRecordListJX(@RequestBody DadaCenterCouponCustomizeRequest request) {
        DataCenterCouponCustomizeResponse response = new DataCenterCouponCustomizeResponse();
        List<DataCenterCouponCustomize> list = couponService.getRecordListJX(request);
        if (!org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
            List<DataCenterCouponCustomizeVO> voList = CommonUtils.convertBeanList(list, DataCenterCouponCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

}
