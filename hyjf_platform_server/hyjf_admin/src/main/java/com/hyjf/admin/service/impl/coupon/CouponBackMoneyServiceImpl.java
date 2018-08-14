package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.client.AmTradeClient;
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
    private AmTradeClient amTradeClient;

    @Override
    public Integer countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHztDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHztDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public String queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryHztInvestTotal(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAttrbute();
        }
        return null;
    }

    @Override
    public String queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAmountTotal();
        }
        return null;
    }

    @Override
    public Integer countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHztTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHztTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public Integer countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHztJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHztJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public Integer countRecordHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHjhDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHjhDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public String queryHjhInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryHjhInvestTotal(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAttrbute();
        }
        return null;
    }

    @Override
    public String queryHjhRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAmountTotal();
        }
        return null;
    }

    @Override
    public Integer countRecordHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHjhTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHjhTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public Integer countRecordHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHjhJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHjhJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }
}
