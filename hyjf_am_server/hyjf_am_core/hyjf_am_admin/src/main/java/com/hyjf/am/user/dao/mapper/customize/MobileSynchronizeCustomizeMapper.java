/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.user.dao.model.customize.MobileSynchronizeCustomize;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeCustomizeMapper, v0.1 2018/8/13 14:39
 */
public interface MobileSynchronizeCustomizeMapper {

    /**
     * 检索已开户用户数量
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer countBankOpenAccountUser(MobileSynchronizeRequest request);

    /**
     * 检索已开户用户列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<MobileSynchronizeCustomize> selectBankOpenAccountUserList(MobileSynchronizeRequest request);
}
