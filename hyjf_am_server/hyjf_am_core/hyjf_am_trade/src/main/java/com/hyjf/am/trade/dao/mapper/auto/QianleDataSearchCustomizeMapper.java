package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.vo.trade.DataSearchCustomizeVO;

import java.util.List;

/**
 * @author lisheng
 * @version QianleDataSearchCustomizeMapper, v0.1 2018/8/22 14:13
 */

public interface QianleDataSearchCustomizeMapper {
    /**
     * 查询散标
      * @return
     */
    List<DataSearchCustomizeVO> querySanList();
}
