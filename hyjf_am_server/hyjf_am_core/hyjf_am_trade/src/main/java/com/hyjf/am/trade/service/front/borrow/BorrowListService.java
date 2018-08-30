/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow;


import com.hyjf.am.trade.dao.model.customize.InvestListCustomize;
import com.hyjf.am.vo.trade.RepayListCustomizeInvestVO;

import java.util.List;
import java.util.Map;


/**
 * Web端项目列表Service
 * @author wenxin
 * @version ProjectListService, v0.1 2018/8/27 11:37
 */
public interface BorrowListService {

    /*-------------------------------  api  start  -------------------------------------------*/
    /**
     * api： 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 14:12
     */
    List<InvestListCustomize> InvestRepaysList(Map<String, Object> params);

    /*-------------------------------  api  end    -------------------------------------------*/
}
