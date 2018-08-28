/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.customize.EveLogCustomize;

/**
 * @author zdj
 * @version VipManageCustomizeMapper, v0.1 2018/7/2 18:08
 */
public interface EveLogCustomizeMapper {

    /**
     * 根据条件查询queryList条数
     * @param mapParam
     * @return
     */
    Integer countRecord(Map<String, Object> mapParam);

    /**
     * 查询银行账务明细列表
     * @param mapParam
     * @return
     */
    List<EveLogCustomize> selectBankEveInfoList(Map<String, Object> mapParam);

}
