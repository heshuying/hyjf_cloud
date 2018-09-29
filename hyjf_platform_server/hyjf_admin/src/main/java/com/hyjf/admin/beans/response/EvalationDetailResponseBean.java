/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.UserEvalationQuestionCustomizeVO;
import com.hyjf.admin.beans.vo.UserEvalationResultCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author nxl
 * @version EvalationInitResponseBean, v0.1 2018/8/06 18:15
 */
public class EvalationDetailResponseBean {
    // 用户角色
    @ApiModelProperty(value = "测评结果")
    private UserEvalationResultCustomizeVO userEvalationResultCustomizeVO;
    @ApiModelProperty(value = "测评分数")
    private List<UserEvalationQuestionCustomizeVO> userEvalationQuestionCustomizeVO;
    //
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    //
    @ApiModelProperty(value = "状态(0:不可用,1:可用)")
    private String isEvalation;

    public UserEvalationResultCustomizeVO getUserEvalationResultCustomizeVO() {
        return userEvalationResultCustomizeVO;
    }

    public void setUserEvalationResultCustomizeVO(UserEvalationResultCustomizeVO userEvalationResultCustomizeVO) {
        this.userEvalationResultCustomizeVO = userEvalationResultCustomizeVO;
    }

    public List<UserEvalationQuestionCustomizeVO> getUserEvalationQuestionCustomizeVO() {
        return userEvalationQuestionCustomizeVO;
    }

    public void setUserEvalationQuestionCustomizeVO(List<UserEvalationQuestionCustomizeVO> userEvalationQuestionCustomizeVO) {
        this.userEvalationQuestionCustomizeVO = userEvalationQuestionCustomizeVO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsEvalation() {
        return isEvalation;
    }

    public void setIsEvalation(String isEvalation) {
        this.isEvalation = isEvalation;
    }
}
