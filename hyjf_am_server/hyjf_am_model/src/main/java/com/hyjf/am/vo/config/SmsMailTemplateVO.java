package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author xiasq
 * @version SmsMailTemplateVO, v0.1 2018/5/4 10:40
 */
public class SmsMailTemplateVO extends BaseVO implements Serializable {
    private String mailValue;

    private String mailName;

    private Integer mailStatus;

    private Integer createTime;

    private Integer updateTime;

    private String mailContent;

    private static final long serialVersionUID = 1L;

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName == null ? null : mailName.trim();
    }

    public Integer getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(Integer mailStatus) {
        this.mailStatus = mailStatus;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent == null ? null : mailContent.trim();
    }

    public String getMailValue() {
        return mailValue;
    }

    public void setMailValue(String mailValue) {
        this.mailValue = mailValue;
    }

    @Override
    public String toString() {
        return "SmsMailTemplateVO{" +
                "mailValue='" + mailValue + '\'' +
                ", mailName='" + mailName + '\'' +
                ", mailStatus=" + mailStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", mailContent='" + mailContent + '\'' +
                '}';
    }
}
