package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.client.CouponTenderClient;
import com.hyjf.admin.service.coupon.CouponTenderHjhService;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVo;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/06 11:17
 */
@Service
public class CouponTenderHjhServiceImpl implements CouponTenderHjhService {
    @Autowired
    private CouponTenderClient couponTenderClient;

    @Override
    public Integer countRecord(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.countRecordHjh(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public String queryInvestTotalHjh(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.queryInvestTotalHjh(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAmountTotal();
        }
        return null;
    }

    @Override
    public List<CouponTenderCustomize> getRecordList(CouponTenderRequest couponTenderRequest) {
        couponTenderRequest.setLimitStart(couponTenderRequest.getLimitStart());
        couponTenderRequest.setLimitEnd(couponTenderRequest.getLimitEnd());
        CouponTenderResponse couponTenderResponse = couponTenderClient.getRecordListHjh(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getResultList();
        }
        return null;
    }

    @Override
    public CouponTenderDetailVo getCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.getHjhCouponTenderDetailCustomize(paramMap);
        if(null != couponTenderResponse){
            CouponTenderDetailVo couponTenderDetailVo = couponTenderResponse.getDetail();
            if(null != couponTenderDetailVo){
                if("1".equals(couponTenderDetailVo.getCouponContent())){
                    couponTenderDetailVo.setCouponContent(couponTenderDetailVo.getUserContent());
                }else if("2".equals(couponTenderDetailVo.getCouponContent())){
                    Integer activityId = couponTenderDetailVo.getActivityId();
                    if(null != activityId){
                        CouponTenderResponse couponTenderResponse1 = couponTenderClient.getActivityById(activityId);
                        couponTenderDetailVo.setCouponContent(couponTenderResponse1.getAttrbute());
                    }
                }else if("3".equals(couponTenderDetailVo.getCouponContent())){
                    couponTenderDetailVo.setCouponContent("欢迎礼包");
                }else{
                    couponTenderDetailVo.setCouponContent("");
                }
                String userId = couponTenderDetailVo.getGrantWay();
                if(null != userId && "40".equals(userId)){
                    couponTenderDetailVo.setGrantWay("系统");
                }else{
                    CouponTenderResponse couponTenderResponse2 = couponTenderClient.getAdminUserByUserId(userId);
                    couponTenderDetailVo.setGrantWay(couponTenderResponse2.getAttrbute());
                }
            }
            return couponTenderDetailVo;
        }
        return null;
    }

    @Override
    public List<CouponRecoverVo> getCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.getHjhCouponRecoverCustomize(paramMap);
        if(null != couponTenderResponse){
            return couponTenderResponse.getCouponRecoverList();
        }
        return null;
    }
}
