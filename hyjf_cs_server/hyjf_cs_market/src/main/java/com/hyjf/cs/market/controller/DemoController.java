package com.hyjf.cs.market.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version DemoController, v0.1 2018/12/13 15:35
 */
@RestController
@RequestMapping("/cs_market")
public class DemoController {

    @RequestMapping("/success/{flag}")
    @HystrixCommand(fallbackMethod = "fail")
    public String success(@PathVariable String flag){
        if("0".equals(flag)){
            throw new RuntimeException("flag is ...");
        }
        return "success";
    }

    public String fail(){
        return "fail";
    }
}
