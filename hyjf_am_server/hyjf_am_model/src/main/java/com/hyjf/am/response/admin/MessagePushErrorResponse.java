/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;


import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushErrorVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorResponse, v0.1 2018/8/14 22:06
 */
public class MessagePushErrorResponse extends Response<MessagePushErrorVO> {

    @ApiModelProperty("查询返回列表条数")
    private int count;

    @ApiModelProperty("处理后的路径")
    private String fileDomainUrl;

    @ApiModelProperty("标签")
    private List<MessagePushTagVO> tagList;

    @ApiModelProperty("发送状态")
    private List<ParamNameVO> messagesSendStatus;

    @ApiModelProperty("请求表单")
    private MessagePushErrorRequest msgErrorForm;

    public List<MessagePushTagVO> getTagList() {
        return tagList;
    }

    public void setTagList(List<MessagePushTagVO> tagList) {
        this.tagList = tagList;
    }

    public List<ParamNameVO> getMessagesSendStatus() {
        return messagesSendStatus;
    }

    public void setMessagesSendStatus(List<ParamNameVO> messagesSendStatus) {
        this.messagesSendStatus = messagesSendStatus;
    }

    public MessagePushErrorRequest getMsgErrorForm() {
        return msgErrorForm;
    }

    public void setMsgErrorForm(MessagePushErrorRequest msgErrorForm) {
        this.msgErrorForm = msgErrorForm;
    }

    public String getFileDomainUrl() {
        return fileDomainUrl;
    }

    public void setFileDomainUrl(String fileDomainUrl) {
        this.fileDomainUrl = fileDomainUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
