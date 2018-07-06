/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AssociatedRecordsService, v0.1 2018/7/5 14:24
 */
public interface AssociatedRecordsService {
    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getAssociatedRecordsCount(AssociatedRecordListRequest request);

    /**
     * 根据筛选条件查询关联记录list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<AssociatedRecordListVo> getAssociatedRecordList(AssociatedRecordListRequest request);
}
