/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import java.util.List;

import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.vo.admin.BindLogVO;

/**
 * @author: sunpeikai
 * @version: AssociatedLogService, v0.1 2018/7/5 15:44
 */
public interface AssociatedLogService {

    /**
     * 根据筛选条件查询绑定日志count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getBindLogCount(BindLogListRequest request);

    /**
     * 根据筛选条件查询绑定日志list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BindLogVO> searchBindLogList(BindLogListRequest request);
}
