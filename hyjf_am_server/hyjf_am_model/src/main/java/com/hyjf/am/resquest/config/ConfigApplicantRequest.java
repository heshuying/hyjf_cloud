package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author lisheng
 * @version ConfigApplicantRequest, v0.1 2019/2/21 11:18
 */

public class ConfigApplicantRequest extends BasePage implements Serializable {
    Integer id;
    /**
     * 项目申请人
     */
    private String applicant;

    /**
     * 备注
     */
    private String remark;

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


    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
