/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PoundageService, v0.1 2018/9/3 15:00
 */
public interface PoundageService extends BaseAdminService {
    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageCount(PoundageListRequest request);
    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PoundageCustomizeVO> searchPoundageList(PoundageListRequest request);

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageCustomizeVO getPoundageSum(PoundageListRequest request);

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageCustomizeVO getPoundageById(Integer loginUserId,Integer id);

    /**
     * 审核-更新poundage表
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean updatePoundage(PoundageCustomizeVO poundageCustomizeVO);
}
