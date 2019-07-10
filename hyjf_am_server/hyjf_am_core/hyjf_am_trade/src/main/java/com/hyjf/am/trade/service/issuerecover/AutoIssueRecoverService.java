package com.hyjf.am.trade.service.issuerecover;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;

import java.math.BigDecimal;
import java.util.List;

/**
 * 汇计划自动发标修复
 * @author walter.limeng
 * @version AutoIssueRecoverJob, v0.1 2018/7/11 10:30
 */
public interface AutoIssueRecoverService extends BaseService {

    Integer updateForSend(String instCode, BigDecimal assetAccount);

    /**
     * 匹配标签
     * @param borrow
     * @param hjhPlanAsset
     * @param borrowInfo
     * @return
     */
    HjhLabel getLabelId(Borrow borrow, HjhPlanAsset hjhPlanAsset, BorrowInfo borrowInfo);

    /**
     * 查询待录标列表
     * @param statusList
     * @return
     */
    List<HjhPlanAsset> selectAssetListByStatus(List statusList);

    /**
     * 查询待关联计划资产列表
     * @return
     */
    List<HjhPlanAsset> selectBorrowAssetList();

    /**
     * 查询待发标关联计划列表
     * @return
     */
    List<HjhDebtCredit> selectCreditAssetList();

    /**
     * 查询散标关联计划资产列表
     * @return
     */
    List<Borrow> selectBorrowList();

    /**
     * 手动录标的自动备案、初审的标
     * @return
     */
    List<BorrowWithBLOBs> selectAutoBorrowNidList();

    /**
     * 根据机构号和资产类型查询自动化流程配置
     * @param assetType
     * @param instCode
     * @return
     */
    HjhAssetBorrowtype selectAssetBorrowType(Integer assetType, String instCode);

    /**
     * 判断是否可以自动录标
     * @param mqHjhPlanAsset
     * @param hjhAssetBorrowType
     * @return
     */
    boolean insertSendBorrow(HjhPlanAsset mqHjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType);

    Integer updateAndCheckAssetCanSend(HjhPlanAsset hjhPlanAsset);

    boolean updateAndCheckNewCredit(String instCode, BigDecimal account);
}
