/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.dao.model.customize.trade.BorrowRegistCustomize;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistService, v0.1 2018/6/29 16:54
 */
public interface BorrowRegistService {
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
     * 获取受托支付电子账户列表
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    StzhWhiteList selectStzfWhiteList(String instCode, String entrustedAccountId);

    /**
     * 更新相应的标的信息
     * @param request
     * @return
     */
    int updateBorrowRegistFromList(BorrowRegistRequest request);

    /**
     * 更新标的信息(受托支付备案)
     * @param request
     * @return
     */
    int updateEntrustedBorrowRegist(BorrowRegistRequest request);
}
