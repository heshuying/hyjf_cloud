package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BorrowSendType implements Serializable {
    /**
     * 唯一标识(AUTO_BAIL,AUTO_FULL)
     *
     * @mbggenerated
     */
    private String sendCd;

    /**
     * 名称(自动发标时间间隔,自动复审时间间隔)
     *
     * @mbggenerated
     */
    private String sendName;

    /**
     * 发表时间
     *
     * @mbggenerated
     */
    private Integer afterTime;

    /**
     * 备注说明
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 删除状态
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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
}