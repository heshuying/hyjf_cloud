package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.caijing;


import com.hyjf.am.trade.dao.model.customize.ZeroOneCaiJingCustomize;

import java.util.List;
import java.util.Map;

public interface ZeroOneCustomizeMapper {

    /**
     * 查询指定日期的出借记录
     * @param map
     * @return
     */
    List<ZeroOneCaiJingCustomize> queryInvestRecordSub(Map<String,Object> map);

}
