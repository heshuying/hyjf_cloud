/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;


import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MessagePushErrorVO;

/**
 * @author dangzw
 * @version MessagePushErrorResponse, v0.1 2018/8/14 22:06
 */
public class MessagePushErrorResponse extends Response<MessagePushErrorVO> {

    private int count;

    private String fileDomainUrl;

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
