package com.hyjf.pay.service;

import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;

public interface DzqzPayLogService {

    /**
     * 保存发送日志
     */
    public void saveDzqzPaySendLog(DzqzCallBean bean);

    /**
     * 保存返回日志
     */
    public void saveDzqzPayReturnLog(DzqzCallBean bean);
}
