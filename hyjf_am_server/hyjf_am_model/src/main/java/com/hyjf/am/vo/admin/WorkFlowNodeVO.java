package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author xiehuili on 2019/4/12.
 */
public class WorkFlowNodeVO  extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 业务id
     */
    private String businessId;
    /**
     * 流程顺序（第几审核人）
     */
    private String flowNodeNum;
    /**
     * 审核人
     */
    private String auditUser;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getFlowNodeNum() {
        return flowNodeNum;
    }

    public void setFlowNodeNum(String flowNodeNum) {
        this.flowNodeNum = flowNodeNum;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }
}
