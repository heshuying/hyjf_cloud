package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.PlanCapitalService;
import com.hyjf.am.response.admin.HjhPlanCapitalPredictionResponse;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalPredictionRequest;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 资金计划 实现
 * @Author : huanghui
 */
@Service
public class PlanCapitalServiceImpl implements PlanCapitalService {


    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest) {
        HjhPlanCapitalResponse planCapitalResponse = this.csMessageClient.getPlanCapitalList(hjhPlanCapitalRequest);
        return planCapitalResponse;
    }

    @Override
    public HjhPlanCapitalPredictionResponse getPlanCapitalPredictionList(HjhPlanCapitalPredictionRequest hjhPlanCapitalPredictionRequest) {
        List<HjhPlanCapitalPredictionVO> hjhPlanCapitalPredictionVOList = new ArrayList<HjhPlanCapitalPredictionVO>();
        HjhPlanCapitalPredictionResponse hjhPlanCapitalPredictionResponse = this.csMessageClient.getPlanCapitalPredictionList(hjhPlanCapitalPredictionRequest);
        // 获取列表参数
        hjhPlanCapitalPredictionVOList = hjhPlanCapitalPredictionResponse.getResultList();
        // 预计当日新增债转额（元） - 预计当日新增复投额（元） “大于零”  =  预计当日所需资金量    （预计当日所需资产量=0）
        // 预计当日新增复投额（元）- 预计当日新增债转额（元）  “大于零”= 预计当日所需资金量    （预计当日所需资产量=0）
        for(HjhPlanCapitalPredictionVO vo: hjhPlanCapitalPredictionVOList){
            if(vo.getCreditAccount().compareTo(vo.getReinvestAccount()) ==1){
                vo.setCapitalAccount(vo.getCreditAccount().subtract(vo.getReinvestAccount()));
                vo.setAssetAccount(BigDecimal.ZERO);
            }else{
                vo.setCapitalAccount(BigDecimal.ZERO);
                vo.setAssetAccount(vo.getReinvestAccount().subtract(vo.getCreditAccount()));
            }
        }
        hjhPlanCapitalPredictionResponse.setResultList(hjhPlanCapitalPredictionVOList);
        return hjhPlanCapitalPredictionResponse;
    }
}

