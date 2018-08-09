/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.ca;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.common.exception.MQException;

import java.text.ParseException;

/**
 * @author zhangqingqing
 * @version FddCertificateService, v0.1 2018/6/27 17:00
 */
public interface FddCertificateService extends BaseService{

    void updateUserCAInfo(Integer userId, User user, UserInfo userInfo) throws Exception;

    /**
     * 法大大CA认证
     * @throws ParseException
     * @throws MQException
     */
    void fddCertificateAuthority() throws ParseException, MQException;
}
