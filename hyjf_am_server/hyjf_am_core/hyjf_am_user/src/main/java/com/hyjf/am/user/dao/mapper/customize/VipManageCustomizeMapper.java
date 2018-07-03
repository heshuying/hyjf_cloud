/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.VipManageListCustomize;

import java.util.List;
import java.util.Map; /**
 * @author yaoyong
 * @version VipManageCustomizeMapper, v0.1 2018/7/2 18:08
 */
public interface VipManageCustomizeMapper {

    /**
     * 根据条件查询vip用户条数
     * @param mapParam
     * @return
     */
    Integer countRecord(Map<String, Object> mapParam);

    /**
     * 查询vip用户列表
     * @param mapParam
     * @return
     */
    List<VipManageListCustomize> selectUserList(Map<String, Object> mapParam);
}
