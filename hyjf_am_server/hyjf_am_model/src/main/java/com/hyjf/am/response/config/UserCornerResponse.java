/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.UserCornerVO;

/**
 * @author: sunpeikai
 * @version: UserCornerResponse, v0.1 2018/7/26 10:53
 */
public class UserCornerResponse extends Response<UserCornerVO> {
    private Integer successCount;

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }
}
