/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.HjhUserAuthConfigCustomizeVO;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;

/**
 * @author jun
 * @version AdminAuthConfigService, v0.1 2018/10/14 12:55
 */
public class AdminAuthConfigLogResponse extends Response<HjhUserAuthConfigLogCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
