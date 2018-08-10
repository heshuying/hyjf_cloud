/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.organization.impl;

import com.hyjf.am.user.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.user.service.admin.organization.GroupInfoService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: GroupInfoServiceImpl, v0.1 2018/6/27 13:30
 * 集团组织结构查询
 */
@Service
public class GroupInfoServiceImpl extends BaseServiceImpl implements GroupInfoService {

    /**
     * 查询集团组织结构List
     * @return List<ROaDepartment> 组织结构list
     */
    @Override
    public List<ROaDepartment> searchGroupInfo() {
        ROaDepartmentExample example = new ROaDepartmentExample();
        List<ROaDepartment> rOaDepartmentList = rOaDepartmentMapper.selectByExample(example);
        return rOaDepartmentList;
    }
}
