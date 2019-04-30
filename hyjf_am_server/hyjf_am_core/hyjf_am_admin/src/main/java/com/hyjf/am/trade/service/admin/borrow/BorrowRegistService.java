/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistService, v0.1 2018/6/29 16:54
 */
public interface BorrowRegistService extends BaseService {
    /**
     * 获取项目类型
     * @return
     */
    List<BorrowProjectType> selectBorrowProjectList();

    /**
     * 获取还款方式
     * @return
     */
    List<BorrowStyle> selectBorrowStyleList();

    /**
     * 获取标的备案列表count
     * @param borrowRegistListRequest
     * @return
     */
    Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的备案列表
     * @param borrowRegistListRequest
     * @return
     */
    List<BorrowRegistCustomize> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 统计总额
     * @param borrowRegistListRequest
     * @return
     */
    String getSumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 标的备案
     * @param request
     * @return
     */
    Response updateBorrowRegist(BorrowRegistUpdateRequest request);

    Response updateForRegistCancel(BorrowRegistUpdateRequest requestBean);
}
