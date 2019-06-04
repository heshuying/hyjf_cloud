package com.hyjf.am.trade.service.hgreportdata.caijing;

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


}
