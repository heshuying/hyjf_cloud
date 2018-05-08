/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.MobileCode;

/**
 * @author fuqiang
 * @version UserAliasService, v0.1 2018/5/8 10:55
 */
public interface UserAliasService {
    /**
     * 根据手机号查询推送别名
     * @param mobile
     * @return
     */
    MobileCode findAliasByMobile(String mobile);
}
