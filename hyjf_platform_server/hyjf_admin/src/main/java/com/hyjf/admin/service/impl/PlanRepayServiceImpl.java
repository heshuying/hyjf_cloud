package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.HjhRepayResponseBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.HjhRepayClient;
import com.hyjf.admin.service.PlanRepayService;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单退出 实现
 * @Author : huanghui
 */
@Service
public class PlanRepayServiceImpl implements PlanRepayService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public Integer selectRepayCount(HjhRepayRequest request) {
        //查询总条数
        Integer hjhRepayCount = amTradeClient.getRepayCount(request);
        return hjhRepayCount;
    }


    @Override
    public List<HjhRepayVO> selectByExample(HjhRepayRequest request) {
        //查询列表
        List<HjhRepayVO> recordList = amTradeClient.selectByExample(request);
        return recordList;
    }

    @Override
    public HjhRepayResponseBean selectByAccedeOrderId(String accedeOrderId) {
        HjhRepayResponseBean repayResponseBean = new HjhRepayResponseBean();
        List<HjhRepayVO> hjhRepayVOList = amTradeClient.selectByAccedeOrderId(accedeOrderId);
        repayResponseBean.setRecordList(hjhRepayVOList);
        return repayResponseBean;
    }
}
