package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/13
 * @Description: 批次还款交易明细查询显示VO
 */
public class BatchBorrowRepayBankInfoVO extends BaseVO {

    private String authCode;// 授权码
    private String txState ;// 交易状态
    private String orderId ;// 还款订单号
    private BigDecimal txAmount;// 操作金额
    private String forAccountId;// 借款人银行账户
    private String productId;// 标的号
    private String fileMsg;//错误描述
    private String txMsg ;// 交易信息
    
    public String getTxMsg() {
		return txMsg;
	}

	public void setTxMsg(String txMsg) {
		this.txMsg = txMsg;
	}

	public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getTxState() {
        return txState;
    }

    public void setTxState(String txState) {
        this.txState = txState;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public String getForAccountId() {
        return forAccountId;
    }

    public void setForAccountId(String forAccountId) {
        this.forAccountId = forAccountId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getFileMsg() {
        return fileMsg;
    }

    public void setFileMsg(String fileMsg) {
        this.fileMsg = fileMsg;
    }
}
