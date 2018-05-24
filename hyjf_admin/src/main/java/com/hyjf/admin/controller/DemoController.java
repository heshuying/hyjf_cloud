package com.hyjf.admin.controller;

import com.hyjf.admin.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version DemoController, v0.1 2018/5/23 10:56
 */
@RestController
@RequestMapping("/")
public class DemoController {

    @Autowired
    Api api;


    @RequestMapping("/demo")
    public String get(){
        api.test();
        return "success";
    }
}
