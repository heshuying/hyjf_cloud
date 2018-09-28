package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.api.WrbRegisterRequest;

/**
 * @author lisheng
 * @version CsMessageClient, v0.1 2018/9/27 14:32
 */

public interface CsMessageClient {
    /**
     * 插入app渠道统计数据
     * @param wrbRegisterRequest
     * @return
     */
    boolean insertAppChannelStatisticsDetail(WrbRegisterRequest wrbRegisterRequest);

}
