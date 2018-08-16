package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiasq
 * @version SmsTemplateVO, v0.1 2018/5/4 10:22
 */
public class SmsTemplateVO extends BaseVO implements Serializable {

    private String tplCode;

    private String tplName;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String tplContent;

    private static final long serialVersionUID = 1L;

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName == null ? null : tplName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent == null ? null : tplContent.trim();
    }

    public String getTplCode() {
        return tplCode;
    }

    public void setTplCode(String tplCode) {
        this.tplCode = tplCode;
    }

    @Override
    public String toString() {
        return "SmsTemplateVO{" +
                "tplCode='" + tplCode + '\'' +
                ", tplName='" + tplName + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", tplContent='" + tplContent + '\'' +
                '}';
    }
}
