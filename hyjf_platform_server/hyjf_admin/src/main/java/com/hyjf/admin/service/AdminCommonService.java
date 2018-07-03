/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version AdminCommonService, v0.1 2018/7/3 10:25
 */
public interface AdminCommonService {
    /**
     * 获取相应paramname数据
     * @param param
     * @return
     */
    Map<String, String> getParamNameMap(String param);

    /**
     * 还款方式下拉列表
     * @return
     */
    List<BorrowStyleVO> selectBorrowStyleList();
}
