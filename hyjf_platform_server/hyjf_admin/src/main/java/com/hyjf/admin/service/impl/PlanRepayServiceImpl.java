package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.HjhRepayResponseBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.PlanRepayService;
import com.hyjf.am.response.trade.HjhRepayResponse;
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

    /**
     * 获取返回结果集
     * @param request
     * @return
     */
    @Override
    public HjhRepayResponse selectHjhRepayList(HjhRepayRequest request) {
        //查询列表
        HjhRepayResponse recordList = amTradeClient.selectHjhRepayList(request);
        return recordList;
    }
}
