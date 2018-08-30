package com.hyjf.am.trade.service.front.crm;

import com.hyjf.am.trade.dao.model.auto.ROaDepartment;
import com.hyjf.am.trade.dao.model.auto.ROaDepartmentExample;

/**
 * @Description crm Department表操作
 * @Author sunss
 * @Date 2018/7/26 11:50
 */
public interface CrmDepartmentService {
    /**
     * 修改
     *
     * @param department
     * @return
     */
    Integer update(ROaDepartment department);

    /**
     * 新增
     *
     * @param department
     * @return
     */
    Integer insert(ROaDepartment department);

    /**
     * 删除
     *
     * @param department
     * @return
     */
    Integer delete(Integer department);

    /**
     * 根据DepartmentExample 修改
     * @param example
     */
    void updateByExample(ROaDepartment department , ROaDepartmentExample example);
}
