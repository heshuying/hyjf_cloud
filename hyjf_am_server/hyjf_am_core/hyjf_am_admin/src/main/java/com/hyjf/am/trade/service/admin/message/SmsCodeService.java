/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.message;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;

import java.util.List;

/**
 * @author fq
 * @version SmsCodeService, v0.1 2018/8/20 20:30
 */
public interface SmsCodeService {
    /**
     * 查询符合条件的用户
     * @param request
     * @return
     */
    List<String> queryUser(SmsCodeUserRequest request);

    /**
     * 筛选符合条件的用户数量
     * @param request
     * @return
     */
    int countUser(SmsCodeUserRequest request);
}
