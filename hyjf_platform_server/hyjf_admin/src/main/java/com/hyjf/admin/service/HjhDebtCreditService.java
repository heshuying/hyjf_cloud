package com.hyjf.admin.service;


import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/3
 * @Description: 汇计划-转让记录接口
 */
public interface HjhDebtCreditService {

    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

    Integer queryHjhDebtCreditListTotal(HjhDebtCreditListRequest request);

}
