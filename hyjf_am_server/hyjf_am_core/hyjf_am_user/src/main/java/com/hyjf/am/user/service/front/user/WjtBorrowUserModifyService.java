/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.BaseService;

/**
 * 温金投标的修改借款人
 *
 * @author liuyang
 * @version WjtBorrowUserModifyService, v0.1 2019/5/14 15:34
 */
public interface WjtBorrowUserModifyService extends BaseService {
    /**
     * 更新用户机构编号
     *
     * @param borrowUser
     */
    void modifyUserInstCode(User borrowUser);

    /**
     * 根据用户ID查询用户注册渠道是否存在
     *
     * @param userId
     * @return
     */
    boolean utmRegByUserId(String userId);

    /**
     * ch'ru
     * @param userId
     * @param wjtChannel
     */
    void insertBorrowUserUtmReg(String userId, String wjtChannel);
}
