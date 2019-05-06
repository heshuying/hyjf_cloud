package com.hyjf.cs.market.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiasq
 * @version RewardReceiveVO, v0.1 2019/4/22 14:06
 */
@ApiModel(value = "查询用户奖励领取信息")
public class RewardReceiveVO {
    @ApiModelProperty("奖励档位")
    private int grade;
    @ApiModelProperty("领取标志, Y-已领取 N-未领取")
    private String receiveFlag;
    @ApiModelProperty("领取描述")
    private String receiveDesc;

    public RewardReceiveVO(int grade, String receiveFlag, String receiveDesc) {
        this.grade = grade;
        this.receiveFlag = receiveFlag;
        this.receiveDesc = receiveDesc;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getReceiveFlag() {
        return receiveFlag;
    }

    public void setReceiveFlag(String receiveFlag) {
        this.receiveFlag = receiveFlag;
    }

    public String getReceiveDesc() {
        return receiveDesc;
    }

    public void setReceiveDesc(String receiveDesc) {
        this.receiveDesc = receiveDesc;
    }
}
