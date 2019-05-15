/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre;

import com.hyjf.am.user.dao.model.customize.RegistRecordCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerService, v0.1 2018/6/20 9:47
 *          后台管理系统：会员中心->注册记录
 */
public interface RegistRecordManagerService extends BaseService {

    /**
     * 根据筛选条件查找会员列表
     *
     * @param mapParam
     * @return
     */
    List<RegistRecordCustomize> selectRegistList(Map<String, Object> mapParam,int limitStart, int limitEnd);

    /**
     * 根据条件查询用户管理总数
     *
     * @return
     */
    int countRecordTotal(Map<String, Object> mapParam );

    /**
     * 根据筛选条件查找注册信息
     *
     * @param userId 筛选条件
     * @return
     */
    RegistRecordCustomize selectRegistOne(Integer userId) ;

    /**
     * 根据用户id查询渠道类型
     *
     * @param userId 筛选条件
     * @return
     */
    RegistRecordCustomize selectByUserType(Integer userId);
}
