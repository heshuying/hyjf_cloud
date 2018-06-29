/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.GroupInfoResponse;
import com.hyjf.am.user.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.user.service.GroupInfoService;
import com.hyjf.am.vo.user.OrganizationStructureVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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

    @Autowired
    private GroupInfoService groupInfoService;
    /**
     * 查询集团组织机构
     * @auther: sunpeikai
     * @date: 2018/6/27
     */
    @RequestMapping("/query_group_info")
    public GroupInfoResponse queryGroupInfo() {
        GroupInfoResponse response = new GroupInfoResponse();
        // 查询全部
        List<ROaDepartment> rOaDepartmentList = groupInfoService.searchGroupInfo();
        if(!CollectionUtils.isEmpty(rOaDepartmentList)){
            List<OrganizationStructureVO> organizationStructureVOList = CommonUtils.convertBeanList(rOaDepartmentList, OrganizationStructureVO.class);
            // 塞入返回列表
            response.setResultList(organizationStructureVOList);
            response.setRtn(Response.SUCCESS);
        }

        return response;
    }
}
