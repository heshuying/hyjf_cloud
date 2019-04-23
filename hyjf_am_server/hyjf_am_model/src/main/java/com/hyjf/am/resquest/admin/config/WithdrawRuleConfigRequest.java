package com.hyjf.am.resquest.admin.config;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author jun
 * @version WithdrawRuleConfigRequest, v0.1 2019/4/23 15:03
 */
public class WithdrawRuleConfigRequest extends BasePage {

    /**
     * 分页查询开始条件
     */
    @ApiModelProperty(value = "分页查询开始条件")
    public int limitStart;

    /**
     * 分页查询结束条件
     */
    @ApiModelProperty(value = "分页查询结束条件")
    public int limitEnd;

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
}
