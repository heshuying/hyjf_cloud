/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version WrbQueryCustomizeMapper, v0.1 2018/9/25 11:25
 */
public interface WrbQueryCustomizeMapper {
    /**
     * 根据标的ID查询标的列表
     * @param params
     * @return
     */
    List<WrbBorrowListCustomize> searchBorrowListByNid(Map<String,Object> params);
}
