package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SponsorLog implements Serializable {
    private Integer id;

    /**
     * ���
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * ԭʼ������id
     *
     * @mbggenerated
     */
    private Integer oldSponsorId;

    /**
     * ԭʼ������username
     *
     * @mbggenerated
     */
    private String oldSponsor;

    /**
     * �µ�����id
     *
     * @mbggenerated
     */
    private Integer newSponsorId;

    /**
     * �µ�����username
     *
     * @mbggenerated
     */
    private String newSponsor;

    /**
     * 0��ʼ1�޸ĳɹ�2�޸�ʧ��
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 0��ʼ 1�ر� 2ɾ��
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * �����û���
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * �����û���
     *
     * @mbggenerated
     */
    private String updateUserName;

    /**
     * ����ʱ��
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * ����ʱ��
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getOldSponsorId() {
        return oldSponsorId;
    }

    public void setOldSponsorId(Integer oldSponsorId) {
        this.oldSponsorId = oldSponsorId;
    }

    public String getOldSponsor() {
        return oldSponsor;
    }

    public void setOldSponsor(String oldSponsor) {
        this.oldSponsor = oldSponsor == null ? null : oldSponsor.trim();
    }

    public Integer getNewSponsorId() {
        return newSponsorId;
    }

    public void setNewSponsorId(Integer newSponsorId) {
        this.newSponsorId = newSponsorId;
    }

    public String getNewSponsor() {
        return newSponsor;
    }

    public void setNewSponsor(String newSponsor) {
        this.newSponsor = newSponsor == null ? null : newSponsor.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
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