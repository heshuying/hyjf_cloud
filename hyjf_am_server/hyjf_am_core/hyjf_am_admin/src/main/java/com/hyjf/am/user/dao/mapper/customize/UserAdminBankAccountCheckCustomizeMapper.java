/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;

/**
 * @author PC-LIUSHOUYI
 * @version AdminBankAccountCheckCustomizeMapper, v0.1 2018/7/2 21:50
 */
public interface UserAdminBankAccountCheckCustomizeMapper {
    /**
     * 查询所有用户开户行数据
     * @param customize
     * @return
     */
    List<AdminBankAccountCheckCustomizeVO> queryAllBankOpenAccount(AdminBankAccountCheckCustomizeVO customize);

}
