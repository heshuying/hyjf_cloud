/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import java.util.List;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;

/**
 * @author fq
 * @version SmsCountCustomizeResponse, v0.1 2018/8/20 16:14
 */
public class SmsCountCustomizeResponse extends Response<SmsCountCustomizeVO> {
    private int count;

    List<OADepartmentCustomizeVO> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OADepartmentCustomizeVO> getList() {
        return list;
    }

    public void setList(List<OADepartmentCustomizeVO> list) {
        this.list = list;
    }
}
