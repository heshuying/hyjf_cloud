package com.hyjf.wbs.trade.dao.mapper.customize;


import com.hyjf.wbs.trade.dao.model.customize.BorrowCustomize;

import java.util.List;


/**
 * @author wxd
 * @version BorrowCustomizeMapper, v0.1 2019/4/30 10:20
 */
public interface BorrowCustomizeMapper {


    /**
     * 集成borrow、 boorow_info表的自定义查询
     * @param borrowNid
     * @return
     */
    List<BorrowCustomize> getBorrowCustomize(String borrowNid);


}
