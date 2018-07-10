/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.response.BorrowFullResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.resquest.admin.BorrowFullRequest;

/**
 * @author wangjun
 * @version BorrowFullService, v0.1 2018/7/6 10:18
 */
public interface BorrowFullService {
    /**
     * 获取借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    BorrowFullResponseBean getBorrowFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 借款复审详细信息
     *
     * @param borrowFullRequest
     * @return
     */
    AdminResult getFullInfo(BorrowFullRequest borrowFullRequest);

    /**
     * 复审提交
     *
     * @param borrowFullRequest
     * @return
     */
    AdminResult updateBorrowFull(BorrowFullRequest borrowFullRequest);

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    AdminResult updateBorrowOver(BorrowFullRequest borrowFullRequest);
}
