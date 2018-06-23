package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.customize.trade.MyInviteCustomizeMapper;
import com.hyjf.am.vo.trade.MyInviteListCustomizeVO;
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
public class MyInviteServiceImpl implements com.hyjf.am.trade.service.MyInviteService {
    @Resource
    MyInviteCustomizeMapper myInviteCustomizeMapper;

    /**
     * 邀请记录列表
     */
    @Override
    public List<MyInviteListCustomizeVO> selectMyInviteList(String userId, Integer limitStart, Integer limitEnd){
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("limitStart", limitStart);
        param.put("limitEnd", limitEnd);

        return myInviteCustomizeMapper.selectMyInviteList(param);
    }

    @Override
    public List<MyRewardRecordCustomizeVO> selectMyRewardList(String userId, Integer limitStart, Integer limitEnd){
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("limitStart", limitStart);
        param.put("limitEnd", limitEnd);

        return myInviteCustomizeMapper.selectMyRewardList(param);
    }
}
