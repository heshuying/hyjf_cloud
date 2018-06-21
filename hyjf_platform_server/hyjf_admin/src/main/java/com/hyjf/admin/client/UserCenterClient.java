/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserManagerVO;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClient, v0.1 2018/6/20 15:37
 */
public interface UserCenterClient {
    /**
     *查找用户信息
     * @param request
     * @return
     */
    List<UserManagerVO> selectUserMemberList(UserManagerRequest request);
    /**
     * 根据机构编号获取机构列表
     * @param instCode
     * @return
     */
    List<HjhInstConfigVO> selectHjhInstConfigListByInstCode(String instCode);

    /**
     * 根据筛选条件查找用户总数
     * @param request
     * @return
     */
    int  countRecordTotal(UserManagerRequest request);
}