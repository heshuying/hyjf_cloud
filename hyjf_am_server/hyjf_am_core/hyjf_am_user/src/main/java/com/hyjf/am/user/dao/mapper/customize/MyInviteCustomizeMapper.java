package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version MyInviteCustomizeMapper, v0.1 2018/6/22 20:00
 */
public interface MyInviteCustomizeMapper {
    List<MyInviteListCustomizeVO> selectMyInviteList(Map<String, Object> paraMap);

}
