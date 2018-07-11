package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.BorrowCustomize;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;

import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowCustomizeMapper, v0.1 2018/6/19 16:20
 */
public interface BorrowCustomizeMapper {
    int countInvest(Integer userId);

    /**
     * @param borrow
     * @return
     */
    int updateOfBorrow(Map<String, Object> borrow);

    /**
     * @param borrow
     * @return
     */
    int updateOfFullBorrow(Map<String, Object> borrow);


    ProjectCustomeDetailVO getProjectDetail(String borrowNid);


    ProjectCompanyDetailVO getProjectCompanyDetail(String borrowNid);


    WebProjectPersonDetailVO  getProjectPsersonDetail(String borrowNid);

    Integer getTotalInverestCount(Integer userId);

    /**
     * 集成borrow、 boorow_info表的自定义查询
     * @param borrowNid
     * @return
     */
    BorrowCustomize getBorrowCustomize(String borrowNid);

}
