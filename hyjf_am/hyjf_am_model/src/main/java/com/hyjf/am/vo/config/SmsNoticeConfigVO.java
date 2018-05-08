package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

/**
 * @author xiasq
 * @version SmsNoticeConfigVO, v0.1 2018/5/4 10:30
 */
public class SmsNoticeConfigVO extends BaseVO {

    private String name;

    private String title;

    private String value;

    private String pvalue;

    private String content;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue == null ? null : pvalue.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SmsNoticeConfigVO{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", pvalue='" + pvalue + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
