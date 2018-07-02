/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.group;

import com.hyjf.am.vo.user.OrganizationStructureVO;
import com.hyjf.cs.user.bean.OrganizationStructureRequestBean;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: ApiOrganizationStructureService, v0.1 2018/6/27 9:38
 * 集团组织结构查询
 */
public interface ApiOrganizationStructureService extends BaseUserService {
    /**
     * 查询集团组织结构
     * @param groupQueryRequestBean 主要是instCode 机构编号
     * */
    List<OrganizationStructureVO> queryInfo(OrganizationStructureRequestBean groupQueryRequestBean);
}
