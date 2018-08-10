package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;

/**
 * @author：yinhui
 * @Date: 2018/8/8  15:47
 */
public class AdminProtocolResponse extends Response<ProtocolTemplateCommonVO> {
    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
