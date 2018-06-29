/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.response.user.GroupInfoResponse;
import com.hyjf.am.user.dao.mapper.auto.ROaDepartmentMapper;
import com.hyjf.am.user.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.user.service.GroupInfoService;
import com.hyjf.am.vo.user.OrganizationStructureVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: GroupInfoServiceImpl, v0.1 2018/6/27 13:30
 * 集团组织结构查询
 */
@Service
public class GroupInfoServiceImpl extends BaseServiceImpl implements GroupInfoService {

    @Autowired
    private ROaDepartmentMapper rOaDepartmentMapper;

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
