/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.auto.ROaDepartmentMapper;
import com.hyjf.am.user.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.user.service.GroupInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: GroupInfoServiceImpl, v0.1 2018/6/27 13:30
 * 集团组织结构查询
 */
@Service
public class GroupInfoServiceImpl implements GroupInfoService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ROaDepartmentMapper rOaDepartmentMapper;

    /**
     * 查询集团组织结构List
     * @param
     * @return List<ROaDepartment>
     */
    @Override
    public List<ROaDepartment> selectByExample(ROaDepartmentExample example) {
        List<ROaDepartment> rOaDepartmentList = rOaDepartmentMapper.selectByExample(example);
        if (rOaDepartmentList != null && rOaDepartmentList.size() > 0) {
            return rOaDepartmentList;
        }
        return null;
    }
}
