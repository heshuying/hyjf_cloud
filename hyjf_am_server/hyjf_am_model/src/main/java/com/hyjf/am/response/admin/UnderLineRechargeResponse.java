package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;

/**
 * @Author : huanghui
 */
public class UnderLineRechargeResponse extends Response<UnderLineRechargeVO> {

    private String message;

    private int count;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
