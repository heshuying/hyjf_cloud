/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception;

import java.util.List;

import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.user.dao.model.customize.MobileSynchronizeCustomize;
import com.hyjf.am.user.service.BaseService;

/**
 * @author: sunpeikai
 * @version: AdminMobileSynchronizeService, v0.1 2018/8/13 14:32
 */
public interface AdminMobileSynchronizeService extends BaseService {
    /**
     * 查询手机号同步数量  用于前端分页显示
     * @auth sunpeikai
     * @param
     * @return
     */
    int countBankOpenAccountUser(MobileSynchronizeRequest request);
    /**
     * 查询手机号同步列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<MobileSynchronizeCustomize> selectBankOpenAccountUserList(MobileSynchronizeRequest request);

    /**
     * 同步手机号
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean updateMobile(MobileSynchronizeRequest request) throws Exception;
}
