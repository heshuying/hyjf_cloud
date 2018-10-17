package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.MessagePushTagVO;

/**
 * @author lisheng
 * @version MessagePushTagResponse, v0.1 2018/8/15 10:25
 */

public class MessagePushTagResponse extends Response<MessagePushTagVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

}
