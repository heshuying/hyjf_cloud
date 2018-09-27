package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.resquest.api.WrbRegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 风车理财注册
 * @author lisheng
 * @version WrbController, v0.1 2018/9/27 10:02
 */
@RestController
@RequestMapping("/am-user/wrb")
public class WrbController {

    @PostMapping("/register")
    public  IntegerResponse register(@RequestBody WrbRegisterRequest wrbRegisterRequest) {
        IntegerResponse response = new IntegerResponse();

        return response;
    }
}
