package com.hyjf.am.trade.service.task.issuerecover;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;

import java.util.List;

/**
 * 汇计划自动发标修复
 * @author walter.limeng
 * @version AutoIssueRecoverJob, v0.1 2018/7/11 10:30
 */
public interface AutoIssueRecoverService {

    /**
     * 查询待录标列表
     * @param statusList
     * @return
     */
    List<HjhPlanAsset> selectAssetList(List statusList);

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
     * 根据ID查询用户加入计划订单
     * @param planId 主键ID
     * @return
     */
    HjhPlanAsset getHjhPlanAssetById(Integer planId);

    /**
     * 根据机构比那好和类型查询对象
     * @param mqHjhPlanAsset
     * @return
     */
    HjhAssetBorrowtype selectAssetBorrowType(HjhPlanAsset mqHjhPlanAsset);

    /**
     * 判断是否可以自动录标
     * @param mqHjhPlanAsset
     * @param hjhAssetBorrowType
     * @return
     */
    boolean insertSendBorrow(HjhPlanAsset mqHjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType);
}
