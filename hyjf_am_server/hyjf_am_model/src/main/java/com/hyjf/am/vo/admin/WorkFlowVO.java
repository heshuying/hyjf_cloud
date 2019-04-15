package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiehuili on 2019/4/12.
 */
public class WorkFlowVO extends BaseVO implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    /**
     * 业务id
     */
    private Integer businessId;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 是否需要审核(1.是，2.否)
     */
    private Integer auditFlag;
    /**
     * 是否需要审核(是，否)
     */
    private String auditFlagString;
    /**
     * 流程节点数
     */
    private Integer flowNode;
    /**
     * 邮件预警人
     */
    private String mailWarningUser;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新操作人
     */
    private String updateUser;
    /**
     * 流程状态（1.正常，2.异常）
     */
    private String processStatus;

    /**
     * 流程状态（正常，异常）
     */
    private String processStatusString;
    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 流程节点
     */
    public List<WorkFlowNodeVO> flowNodes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getAuditFlagString() {
        return auditFlagString;
    }

    public void setAuditFlagString(String auditFlagString) {
        this.auditFlagString = auditFlagString;
    }

    public Integer getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(Integer flowNode) {
        this.flowNode = flowNode;
    }

    public String getMailWarningUser() {
        return mailWarningUser;
    }

    public void setMailWarningUser(String mailWarningUser) {
        this.mailWarningUser = mailWarningUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getProcessStatusString() {
        return processStatusString;
    }

    public void setProcessStatusString(String processStatusString) {
        this.processStatusString = processStatusString;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public List<WorkFlowNodeVO> getFlowNodes() {
        return flowNodes;
    }

    public void setFlowNodes(List<WorkFlowNodeVO> flowNodes) {
        this.flowNodes = flowNodes;
    }
}
