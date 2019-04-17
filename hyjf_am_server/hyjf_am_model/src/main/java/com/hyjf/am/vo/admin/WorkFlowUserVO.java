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
     * 邮箱
     */
    private String email;

    /**
     * 邮件预警人
     */
    private String mailWarningUser;

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
}
