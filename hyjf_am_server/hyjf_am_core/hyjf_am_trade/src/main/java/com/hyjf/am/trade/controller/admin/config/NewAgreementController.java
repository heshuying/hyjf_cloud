/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.config;

import com.hyjf.am.bean.app.NewAgreementResultBean;
import com.hyjf.am.response.config.AppReapyCalendarResponse;
import com.hyjf.am.trade.service.front.config.NewAgreementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dangzw
 * @version NewAgreementController, v0.1 2018/7/31 17:38
 */
@RestController
@RequestMapping("/am-tradenew/new/agreement")
public class NewAgreementController {

    @Autowired
    private NewAgreementService newAgreenmentService;

    /**
     * 获得 协议模板pdf显示地址
     * @param aliasName
     * @return
     */
    @RequestMapping(value = "/setProtocolImg/{aliasName}")
    public AppReapyCalendarResponse setProtocolImg(@PathVariable String aliasName){
        AppReapyCalendarResponse response = new AppReapyCalendarResponse();
        NewAgreementResultBean newAgreenmentServiceList = newAgreenmentService.setProtocolImg(aliasName);
        if (newAgreenmentServiceList != null){
            BeanUtils.copyProperties(newAgreenmentServiceList, response);
        }
        return response;
    }
}
