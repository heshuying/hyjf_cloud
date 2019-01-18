package com.hyjf.am.trade.service.task.issuerecover;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.service.BaseService;

/**
 * @Auther: Walter
 * @Date: 2018/7/11 19:03
 * @Description:自动审核保证金
 */
public interface AutoBailMessageService extends BaseService{

    /**
     * 判断该资产是否可以自动审核保证金
     * @param borrow
     * @return
     */
    boolean updateRecordBorrow(Borrow borrow);
}
