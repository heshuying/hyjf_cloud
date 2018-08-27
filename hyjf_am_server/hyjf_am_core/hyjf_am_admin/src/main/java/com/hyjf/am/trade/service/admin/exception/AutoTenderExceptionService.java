/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.dao.model.customize.AdminPlanAccedeListCustomize;

/**
 * @author nxl
 * @version AutoTenderExceptionService, v0.1 2018/7/12 10:57
 */
public interface AutoTenderExceptionService {

    /**
     * 检索加入明细件数
     *
     * @Title countAccedeRecord
     * @param mapParam
     * @return
     */
    int countAccedeRecord(Map<String,Object> mapParam);

    /**
     * 检索加入明细列表
     *
     * @Title selectAccedeRecordList
     * @param mapParam
     * @return
     */
    List<AdminPlanAccedeListCustomize> selectAccedeRecordList(Map<String,Object> mapParam);


    /**
     * 查询计划加入明细
     * @param mapParam
     * @return
     */
    HjhAccede selectHjhAccede(Map<String,Object> mapParam);

    /**
     * 查询计划加入明细临时表
     * @param mapParam
     * @return
     */
    HjhPlanBorrowTmp selectBorrowJoinList(Map<String,Object> mapParam);
    /**
     * 更改状态
     */
    boolean updateTender(Map<String,Object> mapParam);
}
