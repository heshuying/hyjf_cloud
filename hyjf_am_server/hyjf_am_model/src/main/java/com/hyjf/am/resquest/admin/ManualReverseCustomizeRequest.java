package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author hesy
 * @version ManualReverseCustomizeRequest, v0.1 2018/7/14 12:10
 */
@ApiModel(value = "手动冲正列表请求参数")
public class ManualReverseCustomizeRequest extends BasePage {
    /**
     * 原交易流水号
     */
    @ApiModelProperty(value = "原交易流水号")
    private String seqNoSrch;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userNameSrch;

    /**
     * 电子账号
     */
    @ApiModelProperty(value = "电子账号")
    private String accountIdSrch;

    /**
     * 交易时间(开始)
     */
    @ApiModelProperty(value = "交易时间(开始)")
    private String txTimeStartSrch;

    /**
     * 交易时间(结束)
     */
    @ApiModelProperty(value = "交易时间(结束)")
    private String txTimeEndSrch;

    private Integer limitStart;
    private Integer limitEnd;

    public String getSeqNoSrch() {
        return seqNoSrch;
    }

    public void setSeqNoSrch(String seqNoSrch) {
        this.seqNoSrch = seqNoSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getAccountIdSrch() {
        return accountIdSrch;
    }

    public void setAccountIdSrch(String accountIdSrch) {
        this.accountIdSrch = accountIdSrch;
    }

    public String getTxTimeStartSrch() {
        return txTimeStartSrch;
    }

    public void setTxTimeStartSrch(String txTimeStartSrch) {
        this.txTimeStartSrch = txTimeStartSrch;
    }

    public String getTxTimeEndSrch() {
        return txTimeEndSrch;
    }

    public void setTxTimeEndSrch(String txTimeEndSrch) {
        this.txTimeEndSrch = txTimeEndSrch;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }
}
