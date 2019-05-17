package com.hyjf.cs.market.controller.zuul;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import com.hyjf.cs.market.service.zuul.GateWayService;

/**
 * @author lisheng
 * @version ZuulController, v0.1 2018/12/19 14:44
 */
//@Api(tags = "网关")
@RestController
@RequestMapping("/cs-market/zuul")
public class GateWayController {
    Logger logger = LoggerFactory.getLogger(GateWayController.class);

    @Autowired
    GateWayService gateWayService;
    /**
     *url配置缓存
     */
    ConcurrentHashMap<String,List<GatewayApiConfigVO>> cache= new ConcurrentHashMap<>();

    //@ApiOperation(value = "网关url", notes = "网关")
    @GetMapping(value = "/gateConfig")
    public GatewayApiConfigResponse gateConfig() {
        GatewayApiConfigResponse gatewayConfigs=null;
        try {
            //查询动态路由url配置
            gatewayConfigs = gateWayService.findGatewayConfigs();
            List<GatewayApiConfigVO> resultList = gatewayConfigs.getResultList();
            if (CollectionUtils.isEmpty(resultList)) {
                gatewayConfigs = new GatewayApiConfigResponse();
                List<GatewayApiConfigVO> gateConfig = cache.get("gateConfig");
                if(CollectionUtils.isEmpty(gateConfig)){
                    logger.warn("cache is empty!");
                }
                gatewayConfigs.setResultList(gateConfig);
            }else{
                cache.put("gateConfig", resultList);
            }
        } catch (Exception e) {
            logger.error("gate api config error...",e);
            gatewayConfigs = new GatewayApiConfigResponse();
            List<GatewayApiConfigVO> gateConfig = cache.get("gateConfig");
            if(CollectionUtils.isEmpty(gateConfig)){
                logger.warn("cache is empty!");
            }
            gatewayConfigs.setResultList(gateConfig);
        }
        return gatewayConfigs;
    }
}
