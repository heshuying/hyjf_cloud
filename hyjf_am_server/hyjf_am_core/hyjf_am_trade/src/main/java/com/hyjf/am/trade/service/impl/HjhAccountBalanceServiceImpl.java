/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.service.HjhAccountBalanceService;
import com.hyjf.am.vo.trade.HjhAccountBalanceVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liubin
 * @version HjhAccountBalanceServiceImpl, v0.1 2018/8/2 10:55
 */
@Service
public class HjhAccountBalanceServiceImpl extends BaseServiceImpl implements HjhAccountBalanceService {
    /**
     * 获取该期间的汇计划日交易量
     * @param date
     * @return
     */
    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceForActList(Date date) {
        List<HjhAccountBalanceVO> list = this.hjhAccountBalanceCustomizeMapper.selectHjhAccountBalanceForActList(date);
        return list;
    }
}
