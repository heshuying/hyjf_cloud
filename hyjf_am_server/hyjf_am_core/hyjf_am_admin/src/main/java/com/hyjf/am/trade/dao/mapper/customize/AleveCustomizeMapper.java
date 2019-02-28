package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version AleveCustomizeMapper, v0.1 2018/6/26 15:51
 */
public interface AleveCustomizeMapper {

    /**
     * 根据条件查询queryList条数
     * @author Zha Daojian
     * @date 2018/8/21 9:23
     * @param mapParam
     * @return java.lang.Integer
     **/
    Integer queryAleveLogCount(Map<String, Object> mapParam);

    /**
     * 查询银行账务明细列表
     * @author Zha Daojian
     * @date 2018/8/21 9:23
     * @param mapParam
     * @return java.util.List<com.hyjf.am.trade.dao.model.customize.AleveLogCustomize>
     **/
    List<AleveLogCustomize> queryAleveLogList(Map<String, Object> mapParam);
}
