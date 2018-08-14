/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MobileSynchronizeCustomizeVO;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeResponse, v0.1 2018/8/13 12:02
 */
public class MobileSynchronizeResponse extends Response<MobileSynchronizeCustomizeVO> {
    private int count;

    private boolean update;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
