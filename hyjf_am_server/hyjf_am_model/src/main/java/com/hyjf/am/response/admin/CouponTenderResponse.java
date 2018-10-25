package com.hyjf.am.response.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;

import java.util.List;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public class CouponTenderResponse extends AdminResponse<CouponTenderCustomize> {
    private String amountTotal;
    private CouponTenderDetailVo detail;
    private List<CouponRecoverVO> couponRecoverList;
    private String attrbute;
    private List<CouponBackMoneyCustomize> backMoneyCustomizeList;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        this.amountTotal = amountTotal;
    }

    public CouponTenderDetailVo getDetail() {
        return detail;
    }

    public void setDetail(CouponTenderDetailVo detail) {
        this.detail = detail;
    }

    public List<CouponRecoverVO> getCouponRecoverList() {
        return couponRecoverList;
    }

    public void setCouponRecoverList(List<CouponRecoverVO> couponRecoverList) {
        this.couponRecoverList = couponRecoverList;
    }

    public String getAttrbute() {
        return attrbute;
    }

    public void setAttrbute(String attrbute) {
        this.attrbute = attrbute;
    }

    public List<CouponBackMoneyCustomize> getBackMoneyCustomizeList() {
        return backMoneyCustomizeList;
    }

    public void setBackMoneyCustomizeList(List<CouponBackMoneyCustomize> backMoneyCustomizeList) {
        this.backMoneyCustomizeList = backMoneyCustomizeList;
    }
}
