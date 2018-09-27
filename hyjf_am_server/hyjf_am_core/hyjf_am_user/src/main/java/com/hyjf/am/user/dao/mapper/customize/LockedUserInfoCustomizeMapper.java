package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.auto.LockedUserInfo;

import java.util.List;
import java.util.Map;

public interface LockedUserInfoCustomizeMapper {

    int countRecordTotal(Map<String, Object> parameterMap);

    List<LockedUserInfo> getRecordList(Map<String, Object> parameterMap);
}
