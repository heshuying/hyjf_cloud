package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by xiehuili on 2018/8/1.
 */
public class BorrowSendTypeVO extends BaseVO implements Serializable {
    private String sendCd;

    private String sendName;

    private Integer afterTime;

    private String remark;

    private Integer delFlag;

    private Date createTime;

    private Date updateTime;

    private String modifyFlag;

    private static final long serialVersionUID = 1L;

    public String getSendCd() {
        return sendCd;
    }

    public void setSendCd(String sendCd) {
        this.sendCd = sendCd == null ? null : sendCd.trim();
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName == null ? null : sendName.trim();
    }

    public Integer getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(Integer afterTime) {
        this.afterTime = afterTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }
}