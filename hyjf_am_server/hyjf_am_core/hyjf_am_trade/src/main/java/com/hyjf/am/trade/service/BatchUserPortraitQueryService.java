/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BatchUserPortraitQueryService, v0.1 2018/6/28 11:13
 */
public interface BatchUserPortraitQueryService {
    /**
     * 查询用户画像所需要的投资相关参数
     * */
    List<BatchUserPortraitQueryVO> selectInfoForUserPortrait(List<Integer> userIds);
}
