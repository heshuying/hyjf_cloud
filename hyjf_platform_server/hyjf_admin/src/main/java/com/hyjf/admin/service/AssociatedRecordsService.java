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
    Integer getAssociatedRecordsCount(AssociatedRecordListRequest request);
    List<AssociatedRecordListVo> getAssociatedRecordList(AssociatedRecordListRequest request);
}
