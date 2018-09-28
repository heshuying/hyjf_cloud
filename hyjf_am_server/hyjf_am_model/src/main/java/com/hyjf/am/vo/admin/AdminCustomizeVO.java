package com.hyjf.am.vo.admin;

import java.io.Serializable;



public class AdminCustomizeVO extends AdminVO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 所属部门名 */
    private String departmentName;
    /** 角色ID */
    private String roleId;
    /** 角色名 */
    private String roleName;

    // 查询用变量
    /** 用户名 */
    private String usernameSrch;
    /** 姓名 */
    private String truenameSrch;
    /** 手机号码 */
    private String mobileSrch;
    /** 部门 */
    private String departmentSrch;
    /** 账户状态 */
    private String stateSrch;
    private String stateSrchOn;
    private String stateSrchOff;
    /** 添加时间(开始) */
    private String addtimeStartSrch;
    /** 添加时间(结束) */
    private String addtimeEndSrch;
    /** 翻页开始 */
    protected int limitStart = -1;
    /** 翻页结束 */
    protected int limitEnd = -1;
    
 

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getTruenameSrch() {
        return truenameSrch;
    }

    public void setTruenameSrch(String truenameSrch) {
        this.truenameSrch = truenameSrch;
    }

    public String getMobileSrch() {
        return mobileSrch;
    }

    public void setMobileSrch(String mobileSrch) {
        this.mobileSrch = mobileSrch;
    }

    public String getDepartmentSrch() {
        return departmentSrch;
    }

    public void setDepartmentSrch(String departmentSrch) {
        this.departmentSrch = departmentSrch;
    }

    public String getStateSrch() {
        return stateSrch;
    }

    public void setStateSrch(String stateSrch) {
        this.stateSrch = stateSrch;
    }

    public String getAddtimeStartSrch() {
        return addtimeStartSrch;
    }

    public void setAddtimeStartSrch(String addtimeStartSrch) {
        this.addtimeStartSrch = addtimeStartSrch;
    }

    public String getAddtimeEndSrch() {
        return addtimeEndSrch;
    }

    public void setAddtimeEndSrch(String addtimeEndSrch) {
        this.addtimeEndSrch = addtimeEndSrch;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getStateSrchOn() {
        return stateSrchOn;
    }

    public void setStateSrchOn(String stateSrchOn) {
        this.stateSrchOn = stateSrchOn;
    }

    public String getStateSrchOff() {
        return stateSrchOff;
    }

    public void setStateSrchOff(String stateSrchOff) {
        this.stateSrchOff = stateSrchOff;
    }
}