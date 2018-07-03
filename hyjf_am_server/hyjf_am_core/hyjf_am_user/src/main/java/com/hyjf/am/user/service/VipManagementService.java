/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.customize.VipManageListCustomize;

import java.util.List;
import java.util.Map; /**
 * @author yaoyong
 * @version VipManagementService, v0.1 2018/7/2 17:48
 */
public interface VipManagementService {
    int countRecord(Map<String, Object> mapParam);

    List<VipManageListCustomize> selectUserList(Map<String, Object> mapParam, int offset, int limit);
}
