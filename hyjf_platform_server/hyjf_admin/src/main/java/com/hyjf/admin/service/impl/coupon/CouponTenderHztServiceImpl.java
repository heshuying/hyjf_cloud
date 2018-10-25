package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.coupon.CouponTenderHztService;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@Service
public class CouponTenderHztServiceImpl implements CouponTenderHztService {
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmMarketClient amMarketClient;
    @Autowired
    private AmConfigClient amConfigClient;
    @Override
    public Integer countRecord(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHzt(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public String queryInvestTotalHzt(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryInvestTotalHzt(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAmountTotal();
        }
        return null;
    }

    @Override
    public List<CouponTenderCustomize> getRecordList(CouponTenderRequest couponTenderRequest) {
        couponTenderRequest.setLimitStart(couponTenderRequest.getLimitStart());
        couponTenderRequest.setLimitEnd(couponTenderRequest.getLimitEnd());
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHzt(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getResultList();
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordExport(CouponTenderRequest couponTenderRequest) {
        couponTenderRequest.setLimitStart(couponTenderRequest.getLimitStart());
        couponTenderRequest.setLimitEnd(couponTenderRequest.getLimitEnd());
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHzt(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse;
        }
        return null;
    }

    @Override
    public CouponTenderDetailVo getCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getCouponTenderDetailCustomize(paramMap);
        if(null != couponTenderResponse){
            CouponTenderDetailVo couponTenderDetailVo = couponTenderResponse.getDetail();
            if(null != couponTenderDetailVo){
                if("1".equals(couponTenderDetailVo.getCouponContent())){
                    couponTenderDetailVo.setCouponContent(couponTenderDetailVo.getUserContent());
                }else if("2".equals(couponTenderDetailVo.getCouponContent())){
                    Integer activityId = couponTenderDetailVo.getActivityId();
                    if(null != activityId){
                        CouponTenderResponse couponTenderResponses = amMarketClient.getActivityById(activityId);
                        couponTenderDetailVo.setCouponContent(couponTenderResponses.getAttrbute());
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
                    CouponTenderResponse couponTenderResponses = amConfigClient.getAdminUserByUserId(userId);
                    couponTenderDetailVo.setGrantWay(couponTenderResponses.getAttrbute());
                }
            }
            return couponTenderDetailVo;
        }
        return null;
    }

    @Override
    public List<CouponRecoverVO> getCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getCouponRecoverCustomize(paramMap);
        if(null != couponTenderResponse){
            return couponTenderResponse.getCouponRecoverList();
        }
        return null;
    }

    @Override
    public List<CouponTenderCustomize> exoportRecordList(CouponTenderRequest couponTenderRequest) {
        couponTenderRequest.setLimitStart(null);
        couponTenderRequest.setLimitEnd(null);
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHzt(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getResultList();
        }
        return null;
    }
}
