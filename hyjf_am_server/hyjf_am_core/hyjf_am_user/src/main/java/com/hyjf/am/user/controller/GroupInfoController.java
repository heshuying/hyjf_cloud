/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.user.GroupInfoResponse;
import com.hyjf.am.user.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.user.service.GroupInfoService;
import com.hyjf.am.vo.user.OrganizationStructureVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 集团组织机构查询服务类
 * @author: sunpeikai
 * @version: GroupController, v0.1 2018/6/27 10:47
 */
@RestController
@RequestMapping("/am-user/group")
public class GroupInfoController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GroupInfoService groupInfoService;
    /**
     * 查询集团组织机构
     * @auther: sunpeikai
     * @date: 2018/6/27
     */
    @RequestMapping("/queryGroupInfo")
    public GroupInfoResponse queryGroupInfo() {
        GroupInfoResponse response = new GroupInfoResponse();
        ROaDepartmentExample example = new ROaDepartmentExample();
        List<OrganizationStructureVO> organizationStructureVOList = new ArrayList<>();
        // 查询全部
        List<ROaDepartment> rOaDepartmentList = groupInfoService.selectByExample(example);
        if (rOaDepartmentList != null) {
            for(ROaDepartment rOaDepartment:rOaDepartmentList){
                // 参数赋值
                OrganizationStructureVO organizationStructureVO = new OrganizationStructureVO();
                organizationStructureVO.setId(rOaDepartment.getId());
                organizationStructureVO.setParentid(rOaDepartment.getParentid());
                organizationStructureVO.setName(rOaDepartment.getName());
                organizationStructureVO.setCuttype(rOaDepartment.getCuttype());
                organizationStructureVO.setFlag(rOaDepartment.getFlag());
                // 塞入列表
                organizationStructureVOList.add(organizationStructureVO);
            }
            // 塞入返回列表
            response.setResultList(organizationStructureVOList);
        }
        return response;
    }
}
