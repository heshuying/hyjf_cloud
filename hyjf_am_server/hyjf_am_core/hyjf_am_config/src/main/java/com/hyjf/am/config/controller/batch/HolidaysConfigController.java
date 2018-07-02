/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.batch;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.service.HolidaysConfigService;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.vo.trade.HolidaysConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaoy
 * @version HolidaysConfigController, v0.1 2018/6/22 10:10
 */
@RestController
@RequestMapping("/am-config/holidaysConfig")
public class HolidaysConfigController extends BaseConfigController {

    @Autowired
    private HolidaysConfigService holidaysConfigService;

    @GetMapping("/selectHolidaysConfig/{orderByClause}")
    public HolidaysConfigResponse selectHolidaysConfig(@PathVariable String orderByClause) {
        HolidaysConfigResponse response = new HolidaysConfigResponse();
        List<HolidaysConfig> holidaysConfigList = holidaysConfigService.selectHolidaysConfig(orderByClause);
        if (!CollectionUtils.isEmpty(holidaysConfigList)) {
            List<HolidaysConfigVO> holidaysConfigVOList = CommonUtils.convertBeanList(holidaysConfigList,HolidaysConfigVO.class);
            response.setResultList(holidaysConfigVOList);
        }
        return response;
    }
}
