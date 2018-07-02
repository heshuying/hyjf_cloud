/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchService, v0.1 2018/6/28 18:42
 */
public interface UserPortraitBatchService {
    /**
     * 用户画像定时任务
     * 由hyjf-batch调用
     * */
    void userPortraitBatch();
}
