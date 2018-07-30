/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmDataCollectClient;
import com.hyjf.admin.mq.TestMQ;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: sunpeikai
 * @version: TestController, v0.1 2018/7/16 11:51
 */
@RestController
@RequestMapping(value = "/test")
public class TestController extends BaseController {

    @Autowired
    private TestMQ testMQ;
    @Autowired
    private AmDataCollectClient amDataCollectClient;

    @GetMapping(value = "/testMQ/{userId}")
    public JSONObject testMQ(@PathVariable Integer userId) throws MQException {
        JSONObject jsonObject = new JSONObject();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserId(userId);
        testMQ.messageSend(new MessageContent(MQConstant.FDD_TOPIC,null, UUID.randomUUID().toString(), JSON.toJSONBytes(userInfoVO)));
        return jsonObject;
    }
    @GetMapping(value = "/testMongo")
    public JSONObject testMQ(){
        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }
}
