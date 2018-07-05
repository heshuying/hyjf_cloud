/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AssociatedRecordsService, v0.1 2018/7/5 14:50
 */
public interface AssociatedRecordsService {
    Integer getAssociatedRecordsCount(AssociatedRecordListRequest request);
    List<AssociatedRecordListVo> searchAssociatedRecordList(AssociatedRecordListRequest request);
}
