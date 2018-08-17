/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.AccountMobileSynchVO;

/**
 * @author wangjun
 * @version AccountMobileSynchResponse, v0.1 2018/6/22 14:30
 */
public class AccountMobileSynchResponse extends Response<AccountMobileSynchVO> {
    /**
     * 更新flag 0失败 1成功
     */
    private boolean updateFlag;

    private int count;

    public boolean getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
