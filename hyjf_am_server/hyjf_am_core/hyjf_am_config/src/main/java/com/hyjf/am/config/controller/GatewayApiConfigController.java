package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.GatewayApiConfig;
import com.hyjf.am.config.service.GatewayApiConfigService;
import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiasq
 * @version GatewayApiConfigController, v0.1 2018/4/18 20:25
 */
@RestController
@RequestMapping("/am-config/gateConfig")
public class GatewayApiConfigController extends BaseConfigController{

    @Autowired
    GatewayApiConfigService gatewayApiConfigService;

    /**
     * 查询所有网关配置
     * @return
     */
    @RequestMapping("/findAll")
    public GatewayApiConfigResponse findAll(){
        logger.info("findGatewayConfigs start...");
        GatewayApiConfigResponse response = new GatewayApiConfigResponse();

        List<GatewayApiConfigVO> configVOs = null;
        List<GatewayApiConfig> configs = gatewayApiConfigService.findAllConfigs();
        if (!CollectionUtils.isEmpty(configs)) {
            configVOs = new ArrayList<>(configs.size());
            GatewayApiConfigVO vo = null;
            for (GatewayApiConfig config : configs) {
                vo = new GatewayApiConfigVO();
                BeanUtils.copyProperties(config, vo);
                vo.setId(String.valueOf(config.getId()));
                configVOs.add(vo);
            }
        }
        response.setResultList(configVOs);
        return response;
    }
}
