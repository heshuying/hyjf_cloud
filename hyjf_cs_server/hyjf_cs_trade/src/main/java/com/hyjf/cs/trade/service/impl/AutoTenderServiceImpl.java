/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.cs.trade.controller.batch.AutoTenderController;
import com.hyjf.cs.trade.service.AutoTenderService;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 自动投资
 * @author liubin
 * @version AutoTenderServiceImpl, v0.1 2018/6/28 14:09
 */
@Service
public class AutoTenderServiceImpl extends BaseTradeServiceImpl implements AutoTenderService {
    @Override
    public void AutoTender() {
        logger.info("com.hyjf.cs.trade.service.impl.AutoTenderServiceImpl.AutoTender");
    }
}
