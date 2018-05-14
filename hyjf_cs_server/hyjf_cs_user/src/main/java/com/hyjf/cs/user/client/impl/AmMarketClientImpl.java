package com.hyjf.cs.user.client.impl;

import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.cs.user.client.AmMarketClient;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version AmMarketClientImpl, v0.1 2018/5/14 16:11
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {

    /**
     * 根据活动id查询活动
     * @param integer
     * @return
     */
    @Override
    public ActivityListVO findActivityById(Integer integer) {
        //todo xiashuqing 20180514
        return null;
    }
}
