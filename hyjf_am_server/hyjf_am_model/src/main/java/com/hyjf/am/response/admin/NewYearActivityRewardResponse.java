package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.NewYearActivityRewardVO;

/**
 * @author xiehuili on 2019/3/25.
 */
public class NewYearActivityRewardResponse extends Response<NewYearActivityRewardVO> {

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
