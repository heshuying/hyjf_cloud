package com.hyjf.am.trade.service.task;

/**
 * @author xiasq
 * @version IssueBorrowOfTimingService, v0.1 2018/7/10 14:04
 */
public interface IssueBorrowOfTimingService {
    void issueHjhPlanBorrowOfTiming() throws Exception;

    void issueBorrowOfTiming()throws Exception;

    void issueSplitBorrowOfTiming()throws Exception;
}
