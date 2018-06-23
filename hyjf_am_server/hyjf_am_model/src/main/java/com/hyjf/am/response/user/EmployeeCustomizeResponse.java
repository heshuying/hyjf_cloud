package com.hyjf.am.response.user;


import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.EmployeeCustomizeVO;

/**
 * @author jijun 20180622
 */
public class EmployeeCustomizeResponse extends Response<EmployeeCustomizeVO> {

    private String truename;

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }
}
