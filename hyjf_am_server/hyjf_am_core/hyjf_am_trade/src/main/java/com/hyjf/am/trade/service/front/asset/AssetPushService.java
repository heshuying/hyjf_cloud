/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.asset;

import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;

import java.util.List;

/**
 * @author fuqiang
 * @version AssetPushService, v0.1 2018/6/12 10:07
 */
public interface AssetPushService extends BaseService {

    /**
     * 获取机构信息
     *
     * @param instCode
     * @param assetType
     * @return
     */
    HjhAssetBorrowtype selectAssetBorrowType(String instCode, int assetType);

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
    StzhWhiteList selectStzfWhiteList(String instCode, String entrustedAccountId);

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

    /**
     * 更新资产表
     * @param planAssetVO
     */
    void updatePlanAsset(HjhPlanAssetVO planAssetVO);

    /**
     * 获取项目类型
     * @param borrowCd
     * @return
     */
    List<BorrowProjectType> selectBorrowProjectByBorrowCd(Integer borrowCd);

    /**
     * 更新资产表
     * @param hjhPlanAsset
     * @return
     */
    int updateHjhPlanAssetnew(HjhPlanAsset hjhPlanAsset);

    /**
     * 检查是否存在重复资产
     * @param assetId
     * @return
     */
    List<HjhPlanAsset> checkDuplicateAssetId(String assetId);

    /**
     * 录标时添加企业资产
     *
     * @param borrowUser
     * @return
     */
    int insertCompanyInfoToBorrowUsers(BorrowUser borrowUser);

    /**
     * 检查是否交过保证金 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    List<BorrowBail> selectBorrowBail(String borrowNid);

    /**
     * 更新借款表 add by liushouyi
     *
     * @param borrow
     * @return
     */
    Integer updateBorrowByBorrowNid(Borrow borrow);

    /**
     * 插入保证金 add by liushouyi
     *
     * @param borrowBail
     * @return
     */
    Integer insertBorrowBail(BorrowBail borrowBail);

    /**
     * 根据标的编号查询资产推送表
     *
     * @param borrowNid
     * @return
     */
    HjhPlanAsset selectHjhPlanAssetByBorrowNid(String borrowNid);
}
