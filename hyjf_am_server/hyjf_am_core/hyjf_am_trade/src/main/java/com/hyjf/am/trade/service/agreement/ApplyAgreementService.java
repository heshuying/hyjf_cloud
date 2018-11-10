package com.hyjf.am.trade.service.agreement;

import com.hyjf.am.trade.dao.model.auto.ApplyAgreementInfo;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @version ApplyAgreementService, v0.1 2018/10/22 14:30
 * @Author: Zha Daojian
 */
public interface ApplyAgreementService extends BaseService{
    /**
     * 根据contract_id查询垫付协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 16:37
     * @param contractId
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan>
     **/
    List<ApplyAgreementInfo> selectApplyAgreementInfoByContractId(String contractId);

}
