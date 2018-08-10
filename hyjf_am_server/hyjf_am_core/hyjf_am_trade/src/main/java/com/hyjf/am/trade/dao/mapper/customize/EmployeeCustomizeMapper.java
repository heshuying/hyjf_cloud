/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.EmployeeCustomize;
import org.apache.ibatis.annotations.Param;

/**
 * @author liubin
 * @version EmployeeCustomizeMapper, v0.1 2018/6/30 16:56
 */
public interface EmployeeCustomizeMapper {

    /**
     * 获取员工信息
     *
     * @param
     * @return
     */
    EmployeeCustomize selectEmployeeByUserId(@Param("userId") Integer userId);

    /**
     * 根据平台userid查询其在crm的属性
     * @param userId
     * @return
     */
    public Integer queryCuttype(@Param("userId") Integer userId);
}
