/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserAndSpreadsUserVO, v0.1 2018/10/11 15:25
 */
public class UserAndSpreadsUserVO extends BaseVO {
    private Integer userId;
    private List<Integer> spreadsUserId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getSpreadsUserId() {
        return spreadsUserId;
    }

    public void setSpreadsUserId(List<Integer> spreadsUserId) {
        this.spreadsUserId = spreadsUserId;
    }
}
