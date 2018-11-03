/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BaseVO;

/**
 * 推广渠道
 *
 * @author liuyang
 * @version UtmRequest, v0.1 2018/10/10 16:24
 */
public class UtmRequest extends BaseVO {

     // 推广渠道Id
    private Integer utmId;

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }
}
