package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author by xiehuili on 2018/8/1.
 */
public class BorrowSendTypeRequest extends BasePage implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;
    /**
     * 唯一标识
     */
    private String sendCd;
    /**
     * 名称
     */
    private String sendName;
    /**
     * 发表时间
     */
    private String afterTime;
    /**
     * 备注说明
     */
    private String remark;

    private String modifyFlag;

    public String getSendCd() {
        return sendCd;
    }

    public void setSendCd(String sendCd) {
        this.sendCd = sendCd;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(String afterTime) {
        this.afterTime = afterTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }
}

