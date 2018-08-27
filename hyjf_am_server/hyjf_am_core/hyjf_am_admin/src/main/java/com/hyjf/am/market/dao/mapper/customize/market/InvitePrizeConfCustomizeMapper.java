package com.hyjf.am.market.dao.mapper.customize.market;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.trade.InvitePrizeConfVO;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 19:34
 * @Description: InvitePrizeConfCustomizeMapper
 */
public interface InvitePrizeConfCustomizeMapper {
    /**
     * @Author walter.limeng
     * @Description  根据groupCode查询List
     * @Date 19:23 2018/7/16
     * @Param groupCode
     * @return
     */
    List<InvitePrizeConfVO> getListByGroupCode(Map<String,Object> param);
}
