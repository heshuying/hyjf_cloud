/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.resquest.admin.AssetExceptionRequest;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionService, v0.1 2018/9/28 19:12
 */
public interface AssetExceptionService extends BaseService {

    /**
     * 异常标的列表总件数
     *
     * @param assetExceptionRequest
     * @return
     */
    Integer selectAssetExceptionCount(AssetExceptionRequest assetExceptionRequest);

    /**
     * 异常标的列表查询
     *
     * @param assetExceptionRequest
     * @return
     */
    List<AssetExceptionCustomizeVO> selectAssetExceptionRecordList(AssetExceptionRequest assetExceptionRequest);

    /**
     * 插入异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    Boolean insertAssetException(AssetExceptionRequest assetExceptionRequest);

    /**
     * 项目编号是否存在
     *
     * @param borrowNid
     * @return
     */
    String isExistsBorrow(String borrowNid);

    /**
     * 删除异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    Boolean deleteAssetException(AssetExceptionRequest assetExceptionRequest);

    /**
     * 修改异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    Boolean updateAssetException(AssetExceptionRequest assetExceptionRequest);
}
