package com.hyjf.am.config.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.dao.model.auto.FeeConfig;
import com.hyjf.am.config.service.FeeConfigService;
import com.hyjf.am.response.config.FeeConfigResponse;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author xiasq
 * @version FeeConfigController, v0.1 2018/6/22 14:28
 */
@RestController
@RequestMapping("/am-config/feeConfig")
public class FeeConfigController extends BaseConfigController{

    @Autowired
    private FeeConfigService feeConfigService;
    
    /**
     * 获取用户的提现费率
     * @return
     */
    @PostMapping("/getFeeConfig/{bankCode}")
    public FeeConfigResponse getFeeConfig(@PathVariable String bankCode){
        FeeConfigResponse response = new FeeConfigResponse();
        List<FeeConfig> feeConfigs = feeConfigService.getFeeConfigs(bankCode);
        if(CollectionUtils.isNotEmpty(feeConfigs)){
            List<FeeConfigVO> feeConfigVOList=CommonUtils.convertBeanList(feeConfigs,FeeConfigVO.class);
            response.setResultList(feeConfigVOList);
        }
        return response;
    }


}
