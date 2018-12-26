/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyang
 * @version EvaluationBorrowLevelConfigLogVO, v0.1 2018/12/25 15:41
 */
public class EvaluationBorrowLevelConfigLogVO implements Serializable{
    private static final long serialVersionUID = -9054082768420598531L;


    @ApiModelProperty(value = "BBB信用等级对应的建议出借者类型")
    private String bbbEvaluationProposal;

    @ApiModelProperty(value = "A信用等级对应的建议出借者类型")
    private String aEvaluationProposal;

    @ApiModelProperty(value = "AA-信用等级对应的建议出借者类型")
    private String aa0EvaluationProposal;

    @ApiModelProperty(value = "AA信用等级对应的建议出借者类型")
    private String aa1EvaluationProposal;

    @ApiModelProperty(value = "AA+信用等级对应的建议出借者类型")
    private String aa2EvaluationProposal;

    @ApiModelProperty(value = "AAA信用等级对应的建议出借者类型")
    private String aaaEvaluationProposal;

    @ApiModelProperty(value = "添加人")
    private String createUser;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    public String getBbbEvaluationProposal() {
        return bbbEvaluationProposal;
    }

    public void setBbbEvaluationProposal(String bbbEvaluationProposal) {
        this.bbbEvaluationProposal = bbbEvaluationProposal;
    }

    public String getaEvaluationProposal() {
        return aEvaluationProposal;
    }

    public void setaEvaluationProposal(String aEvaluationProposal) {
        this.aEvaluationProposal = aEvaluationProposal;
    }

    public String getAa0EvaluationProposal() {
        return aa0EvaluationProposal;
    }

    public void setAa0EvaluationProposal(String aa0EvaluationProposal) {
        this.aa0EvaluationProposal = aa0EvaluationProposal;
    }

    public String getAa1EvaluationProposal() {
        return aa1EvaluationProposal;
    }

    public void setAa1EvaluationProposal(String aa1EvaluationProposal) {
        this.aa1EvaluationProposal = aa1EvaluationProposal;
    }

    public String getAa2EvaluationProposal() {
        return aa2EvaluationProposal;
    }

    public void setAa2EvaluationProposal(String aa2EvaluationProposal) {
        this.aa2EvaluationProposal = aa2EvaluationProposal;
    }

    public String getAaaEvaluationProposal() {
        return aaaEvaluationProposal;
    }

    public void setAaaEvaluationProposal(String aaaEvaluationProposal) {
        this.aaaEvaluationProposal = aaaEvaluationProposal;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
