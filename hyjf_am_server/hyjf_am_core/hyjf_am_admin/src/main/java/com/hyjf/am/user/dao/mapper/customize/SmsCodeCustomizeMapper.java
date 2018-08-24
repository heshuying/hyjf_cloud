/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.user.dao.model.customize.SmsCodeCustomize;

/**
 * @author fq
 * @version SmsCodeCustomizeMapper, v0.1 2018/8/20 20:33
 */
public interface SmsCodeCustomizeMapper {
    /**
     * 筛选用户
     * @param request
     * @return
     */
    List<SmsCodeCustomize> queryUser(SmsCodeUserRequest request);
}
