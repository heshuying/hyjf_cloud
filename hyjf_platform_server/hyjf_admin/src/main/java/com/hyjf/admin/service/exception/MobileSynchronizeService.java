/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.exception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.vo.admin.MobileSynchronizeCustomizeVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeService, v0.1 2018/8/13 11:49
 */
public interface MobileSynchronizeService extends BaseAdminService {

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
    List<MobileSynchronizeCustomizeVO> selectBankOpenAccountUserList(MobileSynchronizeRequest request);

    /**
     * 同步手机号
     * @auth sunpeikai
     * @param
     * @return
     */
    JSONObject updateMobile(Integer loginUserId,MobileSynchronizeRequest request);
}
