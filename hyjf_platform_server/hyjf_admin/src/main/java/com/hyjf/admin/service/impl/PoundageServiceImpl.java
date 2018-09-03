/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.service.PoundageService;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PoundageServiceImpl, v0.1 2018/9/3 15:00
 */
@Service
public class PoundageServiceImpl extends BaseAdminServiceImpl implements PoundageService {
    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPoundageCount(PoundageListRequest request) {
        return amAdminClient.getPoundageCount(request);
    }

    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PoundageCustomizeVO> searchPoundageList(PoundageListRequest request) {
        return amAdminClient.searchPoundageList(request);
    }
}
