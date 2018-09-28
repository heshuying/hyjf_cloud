package com.hyjf.am.trade.service.admin.hjhplan;

import com.hyjf.am.trade.dao.model.auto.HjhRepay;

import java.util.List;

/**
 * 汇计划-订单退出 Service
 * @Author : huanghui
 */
public interface HjhRepayService {

    /**
     * 订单退出超过两天邮件预警list
     * @author zhangyk
     * @date 2018/8/15 15:47
     */
    List<HjhRepay>  getPlanExitCheck();

}
