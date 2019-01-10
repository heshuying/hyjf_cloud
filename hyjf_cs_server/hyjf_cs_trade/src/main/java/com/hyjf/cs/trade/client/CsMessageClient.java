/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.am.vo.trade.ChinapnrLogVO;

import java.util.List;

/**
 * @author yaoyong
 * @version CsMessageClient, v0.1 2018/8/14 19:46
 */
public interface CsMessageClient {
    Integer insertAccountWebList(AccountWebListVO accountWebList);

	AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId);

    /**
     * 取得检证数据
     * @param id
     * @return
     */
    ChinapnrExclusiveLogWithBLOBsVO selectChinapnrExclusiveLog(long id);

    /**
     * 更新状态
     * @param uuid
     * @param status
     */
    void updateChinapnrExclusiveLogStatus(long uuid, String status);

    /**
     * 将状态更新成[2:处理中]
     * @param record
     * @return
     */
    int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record);

    /**
     * 取得成功时的信息
     * @param ordId
     * @return
     */
    List<ChinapnrLogVO> getChinapnrLog(String ordId);
}
