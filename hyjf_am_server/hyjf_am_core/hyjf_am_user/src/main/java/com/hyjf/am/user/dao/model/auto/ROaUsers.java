package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class ROaUsers implements Serializable {
    private Integer id;

    private String userLogin;

    private String userPass;

    private String userRealname;

    private String userEmail;

    private String idcard;

    private String avatar;

    private Short sex;

    private String accProvince;

    private String accCity;

    private String accAddress;

    private Integer departmentid;

    private Integer positionid;

    private Short level;

    private Integer temporary;

    private Integer rework;

    private String reworkTime;

    private String ispart;

    private Integer payrollTry;

    private Integer payroll;

    private Date entrydate;

    private String reference;

    private Integer education;

    private String school;

    private String specialty;

    private String mobile;

    private String lastLoginIp;

    private String lastLoginTime;

    private String createTime;

    private String bankAddress;

    private String bankUser;

    private String bankNum;

    private String userStatus;

    private Integer age;

    private String hydName;

    private Integer hydId;

    private Short userType;

    private Integer entrySuccessTime;

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