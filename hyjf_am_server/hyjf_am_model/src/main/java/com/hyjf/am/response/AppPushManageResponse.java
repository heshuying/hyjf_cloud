package com.hyjf.am.response;

import com.hyjf.am.vo.admin.AppPushManageVO;

/**
 * @Author : huanghui
 */
public class AppPushManageResponse extends Response<AppPushManageVO> {

    /** 总条数 */
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
