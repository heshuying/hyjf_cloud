/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.nifa;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.NifaContractTemplateResponse;
import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.response.trade.NifaContractEssenceResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.NifaContractEssence;
import com.hyjf.am.trade.dao.model.auto.NifaContractTemplate;
import com.hyjf.am.trade.dao.model.auto.NifaFieldDefinition;
import com.hyjf.am.trade.service.nifa.NifaContractEssenceService;
import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.am.vo.trade.nifa.NifaContractEssenceVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaContractEssenceController, v0.1 2018/9/6 16:21
 */
@Api(value = "互金生成合同要素")
@RestController
@RequestMapping("/am-trade/nifa_contract_essence")
public class NifaContractEssenceController extends BaseController {

    @Autowired
    NifaContractEssenceService nifaContractEssenceService;

    /**
     * 互金根据放款编号生成合同要素
     *
     * @param contractNo
     * @return
     */
    @GetMapping("/select_nifa_contract_essence/{contractNo}")
    public NifaContractEssenceResponse selectNifaContractEssenceByContractNo(@PathVariable String contractNo) {
        NifaContractEssenceResponse response = new NifaContractEssenceResponse();
        List<NifaContractEssence> nifaContractEssenceList = nifaContractEssenceService.selectNifaContractEssenceByContractNo(contractNo);
        if (!CollectionUtils.isEmpty(nifaContractEssenceList)) {
            List<NifaContractEssenceVO> voList = CommonUtils.convertBeanList(nifaContractEssenceList, NifaContractEssenceVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 根据合同编号获取合同模版约定条款
     *
     * @param templetId
     * @return
     */
    @GetMapping("/select_nifa_contract_template/{templetId}")
    public NifaContractTemplateResponse selectNifaContractTemplateByTemplateNid(@PathVariable String templetId) {
        NifaContractTemplateResponse response = new NifaContractTemplateResponse();
        List<NifaContractTemplate> nifaContractTemplateList = nifaContractEssenceService.selectNifaContractTemplateByTemplateNid(templetId);
        if (!CollectionUtils.isEmpty(nifaContractTemplateList)) {
            List<NifaContractTemplateVO> voList = CommonUtils.convertBeanList(nifaContractTemplateList, NifaContractTemplateVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取最新互金字段定义
     *
     * @return
     */
    @GetMapping("/select_nifa_field_definition")
    public NifaFieldDefinitionResponse selectNifaFieldDefinition() {
        NifaFieldDefinitionResponse response = new NifaFieldDefinitionResponse();
        List<NifaFieldDefinition> nifaContractTemplateList = nifaContractEssenceService.selectNifaFieldDefinition();
        if (!CollectionUtils.isEmpty(nifaContractTemplateList)) {
            List<NifaFieldDefinitionVO> voList = CommonUtils.convertBeanList(nifaContractTemplateList, NifaFieldDefinitionVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 插入合同信息要素表
     *
     * @param nifaContractEssenceVO
     * @return
     */
    @PostMapping("/insert_nifa_contract_essence")
    public IntegerResponse insertNifaContractEssence(@RequestBody NifaContractEssenceVO nifaContractEssenceVO) {
        IntegerResponse result = new IntegerResponse();
        NifaContractEssence nifaContractEssence = new NifaContractEssence();
        Integer re = 0;
        if (null != nifaContractEssenceVO) {
            BeanUtils.copyProperties(nifaContractEssenceVO, nifaContractEssence);
            re = nifaContractEssenceService.insertNifaContractEssence(nifaContractEssence);
        }
        result.setResultInt(re);
        return result;
    }
}
