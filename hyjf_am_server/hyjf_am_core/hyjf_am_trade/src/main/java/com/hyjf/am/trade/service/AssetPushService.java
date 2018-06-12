/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.assetpush.InfoBean;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectRepay;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowType;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.dao.model.auto.STZHWhiteList;

import java.util.List;

/**
 * @author fuqiang
 * @version AssetPushService, v0.1 2018/6/12 10:07
 */
public interface AssetPushService {

    /**
     * 获取机构信息
     *
     * @param instCode
     * @param assetType
     * @return
     */
    HjhAssetBorrowType selectAssetBorrowType(String instCode, int assetType);

    /**
     * 根据项目类型去还款方式
     *
     * @param borrowcCd
     * @return
     */
    List<BorrowProjectRepay> selectProjectRepay(String borrowcCd);

    /**
     * 获取受托支付电子账户列表
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    STZHWhiteList selectStzfWhiteList(String instCode, String entrustedAccountId);

    /**
     * 插入资产表
     * @param hjhPlanAsset
     * @return
     */
    int insertAssert(HjhPlanAsset hjhPlanAsset);

    /**
     * 插入资产表
     * @param infobeans
     */
    void insertRiskInfo(List<InfoBean> infobeans);
}
