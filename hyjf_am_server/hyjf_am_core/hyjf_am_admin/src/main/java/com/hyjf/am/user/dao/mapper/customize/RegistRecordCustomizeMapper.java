/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.RegistRecordCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerCustomizeMapper, v0.1 2018/6/20 10:01
 */
public interface RegistRecordCustomizeMapper {

    /**
     *  根据筛选条件查找会员列表
     * @param userRequest 筛选条件
     * @return
     */
    List<RegistRecordCustomize> selectRegistList(Map<String, Object> userRequest);

    /**
     *  根据筛选条件查找会员信息
     * @param userId 筛选条件
     * @return
     */
    RegistRecordCustomize selectRegistOne(Integer userId);

    /**
     * 根据条件获取用户列表总数
     * @param userRequest
     * @return
     */
    Integer countRecordTotal(Map<String, Object> userRequest);

 }
