package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;

/**
 * @author lisheng
 * @version MessagePushHistoryResponse, v0.1 2018/8/14 20:01
 */

public class MessagePushHistoryResponse extends Response<MessagePushMsgHistoryVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

}
