/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.vo.admin.AdminActivityUserRewardVO;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version ActivityUserGuessCustomizeMapper, v0.1 2019/4/18 16:17
 */
public interface ActivityUserRewardCustomizeMapper {

    /**
     * 获取活动奖励领取列表
     * @param map
     * @return
     */
    List<AdminActivityUserRewardVO> selectRewardList(Map<String, Object> map);

    /**
     * 查询奖励领取列表数据条数
     * @param map
     * @return
     */
    int countRewardList(Map<String, Object> map);
}
