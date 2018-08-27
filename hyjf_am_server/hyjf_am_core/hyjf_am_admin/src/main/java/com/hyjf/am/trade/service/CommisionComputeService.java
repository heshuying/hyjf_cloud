package com.hyjf.am.trade.service;

import java.util.List;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.PushMoney;
import com.hyjf.am.vo.trade.HjhLockVo;

/**
 * @Auther:yangchangwei
 * @Date:2018/8/8
 * @Description: 计划提成计算服务
 */
public interface CommisionComputeService extends BaseService{

    List<HjhAccede> selectHasCommisionAccedeList();

    void commisionCompute(HjhAccede record, HjhLockVo hjhLockVo);

    int statusUpdate(HjhAccede record, Integer status);

    PushMoney getCommisionConfig(Integer projectType, String userType);
}
