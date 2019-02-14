/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception;

import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.BindCardExceptionCustomizeVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BindCardRepairService, v0.1 2018/10/9 11:28
 */
public interface BindCardRepairService extends BaseService {
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
    void updateBindCard(BindCardExceptionRequest request);
}
