package com.hyjf.am.trade.service.task.issuerecover;

import com.hyjf.am.trade.dao.model.auto.*;

/**
 * @Author walter.limeng
 * @Description  关联计划消息
 * @Date 11:10 2018/7/12
 */
public interface AutoIssueMessageService {
    /**
     * @Author walter.limeng
     * @Description  根据标的编号和机构编号查询资产
     * @Date 11:12 2018/7/12
     * @Param borrowNid 标的编号
     * @Param instCode 机构编号
     * @return HjhPlanAsset
     */
    HjhPlanAsset selectPlanAssetByBorrowNid(String borrowNid, String instCode);

    /**
     * @Author walter.limeng
     * @Description  标的匹配标签,取匹配最优的标签
     * @Date 11:15 2018/7/12
     * @Param borrow
     * @Param hjhPlanAsset
     * @return
     */
    HjhLabel getLabelId(BorrowInfo borrowInfo,Borrow borrow, HjhPlanAsset hjhPlanAsset);

    /**
     * @Author walter.limeng
     * @Description  分配计划引擎
     * @Date 11:20 2018/7/12
     * @Param labelId
     * @return 
     */
    String getPlanNid(Integer labelId);

    /**
     * @Author walter.limeng
     * @Description  更新标的计划编号，redis计划
     * @Date 11:21 2018/7/12
     * @Param borrowInfo
     * @Param borrow
     * @Param planNid
     * @Param asset
     * @return
     */
    boolean updateIssueBorrow(BorrowInfo borrowInfo, Borrow borrow, String planNid, HjhPlanAsset asset);

    /**
     * @Author walter.limeng
     * @Description  根据债转ID查询债转对象
     * @Date 11:35 2018/7/12
     * @Param creditNid 债转ID
     * @return
     */
    HjhDebtCredit getCreditByNid(String creditNid);

    /**
     * @Author walter.limeng
     * @Description  根据债转ID获取债转标签
     * @Date 11:36 2018/7/12
     * @Param credit
     * @return
     */
    HjhLabel getLabelId(HjhDebtCredit credit);

    /**
     * @Author walter.limeng
     * @Description  关联计划
     * @Date 11:44 2018/7/12
     * @Param credit
     * @Param planNid
     * @return
     */
    boolean updateIssueCredit(HjhDebtCredit credit, String planNid);
}
