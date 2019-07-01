package com.hyjf.cs.market.vo.activity51;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiasq
 * @version GuessVO, v0.1 2019/4/22 14:06
 */
@ApiModel(value = "查询用户竞猜信息")
public class GuessVO {
    @ApiModelProperty("竞猜标志, Y-已竞猜 N-未竞猜")
    private String guessFlag;
    @ApiModelProperty("竞猜描述")
    private String guessDesc;
    @ApiModelProperty("竞猜档位")
    private Integer grade;

    public GuessVO(String guessFlag, String guessDesc) {
        this.guessFlag = guessFlag;
        this.guessDesc = guessDesc;
    }

    public GuessVO(String guessFlag, String guessDesc, int grade) {
        this(guessFlag, guessDesc);
        this.grade = grade;
    }

    public String getGuessFlag() {
        return guessFlag;
    }

    public void setGuessFlag(String guessFlag) {
        this.guessFlag = guessFlag;
    }

    public String getGuessDesc() {
        return guessDesc;
    }

    public void setGuessDesc(String guessDesc) {
        this.guessDesc = guessDesc;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
