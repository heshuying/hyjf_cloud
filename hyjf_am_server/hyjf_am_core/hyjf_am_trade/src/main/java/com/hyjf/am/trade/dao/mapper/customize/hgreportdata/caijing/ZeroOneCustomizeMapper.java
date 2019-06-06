package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.caijing;


import com.hyjf.am.trade.dao.model.customize.ZeroOneCaiJingCustomize;
import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneBorrowDataVO;

import java.util.List;
import java.util.Map;

public interface ZeroOneCustomizeMapper {

    /**
     * 查询指定日期的出借记录
     * @param map
     * @return
     */
    List<ZeroOneCaiJingCustomize> queryInvestRecordSub(Map<String,Object> map);

    /**
     * 查找提前还款记录
     * @param map
     * @return
     */
    List<ZeroOneCaiJingCustomize> queryAdvancedRepaySub(Map<String,Object> map);

    /**
     * 查询指定日期的借款记录
     * @param map
     * @return
     */
    List<ZeroOneBorrowDataVO> queryBorrowRecordSub(Map<String, Object> map);
}
