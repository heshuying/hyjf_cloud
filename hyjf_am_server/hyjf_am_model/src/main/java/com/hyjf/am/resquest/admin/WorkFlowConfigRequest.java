package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author xiehuili on 2019/4/12.
 */
@ApiModel(value="工作流业务流程配置",description="工作流业务流程配置")
public class WorkFlowConfigRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;
    //业务流程id
    private Integer id;
    /**
     * 业务名称id
     */
    private Integer businessId;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 是否需要审核
     */
    private Integer auditFlag;
    /**
     * 流程状态
     */
    private Integer processStatus;
    /**
     * 创建开始时间
     */
    private String createStartTime;
    /**
     * 创建结束时间
     */
    private String createEndTime;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
