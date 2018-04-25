package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.SmsCodeVO;

/**
 * @author xiasq
 * @version SmsCodeResponse, v0.1 2018/4/11 13:52
 */
public class SmsCodeResponse extends Response<SmsCodeVO> {
    private int cnt;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
