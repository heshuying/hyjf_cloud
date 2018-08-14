package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;

import java.util.List;

/**
 * 我的邀请记录
 * @author hesy
 * @version MyInviteService, v0.1 2018/6/22 20:15
 */
public interface MyInviteService extends BaseService{
    List<MyInviteListCustomizeVO> selectMyInviteList(String userId, Integer limitStart, Integer limitEnd);

    Integer countMyInviteList(String userId);
}
