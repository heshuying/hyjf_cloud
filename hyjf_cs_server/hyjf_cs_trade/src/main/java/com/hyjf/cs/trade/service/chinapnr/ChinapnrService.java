/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.chinapnr;

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
}
