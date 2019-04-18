/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.vo.admin.ActivityUserGuessVO;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version ActivityUserGuessCustomizeMapper, v0.1 2019/4/18 16:17
 */
public interface ActivityUserGuessCustomizeMapper {

    /**
     * 获取活动竞猜列表
     * @param requestMap
     * @return
     */
    List<ActivityUserGuessVO> selectGuessUserList(Map<String, Object> requestMap);

    /**
     * 查询竞猜列表数据条数
     * @param requestMap
     * @return
     */
    int countGuessList(Map<String, Object> requestMap);
}
