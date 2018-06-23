package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.vo.trade.MyInviteListCustomizeVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version MyInviteCustomizeMapper, v0.1 2018/6/22 20:00
 */
public interface MyInviteCustomizeMapper {
    List<MyInviteListCustomizeVO> selectMyInviteList(Map<String,Object> paraMap);

    List<MyRewardRecordCustomizeVO> selectMyRewardList(Map<String,Object> paraMap);
}
