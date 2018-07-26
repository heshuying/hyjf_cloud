package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.CountVO;

/**
 * 专门用来返回统计数据的response
 * @author zhangyk
 * @date 2018/7/25 15:33
 */
public class CountResponse extends Response<CountVO> {

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
