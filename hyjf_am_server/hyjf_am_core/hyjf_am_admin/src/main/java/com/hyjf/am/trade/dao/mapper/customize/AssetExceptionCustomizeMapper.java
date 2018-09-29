/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.AssetExceptionRequest;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionCustomizeMapper, v0.1 2018/9/28 19:15
 */
public interface AssetExceptionCustomizeMapper {
    /**
     * 异常标的列表总件数
     *
     * @param assetExceptionRequest
     * @return
     */
    Integer countBorrowDelete(AssetExceptionRequest assetExceptionRequest);

    /**
     * 异常标的列表查询
     *
     * @param assetExceptionRequest
     * @return
     */
    List<AssetExceptionCustomizeVO> selectBorrowDeleteList(AssetExceptionRequest assetExceptionRequest);
}
