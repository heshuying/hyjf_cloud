package com.hyjf.am.response.admin.vip.content;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.ScreenConfigVO;

/**
 * Created by future on 2019/3/21.
 */
public class ScreenConfigVOResponse extends Response<ScreenConfigVO> {

    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
