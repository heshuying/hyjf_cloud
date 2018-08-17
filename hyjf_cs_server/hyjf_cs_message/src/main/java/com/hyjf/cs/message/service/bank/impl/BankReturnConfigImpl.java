/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.bank.impl;

import com.hyjf.cs.message.bean.mc.BankExclusiveLog;
import com.hyjf.cs.message.mongo.mc.BankReturnConfigDao;
import com.hyjf.cs.message.service.bank.BankReturnConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version BankReturnConfigImpl, v0.1 2018/8/3 14:21
 */
@Service
public class BankReturnConfigImpl implements BankReturnConfig {
    @Autowired
    BankReturnConfigDao bankReturnConfigDao;

    @Override
    public String getRetCode(String logOrdId) {
        BankExclusiveLog result = bankReturnConfigDao.selectChinapnrExclusiveLogByOrderId(logOrdId);
        if (result==null){
            return null;
        }
        return result.getRespcode();
    }
}
