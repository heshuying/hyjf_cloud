package com.hyjf.am.resquest.trade;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/17 10:23
 * @Description: CouponLoansBean
 */
public class CouponLoansBean {
    /**
     * 标的编号
     */
    String borrowNid;

    /**
     * 用户编号
     */
    Integer userId;
    /**
     * 投资编号
     */
    String nid;

    /**
     * 订单号
     */
    String orderId;

    /**
     * 优惠券投资订单号
     */
    String orderIdCoupon;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderIdCoupon() {
        return orderIdCoupon;
    }

    public void setOrderIdCoupon(String orderIdCoupon) {
        this.orderIdCoupon = orderIdCoupon;
    }
}
