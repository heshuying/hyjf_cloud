/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.coupon.CouponConfigVO;

import java.io.Serializable;
import java.text.NumberFormat;
/**
 * @Description 最优优惠券信息
 * @Author sunss
 * @Date 2018/7/9 10:10
 */
public class UserCouponConfigCustomize extends CouponConfigVO implements Serializable {

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
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(getCouponQuota());
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
			tenderQuotaRange="投资金额不限";
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
    
	
	
	
}
