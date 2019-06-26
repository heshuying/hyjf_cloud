package com.hyjf.am.trade.service.hgreportdata.caijing;

import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneBorrowDataVO;
import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneDataVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 14:35
 * @Version 1.0
 */
public interface ZeroOneCaiJingDataService {

    /**
     * 查询指定日期的出借记录
     * @param map
     * @return
     */
    List<ZeroOneDataVO> queryInvestRecordSub(Map<String,Object> map);

    /**
     * 查找提前还款记录
     * @param map
     * @return
     */
    List<ZeroOneDataVO> queryAdvancedRepaySub(Map<String,Object> map);

    /**
     * 查询指定日期的借款记录
     * @param map
     * @return
     */
    List<ZeroOneBorrowDataVO> queryBorrowRecordSub(Map<String, Object> map);
}
