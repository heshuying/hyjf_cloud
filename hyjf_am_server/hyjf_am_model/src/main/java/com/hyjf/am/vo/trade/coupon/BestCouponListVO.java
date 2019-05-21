package com.hyjf.am.vo.trade.coupon;

import java.text.NumberFormat;

/**
 * 最优优惠券vo
 * @author zhangyk
 * @date 2018/6/25 9:48
 */
public class BestCouponListVO  extends CouponConfigVO {

    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;
    //主键id
    private String userCouponId;

    //主键id
    private String couponUserCode;

    //金额初始化
    private String couponQuotaStr;
    //代金券预期收益
    private String couponInterest;
    //使用范围
    private String tenderQuotaRange;
    //金额初始化
    private String couponDescribe;
    //优惠券有效期
    private String endTime;
    //优惠券开始时间
    private String couponAddTime;

    private String delFlg;


    public String getUserCouponId() {
        return userCouponId;
    }
    public void setUserCouponId(String userCouponId) {
        this.userCouponId = userCouponId;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getCouponAddTime() {
        return couponAddTime;
    }
    public void setCouponAddTime(String couponAddTime) {
        this.couponAddTime = couponAddTime;
    }
    public String getCouponUserCode() {
        return couponUserCode;
    }
    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }
    public String getCouponQuotaStr() {
        return getCouponQuota().toString();
//        NumberFormat nf = NumberFormat.getInstance();
//        return nf.format(getCouponQuota());
    }
    public void setCouponQuotaStr(String couponQuotaStr) {
        this.couponQuotaStr = couponQuotaStr;
    }

    public String getCouponDescribe() {
        if(getCouponType() == 1){
            couponDescribe="体验金："+getCouponQuotaStr()+"元";
        }
        if(getCouponType() == 2){
            couponDescribe="加息券："+getCouponQuotaStr()+"%";
        }
        if(getCouponType() == 3){
            couponDescribe="代金券："+getCouponQuotaStr()+"元";
        }
        return couponDescribe;
    }
    public String getCouponInterest() {
        return couponInterest;
    }
    public void setCouponInterest(String couponInterest) {
        this.couponInterest = couponInterest;
    }
    public String getTenderQuotaRange() {

        if(getTenderQuotaType()==0||getTenderQuotaType()==null){
            tenderQuotaRange="出借金额不限";
        }else if(getTenderQuotaType()==1){
            tenderQuotaRange=getTenderQuotaMin()+"元~"+getTenderQuotaMax()+"元可用";
        }else if(getTenderQuotaType()==2){
            tenderQuotaRange="满"+getTenderQuota()+"元可用";
        }

        return tenderQuotaRange;
    }
    public void setTenderQuotaRange(String tenderQuotaRange) {
        this.tenderQuotaRange = tenderQuotaRange;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
}
