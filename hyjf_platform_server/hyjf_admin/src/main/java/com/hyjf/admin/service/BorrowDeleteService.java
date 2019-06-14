/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;

/**
 * 标的删除
 * @author hesy
 */
public interface BorrowDeleteService {

    BorrowDeleteConfirmCustomizeVO selectDeleteConfirm(String borrowNid);

    AdminResult borrowDelete(String borrowNid, String currUserId, String currUserName);
}
