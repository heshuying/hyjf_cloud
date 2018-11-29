/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.Event;
import com.hyjf.am.config.service.EventService;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.vo.config.EventVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinhui
 * @version EventConfigController, v0.1 2018/11/28 15:27
 */
@RestController
@RequestMapping("/am-config/content/contentevent")
public class EventConfigController {

    @Autowired
    private EventService eventService;

    /**
     * 根据纪事ID 获取纪事内容
     * @param id
     * @return
     * @Author : huanghui
     */
    @ApiOperation(value = "根据ID获取公司纪事详情", notes = "信息披露 - 公司历程")
    @RequestMapping(value = "/getEventDetail/{id}", method = RequestMethod.GET)
    public EventResponse getEventDetail(@PathVariable Integer id) {
        EventResponse response = new EventResponse();
        Event event = eventService.getRecord(id);

        // 数据 存在
        if (event != null) {
            EventVO eventVO = new EventVO();
            BeanUtils.copyProperties(event, eventVO);
            //设置返回结果
            response.setResult(eventVO);
        }
        return response;
    }

}
