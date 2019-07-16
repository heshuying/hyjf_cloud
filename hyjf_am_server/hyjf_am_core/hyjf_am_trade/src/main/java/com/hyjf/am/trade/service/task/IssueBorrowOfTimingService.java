package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.customize.BorrowCustomize;

import java.util.List;

/**
 * @author xiasq
 * @version IssueBorrowOfTimingService, v0.1 2018/7/10 14:04
 */
public interface IssueBorrowOfTimingService {
    void issueHjhPlanBorrowOfTiming() throws Exception;

    void issueBorrowOfTiming(BorrowCustomize borrowCustomize) throws Exception;

    void issueSplitBorrowOfTiming() throws Exception;

    /**
     * 获取定时发标列表
     *
     * @return
     */
    List<BorrowCustomize> queryOntimeTenderList();
}
