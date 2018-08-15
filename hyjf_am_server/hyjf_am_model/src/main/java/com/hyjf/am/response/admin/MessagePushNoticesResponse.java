package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MessagePushMsgVO;

/**
 * @author lisheng
 * @version MessagePushNoticesResponse, v0.1 2018/8/14 16:25
 */

public class MessagePushNoticesResponse extends Response<MessagePushMsgVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

}
