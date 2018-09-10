/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.AdminPoundageDetailRequest;
import com.hyjf.am.vo.admin.PoundageDetailVO;
import com.hyjf.am.vo.admin.PoundageLedgerVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PoundageDetailService, v0.1 2018/9/7 9:54
 */
public interface PoundageDetailService extends BaseAdminService {
    /**
     * 查询手续费分账配置
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageLedgerVO getPoundageLedgerById(int id);

    /**
     * 手续费分账详细信息总数
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageDetailCount(AdminPoundageDetailRequest request);

    /**
     * 手续费分账详细信息列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PoundageDetailVO> searchPoundageDetailList(AdminPoundageDetailRequest request);
}
