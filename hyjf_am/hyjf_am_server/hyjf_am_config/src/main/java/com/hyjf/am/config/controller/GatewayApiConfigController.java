package com.hyjf.am.config.controller;

import java.util.ArrayList;
import java.util.List;

import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.dao.model.auto.GatewayApiConfig;
import com.hyjf.am.config.service.GatewayApiConfigService;

/**
 * @author xiasq
 * @version GatewayApiConfigController, v0.1 2018/4/18 20:25
 */
@RestController
@RequestMapping("/am-config/gateConfig")
public class GatewayApiConfigController {
    Logger logger = LoggerFactory.getLogger(GatewayApiConfigController.class);

    @Autowired
    GatewayApiConfigService gatewayApiConfigService;

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
