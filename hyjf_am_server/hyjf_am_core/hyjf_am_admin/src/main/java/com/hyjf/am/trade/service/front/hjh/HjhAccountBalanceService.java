/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.HjhAccountBalanceVO;

import java.util.Date;
import java.util.List;

/**
 * @author liubin
 * @version HjhAccountBalanceService, v0.1 2018/8/2 10:54
 */
public interface HjhAccountBalanceService extends BaseService {
    /**
     * 获取该期间的汇计划日交易量
     * @param date
     * @return
     */
    List<HjhAccountBalanceVO> getHjhAccountBalanceForActList(Date date);
}
