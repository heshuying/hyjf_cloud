/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AdminPlanAccedeListCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AdminAutoTenderExceptionMapper, v0.1 2018/7/12 11:02
 */
public interface AdminAutoTenderExceptionMapper {
    /**
     * 检索加入明细异常件数
     *
     * @Title countAccedeRecord
     * @param param
     * @return
     */
    int countAccedeExceptionRecord(Map<String, Object> param);

    /**
     * 检索加入明细异常列表
     *
     * @Title selectAccedeRecordList
     * @param param
     * @return
     */
    List<AdminPlanAccedeListCustomize> selectAccedeExceptionList(Map<String, Object> param);
}
