package com.hyjf.am.user.service.admin.locked;

import com.hyjf.am.user.dao.model.auto.LockedUserInfo;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author cui
 * @version LockedUserService, v0.1 2018/9/25 10:41
 */
public interface LockedUserService {


    int countRecordTotal(Map<String, Object> parameterMap);

    List<LockedUserInfo> getRecordList(Map<String, Object> parameterMap);

    Integer getLockedUserId(String username);

    boolean unlock(LockedUserInfoVO lockedUserId, boolean isFront);
}
