package com.hyjf.am.response.market;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.market.ActivityListVO;

/**
 * @author xiasq
 * @version ActivityListResponse, v0.1 2018/6/14 11:32
 */
public class ActivityListResponse extends Response<ActivityListVO> {
    private int count;

    private int flag;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
