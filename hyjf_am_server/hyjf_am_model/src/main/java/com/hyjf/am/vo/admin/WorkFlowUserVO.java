package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiehuili on 2019/4/16.
 */
public class WorkFlowUserVO  extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 多个角色的id
     */
    private List<Integer> roleIds;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String truename;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 角色
     */
    private String roleName;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮件预警人
     */
    private String mailWarningUser;

    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 业务流程表ID
     */
    private Integer workflowid;

    /**
     * 业务名称
     */
    private String workname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMailWarningUser() {
        return mailWarningUser;
    }

    public void setMailWarningUser(String mailWarningUser) {
        this.mailWarningUser = mailWarningUser;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(Integer workflowid) {
        this.workflowid = workflowid;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
