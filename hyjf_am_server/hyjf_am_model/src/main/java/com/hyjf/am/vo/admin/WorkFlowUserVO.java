package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author xiehuili on 2019/4/16.
 */
public class WorkFlowUserVO  extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 真实姓名
     */
    private String truename;

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

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
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
}
