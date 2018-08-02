package com.hyjf.am.user.service.front.crm.impl;

import com.hyjf.am.user.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.service.front.crm.ROaDepartmentService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description crm oa Department 表同步
 * @Author sunss
 * @Date 2018/7/26 13:52
 */
@Service
public class ROaDepartmentServiceImpl extends BaseServiceImpl implements ROaDepartmentService {


    /**
     * 修改
     *
     * @param department
     * @return
     */
    @Override
    public Integer update(ROaDepartment department) {
        return rOaDepartmentMapper.updateByPrimaryKeySelective(department);
    }

    /**
     * 新增
     *
     * @param department
     * @return
     */
    @Override
    public Integer insert(ROaDepartment department) {
        return rOaDepartmentMapper.insertSelective(department);
    }

    /**
     * 删除
     *
     * @param department
     * @return
     */
    @Override
    public Integer delete(ROaDepartment department) {
        return rOaDepartmentMapper.deleteByPrimaryKey(department.getId());
    }
}
