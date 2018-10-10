/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.exception;

import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.vo.admin.BindCardExceptionCustomizeVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BindCardExceptionService, v0.1 2018/10/9 11:36
 */
public interface BindCardExceptionService extends BaseAdminService {

    /**
     * 列表数量
     * @auth sunpeikai
     * @param
     * @return
     */
    int getBindCardExceptionCount(BindCardExceptionRequest request);

    /**
     * 列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BindCardExceptionCustomizeVO> searchBindCardExceptionList(BindCardExceptionRequest request);

    /**
     * 更新银行卡
     * @auth sunpeikai
     * @param
     * @return
     */
    void updateBindCard(Integer userId, String accountId);
}
