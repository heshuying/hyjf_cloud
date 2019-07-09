package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.WorkNameVO;

/**
 * @Author: yinhui
 * @Date: 2019/4/15 11:17
 * @Version 1.0
 */
public class BusinessNameMgResponse extends Response<WorkNameVO> {

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
