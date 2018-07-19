package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminBorrowStyleResponse;
import com.hyjf.am.resquest.admin.AdminBorrowStyleRequest;

/**
 * @author by xiehuili on 2018/7/12.
 */
public interface BorrowStyleService {

    /**
     * 分页查询列表
     * @param adminRequest
     * @return
     */
    public AdminBorrowStyleResponse borrowStyelInit(AdminBorrowStyleRequest adminRequest);
    /**
     * 根据id查询还款方式
     * @param adminRequest
     * @return
     */
    public AdminBorrowStyleResponse searchBorrowStyleInfo(AdminBorrowStyleRequest adminRequest);
    /**
     * 保存还款方式
     * @param adminRequest
     * @return
     */
    public AdminBorrowStyleResponse insertBorrowStyle(AdminBorrowStyleRequest adminRequest);
    /**
     * 修改还款方式
     * @param adminRequest
     * @return
     */
    public AdminBorrowStyleResponse updateBorrowStyle(AdminBorrowStyleRequest adminRequest);
    /**
     * 根据id删除还款方式
     * @param id
     * @return
     */
    public AdminBorrowStyleResponse deleteBorrowStyle(Integer id);
    /**
     * 根据id修改还款方式状态
     * @param id
     * @return
     */
    public AdminBorrowStyleResponse modifyBorrowStyle(Integer id);
    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */

    public boolean isExistsPermission(AdminBorrowStyleRequest adminRequest);
}
