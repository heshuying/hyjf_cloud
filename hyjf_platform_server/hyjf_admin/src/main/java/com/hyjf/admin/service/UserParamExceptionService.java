/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author: nxl
 * @version: UserParamException, v0.1 2018/7/2 10:33
 */
public interface UserParamExceptionService {
    /**
     * 更新userInfo的主单与非主单信息
     *
     * @param userId
     */
    void updateUserParam(Integer userId);

    /**
     * 查找所用用户
     *
     * @return
     */
    List<UserVO> getAllUser();

    /**
     * 查询固定时间间隔的用户投资列表
     *
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    List<BorrowTenderVO> selectBorrowTenderListByDate(String repairStartDate, String repairEndDate);

    /**
     * 更新用户的投资记录
     *
     * @param borrowTenderVO
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    void updateUserTender(BorrowTenderVO borrowTenderVO, String repairStartDate, String repairEndDate);
}
