/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditService, v0.1 2018/6/23 17:12
 */
public interface BorrowCreditService extends BaseService {

    /**
     * 查询债转表债转状态为0的数据
     * @return
     */
    List<BorrowCreditVO> selectBorrowCreditList();

    /**
     * 更新债转表状态0的数据的债转状态为1
     * @return
     */
    Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO);

    UserInfoVO findUsersInfoById(int userId);
}
