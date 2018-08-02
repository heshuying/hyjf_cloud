/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.organization;

import com.hyjf.am.user.dao.model.auto.ROaDepartment;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: GroupInfoService, v0.1 2018/6/27 11:57
 */
public interface GroupInfoService {
    /**
     * 查询组织结构列表
     * @
     * */
    List<ROaDepartment> searchGroupInfo();
}
