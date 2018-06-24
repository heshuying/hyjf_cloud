package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.customize.trade.MyRewardCustomizeMapper;
import com.hyjf.am.trade.service.MyRewardService;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的邀请记录
 * @author hesy
 * @version MyInviteServiceImpl, v0.1 2018/6/22 20:08
 */
@Service
public class MyRewardServiceImpl implements MyRewardService {
    @Resource
    MyRewardCustomizeMapper myRewardCustomizeMapper;

    @Override
    public List<MyRewardRecordCustomizeVO> selectMyRewardList(String userId, Integer limitStart, Integer limitEnd){
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("limitStart", limitStart);
        param.put("limitEnd", limitEnd);

        return myRewardCustomizeMapper.selectMyRewardList(param);
    }
}
