package com.hyjf.am.market.service;

import java.util.List;

import com.hyjf.am.resquest.trade.InvitePrizeConfVO;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 19:31
 * @Description: InvitePrizeConfigService
 */
public interface InvitePrizeConfigService {
    /**
     * @Author walter.limeng
     * @Description  根据groupCode查询List
     * @Date 19:23 2018/7/16
     * @Param groupCode
     * @return
     */
    List<InvitePrizeConfVO> getListByGroupCode(String groupCode);
}
