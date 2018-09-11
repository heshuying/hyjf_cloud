/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.chinapnr;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version ChinapnrService, v0.1 2018/9/5 15:27
 */
public interface ChinapnrService extends BaseTradeService{

    /**
     * 提现前获取信息
     * @param userId
     * @return
     */
    Map<String,Object> toWithdraw(Integer userId);

    /**
     * 校验提现参数
     * @param userId
     * @param transAmt
     * @param bankId
     * @return
     */
    void checkParam(Integer userId, String transAmt, String bankId);

    /**
     * 汇付提现
     * @param userId
     * @param transAmt
     * @param bankId
     * @param cashchl
     * @param userName
     * @param ip
     * @return
     */
    Map<String, Object> cash(Integer userId, String transAmt, String bankId, String cashchl,String userName,String ip);

    /**
     * 取得检证数据
     * @param id
     * @return
     */
    ChinapnrExclusiveLogWithBLOBsVO selectChinapnrExclusiveLog(long id);

    /**
     * 将状态更新成[2:处理中]
     * @param record
     * @return
     */
    int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record);

    /**
     * 判断是否提现成功
     * @param ordId
     * @return
     */
    String checkCashResult(String ordId);

    /**
     * 取得成功时的信息
     * @param ordId
     * @return
     */
    JSONObject getMsgData(String ordId);

    /**
     * 根据汇付账户查询user_id
     * @param chinapnrUsrcustid
     * @return
     */
    Integer selectUserIdByUsrcustid(Long chinapnrUsrcustid);

    /**
     * 执行提现后处理
     * @param chinaPnrWithdrawRequest
     * @return
     */
    boolean handlerAfterCash(ChinaPnrWithdrawRequest chinaPnrWithdrawRequest);

    /**
     * 更新提现表
     * @param ordId
     * @param reason
     */
    void updateAccountWithdrawByOrdId(String ordId, String reason);

    /**
     * 更新状态记录
     * @param uuid
     * @param status
     */
    void updateChinapnrExclusiveLogStatus(long uuid, String status);
}
