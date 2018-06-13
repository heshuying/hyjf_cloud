/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fuqiang
 * @version BorrowProjectRepayVO, v0.1 2018/6/11 18:28
 */
public class BorrowProjectRepayVO extends BaseVO implements Serializable {
    private Integer id;

    private String borrowClass;

    private String repayMethod;

    private String methodName;

    private String delFlag;

    private String remark;

    private String createGroupId;

    private String createUserId;

    private Date createTime;

    private String updateGroupId;

    private String updateUserId;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowClass() {
        return borrowClass;
    }

    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass == null ? null : borrowClass.trim();
    }

    public String getRepayMethod() {
        return repayMethod;
    }

    public void setRepayMethod(String repayMethod) {
        this.repayMethod = repayMethod == null ? null : repayMethod.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateGroupId() {
        return createGroupId;
    }

    public void setCreateGroupId(String createGroupId) {
        this.createGroupId = createGroupId == null ? null : createGroupId.trim();
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateGroupId() {
        return updateGroupId;
    }

    public void setUpdateGroupId(String updateGroupId) {
        this.updateGroupId = updateGroupId == null ? null : updateGroupId.trim();
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BorrowProjectRepayVO{" +
                "id=" + id +
                ", borrowClass='" + borrowClass + '\'' +
                ", repayMethod='" + repayMethod + '\'' +
                ", methodName='" + methodName + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", remark='" + remark + '\'' +
                ", createGroupId='" + createGroupId + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", createTime=" + createTime +
                ", updateGroupId='" + updateGroupId + '\'' +
                ", updateUserId='" + updateUserId + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
