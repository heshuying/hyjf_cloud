package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.resquest.market.InviterReturnCashCustomize;
import org.apache.ibatis.annotations.Param;

public interface InviterReturnCashCustomizeMapper {

    /**
     * 获取部门和推荐人
     * @param userId
     * @return
     */
    InviterReturnCashCustomize selectReturnCashList(@Param("userId") Integer userId);
}