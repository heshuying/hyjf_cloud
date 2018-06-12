/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.assetpush;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author fuqiang
 * @version STZHWhiteListVO, v0.1 2018/6/11 19:16
 */
public class STZHWhiteListVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private String accountid;

    private String mobile;

    private String customerName;

    private Integer stUserId;

    private String stUserName;

    private String stAccountid;

    private String stMobile;

    private String stCustomerName;

    private Integer state;

    private String createtime;

    private String createuser;

    private String updatetime;

    private String updateuser;

    private Boolean delFlg;

    private String approvalName;

    private String approvalTime;

    private String remark;

    private String instcode;

    private String instname;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid == null ? null : accountid.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getStUserId() {
        return stUserId;
    }

    public void setStUserId(Integer stUserId) {
        this.stUserId = stUserId;
    }

    public String getStUserName() {
        return stUserName;
    }

    public void setStUserName(String stUserName) {
        this.stUserName = stUserName == null ? null : stUserName.trim();
    }

    public String getStAccountid() {
        return stAccountid;
    }

    public void setStAccountid(String stAccountid) {
        this.stAccountid = stAccountid == null ? null : stAccountid.trim();
    }

    public String getStMobile() {
        return stMobile;
    }

    public void setStMobile(String stMobile) {
        this.stMobile = stMobile == null ? null : stMobile.trim();
    }

    public String getStCustomerName() {
        return stCustomerName;
    }

    public void setStCustomerName(String stCustomerName) {
        this.stCustomerName = stCustomerName == null ? null : stCustomerName.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName == null ? null : approvalName.trim();
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime == null ? null : approvalTime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode == null ? null : instcode.trim();
    }

    public String getInstname() {
        return instname;
    }

    public void setInstname(String instname) {
        this.instname = instname == null ? null : instname.trim();
    }

    @Override
    public String toString() {
        return "STZHWhiteListVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", accountid='" + accountid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", customerName='" + customerName + '\'' +
                ", stUserId=" + stUserId +
                ", stUserName='" + stUserName + '\'' +
                ", stAccountid='" + stAccountid + '\'' +
                ", stMobile='" + stMobile + '\'' +
                ", stCustomerName='" + stCustomerName + '\'' +
                ", state=" + state +
                ", createtime='" + createtime + '\'' +
                ", createuser='" + createuser + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", updateuser='" + updateuser + '\'' +
                ", delFlg=" + delFlg +
                ", approvalName='" + approvalName + '\'' +
                ", approvalTime='" + approvalTime + '\'' +
                ", remark='" + remark + '\'' +
                ", instcode='" + instcode + '\'' +
                ", instname='" + instname + '\'' +
                '}';
    }
}
