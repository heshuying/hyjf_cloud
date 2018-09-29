/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.AssetExceptionService;
import com.hyjf.am.resquest.admin.AssetExceptionRequest;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionServiceImpl, v0.1 2018/9/28 18:00
 */
@Service
public class AssetExceptionServiceImpl extends BaseServiceImpl implements AssetExceptionService {

    @Autowired
    AmAdminClient amAdminClient;

    /**
     * 查询异常标的总件数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectAssetExceptionCount(AssetExceptionRequest request) {
        return amAdminClient.selectAssetExceptionCount(request);
    }

    /**
     * 查询异常标的列表
     *
     * @param request
     * @return
     */
    @Override
    public List<AssetExceptionCustomizeVO> selectAssetExceptionList(AssetExceptionRequest request) {
        return amAdminClient.selectAssetExceptionList(request);
    }

    /**
     * 插入异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public boolean insertAssetException(AssetExceptionRequest assetExceptionRequest) {
        return amAdminClient.insertAssetException(assetExceptionRequest);
    }

    /**
     * 项目编号是否存在
     *
     * @param borrowNid
     * @return
     */
    @Override
    public String isExistsBorrow(String borrowNid) {
        return amAdminClient.isExistsBorrow(borrowNid);
    }

    /**
     * 删除异常标的
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public boolean deleteAssetException(AssetExceptionRequest assetExceptionRequest) {
        return amAdminClient.deleteAssetException(assetExceptionRequest);
    }

    /**
     * 修改异常标的
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public boolean updateAssetException(AssetExceptionRequest assetExceptionRequest) {
        return amAdminClient.updateAssetException(assetExceptionRequest);
    }
}
