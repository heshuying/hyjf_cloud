package com.hyjf.am.response.user;


import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.BankCardVO;

/**
 * @author hesy
 */
public class BankCardResponse extends Response<BankCardVO> {
	private int cnt;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
