/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

import java.util.List;


/**
 * @author zdj
 * @version PushMoneyManageService, v0.1 2018/7/3 15:34
 */
public interface PushMoneyManageService {
    /**
     * 查找直投提成管理列表
     *
     * @param request
     * @return
     */
    PushMoneyResponse findPushMoneyList(PushMoneyRequest request);

    /**
     * 取得借款API表
     * @param borrowNid
     * @return
     */
     BorrowApicronVO getBorrowApicronBorrowNid(String borrowNid);

    /**
     * 提成处理
     *
     * @param apicornId,request
     * @return
     */
     int insertTenderCommissionRecord(Integer apicornId, PushMoneyRequest request);


}
