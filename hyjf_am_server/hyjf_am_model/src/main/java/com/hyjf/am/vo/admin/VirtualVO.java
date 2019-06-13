package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author wenxin
 * @version VirtualVO, v0.1 2019/6/13
 */
public class VirtualVO extends BaseVO implements Serializable {
    private String status; // 充值是否成功的状态
    private String errorMessage="";// 失败信息
    private String supplierBizId="";// 虚拟商品充值流水号
    private String credits=""; // 积分

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSupplierBizId() {
        return supplierBizId;
    }

    public void setSupplierBizId(String supplierBizId) {
        this.supplierBizId = supplierBizId;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
}
