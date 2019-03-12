/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.crm;

import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * CRM投资同步修复Service
 *
 * @author liuyang
 * @version CrmTenderRepairService, v0.1 2019/3/12 9:54
 */
public interface CrmTenderRepairService extends BaseService {

    List<BorrowTender> selectCrmBorrowTenderList();

    List<HjhAccede> selectCrmHjhAccedeList();
}
