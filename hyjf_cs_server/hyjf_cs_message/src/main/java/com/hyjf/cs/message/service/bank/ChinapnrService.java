/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.bank;

import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.cs.message.bean.ic.ChinapnrExclusiveLog;
import com.hyjf.cs.message.bean.ic.ChinapnrLog;

import java.util.List;

/**
 * @author zhangqingqing
 * @version ChinapnrService, v0.1 2019/1/10 16:06
 */
public interface ChinapnrService {

    /**
     * 根据id获取 ChinapnrExclusiveLog
     * @param id
     * @return
     */
    ChinapnrExclusiveLog queryById(String id);

    /**
     * 更新检证状态
     * @param uuid
     * @param status
     * @return
     */
    void updateChinapnrExclusiveLogStatus(String uuid, String status);

    /**
     * 更新信息
     * @param record
     * @return
     */
    void updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record);

    List<ChinapnrLog> getChinapnrLog(String ordId);
}
