package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.trade.dao.mapper.customize.trade.MyRewardCustomizeMapper;
import com.hyjf.am.trade.service.front.trade.MyRewardService;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    /**
     * 我的奖励列表
     */
    @Override
    public List<MyRewardRecordCustomizeVO> selectMyRewardList(String userId, Integer limitStart, Integer limitEnd){
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("limitStart", limitStart);
        param.put("limitEnd", limitEnd);

        return myRewardCustomizeMapper.selectMyRewardList(param);
    }

    /**
     * 统计总的奖励金额
     */
    @Override
    public BigDecimal sumMyRewardTotal(String userId){
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("userId", userId);

        BigDecimal result = myRewardCustomizeMapper.sumMyRewardTotal(param);
        if(result == null){
            result = BigDecimal.ZERO;
        }
        return result;
    }

    /**
     * 统计总记录数
     * @param userId
     * @return
     */
    @Override
    public Integer countMyRewardTotal(String userId){
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("userId", userId);

        Integer result = myRewardCustomizeMapper.countMyRewardTotal(param);
        if(result == null){
            result = 0;
        }
        return result;
    }
}
