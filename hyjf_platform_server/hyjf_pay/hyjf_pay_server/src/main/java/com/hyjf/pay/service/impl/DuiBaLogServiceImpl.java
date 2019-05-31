package com.hyjf.pay.service.impl;

import com.hyjf.pay.entity.*;
import com.hyjf.pay.mongo.*;
import com.hyjf.pay.service.DuiBaLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DuiBaLogServiceImpl implements DuiBaLogService {

    @Autowired
    private DuiBaSendLogDao duiBaSendLogDao;

    @Autowired
    private DuiBaReturnLogDao duiBaReturnLogDao;

    /**
     * 保存兑吧发送日志
     * @param duiBaSendLog
     */
    @Override
    public void insertDuiBaSendLog(DuiBaSendLog duiBaSendLog) {
        duiBaSendLogDao.save(duiBaSendLog);
    }

    /**
     * 保存兑吧回调日志
     * @param duiBaReturnLog
     */
    @Override
    public void insertDuiBaReturnLog(DuiBaReturnLog duiBaReturnLog) {
        duiBaReturnLogDao.save(duiBaReturnLog);
    }
}
