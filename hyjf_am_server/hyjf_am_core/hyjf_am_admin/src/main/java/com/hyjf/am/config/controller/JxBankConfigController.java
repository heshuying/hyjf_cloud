package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.service.JxBankConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 银行配置
 * @Author : huanghui
 */
@RestController
@RequestMapping("/am-config/jxBankConfig")
public class JxBankConfigController extends BaseConfigController {

    @Autowired
    private JxBankConfigService jxBankConfigService;

    /**
     * 获取充值银行列表
     * @Author : huanghui
     */
    @RequestMapping(value = "/selectBankConfigList", method = RequestMethod.GET)
    public JxBankConfigResponse selectBankConfigList(){
        JxBankConfigResponse response = new JxBankConfigResponse();
        List<JxBankConfig> listBankConfig = jxBankConfigService.selectBankConfigList();
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<JxBankConfigVO> listBanksConfig = CommonUtils.convertBeanList(listBankConfig, JxBankConfigVO.class);
            response.setResultList(listBanksConfig);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
