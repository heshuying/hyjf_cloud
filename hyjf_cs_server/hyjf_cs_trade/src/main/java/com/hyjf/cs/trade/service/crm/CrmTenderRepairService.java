/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.crm;

import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * CRM投资修复
 *
 * @author liuyang
 * @version CrmTenderRepairService, v0.1 2019/3/12 9:37
 */

public interface CrmTenderRepairService extends BaseService {

    /**
     * 查询投资数据
     * @return
     */
    List<BorrowTenderVO> selectCrmBorrowTenderList();

    /**
     * 发送mq
     *
     * @param borrowTenderVO
     */
    void sendCrmTenderInfoMQ(BorrowTenderVO borrowTenderVO);

    /**
     * 查询智投投资数据
     * @return
     */
    List<HjhAccedeVO> selectCrmHjhAccedeList();


    void sendCrmHjhInfoMQ(HjhAccedeVO hjhAccedeVO);
}
