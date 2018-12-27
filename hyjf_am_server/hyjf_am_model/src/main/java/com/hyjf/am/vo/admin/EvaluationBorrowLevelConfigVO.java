/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 风险测评风险等级配置
 *
 * @author liuyang
 * @version EvaluationBorrowLevelConfigVO, v0.1 2018/12/25 8:51
 */
public class EvaluationBorrowLevelConfigVO implements Serializable {
    private static final long serialVersionUID = 1937871436096491064L;

    @ApiModelProperty(value = "ID")
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
