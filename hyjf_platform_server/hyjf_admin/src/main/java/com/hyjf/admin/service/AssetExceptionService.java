/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.resquest.admin.AssetExceptionRequest;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionService, v0.1 2018/9/28 18:00
 */
public interface AssetExceptionService extends BaseService {
    /**
     * 查询异常标的总件数
     *
     * @param request
     * @return
     */
    Integer selectAssetExceptionCount(AssetExceptionRequest request);

    /**
     * 查询异常标的列表
     *
     * @param request
     * @return
     */
    List<AssetExceptionCustomizeVO> selectAssetExceptionList(AssetExceptionRequest request);

    /**
     * 插入异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    boolean insertAssetException(AssetExceptionRequest assetExceptionRequest);

    /**
     * 项目编号是否存在
     *
     * @param borrowNid
     * @return
     */
    String isExistsBorrow(String borrowNid);

    /**
     * 删除异常标的
     *
     * @param assetExceptionRequest
     * @return
     */
    boolean deleteAssetException(AssetExceptionRequest assetExceptionRequest);

    /**
     * 修改异常标的
     *
     * @param assetExceptionRequest
     * @return
     */
    boolean updateAssetException(AssetExceptionRequest assetExceptionRequest);

    /**
     * 资产来源下拉列表
     *
     * @return
     */
    List<DropDownVO> selectHjhInstConfigList();
}
