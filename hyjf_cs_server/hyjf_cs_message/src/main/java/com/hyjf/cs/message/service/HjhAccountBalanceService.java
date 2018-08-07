/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service;

import com.hyjf.am.vo.trade.HjhAccountBalanceVO;

import java.util.Date;
import java.util.List;

/**
 * @author liubin
 * @version HjhAccountBalanceService, v0.1 2018/8/2 11:07
 */
public interface HjhAccountBalanceService extends BaseMessageService{
    /**
     * 获取该期间的汇计划日交易量
     * @param date
     * @return
     */
    List<HjhAccountBalanceVO> getHjhAccountBalanceForActList(Date date);

    Boolean updateAccountBalance(HjhAccountBalanceVO hjhAccountBalance);
}
