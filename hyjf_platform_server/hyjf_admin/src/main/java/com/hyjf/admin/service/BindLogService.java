/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.vo.admin.BindLogVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BindLogService, v0.1 2018/7/5 15:47
 */
public interface BindLogService {

    /**
     * 根据筛选条件查询绑定日志count
     * @auth sunpeikai
     * @param request 筛选条件
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
