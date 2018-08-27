/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.trade.dao.model.customize.SmsCodeCustomize;

import java.util.List;

/**
 * @author fq
 * @version SmsCodeCustomizeMapper, v0.1 2018/8/20 19:12
 */
public interface SmsCodeCustomizeMapper {
    /**
     *筛选符合条件的用户
     * @param request
     * @return
     */
    List<SmsCodeCustomize> queryUser(SmsCodeUserRequest request);
}
