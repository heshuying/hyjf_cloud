/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayToHjhQuitService, v0.1 2018/6/25 9:37
 */
public interface BorrowRepayToHjhQuitService extends BaseService {

    /**
     * 查询待退出计划的标的
     * @return
     */
    List<HjhAccedeVO> selectWaitQuitHjhList();

}
