package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.WorkFlowUserVO;

/**
 * @author xiehuili on 2019/4/16.
 */
public class WorkFlowUserResponse extends Response<WorkFlowUserVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
