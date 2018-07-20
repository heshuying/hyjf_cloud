package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;

import java.util.List;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情 Service
 * @Author : huanghui
 */
public interface DayCreditDetailService {

    List<DayCreditDetailVO> selectDebtCreditList(DayCreditDetailRequest request);
}
