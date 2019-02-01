package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.bifa;

import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;

import java.util.List;

/**
 * @author jijun
 * @version BifaBorrowCustomizeMapper
 */
public interface BifaBorrowCustomizeMapper {

    /**
     * 借款人信息
     * @param startDate
     * @param endDate
     * @return
     */
    List<BorrowAndInfoVO> selectBorrowUserInfo(Integer startDate, Integer endDate);
}
