/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.dao.model.auto.ROaDepartmentExample;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: GroupInfoService, v0.1 2018/6/27 11:57
 */
public interface GroupInfoService {
    List<ROaDepartment> selectByExample(ROaDepartmentExample example);
}
