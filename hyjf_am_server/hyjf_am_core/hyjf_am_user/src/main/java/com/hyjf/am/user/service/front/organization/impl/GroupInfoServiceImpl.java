/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.organization.impl;

import com.hyjf.am.user.dao.mapper.auto.ROaDepartmentMapper;
import com.hyjf.am.user.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.user.service.front.organization.GroupInfoService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: GroupInfoServiceImpl, v0.1 2018/6/27 13:30
 * 集团组织结构查询
 */
@Service
public class GroupInfoServiceImpl extends BaseServiceImpl implements GroupInfoService {
    @Autowired
    protected ROaDepartmentMapper rOaDepartmentMapper;
    /**
     * 查询集团组织结构List
     * @return List<ROaDepartment> 组织结构list
     */
    @Override
    public List<ROaDepartment> searchGroupInfo() {
        ROaDepartmentExample example = new ROaDepartmentExample();
		List<ROaDepartment> rOaDepartmentList = rOaDepartmentMapper.selectByExample(example);
		for(ROaDepartment rOaDepartment:rOaDepartmentList){
		    if(null == rOaDepartment.getFlag()){
		        rOaDepartment.setFlag(0);
		        continue;
            }
            rOaDepartment.setFlag((rOaDepartment.getFlag()==1)?1:0);
        }
        return rOaDepartmentList;
    }
}
