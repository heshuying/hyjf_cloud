package com.hyjf.wbs.trade.service.customerinfo;

import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.service.BaseService;


/**
 * @Auther: wxd
 * @Date: 2019-04-11 09:20
 * @Description:
 */
public interface AccountService extends BaseService {
    Account getAccount(Integer userId);
}
