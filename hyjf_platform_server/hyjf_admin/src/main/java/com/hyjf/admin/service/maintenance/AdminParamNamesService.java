/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.maintenance;

import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.resquest.admin.AdminParamNameRequest;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AdminParamNamesService, v0.1 2018/9/6 11:03
 */
public interface AdminParamNamesService extends BaseAdminService {
    /**
     * 查询数据字典条数
     * @auth sunpeikai
     * @param
     * @return
     */
    int getParamNamesCount(AdminParamNameRequest request);
    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<ParamNameVO> searchParamNamesList(AdminParamNameRequest request);
    /**
     * 添加数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean insertParamName(ParamNameVO paramNameVO);
    /**
     * 修改数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean updateParamName(ParamNameVO paramNameVO);
    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean deleteParamName(ParamNameVO paramNameVO);
}
