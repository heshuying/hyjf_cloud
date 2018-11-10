package com.hyjf.am.trade.service.agreement.impl;

import com.hyjf.am.trade.dao.model.auto.ApplyAgreementInfo;
import com.hyjf.am.trade.dao.model.auto.ApplyAgreementInfoExample;
import com.hyjf.am.trade.service.agreement.ApplyAgreementService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:Zhadaojian
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class ApplyAgreementServiceImpl extends BaseServiceImpl implements ApplyAgreementService {

    /**
     * 根据contract_id查询垫付协议生成详情
     *
     * @param contractId
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan>
     * @author Zha Daojian
     * @date 2018/8/23 16:37
     **/
    @Override
    public List<ApplyAgreementInfo> selectApplyAgreementInfoByContractId(String contractId) {
        ApplyAgreementInfoExample applyAgreementInfoExample = new ApplyAgreementInfoExample();
        ApplyAgreementInfoExample.Criteria cra = applyAgreementInfoExample.createCriteria();
        cra.andContractIdEqualTo(contractId);
        List<ApplyAgreementInfo> applyAgreementInfoList = this.applyAgreementInfoMapper.selectByExample(applyAgreementInfoExample);
        return applyAgreementInfoList;
    }
}
