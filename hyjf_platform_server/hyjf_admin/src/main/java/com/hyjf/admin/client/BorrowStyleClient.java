package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminBorrowStyleResponse;
import com.hyjf.am.resquest.admin.AdminBorrowStyleRequest;

/**
 * @author by xiehuili on 2018/7/12.
 */
public interface BorrowStyleClient {

    /**
     * 查询配置中心还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse borrowStyelInit(AdminBorrowStyleRequest adminRequest);

    /**
     * 根据id查询还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse searchBorrowStyleInfo(AdminBorrowStyleRequest adminRequest);
    /**
     * 保存还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse insertBorrowStyle(AdminBorrowStyleRequest adminRequest);
    /**
     * 修改还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse updateBorrowStyle(AdminBorrowStyleRequest adminRequest);
    /**
     * 修改还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse deleteBorrowStyle(Integer id);
    /**
     * 修改还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse modifyBorrowStyle(Integer id);
    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */

    public boolean validatorFieldCheck(AdminBorrowStyleRequest adminRequest);
}
