/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.account;

import com.hyjf.am.user.service.BaseService;

/**
 * @author zhangqingqing
 * @version ChinapnrAccountService, v0.1 2018/9/8 11:21
 */
public interface ChinapnrAccountService extends BaseService {

    /**
     * 根据汇付账户查询user_id
     * @param chinapnrUsrcustid
     * @return
     */
    int selectUserIdByUsrcustid(Long chinapnrUsrcustid);
}
