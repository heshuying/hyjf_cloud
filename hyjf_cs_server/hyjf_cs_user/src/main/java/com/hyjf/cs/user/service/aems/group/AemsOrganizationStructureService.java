/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.group;

import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.OrganizationStructureVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.bean.AemsOrganizationStructureBean;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/7 14:06
 * @param 
 * @return 
 **/
public interface AemsOrganizationStructureService extends BaseUserService {

    /**
     * 获取集团组织架构信息
     * @param bean
     * @return
     */
    List<OrganizationStructureVO> searchOrganizationList(AemsOrganizationStructureBean bean);
}
