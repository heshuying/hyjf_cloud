package com.hyjf.am.trade.dao.mapper.customize;


import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface BorrowManinfoCustomizeMapper {

    /**
     * 根据标的编号查询借款人信息
     * @param nids
     * @return
     */
    List<BorrowManinfoVO> getBorrowManinfoList(@Param("set") List<String> nids);
}