package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ContentAdsBeanVO;

/**
 * 内容中心 -广告管理
 * @author：yinhui
 * @Date: 2018/7/19  14:15
 */
public class ContentAdsResponse extends Response<ContentAdsBeanVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
