/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditCustomize;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditService, v0.1 2018/6/24 10:55
 */
public interface BorrowCreditService {
    /**
     * 查询债转表债转状态为0的数据
     * @return
     */
    List<BorrowCredit> selectBorrowCreditList();

    /**
     * 更新债转表状态0的数据的债转状态为1
     * @return
     */
    Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO);

    /**
     * 查询债转详情
     * @author zhangyk
     * @date 2018/6/26 11:54
     */
    BorrowCreditDetailVO getBorrowCreditDetail(String creditNid);

    /**
     * 获取投资债转信息
     * @param request1
     * @return
     */
	List<BorrowCredit> getBorrowCreditList(BorrowCreditRequest request1);

	/**
	 * admin：查询会转让列表
	 * @author zhangyk
	 * @date 2018/7/9 16:01
	 */
	List<AdminBorrowCreditCustomize> getBorrowCreditList4Admin(BorrowCreditAmRequest request);

    /**
     * admin：查询汇转让count
     * @author zhangyk
     * @date 2018/7/9 16:01
     */
    Integer countBorrowCreditList4Admin(BorrowCreditAmRequest request);

    /**
     * admin： 查询统计数据total
     * @author zhangyk
     * @date 2018/7/9 17:52
     */
    BorrowCreditSumVO getBorrowCreditTotalCount(BorrowCreditAmRequest request);

}
