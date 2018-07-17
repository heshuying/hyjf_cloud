package com.hyjf.common.constants;

/**
 * 交易类常量
 * @author hesy
 * @version TradeConstant, v0.1 2018/7/7 15:43
 */
public class TradeConstant {
    /**标的状态7 */
    public static final Integer BORROW_STATUS_WITE_AUTHORIZATION = 7;
    /**是否受托支付 0否1 是 */
    public static final Integer ENTRUSTED_FLG = 1;

    /** 放款状态:放款校验成功 */
    public static final Integer STATUS_VERIFY_SUCCESS = 3;
    /** 放款状态:放款校验失败 */
    public static final Integer STATUS_VERIFY_FAIL = 4;
    /** 放款状态:放款失败 */
    public static final Integer STATUS_LOAN_FAIL = 5;
    /** 放款状态:放款成功 */
    public static final Integer STATUS_LOAN_SUCCESS = 6;
}
