/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MessagePushPlatStaticsVO;

/**
 * @author fq
 * @version MessagePushPlatStaticsResponse, v0.1 2018/8/14 16:09
 */
public class MessagePushPlatStaticsResponse extends Response<MessagePushPlatStaticsVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
