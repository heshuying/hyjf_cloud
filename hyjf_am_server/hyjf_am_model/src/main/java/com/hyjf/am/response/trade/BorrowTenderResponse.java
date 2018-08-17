package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;

import java.math.BigDecimal;

public class BorrowTenderResponse extends Response<BorrowTenderVO> {
    /** 投资笔数 */
    private Integer tenderCount;
    /** 汇直投金额 */
    private BigDecimal hztTenderPrice;
    /** 汇消费金额 */
    private BigDecimal hxfTenderPrice;
    /** 汇金理财投资金额 */
    private BigDecimal rtbTenderPrice;
    /** app渠道用户Android投资数 */
    private Integer tenderNumberAndroid;
    /** app渠道用户ios投资数 */
    private Integer tenderNumberIos;
    /** app渠道用户pc投资数 */
    private Integer tenderNumberPc;
    /** app渠道用户wechat投资数 */
    private Integer tenderNumberWechat;

    public Integer getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(Integer tenderCount) {
        this.tenderCount = tenderCount;
    }

    public BigDecimal getHztTenderPrice() {
        return hztTenderPrice;
    }

    public void setHztTenderPrice(BigDecimal hztTenderPrice) {
        this.hztTenderPrice = hztTenderPrice;
    }

    public BigDecimal getHxfTenderPrice() {
        return hxfTenderPrice;
    }

    public void setHxfTenderPrice(BigDecimal hxfTenderPrice) {
        this.hxfTenderPrice = hxfTenderPrice;
    }

    public BigDecimal getRtbTenderPrice() {
        return rtbTenderPrice;
    }

    public void setRtbTenderPrice(BigDecimal rtbTenderPrice) {
        this.rtbTenderPrice = rtbTenderPrice;
    }

    public Integer getTenderNumberAndroid() {
        return tenderNumberAndroid;
    }

    public void setTenderNumberAndroid(Integer tenderNumberAndroid) {
        this.tenderNumberAndroid = tenderNumberAndroid;
    }

    public Integer getTenderNumberIos() {
        return tenderNumberIos;
    }

    public void setTenderNumberIos(Integer tenderNumberIos) {
        this.tenderNumberIos = tenderNumberIos;
    }

    public Integer getTenderNumberPc() {
        return tenderNumberPc;
    }

    public void setTenderNumberPc(Integer tenderNumberPc) {
        this.tenderNumberPc = tenderNumberPc;
    }

    public Integer getTenderNumberWechat() {
        return tenderNumberWechat;
    }

    public void setTenderNumberWechat(Integer tenderNumberWechat) {
        this.tenderNumberWechat = tenderNumberWechat;
    }
}
