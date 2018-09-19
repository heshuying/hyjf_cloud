/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.service.PoundageDetailService;
import com.hyjf.am.resquest.admin.AdminPoundageDetailRequest;
import com.hyjf.am.vo.admin.PoundageDetailVO;
import com.hyjf.am.vo.admin.PoundageLedgerVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PoundageDetailServiceImpl, v0.1 2018/9/7 9:55
 */
@Service
public class PoundageDetailServiceImpl extends BaseAdminServiceImpl implements PoundageDetailService {

    /**
     * 查询手续费分账配置
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageLedgerVO getPoundageLedgerById(int id) {
        return amAdminClient.getPoundageLedgerById(id);
    }

    /**
     * 手续费分账详细信息总数
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPoundageDetailCount(AdminPoundageDetailRequest request) {
        return amAdminClient.getPoundageDetailCount(request);
    }

    /**
     * 手续费分账详细信息列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PoundageDetailVO> searchPoundageDetailList(AdminPoundageDetailRequest request) {
        return amAdminClient.searchPoundageDetailList(request);
    }
}
