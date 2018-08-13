/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;

import java.util.List;

/**
 * @author fq
 * @version FddTempletCustomizeMapper, v0.1 2018/8/7 10:09
 */
public interface FddTempletCustomizeMapper {
    /**
     * 获取协议列表
     * @param request
     * @return
     */
    List<FddTempletCustomize> selectFddTempletList(FddTempletCustomize request);
}
