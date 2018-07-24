/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize.admin.finance;

import com.hyjf.am.user.dao.model.customize.admin.finance.AleveLogCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version VipManageCustomizeMapper, v0.1 2018/7/2 18:08
 */
public interface AleveLogCustomizeMapper {

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
    List<AleveLogCustomize> selectBankAleveInfoList(Map<String, Object> mapParam);

}
