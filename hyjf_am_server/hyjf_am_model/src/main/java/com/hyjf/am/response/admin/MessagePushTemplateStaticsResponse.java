/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MessagePushTemplateStaticsVO;

/**
 * @author fq
 * @version MessagePushTemplateStaticsResponse, v0.1 2018/8/14 14:35
 */
public class MessagePushTemplateStaticsResponse extends Response<MessagePushTemplateStaticsVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
