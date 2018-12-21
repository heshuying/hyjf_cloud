package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.WebUserRepayTransferCustomizeVO;

import java.util.List;

/**
 * @Author : huanghui
 */
public class WebUserRepayTransferCustomizeResponse extends Response<WebUserRepayTransferCustomizeVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
