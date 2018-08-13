package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;

import java.util.List;

/**
 * @author wangjun
 * @version AleveCustomizeMapper, v0.1 2018/6/26 15:51
 */
public interface AleveCustomizeMapper {

    Integer queryAleveLogCount(AleveLogCustomize aleveLogCustomize);

    List<AleveLogCustomize> queryAleveLogList(AleveLogCustomize aleveLogCustomize);

    List<AleveLogCustomize> queryAleveLogListByTranstype(List<String> tranStype);
}
