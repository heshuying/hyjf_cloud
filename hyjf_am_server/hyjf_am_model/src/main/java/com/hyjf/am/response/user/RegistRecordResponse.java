/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.RegistRecordVO;

/**
 * @author Administrator
 * @version UserManagerResponse, v0.1 2018/6/20 9:25
 * 会员中心->会员管理
 */
public class RegistRecordResponse extends Response<RegistRecordVO>{
    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
