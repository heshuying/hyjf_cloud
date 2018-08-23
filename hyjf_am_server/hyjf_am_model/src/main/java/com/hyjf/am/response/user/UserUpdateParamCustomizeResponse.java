/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.UserUpdateParamCustomizeVO;

/**
 * @author nxl
 * @version UserUpdateParamCustomizeResponse, v0.1 2018/6/20 9:25
 */
public class UserUpdateParamCustomizeResponse extends Response<UserUpdateParamCustomizeVO>{
    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
