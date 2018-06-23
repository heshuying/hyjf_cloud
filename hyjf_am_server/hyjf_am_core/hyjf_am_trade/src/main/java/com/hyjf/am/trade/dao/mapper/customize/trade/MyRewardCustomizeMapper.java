package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version MyRewardCustomizeMapper, v0.1 2018/6/22 20:00
 */
public interface MyRewardCustomizeMapper {
    List<MyRewardRecordCustomizeVO> selectMyRewardList(Map<String,Object> paraMap);
}
