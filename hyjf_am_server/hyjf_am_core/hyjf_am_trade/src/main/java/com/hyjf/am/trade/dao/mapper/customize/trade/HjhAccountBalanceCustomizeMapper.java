package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.vo.trade.HjhAccountBalanceVO;

import java.util.Date;
import java.util.List;

public interface HjhAccountBalanceCustomizeMapper {
    /**
     * 获取该日期的实际债转和复投金额
     * @param date
     * @return
     */
    List<HjhAccountBalanceVO> selectHjhAccountBalanceForActList(Date date);
}
