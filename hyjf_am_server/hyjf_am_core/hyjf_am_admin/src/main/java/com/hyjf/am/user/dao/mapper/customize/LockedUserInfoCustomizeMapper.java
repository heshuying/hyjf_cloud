package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.user.dao.model.auto.LockedUserInfo;

public interface LockedUserInfoCustomizeMapper {

    int countRecordTotal(Map<String, Object> parameterMap);

    List<LockedUserInfo> getRecordList(Map<String, Object> parameterMap);
}
