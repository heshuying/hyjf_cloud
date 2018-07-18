package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.user.KeyCountRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.am.vo.user.KeyCountVO;

import java.util.List;

/**
 * @author tanyy
 * @version KeyCountCustomizeMapper, v0.1 2018/7/16 17:25
 */

public interface KeyCountCustomMapper {
    /**
     * 取得数据总数量
     *
     * @return
     */
    int countTotal(KeyCountRequest request);

    /**
     * 根据查询条件 取得数据
     * @param request
     * @return
     */
    List<KeyCountVO> searchAction(KeyCountRequest request);

}
