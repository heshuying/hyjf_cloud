/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.trade;

import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.trade.dao.model.auto.ChinapnrExclusiveLogWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.ChinapnrLog;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * 汇付
 * @author zhangqingqing
 * @version ChinapnrService, v0.1 2018/9/8 10:11
 */
public interface ChinapnrService extends BaseService {

    /**
     * 取得检证数据
     * @param id
     * @return
     */
    ChinapnrExclusiveLogWithBLOBs selectChinapnrExclusiveLog(long id);

    /**
     * 更新信息
     * @param record
     * @return
     */
    int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBs record);

    /**
     * 取得成功时的信息
     * @param ordId
     * @return
     */
    List<ChinapnrLog> getChinapnrLog(String ordId);

    /**
     * 更新提现表
     * @param ordId
     * @param reason
     * @return
     */
    int updateAccountWithdrawByOrdId(String ordId, String reason);

    /**
     * 更新检证状态
     * @param uuid
     * @param status
     * @return
     */
    int updateChinapnrExclusiveLogStatus(long uuid, String status);

    /**
     * 汇付体现后处理
     * @param chinaPnrWithdrawRequest
     * @return
     */
    boolean updateAfterCash(ChinaPnrWithdrawRequest chinaPnrWithdrawRequest);
}
