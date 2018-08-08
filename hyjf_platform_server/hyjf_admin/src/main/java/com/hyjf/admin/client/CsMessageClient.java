/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
import com.hyjf.am.vo.datacollect.AccountWebListVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version CsMessageClient, v0.1 2018/6/25 10:27
 */
public interface CsMessageClient {

    AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList);

    String selectBorrowInvestAccount(AccountWebListVO accountWebList);

    /**
     * 查询关联记录列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getAssociatedRecordsCount(AssociatedRecordListRequest request);
    /**
     * 根据筛选条件查询关联记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AssociatedRecordListVo> getAssociatedRecordList(AssociatedRecordListRequest request);

    /**
     * 获取错误码
     * @param logOrdId
     * @return
     */
    String getRetCode(String logOrdId);
}
