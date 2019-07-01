package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.TenderUtmChangeLogCustomize;

import java.util.List;

/**
 * @author cui
 * @version TenderUtmChangeLogCustomizeMapper, v0.1 2019/6/18 10:10
 */
public interface TenderUtmChangeLogCustomizeMapper {

    List<TenderUtmChangeLogCustomize> getUtmChangeLog(String nid);

    List<TenderUtmChangeLogCustomize> getPlanTenderChangeLog(String planOrderId);

}
