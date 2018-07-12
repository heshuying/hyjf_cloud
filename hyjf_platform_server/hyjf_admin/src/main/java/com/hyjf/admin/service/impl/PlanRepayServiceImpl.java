package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.HjhRepayResponseBean;
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
    private HjhRepayClient hjhRepayClient;

    @Override
    public HjhRepayResponseBean selectByExample(HjhRepayRequest request) {

        HjhRepayResponseBean repayResponseBean = new HjhRepayResponseBean();

        //查询总条数
        Integer hjhRepayCount = hjhRepayClient.getRepayCount(request);
        repayResponseBean.setTotal(hjhRepayCount);
        List<HjhRepayVO> recordList = hjhRepayClient.selectByExample(request);

        repayResponseBean.setRecordList(recordList);

        return repayResponseBean;
    }

    @Override
    public HjhRepayResponseBean selectByAccedeOrderId(String accedeOrderId) {
        HjhRepayResponseBean repayResponseBean = new HjhRepayResponseBean();
        List<HjhRepayVO> hjhRepayVOList = hjhRepayClient.selectByAccedeOrderId(accedeOrderId);
        repayResponseBean.setRecordList(hjhRepayVOList);
        return repayResponseBean;
    }
}
