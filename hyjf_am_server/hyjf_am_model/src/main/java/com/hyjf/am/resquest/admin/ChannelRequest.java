/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;

import java.util.List;

/**
 * @author fq
 * @version ChannelRequest, v0.1 2018/10/12 10:24
 */
public class ChannelRequest {
    private List<ChannelCustomizeVO> list;

    public List<ChannelCustomizeVO> getList() {
        return list;
    }

    public void setList(List<ChannelCustomizeVO> list) {
        this.list = list;
    }
}
