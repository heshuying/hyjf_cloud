/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version BatchTyjRepayCustomizeMapper, v0.1 2018/6/15 17:50
 */
public interface BatchTyjRepayCustomizeMapper {

    /**
     * 查询出到期但是未满标的标的,给其发短信
     *
     * @return
     */
    List<String> selectNidForTyj(Map<String, Object> paramMap);

}
