/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.message;


import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author fq
 * @version MessagePushTemplateStaticsRequest, v0.1 2018/8/14 14:18
 */
public class MessagePushTemplateStaticsRequest extends BasePage implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    /**
     * 标题查询
     */
    @ApiModelProperty(value = "标题")
    private String msgTitleSrch;
    /**
     * 标签查询
     */
    @ApiModelProperty(value = "标签")
    private String tagIdSrch;
    /**
     * 消息编码查询
     */
    @ApiModelProperty(value = "消息编码")
    private String msgCodeSrch;
    /**
     * 时间查询
     */
    @ApiModelProperty(value = "开始时间")
    private String startDateSrch;
    /**
     * 时间查询
     */
    @ApiModelProperty(value = "结束时间")
    private String endDateSrch;

    public String getMsgTitleSrch() {
        return msgTitleSrch;
    }

    public void setMsgTitleSrch(String msgTitleSrch) {
        this.msgTitleSrch = msgTitleSrch;
    }

    public String getTagIdSrch() {
        return tagIdSrch;
    }

    public void setTagIdSrch(String tagIdSrch) {
        this.tagIdSrch = tagIdSrch;
    }

    public String getMsgCodeSrch() {
        return msgCodeSrch;
    }

    public void setMsgCodeSrch(String msgCodeSrch) {
        this.msgCodeSrch = msgCodeSrch;
    }

    public String getStartDateSrch() {
        return startDateSrch;
    }

    public void setStartDateSrch(String startDateSrch) {
        this.startDateSrch = startDateSrch;
    }

    public String getEndDateSrch() {
        return endDateSrch;
    }

    public void setEndDateSrch(String endDateSrch) {
        this.endDateSrch = endDateSrch;
    }
}
