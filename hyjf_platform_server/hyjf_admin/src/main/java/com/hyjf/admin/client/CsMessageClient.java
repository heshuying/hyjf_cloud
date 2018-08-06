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
 * @version AmDataCollectClient, v0.1 2018/6/25 10:27
 */
public interface CsMessageClient {

    AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList);

    String selectBorrowInvestAccount(AccountWebListVO accountWebList);

    /**
     * 插入数据
     * @auth sunpeikai
     * @param accountWebListVO 网站收支表
     * @return
     */
    Integer insertAccountWebList(AccountWebListVO accountWebListVO);
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
}
