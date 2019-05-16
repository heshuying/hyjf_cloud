package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.WorkFlowVO;

/**
 * @author xiehuili on 2019/4/12.
 */
public class WorkFlowConfigResponse  extends Response<WorkFlowVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
