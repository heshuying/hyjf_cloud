/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminBankCardExceptionCustomizeVO;

/**
 * @author: sunpeikai
 * @version: AdminBankCardExceptionResponse, v0.1 2018/8/14 14:56
 */
public class AdminBankCardExceptionResponse extends Response<AdminBankCardExceptionCustomizeVO> {
    private int count;

    private String resultMsg;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
