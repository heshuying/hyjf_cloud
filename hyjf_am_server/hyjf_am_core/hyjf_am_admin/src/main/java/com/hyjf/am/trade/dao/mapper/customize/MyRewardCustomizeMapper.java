package com.hyjf.am.trade.dao.mapper.customize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;

/**
 * @author hesy
 * @version MyRewardCustomizeMapper, v0.1 2018/6/22 20:00
 */
public interface MyRewardCustomizeMapper {
    List<MyRewardRecordCustomizeVO> selectMyRewardList(Map<String,Object> paraMap);
    BigDecimal sumMyRewardTotal(Map<String,Object> paraMap);
    Integer countMyRewardTotal(Map<String,Object> paraMap);
}
