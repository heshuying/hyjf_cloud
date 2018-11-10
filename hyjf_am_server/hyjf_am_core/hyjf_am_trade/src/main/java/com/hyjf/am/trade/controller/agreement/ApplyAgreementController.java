package com.hyjf.am.trade.controller.agreement;

import com.hyjf.am.response.admin.ApplyAgreementInfoResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.ApplyAgreementInfo;
import com.hyjf.am.trade.service.agreement.ApplyAgreementService;
import com.hyjf.am.vo.user.ApplyAgreementInfoVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther:Zhdaojian
 * @Date:2018/7/7
 * @Description: 法大大垫付机构协议管理（cs-trade调用）
 */
@Api(value = "Admin端产品中心-汇转让-垫付协议管理")
@RestController
@RequestMapping("/am-trade/applyAgreement")
public class ApplyAgreementController extends BaseController {

    @Autowired
    private ApplyAgreementService applyAgreementService;

    /**
     * 根据contract_id查询垫付协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 16:36
     * @param contractId
     * @return com.hyjf.am.response.trade.HjhDebtCreditRepayResponse
     **/
    @RequestMapping("/selectApplyAgreementInfoByContractId/{contractId}")
    public ApplyAgreementInfoResponse selectApplyAgreementInfoByContractId(@PathVariable(value = "contractId") String contractId){
        logger.info("---------------------------根据contract_id查询垫付协议生成详情contractId:"+contractId);
        ApplyAgreementInfoResponse response = new ApplyAgreementInfoResponse();
        List<ApplyAgreementInfo> list = applyAgreementService.selectApplyAgreementInfoByContractId(contractId);
        List<ApplyAgreementInfoVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, ApplyAgreementInfoVO.class);
        }
        response.setResultList(voList);
        return response;
    }
}