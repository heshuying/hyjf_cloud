/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.am.vo.user.RegistRecordVO;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClient, v0.1 2018/6/20 15:37
 */
public interface AccountDetailClient {

    /**
     * 查找资金明细列表
     *
     * @param request
     * @return
     */
    AccountDetailResponse findAccountDetailList(AccountDetailRequest request);
}