/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.controller.test;

import com.hyjf.data.market.entity.HtActivityList;
import com.hyjf.data.market.service.IHtActivityListService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingqing
 * @version TestController, v0.1 2019/6/20 14:08
 */
@Api(value = "测试",tags = "测试")
@RestController
@Slf4j
@RequestMapping("/hyjf")
public class TestController {
    @Autowired
    IHtActivityListService iHtActivityListService;

    /**
     * 测试 mybatisplus
     */
    @GetMapping("/test1")
    public HtActivityList testPlus(){
        return iHtActivityListService.selectById();
    }
}
