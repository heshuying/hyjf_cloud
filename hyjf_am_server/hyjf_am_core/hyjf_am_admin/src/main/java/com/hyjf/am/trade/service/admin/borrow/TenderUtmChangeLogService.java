/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.vo.trade.borrow.TenderUpdateUtmHistoryVO;

import java.util.List;

/**
 * @author cui
 * @version TenderUtmChangeLogService, v0.1 2019/6/18 10:44
 */
public interface TenderUtmChangeLogService {

    /**
     * 根据订单ID查询修改渠道记录
     * @param nid
     * @return
     */
    List<TenderUpdateUtmHistoryVO> getChangeLog(String nid);

    List<TenderUpdateUtmHistoryVO> getPlanTenderChangeLog(String planOrderId);

}
