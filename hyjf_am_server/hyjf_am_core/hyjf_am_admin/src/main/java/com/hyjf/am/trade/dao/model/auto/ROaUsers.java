package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class ROaUsers implements Serializable {
    private Integer id;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userLogin;

    /**
     * 登录密码；oa_password加密
     *
     * @mbggenerated
     */
    private String userPass;

    /**
     * 姓名
     *
     * @mbggenerated
     */
    private String userRealname;

    /**
     * 登录邮箱
     *
     * @mbggenerated
     */
    private String userEmail;

    /**
     * 身份证号
     *
     * @mbggenerated
     */
    private String idcard;

    /**
     * 用户头像，相对于upload/avatar目录
     *
     * @mbggenerated
     */
    private String avatar;

    /**
     * 性别；0：保密，1：男；2：女
     *
     * @mbggenerated
     */
    private Short sex;

    /**
     * 户口省份
     *
     * @mbggenerated
     */
    private String accProvince;

    /**
     * 户口城市
     *
     * @mbggenerated
     */
    private String accCity;

    /**
     * 户口所在地址
     *
     * @mbggenerated
     */
    private String accAddress;

    /**
     * 部门
     *
     * @mbggenerated
     */
    private Integer departmentid;

    /**
     * 岗位名称
     *
     * @mbggenerated
     */
    private Integer positionid;

    /**
     * 角色等级: 2-员工/1-leader
     *
     * @mbggenerated
     */
    private Short level;

    /**
     * 1,兼职/2,正式员工
     *
     * @mbggenerated
     */
    private Integer temporary;

    /**
     * 是否转正 0：试用期 1：转正
     *
     * @mbggenerated
     */
    private Integer rework;

    /**
     * 入职时间或角色（正式/兼职）修改时间
     *
     * @mbggenerated
     */
    private String reworkTime;

    /**
     * Y/N
     *
     * @mbggenerated
     */
    private String ispart;

    /**
     * 试用期月薪
     *
     * @mbggenerated
     */
    private Integer payrollTry;

    /**
     * 转正月薪
     *
     * @mbggenerated
     */
    private Integer payroll;

    /**
     * 入职时间
     *
     * @mbggenerated
     */
    private Date entrydate;

    /**
     * 入职推荐人
     *
     * @mbggenerated
     */
    private String reference;

    /**
     * 学历
     *
     * @mbggenerated
     */
    private Integer education;

    /**
     * 毕业院校
     *
     * @mbggenerated
     */
    private String school;

    /**
     * 专业
     *
     * @mbggenerated
     */
    private String specialty;

    /**
     * 手机
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 最后登录ip
     *
     * @mbggenerated
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     *
     * @mbggenerated
     */
    private String lastLoginTime;

    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 开户行地址
     *
     * @mbggenerated
     */
    private String bankAddress;

    /**
     * 开户人姓名
     *
     * @mbggenerated
     */
    private String bankUser;

    /**
     * 银行卡账号
     *
     * @mbggenerated
     */
    private String bankNum;

    /**
     * 用户状态 E1:一级未审核;E2二级未审核 ；E已入职；E11一级审核未通过；E21二级审核未通过;E3取消入职；Q已离职；Q1离职一级未审核；Q2离职一级审核未通过；Q2离职二级未审核；Q22离职二级审核未通过；Q3取消离职
     *
     * @mbggenerated
     */
    private String userStatus;

    /**
     * 年龄
     *
     * @mbggenerated
     */
    private Integer age;

    /**
     * 平台账号
     *
     * @mbggenerated
     */
    private String hydName;

    /**
     * //汇盈贷账号ID
     *
     * @mbggenerated
     */
    private Integer hydId;

    /**
     * 用户类型，1:admin ;2:会员
     *
     * @mbggenerated
     */
    private Short userType;

    /**
     * 入职终审通过时间
     *
     * @mbggenerated
     */
    private Integer entrySuccessTime;

    /**
     * 离职终审通过时间
     *
     * @mbggenerated
     */
    private Integer leaveSuccessTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin == null ? null : userLogin.trim();
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass == null ? null : userPass.trim();
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname == null ? null : userRealname.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getAccProvince() {
        return accProvince;
    }

    public void setAccProvince(String accProvince) {
        this.accProvince = accProvince == null ? null : accProvince.trim();
    }

    public String getAccCity() {
        return accCity;
    }

    public void setAccCity(String accCity) {
        this.accCity = accCity == null ? null : accCity.trim();
    }

    public String getAccAddress() {
        return accAddress;
    }

    public void setAccAddress(String accAddress) {
        this.accAddress = accAddress == null ? null : accAddress.trim();
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getPositionid() {
        return positionid;
    }

    public void setPositionid(Integer positionid) {
        this.positionid = positionid;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Integer getTemporary() {
        return temporary;
    }

    public void setTemporary(Integer temporary) {
        this.temporary = temporary;
    }

    public Integer getRework() {
        return rework;
    }

    public void setRework(Integer rework) {
        this.rework = rework;
    }

    public String getReworkTime() {
        return reworkTime;
    }

    public void setReworkTime(String reworkTime) {
        this.reworkTime = reworkTime == null ? null : reworkTime.trim();
    }

    public String getIspart() {
        return ispart;
    }

    public void setIspart(String ispart) {
        this.ispart = ispart == null ? null : ispart.trim();
    }

    public Integer getPayrollTry() {
        return payrollTry;
    }

    public void setPayrollTry(Integer payrollTry) {
        this.payrollTry = payrollTry;
    }

    public Integer getPayroll() {
        return payroll;
    }

    public void setPayroll(Integer payroll) {
        this.payroll = payroll;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference == null ? null : reference.trim();
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime == null ? null : lastLoginTime.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress == null ? null : bankAddress.trim();
    }

    public String getBankUser() {
        return bankUser;
    }

    public void setBankUser(String bankUser) {
        this.bankUser = bankUser == null ? null : bankUser.trim();
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum == null ? null : bankNum.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHydName() {
        return hydName;
    }

    public void setHydName(String hydName) {
        this.hydName = hydName == null ? null : hydName.trim();
    }

    public Integer getHydId() {
        return hydId;
    }

    public void setHydId(Integer hydId) {
        this.hydId = hydId;
    }

    public Short getUserType() {
        return userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }

    public Integer getEntrySuccessTime() {
        return entrySuccessTime;
    }

    public void setEntrySuccessTime(Integer entrySuccessTime) {
        this.entrySuccessTime = entrySuccessTime;
    }

    public Integer getLeaveSuccessTime() {
        return leaveSuccessTime;
    }

    public void setLeaveSuccessTime(Integer leaveSuccessTime) {
        this.leaveSuccessTime = leaveSuccessTime;
    }
}