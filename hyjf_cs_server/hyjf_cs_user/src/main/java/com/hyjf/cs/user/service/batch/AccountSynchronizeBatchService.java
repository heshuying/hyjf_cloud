/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch;


/**
 * @author wangjun
 * @version AccountSynchronizeBatchService, v0.1 2018/6/22 13:40
 */
public interface AccountSynchronizeBatchService {

    /**
     * 同步银行卡号任务
     */
    void accountSynchronize();

    void mobileSychronize();
}
