package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class Admin implements Serializable {
    private Integer id;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    private String truename;

    /**
     * 部门ID
     *
     * @mbggenerated
     */
    private Integer departmentId;

    /**
     * 性别 0:男,1:女
     *
     * @mbggenerated
     */
    private String sex;

    /**
     * 密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 电话
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * QQ
     *
     * @mbggenerated
     */
    private String qq;

    /**
     * 邮箱
     *
     * @mbggenerated
     */
    private String email;

    /**
     * 传真
     *
     * @mbggenerated
     */
    private String fax;

    /**
     * 地址
     *
     * @mbggenerated
     */
    private String address;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private String state;

    /**
     * 登录次数
     *
     * @mbggenerated
     */
    private Integer loginNum;

    /**
     * 最后登录时间
     *
     * @mbggenerated
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     *
     * @mbggenerated
     */
    private String lastLoginIp;

    /**
     * 操作者
     *
     * @mbggenerated
     */
    private String operator;

    /**
     * 权限
     *
     * @mbggenerated
     */
    private String role;

    private Integer status;

    private Integer posttime;

    /**
     * 作废，每个用户可以有多个角色,关联hyjf_admin_and_role
     *
     * @mbggenerated
     */
    private Integer groupId;

    /**
     * ???
     *
     * @mbggenerated
     */
    private String accredit;

    /**
     * 删除FLAG
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPosttime() {
        return posttime;
    }

    public void setPosttime(Integer posttime) {
        this.posttime = posttime;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getAccredit() {
        return accredit;
    }

    public void setAccredit(String accredit) {
        this.accredit = accredit == null ? null : accredit.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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