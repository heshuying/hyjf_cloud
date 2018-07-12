package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/11
 * @Description: 批次中心批次放款银行实时放款查询接口显示返回VO
 */
public class BorrowRecoverBankInfoVo extends BaseVO implements Serializable{

    /**
     * 授权码
     */
    private String authCode;
    /**
     * 交易状态
     */
    private String txState ;
    /**
     * 还款订单号
     */
    private String orderId ;
    /**
     *操作金额
     */
    private String txAmount;
    /**
     * 借款人银行账户
     */
    private String forAccountId;
    /**
     *标的号
     */
    private String productId;
    /**
     *错误描述
     */
    private String fileMsg;
    /**
     * 借款人姓名
     */
    private String name;
    /**
     *      手续费金额
     */
    private String feeAmount;
    /**
     * 风险准备金
     */
    private String  riskAmount;
    /**
     * 响应代码
     */
    private String retCode;

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

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getRiskAmount() {
        return riskAmount;
    }

    public void setRiskAmount(String riskAmount) {
        this.riskAmount = riskAmount;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
}
