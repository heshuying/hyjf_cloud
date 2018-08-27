package com.hyjf.am.trade.service.admin.hjhplan;

import java.util.List;

import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

/**
 * 汇计划-订单退出 Service
 * @Author : huanghui
 */
public interface HjhRepayService {

    /**
     * 获取总数
     * @param repayRequest
     * @return
     */
    Integer getRepayCount(HjhRepayRequest repayRequest);

    List<HjhRepay> selectByExample(HjhRepayRequest request);

    List<HjhRepayVO> selectByAccedeOrderId(String accedeOrderId);

    /**
     * 订单退出超过两天邮件预警list
     * @author zhangyk
     * @date 2018/8/15 15:47
     */
    List<HjhRepay>  getPlanExitCheck();

}
