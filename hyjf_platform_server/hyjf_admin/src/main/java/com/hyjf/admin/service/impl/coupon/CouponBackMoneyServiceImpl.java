package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.client.CouponTenderClient;
import com.hyjf.admin.service.coupon.CouponBackMoneyService;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/06 15:17
 */
@Service
public class CouponBackMoneyServiceImpl implements CouponBackMoneyService {
    @Autowired
    private CouponTenderClient couponTenderClient;

    @Override
    public Integer countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.countRecordHztDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.getRecordListHztDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public String queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.queryHztInvestTotal(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAttrbute();
        }
        return null;
    }

    @Override
    public String queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAmountTotal();
        }
        return null;
    }

    @Override
    public Integer countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.countRecordHztTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.getRecordListHztTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public Integer countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.countRecordHztJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = couponTenderClient.getRecordListHztJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }
}
