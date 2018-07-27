package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.ROaDepartment;

/**
 * @Description crm Department表操作
 * @Author sunss
 * @Date 2018/7/26 11:50
 */
public interface ROaDepartmentService {
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
    Integer delete(ROaDepartment department);
}
