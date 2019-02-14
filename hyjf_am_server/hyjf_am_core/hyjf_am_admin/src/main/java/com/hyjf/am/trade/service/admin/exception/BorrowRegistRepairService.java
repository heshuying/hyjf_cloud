/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistRepairService, v0.1 2018/7/3 15:06
 */
public interface BorrowRegistRepairService {

    /**
     * 获取项目类型,筛选条件展示
     * @auth sunpeikai
     * @param
     * @return 项目类型list封装
     */
    List<BorrowProjectType> selectBorrowProjectList();

    /**
     * 获取还款方式,筛选条件展示
     * @auth sunpeikai
     * @param
     * @return 还款方式list封装
     */
    List<BorrowStyle> selectBorrowStyleList();

    /**
     * 获取标的列表count,用于前端分页展示
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的列表
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return 异常标list封装
     */
    List<BorrowRegistCustomize> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 根据borrowNid查询borrow信息
     * @auth sunpeikai
     * @param
     * @return
     */
    BorrowAndInfoVO searchBorrowByBorrowNid(String borrowNid);

    /**
     * 根据受托支付userId查询stAccountId
     * @auth sunpeikai
     * @param entrustedUserId 受托支付userId
     * @return
     */
    String getStAccountIdByEntrustedUserId(Integer entrustedUserId);

    /**
     * 更新标
     * @auth sunpeikai
     * @param type 1更新标的备案 2更新受托支付标的备案
     * @return
     */
    Boolean updateBorrowRegistByType(BorrowRegistUpdateRequest registUpdateRequest);

    /**
     * 更新标的资产信息如果关联计划的话
     * @auth sunpeikai
     * @param status 状态  受托支付传4，非受托支付传5
     * @return
     */
    Boolean updateBorrowAsset(BorrowAndInfoVO borrowVO,Integer status);
}
