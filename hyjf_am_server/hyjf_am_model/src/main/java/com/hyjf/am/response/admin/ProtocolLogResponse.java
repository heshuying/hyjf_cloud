package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.ProtocolLogVO;

/**
 * @author：yinhui
 * @Date: 2018/8/10  14:02
 */
public class ProtocolLogResponse extends Response<ProtocolLogVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
